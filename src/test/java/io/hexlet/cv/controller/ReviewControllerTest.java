package io.hexlet.cv.controller;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.hexlet.cv.model.User;
import io.hexlet.cv.model.enums.RoleType;
import io.hexlet.cv.model.marketing.Review;
import io.hexlet.cv.repository.ReviewRepository;
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

    @Autowired
    private ObjectMapper objectMapper;

    private Review testReview;
    private User adminUser;

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
        adminUser = new User();
        adminUser.setEmail(ADMIN_EMAIL);
        adminUser.setEncryptedPassword(encoder.encode("admin_password"));
        adminUser.setFirstName("Admin");
        adminUser.setLastName("User");
        adminUser.setRole(RoleType.ADMIN);
        userRepository.save(adminUser);

        testReview = new Review();
        testReview.setAuthor("Test Author");
        testReview.setContent("Test review content for testing purposes");
        testReview.setAvatarUrl("https://example.com/avatar.jpg");
        testReview.setIsPublished(true);
        testReview.setShowOnHomepage(true);
        testReview.setDisplayOrder(1);
        testReview.setPublishedAt(LocalDateTime.now());

        testReview = reviewRepository.save(testReview);

        mockMvc = MockMvcBuilders.webAppContextSetup(wac)
                .apply(springSecurity())
                .build();
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void testGetAllReviews() throws Exception {
        mockMvc.perform(get("/ru/admin/marketing")
                        .param("section", "reviews")
                        .header("X-Inertia", "true"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void testGetReviewsList() throws Exception {
        mockMvc.perform(get("/ru/admin/marketing/reviews")
                        .header("X-Inertia", "true"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void testGetCreateForm() throws Exception {
        mockMvc.perform(get("/ru/admin/marketing/reviews/create")
                        .header("X-Inertia", "true"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void testGetEditForm() throws Exception {
        mockMvc.perform(get("/ru/admin/marketing/reviews/{id}/edit", testReview.getId())
                        .header("X-Inertia", "true"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
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
                        .header("X-Inertia", "true")
                        .contentType("application/json")
                        .content(reviewJson))
                .andExpect(status().isFound());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
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
                        .header("X-Inertia", "true")
                        .contentType("application/json")
                        .content(reviewJson))
                .andDo(print())
                .andExpect(status().isSeeOther());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void testDeleteReview() throws Exception {
        mockMvc.perform(delete("/ru/admin/marketing/reviews/{id}", testReview.getId())
                        .header("X-Inertia", "true"))
                .andExpect(status().isSeeOther());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void testTogglePublishReview() throws Exception {
        mockMvc.perform(post("/ru/admin/marketing/reviews/{id}/toggle-publish", testReview.getId())
                        .header("X-Inertia", "true"))
                .andExpect(status().isFound());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void testToggleHomepageReview() throws Exception {
        mockMvc.perform(post("/ru/admin/marketing/reviews/{id}/toggle-homepage", testReview.getId())
                        .header("X-Inertia", "true"))
                .andExpect(status().isFound());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void testUpdateReviewDisplayOrder() throws Exception {
        String json = "{\"display_order\": 3}";

        mockMvc.perform(put("/ru/admin/marketing/reviews/{id}/display-order", testReview.getId())
                        .header("X-Inertia", "true")
                        .contentType("application/json")
                        .content(json))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void testGetHomeComponents() throws Exception {
        mockMvc.perform(get("/ru/admin/marketing/home-components")
                        .header("X-Inertia", "true"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void testUpdateReviewValidation() throws Exception {
        String invalidReviewJson = """
                {
                    "author": "Author Name",
                    "content": "",
                    "show_on_homepage": false
                }
                """;

        mockMvc.perform(put("/ru/admin/marketing/reviews/{id}", testReview.getId())
                        .header("X-Inertia", "true")
                        .contentType("application/json")
                        .content(invalidReviewJson))
                .andDo(print())
                .andExpect(status().isSeeOther());
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
