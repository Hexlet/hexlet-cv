package io.hexlet.cv.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.hexlet.cv.model.User;
import io.hexlet.cv.model.enums.RoleType;
import io.hexlet.cv.model.marketing.Team;
import io.hexlet.cv.repository.TeamRepository;
import io.hexlet.cv.repository.UserRepository;
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

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class TeamControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext wac;

    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder encoder;

    @Autowired
    private ObjectMapper objectMapper;

    private Team testTeam;
    private User adminUser;

    private static final String ADMIN_EMAIL = "admin@example.com";

    @AfterEach
    public void cleanUp() {
        teamRepository.deleteAll();
        userRepository.deleteAll();
    }

    @BeforeEach
    public void setUp() {
        teamRepository.deleteAll();
        userRepository.deleteAll();

        adminUser = new User();
        adminUser.setEmail(ADMIN_EMAIL);
        adminUser.setEncryptedPassword(encoder.encode("admin_password"));
        adminUser.setFirstName("Admin");
        adminUser.setLastName("User");
        adminUser.setRole(RoleType.ADMIN);
        userRepository.save(adminUser);

        testTeam = new Team();
        testTeam.setFirstName("John");
        testTeam.setLastName("Doe");
        testTeam.setSiteRole("Продакт");
        testTeam.setSystemRole("Наставник");
        testTeam.setAvatarUrl("https://example.com/avatar.jpg");
        testTeam.setIsPublished(true);
        testTeam.setShowOnHomepage(true);
        testTeam.setDisplayOrder(1);

        testTeam = teamRepository.save(testTeam);

        mockMvc = MockMvcBuilders.webAppContextSetup(wac)
                .apply(springSecurity())
                .build();
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void testGetAllTeam() throws Exception {
        mockMvc.perform(get("/ru/admin/marketing")
                        .param("section", "team")
                        .header("X-Inertia", "true"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void testGetTeamList() throws Exception {
        mockMvc.perform(get("/ru/admin/marketing/team")
                        .header("X-Inertia", "true"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void testGetCreateForm() throws Exception {
        mockMvc.perform(get("/ru/admin/marketing/team/create")
                        .header("X-Inertia", "true"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void testGetEditForm() throws Exception {
        mockMvc.perform(get("/ru/admin/marketing/team/{id}/edit", testTeam.getId())
                        .header("X-Inertia", "true"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void testCreateTeamMember() throws Exception {
        String teamJson = """
            {
                "first_name": "Jane",
                "last_name": "Smith",
                "site_role": "Дизайнер",
                "system_role": "Ментор",
                "avatar_url": "https://example.com/jane-avatar.jpg",
                "is_published": false,
                "show_on_homepage": true,
                "display_order": 2
            }
        """;

        mockMvc.perform(post("/ru/admin/marketing/team")
                        .header("X-Inertia", "true")
                        .contentType("application/json")
                        .content(teamJson))
                .andExpect(status().isFound());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void testUpdateTeamMember() throws Exception {
        String teamJson = """
            {
                "first_name": "Updated",
                "last_name": "Name",
                "site_role": "Updated Role",
                "system_role": "Updated System Role",
                "avatar_url": "https://example.com/updated-avatar.jpg",
                "is_published": true,
                "show_homepage": false,
                "display_order": 5
            }
        """;

        mockMvc.perform(put("/ru/admin/marketing/team/{id}", testTeam.getId())
                        .header("X-Inertia", "true")
                        .contentType("application/json")
                        .content(teamJson))
                .andExpect(status().isSeeOther());
    }


    @Test
    @WithMockUser(roles = "ADMIN")
    public void testDeleteTeamMember() throws Exception {
        mockMvc.perform(delete("/ru/admin/marketing/team/{id}", testTeam.getId())
                        .header("X-Inertia", "true"))
                .andExpect(status().isSeeOther());
    }


    @Test
    @WithMockUser(roles = "ADMIN")
    public void testTogglePublishTeam() throws Exception {
        mockMvc.perform(post("/ru/admin/marketing/team/{id}/toggle-publish", testTeam.getId())
                        .header("X-Inertia", "true"))
                .andExpect(status().isFound());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void testToggleHomepageTeam() throws Exception {
        mockMvc.perform(post("/ru/admin/marketing/team/{id}/toggle-homepage", testTeam.getId())
                        .header("X-Inertia", "true"))
                .andExpect(status().isFound());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void testUpdateTeamDisplayOrder() throws Exception {
        String json = "{\"display_order\": 3}";

        mockMvc.perform(put("/ru/admin/marketing/team/{id}/display-order", testTeam.getId())
                        .header("X-Inertia", "true")
                        .contentType("application/json")
                        .content(json))
                .andExpect(status().isOk());
    }
}
