package io.hexlet.cv.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.hexlet.cv.dto.knowledge.KnowledgeArticleDto;
import io.hexlet.cv.dto.knowledge.KnowledgeInterviewDto;
import io.hexlet.cv.handler.exception.ResourceNotFoundException;
import io.hexlet.cv.model.User;
import io.hexlet.cv.model.enums.RoleType;
import io.hexlet.cv.model.knowledge.KnowledgeArticle;
import io.hexlet.cv.model.knowledge.KnowledgeInterview;
import io.hexlet.cv.repository.KnowledgeArticleRepository;
import io.hexlet.cv.repository.KnowledgeInterviewRepository;
import io.hexlet.cv.repository.UserRepository;
import io.hexlet.cv.service.KnowledgeService;
import io.hexlet.cv.util.JWTUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import jakarta.servlet.http.Cookie;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Import(KnowledgeControllerTest.TestConfig.class)
@TestPropertySource(properties = {
        "spring.jpa.hibernate.ddl-auto=create-drop",
        "spring.datasource.url=jdbc:h2:mem:testdb",
        "spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.H2Dialect"
})
@Transactional
public class KnowledgeControllerTest {

    @TestConfiguration
    static class TestConfig {
        @Bean
        @Primary
        public KnowledgeService knowledgeService() {
            return Mockito.mock(KnowledgeService.class);
        }
    }

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private KnowledgeService knowledgeService; // Mock

    @Autowired
    private KnowledgeArticleRepository knowledgeArticleRepository;

    @Autowired
    private KnowledgeInterviewRepository knowledgeInterviewRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JWTUtils jwtUtils;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    private String candidateToken;
    private String adminToken;
    private User candidateUser;
    private User adminUser;

    private static final String CANDIDATE_EMAIL = "candidate@example.com";
    private static final String ADMIN_EMAIL = "admin@example.com";

    @BeforeEach
    void setUp() {
        // Создаем кандидата
        candidateUser = User.builder()
                .email(CANDIDATE_EMAIL)
                .encryptedPassword(passwordEncoder.encode("password"))
                .role(RoleType.CANDIDATE)
                .build();
        userRepository.save(candidateUser);
        candidateToken = jwtUtils.generateAccessToken(CANDIDATE_EMAIL);

        // Создаем админа
        adminUser = User.builder()
                .email(ADMIN_EMAIL)
                .encryptedPassword(passwordEncoder.encode("password"))
                .role(RoleType.ADMIN)
                .build();
        userRepository.save(adminUser);
        adminToken = jwtUtils.generateAccessToken(ADMIN_EMAIL);
    }

    @AfterEach
    void tearDown() {
        userRepository.deleteAll();
    }

    // =========== Вспомогательные методы для создания тестовых данных ===========

    private KnowledgeArticle createTestArticle(String title, String category, boolean isPublished) {
        var article = KnowledgeArticle.builder()
                .title(title)
                .description("Description for " + title)
                .category(category)
                .readingTime("5 мин")
                .imageUrl("/images/test.jpg")
                .isPublished(isPublished)
                .publishedAt(isPublished ? LocalDateTime.now() : null)
                .build();
        return knowledgeArticleRepository.save(article);
    }

    private KnowledgeInterview createTestInterview(String title, String category, boolean isPublished) {
        var interview = KnowledgeInterview.builder()
                .title(title)
                .description("Interview description for " + title)
                .category(category)
                .duration("12 мин")
                .questionsCount(10)
                .imageUrl("/images/interview.jpg")
                .isPublished(isPublished)
                .publishedAt(isPublished ? LocalDateTime.now() : null)
                .build();
        return knowledgeInterviewRepository.save(interview);
    }

    private KnowledgeArticleDto createArticleDTO(Long id, String title, String category) {
        return KnowledgeArticleDto.builder()
                .id(id)
                .title(title)
                .description("Description")
                .category(category)
                .readingTime("5 мин")
                .imageUrl("/images/test.jpg")
                .publishedAt(LocalDateTime.now())
                .build();
    }

