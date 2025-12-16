package io.hexlet.cv.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import io.hexlet.cv.model.User;
import io.hexlet.cv.model.admin.marketing.Article;
import io.hexlet.cv.model.enums.RoleType;
import io.hexlet.cv.repository.ArticleRepository;
import io.hexlet.cv.repository.UserRepository;
import io.hexlet.cv.util.JWTUtils;
import jakarta.servlet.http.Cookie;
import java.time.LocalDateTime;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
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

    private Article testArticle;
    private String adminToken;

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

        var adminUser = new User();
        adminUser = new User();
        adminUser.setEmail(ADMIN_EMAIL);
        adminUser.setEncryptedPassword(encoder.encode("admin_password"));
        adminUser.setFirstName("Admin");
        adminUser.setLastName("User");
        adminUser.setRole(RoleType.ADMIN);
        userRepository.save(adminUser);

        adminToken = jwtUtils.generateAccessToken(ADMIN_EMAIL);

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

    }

    @Test
    public void testGetArticlesSection() throws Exception {
        mockMvc.perform(get("/admin/marketing/articles")
                        .cookie(new Cookie("access_token", adminToken))
                        .header("X-Inertia", "true"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.props.activeMainSection").value("marketing"))
                .andExpect(jsonPath("$.props.activeSubSection").value("articles"))
                .andExpect(jsonPath("$.props.articles").isArray());
    }

    @Test
    public void testGetCreateForm() throws Exception {
        mockMvc.perform(get("/admin/marketing/articles/create")
                        .cookie(new Cookie("access_token", adminToken))
                        .header("X-Inertia", "true"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.props.activeMainSection").value("marketing"))
                .andExpect(jsonPath("$.props.activeSubSection").value("articles"));
    }

    @Test
    public void testGetEditForm() throws Exception {
        mockMvc.perform(get("/admin/marketing/articles/{id}/edit", testArticle.getId())
                        .cookie(new Cookie("access_token", adminToken))
                        .header("X-Inertia", "true"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.props.activeMainSection").value("marketing"))
                .andExpect(jsonPath("$.props.activeSubSection").value("articles"))
                .andExpect(jsonPath("$.props.article.id").value(testArticle.getId()));
    }


    @Test
    public void testCreateArticle() throws Exception {
        String articleJson = """
            {
                "title": "New Article",
                "content": "New content",
                "author": "Test Author",
                "readingTime": 3
            }
            """;

        mockMvc.perform(post("/admin/marketing/articles")
                        .cookie(new Cookie("access_token", adminToken))
                        .header("X-Inertia", "true")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(articleJson))
                .andExpect(status().isFound())
                .andExpect(header().string("Location", "/admin/marketing/articles"));
    }

    @Test
    public void testUpdateArticle() throws Exception {
        String articleJson = """
            {
                "title": "Updated Article",
                "content": "Updated content",
                "author": "Updated Author"
            }
            """;

        mockMvc.perform(put("/admin/marketing/articles/{id}", testArticle.getId())
                        .cookie(new Cookie("access_token", adminToken))
                        .header("X-Inertia", "true")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(articleJson))
                .andExpect(status().isSeeOther())
                .andExpect(header().string("Location", "/admin/marketing/articles"));
    }


    @Test
    public void testDeleteArticle() throws Exception {
        mockMvc.perform(delete("/admin/marketing/articles/{id}", testArticle.getId())
                        .cookie(new Cookie("access_token", adminToken))
                        .header("X-Inertia", "true"))
                .andExpect(status().isSeeOther())
                .andExpect(header().string("Location", "/admin/marketing/articles"));
    }

    @Test
    public void testToggleArticleHomepage() throws Exception {
        mockMvc.perform(post("/admin/marketing/articles/{id}/toggle-homepage", testArticle.getId())
                        .cookie(new Cookie("access_token", adminToken))
                        .header("X-Inertia", "true"))
                .andExpect(status().isFound())
                .andExpect(header().string("Location", "/admin/marketing/home-components"));
    }

    @Test
    public void testTogglePublish() throws Exception {
        mockMvc.perform(post("/admin/marketing/articles/{id}/toggle-publish", testArticle.getId())
                        .cookie(new Cookie("access_token", adminToken))
                        .header("X-Inertia", "true"))
                .andExpect(status().isFound())
                .andExpect(header().string("Location", "/admin/marketing/articles"));
    }

    @Test
    public void testUpdateArticleDisplayOrder() throws Exception {
        String json = "{\"display_order\": 5}";

        mockMvc.perform(put("/admin/marketing/articles/{id}/display-order", testArticle.getId())
                        .cookie(new Cookie("access_token", adminToken))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk());
    }

    @Test
    public void testAccessAsNonAdmin() throws Exception {
        User candidate = new User();
        candidate.setEmail("candidate@example.com");
        candidate.setEncryptedPassword(encoder.encode("password"));
        candidate.setRole(RoleType.CANDIDATE);
        userRepository.save(candidate);

        String candidateToken = jwtUtils.generateAccessToken("candidate@example.com");

        mockMvc.perform(get("/admin/marketing/articles")
                        .cookie(new Cookie("access_token", candidateToken))
                        .header("X-Inertia", "true"))
                .andExpect(status().isForbidden());
    }
}
