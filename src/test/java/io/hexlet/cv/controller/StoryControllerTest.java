package io.hexlet.cv.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import io.hexlet.cv.model.User;
import io.hexlet.cv.model.admin.marketing.Story;
import io.hexlet.cv.model.enums.RoleType;
import io.hexlet.cv.repository.StoryRepository;
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
public class StoryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext wac;

    @Autowired
    private StoryRepository storyRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JWTUtils jwtUtils;

    @Autowired
    private BCryptPasswordEncoder encoder;

    private Story testStory;
    private String adminToken;

    private static final String ADMIN_EMAIL = "admin@example.com";

    @AfterEach
    public void cleanUp() {
        storyRepository.deleteAll();
        userRepository.deleteAll();
    }

    @BeforeEach
    public void setUp() {
        storyRepository.deleteAll();
        userRepository.deleteAll();

        var adminUser = new User();
        adminUser.setEmail(ADMIN_EMAIL);
        adminUser.setEncryptedPassword(encoder.encode("admin_password"));
        adminUser.setFirstName("Admin");
        adminUser.setLastName("User");
        adminUser.setRole(RoleType.ADMIN);
        userRepository.save(adminUser);

        adminToken = jwtUtils.generateAccessToken(ADMIN_EMAIL);

        testStory = new Story();
        testStory.setTitle("Test Story title");
        testStory.setContent("Test story content for testing purposes");
        testStory.setImageUrl("https://example.com/story-image.jpg");
        testStory.setIsPublished(true);
        testStory.setShowOnHomepage(true);
        testStory.setDisplayOrder(1);
        testStory.setPublishedAt(LocalDateTime.now());

        testStory = storyRepository.save(testStory);

    }

    @Test
    public void testGetStoriesSection() throws Exception {
        mockMvc.perform(get("/admin/marketing/stories")
                        .cookie(new Cookie("access_token", adminToken))
                        .header("X-Inertia", "true"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.props.activeMainSection").value("marketing"))
                .andExpect(jsonPath("$.props.activeSubSection").value("stories"))
                .andExpect(jsonPath("$.props.stories").isArray())
                .andExpect(jsonPath("$.props.pageTitle").value("Истории"));
    }


    @Test
    public void testGetCreateForm() throws Exception {
        mockMvc.perform(get("/admin/marketing/stories/create")
                        .cookie(new Cookie("access_token", adminToken))
                        .header("X-Inertia", "true"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.props.activeMainSection").value("marketing"))
                .andExpect(jsonPath("$.props.activeSubSection").value("stories"));
    }

    @Test
    public void testGetEditForm() throws Exception {
        mockMvc.perform(get("/admin/marketing/stories/{id}/edit", testStory.getId())
                        .cookie(new Cookie("access_token", adminToken))
                        .header("X-Inertia", "true"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.props.activeMainSection").value("marketing"))
                .andExpect(jsonPath("$.props.activeSubSection").value("stories"))
                .andExpect(jsonPath("$.props.story.id").value(testStory.getId()));
    }

    @Test
    public void testCreateStory() throws Exception {
        String storyJson = """
            {
                "title": "New Story",
                "content": "New story content",
                "image_url": "https://example.com/image.jpg",
                "is_published": false,
                "show_on_homepage": true,
                "display_order": 2
            }
            """;

        mockMvc.perform(post("/admin/marketing/stories")
                        .cookie(new Cookie("access_token", adminToken))
                        .header("X-Inertia", "true")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(storyJson))
                .andExpect(status().isFound())
                .andExpect(header().string("Location", "/admin/marketing/stories"));
    }

    @Test
    public void testUpdateStory() throws Exception {
        String storyJson = """
            {
                "title": "Updated Story Title",
                "content": "Updated story content",
                "image_url": "https://example.com/updated-image.jpg",
                "is_published": true,
                "show_on_homepage": false,
                "display_order": 5
            }
            """;

        mockMvc.perform(put("/admin/marketing/stories/{id}", testStory.getId())
                        .cookie(new Cookie("access_token", adminToken))
                        .header("X-Inertia", "true")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(storyJson))
                .andExpect(status().isSeeOther())
                .andExpect(header().string("Location", "/admin/marketing/stories"));
    }

    @Test
    public void testDeleteStory() throws Exception {
        mockMvc.perform(delete("/admin/marketing/stories/{id}", testStory.getId())
                        .cookie(new Cookie("access_token", adminToken))
                        .header("X-Inertia", "true"))
                .andExpect(status().isSeeOther())
                .andExpect(header().string("Location", "/admin/marketing/stories"));
    }

    @Test
    public void testTogglePublishStory() throws Exception {
        mockMvc.perform(post("/admin/marketing/stories/{id}/toggle-publish", testStory.getId())
                        .cookie(new Cookie("access_token", adminToken))
                        .header("X-Inertia", "true"))
                .andExpect(status().isFound())
                .andExpect(header().string("Location", "/admin/marketing/stories"));
    }


    @Test
    public void testToggleStoryHomepage() throws Exception {
        mockMvc.perform(post("/admin/marketing/stories/{id}/toggle-homepage", testStory.getId())
                        .cookie(new Cookie("access_token", adminToken))
                        .header("X-Inertia", "true"))
                .andExpect(status().isFound())
                .andExpect(header().string("Location", "/admin/marketing/home-components"));
    }

    @Test
    public void testUpdateStoryDisplayOrder() throws Exception {
        String json = "{\"display_order\": 5}";

        mockMvc.perform(put("/admin/marketing/stories/{id}/display-order", testStory.getId())
                        .cookie(new Cookie("access_token", adminToken))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetHomeComponentsSection() throws Exception {
        mockMvc.perform(get("/admin/marketing/home-components")
                        .cookie(new Cookie("access_token", adminToken))
                        .header("X-Inertia", "true"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.props.activeMainSection").value("marketing"))
                .andExpect(jsonPath("$.props.activeSubSection").value("home-components"))
                .andExpect(jsonPath("$.props.pageTitle").value("Компоненты главной"))
                .andExpect(jsonPath("$.props.stories").exists());
    }

    @Test
    public void testAccessAsNonAdmin() throws Exception {
        User candidate = new User();
        candidate.setEmail("candidate@example.com");
        candidate.setEncryptedPassword(encoder.encode("password"));
        candidate.setRole(RoleType.CANDIDATE);
        userRepository.save(candidate);

        String candidateToken = jwtUtils.generateAccessToken("candidate@example.com");

        mockMvc.perform(get("/admin/marketing/stories")
                        .cookie(new Cookie("access_token", candidateToken))
                        .header("X-Inertia", "true"))
                .andExpect(status().isForbidden());
    }
}
