package io.hexlet.cv.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import io.hexlet.cv.model.User;
import io.hexlet.cv.model.enums.RoleType;
import io.hexlet.cv.model.marketing.Review;
import io.hexlet.cv.repository.ReviewRepository;
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
public class ReviewControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext wac;

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JWTUtils jwtUtils;

    @Autowired
    private BCryptPasswordEncoder encoder;

    private Review testReview;
    private String adminToken;

    private static final String ADMIN_EMAIL = "admin@example.com";

    @AfterEach
    public void cleanUp() {
        reviewRepository.deleteAll();
        userRepository.deleteAll();
    }

    @BeforeEach
    public void setUp() {
        reviewRepository.deleteAll();
        userRepository.deleteAll();

        var adminUser = new User();
        adminUser.setEmail(ADMIN_EMAIL);
        adminUser.setEncryptedPassword(encoder.encode("admin_password"));
        adminUser.setFirstName("Admin");
        adminUser.setLastName("User");
        adminUser.setRole(RoleType.ADMIN);
        userRepository.save(adminUser);

        adminToken = jwtUtils.generateAccessToken(ADMIN_EMAIL);

        testReview = new Review();
        testReview.setAuthor("Test Author");
        testReview.setContent("Test review content for testing purposes");
        testReview.setAvatarUrl("https://example.com/avatar.jpg");
        testReview.setIsPublished(true);
        testReview.setShowOnHomepage(true);
        testReview.setDisplayOrder(1);
        testReview.setPublishedAt(LocalDateTime.now());

        testReview = reviewRepository.save(testReview);
    }

    @Test
    public void testGetReviewsSection() throws Exception {
        mockMvc.perform(get("/ru/admin/marketing/reviews")
                        .cookie(new Cookie("access_token", adminToken))
                        .header("X-Inertia", "true"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.props.activeMainSection").value("marketing"))
                .andExpect(jsonPath("$.props.activeSubSection").value("reviews"))
                .andExpect(jsonPath("$.props.reviews").isArray())
                .andExpect(jsonPath("$.props.pageTitle").value("Отзывы"));
    }

    @Test
    public void testGetCreateForm() throws Exception {
        mockMvc.perform(get("/ru/admin/marketing/reviews/create")
                        .cookie(new Cookie("access_token", adminToken))
                        .header("X-Inertia", "true"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.props.activeMainSection").value("marketing"))
                .andExpect(jsonPath("$.props.activeSubSection").value("reviews"));
    }

    @Test
    public void testGetEditForm() throws Exception {
        mockMvc.perform(get("/ru/admin/marketing/reviews/{id}/edit", testReview.getId())
                        .cookie(new Cookie("access_token", adminToken))
                        .header("X-Inertia", "true"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.props.activeMainSection").value("marketing"))
                .andExpect(jsonPath("$.props.activeSubSection").value("reviews"))
                .andExpect(jsonPath("$.props.review.id").value(testReview.getId()));
    }

    @Test
    public void testCreateReview() throws Exception {
        String reviewJson = """
            {
                "author": "New Review Author",
                "content": "New review content",
                "avatar_url": "https://example.com/new-avatar.jpg",
                "is_published": false,
                "show_on_homepage": true,
                "display_order": 2
            }
            """;

        mockMvc.perform(post("/ru/admin/marketing/reviews")
                        .cookie(new Cookie("access_token", adminToken))
                        .header("X-Inertia", "true")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(reviewJson))
                .andExpect(status().isFound())
                .andExpect(header().string("Location", "/ru/admin/marketing/reviews"));
    }

    @Test
    public void testUpdateReview() throws Exception {
        String reviewJson = """
            {
                "author": "Updated Author",
                "content": "Updated review content",
                "avatar_url": "https://example.com/updated-avatar.jpg",
                "is_published": true,
                "show_on_homepage": false,
                "display_order": 5
            }
            """;

        mockMvc.perform(put("/ru/admin/marketing/reviews/{id}", testReview.getId())
                        .cookie(new Cookie("access_token", adminToken))
                        .header("X-Inertia", "true")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(reviewJson))
                .andExpect(status().isSeeOther())
                .andExpect(header().string("Location", "/ru/admin/marketing/reviews"));
    }

    @Test
    public void testDeleteReview() throws Exception {
        mockMvc.perform(delete("/ru/admin/marketing/reviews/{id}", testReview.getId())
                        .cookie(new Cookie("access_token", adminToken))
                        .header("X-Inertia", "true"))
                .andExpect(status().isSeeOther())
                .andExpect(header().string("Location", "/ru/admin/marketing/reviews"));
    }


    @Test
    public void testTogglePublishReview() throws Exception {
        mockMvc.perform(post("/ru/admin/marketing/reviews/{id}/toggle-publish", testReview.getId())
                        .cookie(new Cookie("access_token", adminToken))
                        .header("X-Inertia", "true"))
                .andExpect(status().isFound())
                .andExpect(header().string("Location", "/ru/admin/marketing/reviews"));
    }

    @Test
    public void testToggleHomepageReview() throws Exception {
        mockMvc.perform(post("/ru/admin/marketing/reviews/{id}/toggle-homepage", testReview.getId())
                        .cookie(new Cookie("access_token", adminToken))
                        .header("X-Inertia", "true"))
                .andExpect(status().isFound())
                .andExpect(header().string("Location", "/ru/admin/marketing/home-components"));
    }


    @Test
    public void testUpdateReviewDisplayOrder() throws Exception {
        String json = "{\"display_order\": 3}";

        mockMvc.perform(put("/ru/admin/marketing/reviews/{id}/display-order", testReview.getId())
                        .cookie(new Cookie("access_token", adminToken))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetHomeComponentsSection() throws Exception {
        mockMvc.perform(get("/ru/admin/marketing/home-components")
                        .cookie(new Cookie("access_token", adminToken))
                        .header("X-Inertia", "true"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.props.activeMainSection").value("marketing"))
                .andExpect(jsonPath("$.props.activeSubSection").value("home-components"))
                .andExpect(jsonPath("$.props.pageTitle").value("Компоненты главной"))
                .andExpect(jsonPath("$.props.reviews").exists());
    }

    @Test
    public void testAccessAsNonAdmin() throws Exception {
        // Создаем пользователя с ролью CANDIDATE
        User candidate = new User();
        candidate.setEmail("candidate@example.com");
        candidate.setEncryptedPassword(encoder.encode("password"));
        candidate.setRole(RoleType.CANDIDATE);
        userRepository.save(candidate);

        String candidateToken = jwtUtils.generateAccessToken("candidate@example.com");

        mockMvc.perform(get("/ru/admin/marketing/reviews")
                        .cookie(new Cookie("access_token", candidateToken))
                        .header("X-Inertia", "true"))
                .andExpect(status().isForbidden());
    }

    @Test
    public void testUnauthorizedAccess() throws Exception {
        // Тест на неавторизованный доступ
        mockMvc.perform(get("/ru/admin/marketing/reviews")
                        .header("X-Inertia", "true"))
                .andDo(print())
                .andExpect(status().isUnauthorized());
    }
}
