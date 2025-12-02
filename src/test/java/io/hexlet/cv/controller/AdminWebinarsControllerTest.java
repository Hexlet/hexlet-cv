package io.hexlet.cv.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.hexlet.cv.controller.builders.WebinarTestBuilder;
import io.hexlet.cv.dto.admin.WebinarDto;
import io.hexlet.cv.mapper.AdminWebinarMapper;
import io.hexlet.cv.model.User;
import io.hexlet.cv.model.enums.RoleType;
import io.hexlet.cv.model.webinars.Webinar;
import io.hexlet.cv.repository.UserRepository;
import io.hexlet.cv.repository.WebinarRepository;
import io.hexlet.cv.util.JWTUtils;
import jakarta.servlet.http.Cookie;
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

    @Autowired
    private AdminWebinarMapper adminWebinarMapper;

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

        webinarRepository.save(
                WebinarTestBuilder.aWebinar()
                        .withName("Webinar 0001")
                        .withDate("2001-01-02 14:30")
                                //,DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")))
                        .withRegLink("http://example_reg001.com")
                        .build()
        );

        webinarRepository.save(
                WebinarTestBuilder.aWebinar()
                        .withName("Webinar 0002")
                        .withDate("2022-08-13 20:00")
                        .withRegLink("http://example_reg003.com")
                        .build()
        );
    }

    @Test
    public void testCreateWebinar() throws Exception {
//given
        var webinar = WebinarTestBuilder.aWebinar()
                .withName("Webinar 123")
                .withRegLink("http://example_new-reg.com")
                .withRecordLink("http://example_new-reg.com")
                .withDate("2025-10-11 12:00")
                .build();
        WebinarDto dto = adminWebinarMapper.map(webinar);
//when
        mockMvc.perform(post("/admin/webinars/create")
                        .cookie(new Cookie("access_token", adminToken))
                        .header("X-Inertia", "true")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsString(dto)))
//then
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
//given
        Webinar existing = webinarRepository.findFirstByOrderByIdAsc()
                .orElseThrow(() -> new IllegalStateException("Нет записей в БД"));

        var webinar = WebinarTestBuilder.aWebinar()
                .withName("Webinar 123")
                .withRegLink("http://example_new-reg.com")
                .withRecordLink("http://example_new-reg.com")
                .withDate("2025-10-11 12:00")
                .build();
        WebinarDto dto = adminWebinarMapper.map(webinar);
//when
        mockMvc.perform(put("/admin/webinars/" + existing.getId() + "/update")
                        .cookie(new Cookie("access_token", adminToken))
                        .header("X-Inertia", "true")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsString(dto)))
//then
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
//given
        List<Webinar> existing = webinarRepository.findAll();
//when
        mockMvc.perform(delete("/admin/webinars/" + existing.get(0).getId() + "/delete")
                        .cookie(new Cookie("access_token", adminToken))
                        .header("X-Inertia", "true"))
//then
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/Admin/Webinars/Index"));

        assertThat(webinarRepository.findFirstByWebinarName(existing.get(0).getWebinarName())).isEmpty();
    }

    @Test
    public void testSearchByNameWebinars() throws Exception {
//given
        List<Webinar> existing = webinarRepository.findAll();

        String expectedName = existing.get(0).getWebinarName();
        String searchStr = existing.get(0).getWebinarName().substring(5);
//when
        var mvcResult = mockMvc.perform(get("/admin/webinars")
                        .param("search", searchStr)
                        .cookie(new Cookie("access_token", adminToken))
                        .header("X-Inertia", "true"))
//then
                .andExpect(status().isOk())
                .andReturn();

        String json = mvcResult.getResponse().getContentAsString();
        assertThat(json).contains(expectedName);
        assertThat(json).doesNotContain(existing.get(1).getWebinarName());
    }

    @Test
    public void testSearchByDateWebinars() throws Exception {
//given
        List<Webinar> existing = webinarRepository.findAll();

        String expectedDate = existing.get(1).getWebinarDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
//when
        var mvcResult = mockMvc.perform(get("/admin/webinars")
                        .param("search", expectedDate)
                        .cookie(new Cookie("access_token", adminToken))
                        .header("X-Inertia", "true"))
//then
                .andExpect(status().isOk())
                .andReturn();

        String json = mvcResult.getResponse().getContentAsString();
        assertThat(json).contains(existing.get(1).getWebinarName());
        assertThat(json).doesNotContain(existing.get(0).getWebinarName());
    }

    @Test
    public void testSearchByUrlWebinars() throws Exception {
//given
        List<Webinar> existing = webinarRepository.findAll();

        String searchStr = existing.get(0).getWebinarRegLink().substring(15);
//when
        var mvcResult = mockMvc.perform(get("/admin/webinars")
                        .param("search", searchStr)
                        .cookie(new Cookie("access_token", adminToken))
                        .header("X-Inertia", "true"))
//then
                .andExpect(status().isOk())
                .andReturn();

        String json = mvcResult.getResponse().getContentAsString();
        assertThat(json).contains(existing.get(0).getWebinarName());
        assertThat(json).doesNotContain(existing.get(1).getWebinarName());
    }

}
