package io.hexlet.cv.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import io.hexlet.cv.model.User;
import io.hexlet.cv.model.enums.RoleType;
import io.hexlet.cv.model.marketing.Story;
import io.hexlet.cv.repository.StoryRepository;
import io.hexlet.cv.repository.UserRepository;
import io.hexlet.cv.util.JWTUtils;
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

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

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

    @Autowired
    private ObjectMapper objectMapper;

    private Story testStory;
    private User adminUser;

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

        adminUser = new User();
        adminUser.setEmail(ADMIN_EMAIL);
        adminUser.setEncryptedPassword(encoder.encode("admin_password"));
        adminUser.setFirstName("Admin");
        adminUser.setLastName("User");
        adminUser.setRole(RoleType.ADMIN);
        userRepository.save(adminUser);

        testStory = new Story();
        testStory.setTitle("Test Story title");
        testStory.setContent("Test story content for testing purposes");
        testStory.setImageUrl("https://example.com/story-image.jpg");
        testStory.setIsPublished(true);
        testStory.setShowOnHomepage(true);
        testStory.setDisplayOrder(1);
        testStory.setPublishedAt(LocalDateTime.now());

        testStory = storyRepository.save(testStory);

        mockMvc = MockMvcBuilders.webAppContextSetup(wac)
                .apply(springSecurity())
                .build();
    }

    @Test
    public void testObjectMapperConfiguration() {
        assertFalse(objectMapper.isEnabled(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS));
        assertTrue(objectMapper.getRegisteredModuleIds().contains("jackson-datatype-jsr310"));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void testGetAllStories() throws Exception {
        mockMvc.perform(get("/ru/admin/marketing")
                        .param("section", "stories")
                        .header("X-Inertia", "true"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void testGetStoriesList() throws Exception {
        mockMvc.perform(get("/ru/admin/marketing/stories")
                        .header("X-Inertia", "true"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void testGetCreateForm() throws Exception {
        mockMvc.perform(get("/ru/admin/marketing/stories/create")
                        .header("X-Inertia", "true"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void testGetEditForm() throws Exception {
        mockMvc.perform(get("/ru/admin/marketing/stories/{id}/edit", testStory.getId())
                        .header("X-Inertia", "true"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
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

        mockMvc.perform(post("/ru/admin/marketing/stories")
                        .header("X-Inertia", "true")
                        .contentType("application/json")
                        .content(storyJson))
                .andExpect(status().isFound());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
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

        mockMvc.perform(put("/ru/admin/marketing/stories/{id}", testStory.getId())
                        .header("X-Inertia", "true")
                        .contentType("application/json")
                        .content(storyJson))
                .andDo(print())
                .andExpect(status().isSeeOther());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void testDeleteStory() throws Exception {
        mockMvc.perform(delete("/ru/admin/marketing/stories/{id}", testStory.getId())
                        .header("X-Inertia", "true"))
                .andExpect(status().isSeeOther());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void testTogglePublishStory() throws Exception {
        mockMvc.perform(post("/ru/admin/marketing/stories/{id}/toggle-publish", testStory.getId())
                        .header("X-Inertia", "true"))
                .andExpect(status().isFound());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void testGetHomeComponents() throws Exception {
        mockMvc.perform(get("/ru/admin/marketing/home-components")
                        .header("X-Inertia", "true"))
                .andExpect(status().isOk());
    }
}
