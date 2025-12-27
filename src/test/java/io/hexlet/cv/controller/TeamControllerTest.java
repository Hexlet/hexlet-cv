package io.hexlet.cv.controller;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import io.hexlet.cv.model.User;
import io.hexlet.cv.model.admin.marketing.Team;
import io.hexlet.cv.model.enums.RoleType;
import io.hexlet.cv.model.enums.TeamMemberType;
import io.hexlet.cv.model.enums.TeamPosition;
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
        testTeam.setPosition(TeamPosition.PRODUCT);
        testTeam.setMemberType(TeamMemberType.MENTOR);
        testTeam.setAvatarUrl("https://example.com/avatar.jpg");
        testTeam.setIsPublished(true);
        testTeam.setShowOnHomepage(true);
        testTeam.setDisplayOrder(1);
        testTeam = teamRepository.save(testTeam);

    }

    @Test
    public void testGetTeamSection() throws Exception {
        mockMvc.perform(get("/admin/marketing/team")
                        .cookie(new Cookie("access_token", adminToken))
                        .header("X-Inertia", "true"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.props.activeMainSection").value("marketing"))
                .andExpect(jsonPath("$.props.activeSubSection").value("team"))
                .andExpect(jsonPath("$.props.team").isArray());
    }

    @Test
    public void testGetCreateForm() throws Exception {
        mockMvc.perform(get("/admin/marketing/team/create")
                        .cookie(new Cookie("access_token", adminToken))
                        .header("X-Inertia", "true"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.props.activeMainSection").value("marketing"))
                .andExpect(jsonPath("$.props.activeSubSection").value("team"));
    }

    @Test
    public void testGetEditForm() throws Exception {
        mockMvc.perform(get("/admin/marketing/team/{id}/edit", testTeam.getId())
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
                    "firstName": "Jane",
                    "lastName": "Smith",
                    "position": "PRODUCT",
                    "memberType": "MENTOR",
                    "avatarUrl": "https://example.com/jane-avatar.jpg",
                    "isPublished": false,
                    "showOnHomepage": true,
                    "displayOrder": 2
                }
                """;

        mockMvc.perform(post("/admin/marketing/team")
                        .cookie(new Cookie("access_token", adminToken))
                        .header("X-Inertia", "true")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(teamJson))
                .andExpect(status().isFound())
                .andExpect(header().string("Location", "/admin/marketing/team"));
    }

    @Test
    public void testUpdateTeamMember() throws Exception {
        String teamJson = """
                {
                    "firstName": "Updated",
                    "lastName": "Name",
                    "position": "DEVELOPER",
                    "memberType": "ADVISOR",
                    "avatarUrl": "https://example.com/updated-avatar.jpg",
                    "isPublished": true,
                    "showOnHomepage": false,
                    "displayOrder": 5
                }
                """;

        mockMvc.perform(put("/admin/marketing/team/{id}", testTeam.getId())
                        .cookie(new Cookie("access_token", adminToken))
                        .header("X-Inertia", "true")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(teamJson))
                .andExpect(status().isSeeOther())
                .andExpect(header().string("Location", "/admin/marketing/team"));
    }


    @Test
    public void testDeleteTeamMember() throws Exception {
        mockMvc.perform(delete("/admin/marketing/team/{id}", testTeam.getId())
                        .cookie(new Cookie("access_token", adminToken))
                        .header("X-Inertia", "true"))
                .andExpect(status().isSeeOther())
                .andExpect(header().string("Location", "/admin/marketing/team"));
    }


    @Test
    public void testTogglePublishTeam() throws Exception {
        mockMvc.perform(post("/admin/marketing/team/{id}/toggle-publish", testTeam.getId())
                        .cookie(new Cookie("access_token", adminToken))
                        .header("X-Inertia", "true"))
                .andExpect(status().isFound())
                .andExpect(header().string("Location", "/admin/marketing/team"));
    }

    @Test
    public void testToggleHomepageTeam() throws Exception {
        mockMvc.perform(post("/admin/marketing/team/{id}/toggle-homepage", testTeam.getId())
                        .cookie(new Cookie("access_token", adminToken))
                        .header("X-Inertia", "true"))
                .andExpect(status().isFound())
                .andExpect(header().string("Location", "/admin/marketing/home-components"));
    }

    @Test
    public void testUpdateTeamDisplayOrder() throws Exception {
        String json = "{\"display_order\": 3}";

        mockMvc.perform(put("/admin/marketing/team/{id}/display-order", testTeam.getId())
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
                .andExpect(jsonPath("$.props.team").exists());
    }

    @Test
    public void testAccessAsNonAdmin() throws Exception {
        User candidate = new User();
        candidate.setEmail("candidate@example.com");
        candidate.setEncryptedPassword(encoder.encode("password"));
        candidate.setRole(RoleType.CANDIDATE);
        userRepository.save(candidate);

        String candidateToken = jwtUtils.generateAccessToken("candidate@example.com");

        mockMvc.perform(get("/admin/marketing/team")
                        .cookie(new Cookie("access_token", candidateToken))
                        .header("X-Inertia", "true"))
                .andExpect(status().isForbidden());
    }

    @Test
    public void testCreateTeamMemberInvalidData() throws Exception {
        String invalidTeamJson = """
                {
                    "firstName": "",
                    "lastName": "",
                    "position": "INVALID_POSITION",
                    "memberType": "INVALID_TYPE"
                }
                """;

        mockMvc.perform(post("/admin/marketing/team")
                        .cookie(new Cookie("access_token", adminToken))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(invalidTeamJson))
                .andExpect(status().isBadRequest())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors").exists())
                .andExpect(jsonPath("$.errors.error").exists())
                .andExpect(jsonPath("$.errors.error").isString())
                .andExpect(jsonPath("$.errors.error").value(containsString("Invalid value")))
                .andExpect(jsonPath("$.errors.error").value(containsString("position")))
                .andExpect(jsonPath("$.errors.error").value(containsString("Allowed values")));
    }
}
