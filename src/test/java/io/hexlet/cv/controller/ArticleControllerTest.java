package io.hexlet.cv.controller;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import io.hexlet.cv.model.User;
import io.hexlet.cv.model.enums.RoleType;
import io.hexlet.cv.model.marketing.Article;
import io.hexlet.cv.repository.ArticleRepository;
import io.hexlet.cv.repository.UserRepository;
import io.hexlet.cv.util.JWTUtils;
import java.time.LocalDateTime;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@SpringBootTest
@AutoConfigureMockMvc
public class ArticleControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext wac;

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JWTUtils jwtUtils;

    @Autowired
    private BCryptPasswordEncoder encoder;

    @Autowired
    private ObjectMapper objectMapper;

    private Article testArticle;
    private User adminUser;

    private static final String ADMIN_EMAIL = "admin@example.com";

    @AfterEach
    public void cleanUp() {
        articleRepository.deleteAll();
        userRepository.deleteAll();
    }

    @BeforeEach
    public void setUp() {
        articleRepository.deleteAll();
        userRepository.deleteAll();

        adminUser = new User();
        adminUser.setEmail(ADMIN_EMAIL);
        adminUser.setEncryptedPassword(encoder.encode("admin_password"));
        adminUser.setFirstName("Admin");
        adminUser.setLastName("User");
        adminUser.setRole(RoleType.ADMIN);
        userRepository.save(adminUser);

        testArticle = new Article();
        testArticle.setTitle("Test Article title");
        testArticle.setContent("Test article content for testing purposes");
        testArticle.setAuthor("Test Author");
        testArticle.setReadingTime(5);
        testArticle.setIsPublished(true);
        testArticle.setShowOnHomepage(true);
        testArticle.setHomeComponentId("877");
        testArticle.setDisplayOrder(1);
        testArticle.setPublishedAt(LocalDateTime.now());

        testArticle = articleRepository.save(testArticle);

        mockMvc = MockMvcBuilders.webAppContextSetup(wac)
                .apply(springSecurity())
                .build();
    }

    @Test
    public void testObjectMapperConfiguration() {
        // Проверяем что наш ObjectMapper настроен правильно
        assertFalse(objectMapper.isEnabled(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS));

        // Проверяем что модуль JavaTime зарегистрирован
        assertTrue(objectMapper.getRegisteredModuleIds().contains("jackson-datatype-jsr310"));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void testGetAllArticles() throws Exception {
        mockMvc.perform(get("/ru/admin/marketing")
                        .param("section", "articles")
                        .header("X-Inertia", "true"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void testGetCreateForm() throws Exception {
        mockMvc.perform(get("/ru/admin/marketing/articles/create")
                        .header("X-Inertia", "true"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void testGetEditForm() throws Exception {
        mockMvc.perform(get("/ru/admin/marketing/articles/{id}/edit", testArticle.getId())
                        .header("X-Inertia", "true"))
                .andDo(print())
                .andExpect(status().isOk());
    }


    @Test
    @WithMockUser(roles = "ADMIN")
    public void testCreateArticle() throws Exception {
        String articleJson = """
                    {
                        "title": "New Article",
                        "content": "New content"
                    }
                """;

        mockMvc.perform(post("/ru/admin/marketing/articles")
                        .header("X-Inertia", "true")
                        .contentType("application/json")
                        .content(articleJson))
                .andExpect(status().isFound());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void testDeleteArticle() throws Exception {
        mockMvc.perform(delete("/ru/admin/marketing/articles/{id}", testArticle.getId())
                        .header("X-Inertia", "true"))
                .andExpect(status().isSeeOther());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void testToggleArticleHomepage() throws Exception {
        mockMvc.perform(post("/ru/admin/marketing/articles/{id}/toggle-homepage", testArticle.getId())
                        .header("X-Inertia", "true"))
                .andExpect(status().isFound());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void testUpdateArticleDisplayOrder() throws Exception {
        String json = "{\"display_order\": 5}";

        mockMvc.perform(put("/ru/admin/marketing/articles/{id}/display-order", testArticle.getId())
                        .header("X-Inertia", "true")
                        .contentType("application/json")
                        .content(json))
                .andDo(print())
                .andExpect(status().isOk());
    }
}
