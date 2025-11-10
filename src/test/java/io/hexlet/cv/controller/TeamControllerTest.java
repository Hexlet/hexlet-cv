package io.hexlet.cv.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import io.hexlet.cv.model.User;
import io.hexlet.cv.model.enums.RoleType;
import io.hexlet.cv.model.marketing.Team;
import io.hexlet.cv.repository.TeamRepository;
import io.hexlet.cv.repository.UserRepository;
import io.hexlet.cv.util.JWTUtils;
import jakarta.servlet.http.Cookie;
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
    private JWTUtils jwtUtils;

    @Autowired
    private BCryptPasswordEncoder encoder;

    private Team testTeam;
    private String adminToken;

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

        var adminUser = new User();
        adminUser.setEmail(ADMIN_EMAIL);
        adminUser.setEncryptedPassword(encoder.encode("admin_password"));
        adminUser.setFirstName("Admin");
        adminUser.setLastName("User");
        adminUser.setRole(RoleType.ADMIN);
        userRepository.save(adminUser);

        adminToken = jwtUtils.generateAccessToken(ADMIN_EMAIL);

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

    }

    @Test
    public void testGetTeamSection() throws Exception {
        mockMvc.perform(get("/ru/admin/marketing/team")
                        .cookie(new Cookie("access_token", adminToken))
                        .header("X-Inertia", "true"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.props.activeMainSection").value("marketing"))
                .andExpect(jsonPath("$.props.activeSubSection").value("team"))
                .andExpect(jsonPath("$.props.team").isArray())
                .andExpect(jsonPath("$.props.pageTitle").value("Команда"));
    }

    @Test
    public void testGetCreateForm() throws Exception {
        mockMvc.perform(get("/ru/admin/marketing/team/create")
                        .cookie(new Cookie("access_token", adminToken))
                        .header("X-Inertia", "true"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.props.activeMainSection").value("marketing"))
                .andExpect(jsonPath("$.props.activeSubSection").value("team"));
    }

    @Test
    public void testGetEditForm() throws Exception {
        mockMvc.perform(get("/ru/admin/marketing/team/{id}/edit", testTeam.getId())
                        .cookie(new Cookie("access_token", adminToken))
                        .header("X-Inertia", "true"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.props.activeMainSection").value("marketing"))
                .andExpect(jsonPath("$.props.activeSubSection").value("team"))
                .andExpect(jsonPath("$.props.teamMember.id").value(testTeam.getId()));
    }

    @Test
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
                        .cookie(new Cookie("access_token", adminToken))
                        .header("X-Inertia", "true")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(teamJson))
                .andExpect(status().isFound())
                .andExpect(header().string("Location", "/ru/admin/marketing/team"));
    }

    @Test
    public void testUpdateTeamMember() throws Exception {
        String teamJson = """
                {
                    "first_name": "Updated",
                    "last_name": "Name",
                    "site_role": "Updated Role",
                    "system_role": "Updated System Role",
                    "avatar_url": "https://example.com/updated-avatar.jpg",
                    "is_published": true,
                    "show_on_homepage": false,
                    "display_order": 5
                }
                """;

        mockMvc.perform(put("/ru/admin/marketing/team/{id}", testTeam.getId())
                        .cookie(new Cookie("access_token", adminToken))
                        .header("X-Inertia", "true")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(teamJson))
                .andExpect(status().isSeeOther())
                .andExpect(header().string("Location", "/ru/admin/marketing/team"));
    }


    @Test
    public void testDeleteTeamMember() throws Exception {
        mockMvc.perform(delete("/ru/admin/marketing/team/{id}", testTeam.getId())
                        .cookie(new Cookie("access_token", adminToken))
                        .header("X-Inertia", "true"))
                .andExpect(status().isSeeOther())
                .andExpect(header().string("Location", "/ru/admin/marketing/team"));
    }


    @Test
    public void testTogglePublishTeam() throws Exception {
        mockMvc.perform(post("/ru/admin/marketing/team/{id}/toggle-publish", testTeam.getId())
                        .cookie(new Cookie("access_token", adminToken))
                        .header("X-Inertia", "true"))
                .andExpect(status().isFound())
                .andExpect(header().string("Location", "/ru/admin/marketing/team"));
    }

    @Test
    public void testToggleHomepageTeam() throws Exception {
        mockMvc.perform(post("/ru/admin/marketing/team/{id}/toggle-homepage", testTeam.getId())
                        .cookie(new Cookie("access_token", adminToken))
                        .header("X-Inertia", "true"))
                .andExpect(status().isFound())
                .andExpect(header().string("Location", "/ru/admin/marketing/home-components"));
    }

    @Test
    public void testUpdateTeamDisplayOrder() throws Exception {
        String json = "{\"display_order\": 3}";

        mockMvc.perform(put("/ru/admin/marketing/team/{id}/display-order", testTeam.getId())
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
                .andExpect(jsonPath("$.props.team").exists());
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

        mockMvc.perform(get("/ru/admin/marketing/team")
                        .cookie(new Cookie("access_token", candidateToken))
                        .header("X-Inertia", "true"))
                .andExpect(status().isForbidden());
    }

    @Test
    public void testCreateTeamMemberInvalidData() throws Exception {
        String invalidTeamJson = """
                {
                    "first_name": "",
                    "last_name": ""
                }
                """;

        mockMvc.perform(post("/ru/admin/marketing/team")
                        .cookie(new Cookie("access_token", adminToken))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(invalidTeamJson))
                .andExpect(status().isUnprocessableEntity());
    }
}