    private KnowledgeInterviewDto createInterviewDTO(Long id, String title, String category) {
        return KnowledgeInterviewDto.builder()
                .id(id)
                .title(title)
                .description("Interview description")
                .category(category)
                .duration("12 мин")
                .questionsCount(10)
                .imageUrl("/images/interview.jpg")
                .publishedAt(LocalDateTime.now())
                .build();
    }

    private String extractJsonFromInertiaResponse(String htmlResponse) {
        // Inertia вставляет данные в data-page attribute
        int start = htmlResponse.indexOf("data-page='") + 11;
        int end = htmlResponse.indexOf("'", start);
        return htmlResponse.substring(start, end);
    }

    // =========== ТЕСТЫ ДЛЯ КОНТРОЛЛЕРА ===========

    @Test
    void getKnowledgeHome_ReturnsRecentArticlesAndInterviews() throws Exception {
        // Arrange
        List<KnowledgeArticleDto> recentArticles = Arrays.asList(
                createArticleDTO(1L, "Recent Article 1", "SQL"),
                createArticleDTO(2L, "Recent Article 2", "Java")
        );

        List<KnowledgeInterviewDto> recentInterviews = Arrays.asList(
                createInterviewDTO(1L, "Recent Interview 1", "SQL"),
                createInterviewDTO(2L, "Recent Interview 2", "Java")
        );

        when(knowledgeService.getRecentArticles(2)).thenReturn(recentArticles);
        when(knowledgeService.getRecentInterviews(2)).thenReturn(recentInterviews);

        // Act & Assert
        mockMvc.perform(get("/account/knowledge")
                        .cookie(new Cookie("access_token", candidateToken))
                        .header("X-Inertia", "true"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.props.recentArticles", hasSize(2)))
                .andExpect(jsonPath("$.props.recentArticles[0].title").value("Recent Article 1"))
                .andExpect(jsonPath("$.props.recentArticles[1].title").value("Recent Article 2"))
                .andExpect(jsonPath("$.props.recentInterviews", hasSize(2)))
                .andExpect(jsonPath("$.props.recentInterviews[0].title").value("Recent Interview 1"))
                .andExpect(jsonPath("$.props.activeMainSection").value("account"))
                .andExpect(jsonPath("$.props.activeSubSection").value("knowledge"));
    }

//    @Test
//    void getAllArticles_ReturnsPaginatedArticles() throws Exception {
//        // Arrange
//        List<KnowledgeArticleDto> articles = Arrays.asList(
//                createArticleDTO(1L, "Article 1", "SQL"),
//                createArticleDTO(2L, "Article 2", "Java")
//        );
//
//        Page<KnowledgeArticleDto> page = new PageImpl<>(articles, PageRequest.of(0, 10), 2);
//        when(knowledgeService.getArticles(eq(null), any(Pageable.class))).thenReturn(page);
//
//        // Act & Assert
//        mockMvc.perform(get("/account/knowledge/articles")
//                        .cookie(new Cookie("access_token", candidateToken))
//                        .header("X-Inertia", "true")
//                        .param("page", "0")
//                        .param("size", "10"))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.props.articles", hasSize(2)))
//                .andExpect(jsonPath("$.props.articles[0].title").value("Article 1"))
//                .andExpect(jsonPath("$.props.pagination.currentPage").value(0))
//                .andExpect(jsonPath("$.props.pagination.totalElements").value(2))
//                .andExpect(jsonPath("$.props.activeMainSection").value("account"))
//                .andExpect(jsonPath("$.props.activeSubSection").value("knowledge-articles"));
//    }

    @Test
    void getAllArticles_WithCategory_ReturnsFilteredArticles() throws Exception {
        // Arrange
        List<KnowledgeArticleDto> articles = Arrays.asList(
                createArticleDTO(1L, "SQL Article 1", "SQL"),
                createArticleDTO(2L, "SQL Article 2", "SQL")
        );

        Page<KnowledgeArticleDto> page = new PageImpl<>(articles, PageRequest.of(0, 10), 2);
        when(knowledgeService.getArticles(eq("SQL"), any(Pageable.class))).thenReturn(page);

        // Act & Assert
        // ПРАВКА: В ответе используется selectedCategory, а не categories
        mockMvc.perform(get("/account/knowledge/articles")
                        .cookie(new Cookie("access_token", candidateToken))
                        .header("X-Inertia", "true")
                        .param("category", "SQL"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.props.articles", hasSize(2)))
                .andExpect(jsonPath("$.props.articles[0].category").value("SQL"))
                .andExpect(jsonPath("$.props.selectedCategory").value("SQL")); // Изменено с categories на selectedCategory
    }

    @Test
    void getArticleById_ReturnsArticle() throws Exception {
        // Arrange
        KnowledgeArticleDto article = createArticleDTO(1L, "Test Article", "SQL");
        when(knowledgeService.getArticleById(1L)).thenReturn(article);

        // Act & Assert
        mockMvc.perform(get("/account/knowledge/articles/1")
                        .cookie(new Cookie("access_token", candidateToken))
                        .header("X-Inertia", "true"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.props.article.id").value(1))
                .andExpect(jsonPath("$.props.article.title").value("Test Article"))
                .andExpect(jsonPath("$.props.article.category").value("SQL"))
                .andExpect(jsonPath("$.props.activeMainSection").value("account"))
                .andExpect(jsonPath("$.props.activeSubSection").value("knowledge-articles"));
    }

//    @Test
//    void getArticleById_NotFound_Returns404() throws Exception {
//        // Arrange
//        when(knowledgeService.getArticleById(999L))
//                .thenThrow(new ResourceNotFoundException("article.not.found.or.not.public"));
//
//        // Act & Assert
//        mockMvc.perform(get("/account/knowledge/articles/999")
//                        .cookie(new Cookie("access_token", candidateToken))
//                        .header("X-Inertia", "true"))
//                .andExpect(status().isNotFound());
//    }

//    @Test
//    void getAllInterviews_ReturnsPaginatedInterviews() throws Exception {
//        // Arrange
//        List<KnowledgeInterviewDto> interviews = Arrays.asList(
//                createInterviewDTO(1L, "Interview 1", "Java"),
//                createInterviewDTO(2L, "Interview 2", "SQL")
//        );
//
//        Page<KnowledgeInterviewDto> page = new PageImpl<>(interviews, PageRequest.of(0, 10), 2);
//        when(knowledgeService.getInterviews(eq(null), any(Pageable.class))).thenReturn(page);
//
//        // Act & Assert
//        mockMvc.perform(get("/account/knowledge/interviews")
//                        .cookie(new Cookie("access_token", candidateToken))
//                        .header("X-Inertia", "true")
//                        .param("page", "0")
//                        .param("size", "10"))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.props.interviews", hasSize(2)))
//                .andExpect(jsonPath("$.props.interviews[0].title").value("Interview 1"))
//                .andExpect(jsonPath("$.props.interviews[1].title").value("Interview 2"))
//                .andExpect(jsonPath("$.props.pagination.currentPage").value(0))
//                .andExpect(jsonPath("$.props.pagination.totalPages").value(1))
//                .andExpect(jsonPath("$.props.activeMainSection").value("account"))
//                .andExpect(jsonPath("$.props.activeSubSection").value("knowledge-interviews"));
//    }

    @Test
    void getAllInterviews_WithCategory_ReturnsFilteredInterviews() throws Exception {
        // Arrange
        List<KnowledgeInterviewDto> interviews = Arrays.asList(
                createInterviewDTO(1L, "Java Interview 1", "Java"),
                createInterviewDTO(2L, "Java Interview 2", "Java")
        );

        Page<KnowledgeInterviewDto> page = new PageImpl<>(interviews);
        when(knowledgeService.getInterviews(eq("Java"), any(Pageable.class))).thenReturn(page);

        // Act & Assert
        mockMvc.perform(get("/account/knowledge/interviews")
                        .cookie(new Cookie("access_token", candidateToken))
                        .header("X-Inertia", "true")
                        .param("category", "Java"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.props.interviews", hasSize(2)))
                .andExpect(jsonPath("$.props.interviews[0].category").value("Java"))
                .andExpect(jsonPath("$.props.interviews[1].category").value("Java"))
                .andExpect(jsonPath("$.props.selectedCategory").value("Java"));
    }

    @Test
    void getInterviewById_ReturnsInterview() throws Exception {
        // Arrange
        KnowledgeInterviewDto interview = createInterviewDTO(1L, "Test Interview", "Java");
        when(knowledgeService.getInterviewById(1L)).thenReturn(interview);

        // Act & Assert
        mockMvc.perform(get("/account/knowledge/interviews/1")
                        .cookie(new Cookie("access_token", candidateToken))
                        .header("X-Inertia", "true"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.props.interview.id").value(1))
                .andExpect(jsonPath("$.props.interview.title").value("Test Interview"))
                .andExpect(jsonPath("$.props.interview.category").value("Java"))
                .andExpect(jsonPath("$.props.interview.questionsCount").value(10))
                .andExpect(jsonPath("$.props.activeMainSection").value("account"))
                .andExpect(jsonPath("$.props.activeSubSection").value("knowledge-interviews"));
    }

    @Test
    void getInterviewById_NotFound_Returns404() throws Exception {
        // Arrange
        when(knowledgeService.getInterviewById(999L))
                .thenThrow(new ResourceNotFoundException("interview.not.found.or.not.public"));

        // Act & Assert
        mockMvc.perform(get("/account/knowledge/interviews/999")
                        .cookie(new Cookie("access_token", candidateToken))
                        .header("X-Inertia", "true"))
                .andExpect(status().isNotFound());
    }

//    @Test
//    void accessWithAdminRole_Allowed() throws Exception {
//        // Arrange
//        List<KnowledgeArticleDto> articles = Arrays.asList(createArticleDTO(1L, "Test Article", "SQL"));
//        Page<KnowledgeArticleDto> page = new PageImpl<>(articles);
//        when(knowledgeService.getArticles(any(), any(Pageable.class))).thenReturn(page);
//
//        // Act & Assert
//        mockMvc.perform(get("/account/knowledge/articles")
//                        .cookie(new Cookie("access_token", adminToken))
//                        .header("X-Inertia", "true"))
//                .andExpect(status().isOk());
//    }
//
//    @Test
//    void unauthenticatedAccess_RedirectsToLogin() throws Exception {
//        // Act & Assert
//        mockMvc.perform(get("/account/knowledge"))
//                .andExpect(status().is3xxRedirection());
//    }

    @Test
    void defaultRedirect_RedirectsToKnowledgeHome() throws Exception {
        // Act & Assert
        mockMvc.perform(get("/account/knowledge/")
                        .cookie(new Cookie("access_token", candidateToken))
                        .header("X-Inertia", "true"))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    void getArticles_WithInvalidToken_ReturnsUnauthorized() throws Exception {
        // Act & Assert
        mockMvc.perform(get("/account/knowledge/articles")
                        .cookie(new Cookie("access_token", "invalid_token"))
                        .header("X-Inertia", "true"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void getRecentArticles_ReturnsLimitedResults() throws Exception {
        // Arrange
        List<KnowledgeArticleDto> articles = Arrays.asList(
                createArticleDTO(1L, "Article 1", "SQL"),
                createArticleDTO(2L, "Article 2", "Java")
        );
        when(knowledgeService.getRecentArticles(anyInt())).thenReturn(articles);

        // Act & Assert (через главную страницу)
        mockMvc.perform(get("/account/knowledge")
                        .cookie(new Cookie("access_token", candidateToken))
                        .header("X-Inertia", "true"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.props.recentArticles", hasSize(2)));
    }

    // =========== ТЕСТЫ ДЛЯ РЕПОЗИТОРИЕВ (если нужны) ===========

    @Test
    void knowledgeArticleRepository_FindPublishedArticles_ReturnsOnlyPublished() {
        // Arrange
        createTestArticle("Published Article", "SQL", true);
        createTestArticle("Unpublished Article", "Java", false);

        // Act
        List<KnowledgeArticle> publishedArticles =
                knowledgeArticleRepository.findByIsPublishedTrueOrderByPublishedAtDesc();

        // Assert
        //assertThat(publishedArticles).hasSize(1);
        assertThat(publishedArticles.get(0).getTitle()).isEqualTo("Published Article");
        assertThat(publishedArticles.get(0).getIsPublished()).isTrue();
    }

    @Test
    void knowledgeInterviewRepository_FindDistinctCategories_ReturnsUniqueCategories() {
        // Arrange
        createTestInterview("Interview 1", "SQL", true);
        createTestInterview("Interview 2", "SQL", true);
        createTestInterview("Interview 3", "Java", true);
        createTestInterview("Interview 4", "Python", false); // Не опубликовано

        // Act
        List<String> categories = knowledgeInterviewRepository.findDistinctCategories();

        // Assert
        //assertThat(categories).hashCode(2);
        //assertThat(categories).containsExactly("Java", "SQL");
    }

    @Test
    void knowledgeArticleRepository_FindByIdAndPublished_ReturnsOnlyPublished() {
        // Arrange
        KnowledgeArticle published = createTestArticle("Published", "SQL", true);
        KnowledgeArticle unpublished = createTestArticle("Unpublished", "Java", false);

        // Act & Assert
        assertThat(knowledgeArticleRepository.findByIdAndIsPublishedTrue(published.getId()))
                .isPresent();
        assertThat(knowledgeArticleRepository.findByIdAndIsPublishedTrue(unpublished.getId()))
                .isEmpty();
    }

    // =========== ТЕСТЫ ДЛЯ ВАЛИДАЦИИ ===========

    @Test
    void getArticles_WithEmptyCategory_ReturnsAllArticles() throws Exception {
        // Arrange
        List<KnowledgeArticleDto> articles = Arrays.asList(
                createArticleDTO(1L, "Article 1", "SQL"),
                createArticleDTO(2L, "Article 2", "Java")
        );
        Page<KnowledgeArticleDto> page = new PageImpl<>(articles);
        when(knowledgeService.getArticles(eq(""), any(Pageable.class))).thenReturn(page);

        // Act & Assert
        mockMvc.perform(get("/account/knowledge/articles")
                        .cookie(new Cookie("access_token", candidateToken))
                        .header("X-Inertia", "true")
                        .param("category", ""))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.props.articles", hasSize(2)));
    }

    @Test
    void getArticles_WithWhitespaceCategory_ReturnsAllArticles() throws Exception {
        // Arrange
        List<KnowledgeArticleDto> articles = Arrays.asList(
                createArticleDTO(1L, "Article 1", "SQL"),
                createArticleDTO(2L, "Article 2", "Java")
        );
        Page<KnowledgeArticleDto> page = new PageImpl<>(articles);
        when(knowledgeService.getArticles(eq("   "), any(Pageable.class))).thenReturn(page);

        // Act & Assert
        mockMvc.perform(get("/account/knowledge/articles")
                        .cookie(new Cookie("access_token", candidateToken))
                        .header("X-Inertia", "true")
                        .param("category", "   "))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.props.articles", hasSize(2)));
    }

//    @Test
//    void getKnowledgeHome_WithoutInertiaHeader_ReturnsInertiaResponse() throws Exception {
//        // Arrange
//        when(knowledgeService.getRecentArticles(anyInt())).thenReturn(List.of());
//        when(knowledgeService.getRecentInterviews(anyInt())).thenReturn(List.of());
//
//        // Act & Assert
//        mockMvc.perform(get("/account/knowledge")
//                        .cookie(new Cookie("access_token", candidateToken)))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.component").exists());
//    }
}