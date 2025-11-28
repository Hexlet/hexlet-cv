package io.hexlet.cv.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.hexlet.cv.dto.admin.WebinarDTO;
import io.hexlet.cv.model.User;
import io.hexlet.cv.model.enums.RoleType;
import io.hexlet.cv.model.webinars.Webinar;
import io.hexlet.cv.repository.UserRepository;
import io.hexlet.cv.repository.WebinarRepository;
import io.hexlet.cv.util.JWTUtils;
import jakarta.servlet.http.Cookie;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
public class AdminWebinarsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebinarRepository webinarRepository;

    @Autowired
    private ObjectMapper om;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JWTUtils jwtUtils;

    @Autowired
    private BCryptPasswordEncoder encoder;

    private String adminToken;

    private static final String ADMIN_EMAIL = "admin@example.com";
    private static final String ADMIN_PASSWORD = "test_admin_pass_001";

    @AfterEach
    public void cleanUp() {
        webinarRepository.deleteAll();
        userRepository.deleteAll();
    }

    @BeforeEach
    public void setUp() {
        webinarRepository.deleteAll();
        userRepository.deleteAll();

        var adminUser = new User();
        adminUser.setEmail(ADMIN_EMAIL);
        adminUser.setEncryptedPassword(encoder.encode(ADMIN_PASSWORD));
        adminUser.setFirstName("Admin");
        adminUser.setLastName("User");
        adminUser.setRole(RoleType.ADMIN);
        userRepository.save(adminUser);

        adminToken = jwtUtils.generateAccessToken(ADMIN_EMAIL);


        Webinar w001 = new Webinar();

        w001.setWebinarName("Webinar 0001");
        w001.setWebinarRegLink("http://example_reg001.com");
        w001.setWebinarRecordLink("http://example_record002.com");
        w001.setWebinarDate(
                LocalDateTime.parse("2001-01-02 14:30", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"))
        );
        webinarRepository.save(w001);

        Webinar w002 = new Webinar();
        w002.setWebinarName("Webinar 0002");
        w002.setWebinarRegLink("http://example_reg003.com");
        w002.setWebinarRecordLink("http://example_record004.com");
        w002.setWebinarDate(
                LocalDateTime.parse("2022-08-13 20:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"))
        );
        webinarRepository.save(w002);


    }

    @Test
    public void testCreateWebinar() throws Exception {

        WebinarDTO dto = new WebinarDTO();
        dto.setWebinarName("Test name");
        dto.setWebinarRegLink("https://test_reglink.org");
        dto.setWebinarRecordLink("https://test_recordlink.org");
        dto.setWebinarDate(
                LocalDateTime.parse("2025-10-11 12:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"))
        );
        dto.setFeature(true);
        dto.setPublicated(true);

        mockMvc.perform(post("/admin/webinars/create")
                        .cookie(new Cookie("access_token", adminToken))
                        .header("X-Inertia", "true")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsString(dto)))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/Admin/Webinars/Index"));

        var whatInDB = webinarRepository.findFirstByWebinarName(dto.getWebinarName());
        assertThat(whatInDB).isPresent();
        assertThat(whatInDB.get().getWebinarName()).isEqualTo(dto.getWebinarName());
        assertThat(whatInDB.get().getWebinarRecordLink()).isEqualTo(dto.getWebinarRecordLink());
        assertThat(whatInDB.get().getWebinarRegLink()).isEqualTo(dto.getWebinarRegLink());
        assertThat(whatInDB.get().getWebinarDate()).isEqualTo(dto.getWebinarDate());

    }

    @Test
    public void testUpdateWebinar() throws Exception {

        Webinar existing = webinarRepository.findFirstByOrderByIdAsc()
                .orElseThrow(() -> new IllegalStateException("Нет записей в БД"));

        WebinarDTO dto = new WebinarDTO();
        dto.setWebinarName("Test update name");
        dto.setWebinarRegLink("https://test_reglink_update.org");
        dto.setWebinarRecordLink("https://test_recordlink_update.org");
        dto.setWebinarDate(LocalDateTime.parse("2001-01-01 01:00",
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
        dto.setFeature(true);
        dto.setPublicated(true);

        String endpoint = "/admin/webinars/" + existing.getId() + "/update";

        mockMvc.perform(put(endpoint)
                        .cookie(new Cookie("access_token", adminToken))
                        .header("X-Inertia", "true")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsString(dto)))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/Admin/Webinars/Index"));

        Webinar updated = webinarRepository.findById(existing.getId())
                .orElseThrow(() -> new IllegalStateException("Updated webinar not found"));

        assertThat(updated.getWebinarName()).isEqualTo(dto.getWebinarName());
        assertThat(updated.getWebinarRegLink()).isEqualTo(dto.getWebinarRegLink());
        assertThat(updated.getWebinarRecordLink()).isEqualTo(dto.getWebinarRecordLink());
        assertThat(updated.getWebinarDate()).isEqualTo(dto.getWebinarDate());

        assertThat(webinarRepository.findFirstByWebinarName(existing.getWebinarName())).isEmpty();
    }

    @Test
    public void testDeleteWebinar() throws Exception {
        List<Webinar> existing = webinarRepository.findAll();

        String endpoint = "/admin/webinars/" + existing.get(0).getId() + "/delete";

        mockMvc.perform(delete(endpoint)
                        .cookie(new Cookie("access_token", adminToken))
                        .header("X-Inertia", "true"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/Admin/Webinars/Index"));

        assertThat(webinarRepository.findFirstByWebinarName(existing.get(0).getWebinarName())).isEmpty();
    }

    @Test
    public void testSearchByNameWebinars() throws Exception {

        List<Webinar> existing = webinarRepository.findAll();


        String expectedName = existing.get(0).getWebinarName();
        String searchStr = existing.get(0).getWebinarName().substring(5);

        var mvcResult = mockMvc.perform(get("/admin/webinars")
                        .param("search", searchStr)
                        .cookie(new Cookie("access_token", adminToken))
                        .header("X-Inertia", "true"))
                .andExpect(status().isOk())
                .andReturn();

        String json = mvcResult.getResponse().getContentAsString();
        assertThat(json).contains(expectedName);
        assertThat(json).doesNotContain(existing.get(1).getWebinarName());
    }

    @Test
    public void testSearchByDateWebinars() throws Exception {
        List<Webinar> existing = webinarRepository.findAll();

        String expectedDate = existing.get(1).getWebinarDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));

        var mvcResult = mockMvc.perform(get("/admin/webinars")
                        .param("search", expectedDate)
                        .cookie(new Cookie("access_token", adminToken))
                        .header("X-Inertia", "true"))
                .andExpect(status().isOk())
                .andReturn();

        String json = mvcResult.getResponse().getContentAsString();
        assertThat(json).contains(existing.get(1).getWebinarName());
        assertThat(json).doesNotContain(existing.get(0).getWebinarName());
    }

    @Test
    public void testSearchByUrlWebinars() throws Exception {
        List<Webinar> existing = webinarRepository.findAll();

        String expectedDate = existing.get(0).getWebinarRegLink();

        String searchStr = existing.get(0).getWebinarRegLink().substring(15);

        var mvcResult = mockMvc.perform(get("/admin/webinars")
                        .param("search", searchStr)
                        .cookie(new Cookie("access_token", adminToken))
                        .header("X-Inertia", "true"))
                .andExpect(status().isOk())
                .andReturn();

        String json = mvcResult.getResponse().getContentAsString();
        assertThat(json).contains(existing.get(0).getWebinarName());
        assertThat(json).doesNotContain(existing.get(1).getWebinarName());
    }

}
