//package io.hexlet.cv.controller;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import io.hexlet.cv.dto.user.RegistrationRequestDTO;
//import io.hexlet.cv.mapper.PageSectionMapper;
//import io.hexlet.cv.model.User;
//import io.hexlet.cv.model.enums.RoleType;
//import io.hexlet.cv.repository.PageSectionRepository;
//import org.hamcrest.Matchers;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.openapitools.jackson.nullable.JsonNullable;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.MediaType;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.setup.MockMvcBuilders;
//import org.springframework.web.context.WebApplicationContext;
//
//import java.nio.charset.StandardCharsets;
//
//import static org.hamcrest.Matchers.hasKey;
//import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.flash;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//
//@SpringBootTest
//@AutoConfigureMockMvc
//public class PageSectionControllerTest {
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @Autowired
//    private WebApplicationContext wac;
//
//    @Autowired
//    private PageSectionRepository pageSectionRepository;
//
//    @Autowired
//    private ObjectMapper om;
//
//    @Autowired
//    private PageSectionMapper pageSectionMapper;
//
//    @BeforeEach
//    public void setUp() {
//
//        pageSectionRepository.deleteAll();
//
//        mockMvc = MockMvcBuilders.webAppContextSetup(wac).defaultResponseCharacterEncoding(StandardCharsets.UTF_8)
//            .apply(springSecurity()).build();
//    }
//
//    @Test
//    public void testShow() throws Exception {
//
//        var response = mockMvc.perform(get("/ru/" + testTask.getId())
//                .with(adminToken))
//            .andExpect(status().isOk())
//            .andReturn()
//            .getResponse();
//
//        assertThatJson(response.getContentAsString()).and(
//            v -> v.node("assignee_id").isEqualTo(testUser.getId()),
//            v -> v.node("status").isEqualTo(testTaskStatus.getSlug())
//        );
//    }
//
//    @Test
//    public void testIndex() throws Exception {
//
//        var response = mockMvc.perform(get("/api/tasks")
//                .with(adminToken))
//            .andExpect(status().isOk())
//            .andReturn()
//            .getResponse();
//
//        assertThat(response.getContentAsString())
//            .contains(String.valueOf(testTask.getAssignee().getId()))
//            .contains(testTask.getName())
//            .contains(testTask.getDescription())
//            .contains(testTask.getTaskStatus().getSlug());
//    }
//
//    @Test
//    public void testCreate() throws Exception {
//
//        taskRepository.delete(testTask);
//
//        var dto = new TaskCreateDTO();
//        dto.setTitle(testTask.getName());
//        dto.setContent(testTask.getDescription());
//        dto.setIndex(testTask.getIndex());
//        dto.setAssigneeId(testTask.getAssignee().getId());
//        dto.setStatus(testTask.getTaskStatus().getSlug());
//
//        mockMvc.perform(post("/api/tasks")
//                .with(adminToken)
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(om.writeValueAsString(dto)))
//            .andExpect(status().isCreated());
//
//        var task = taskRepository.findByName(dto.getTitle()).orElseThrow();
//
//        assertThat(task.getIndex()).isEqualTo(dto.getIndex());
//        assertThat(task.getName()).isEqualTo(dto.getTitle());
//        assertThat(task.getDescription()).isEqualTo(dto.getContent());
//        assertThat(task.getTaskStatus().getSlug()).isEqualTo(dto.getStatus());
//        assertThat(task.getAssignee().getId()).isEqualTo(dto.getAssigneeId());
//    }
//
//    @Test
//    public void testUpdate() throws Exception {
//
//        var dto = new TaskUpdateDTO();
//        dto.setTitle(JsonNullable.of("some title"));
//        dto.setContent(JsonNullable.of("some content"));
//        dto.setStatus(JsonNullable.of("draft"));
//
//        mockMvc.perform(put("/api/tasks/" + testTask.getId())
//                .with(adminToken)
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(om.writeValueAsString(dto)))
//            .andExpect(status().isOk());
//
//        var task = taskRepository.findByName(dto.getTitle().get()).orElseThrow();
//
//        assertThat(task.getName()).isEqualTo(dto.getTitle().get());
//        assertThat(task.getDescription()).isEqualTo(dto.getContent().get());
//        assertThat(task.getTaskStatus().getSlug()).isEqualTo(dto.getStatus().get());
//    }
//
//    @Test
//    public void testDelete() throws Exception {
//
//        mockMvc.perform(delete("/api/tasks/" + testTask.getId())
//                .with(adminToken))
//            .andExpect(status().isNoContent());
//
//        assertThat(taskRepository.existsById(testTask.getId())).isFalse();
//    }
//
//    // Inertia тесты ---------
//    // исключение при регистрации цже имеющегося в базе юзера
//    // проверяем что 303, что редирект и что флэш сообщения передаются
//    @Test
//
//    void testInertiaDuplicateEmail() throws Exception {
//        var existing = new User();
//        existing.setEmail("test@google.com");
//        existing.setFirstName("testFirstName");
//        existing.setLastName("testLastName");
//        existing.setEncryptedPassword("password");
//        existing.setRole(RoleType.CANDIDATE);
//
//        userRepository.save(existing);
//
//        var dto = new RegistrationRequestDTO();
//        dto.setEmail("test@google.com");
//        dto.setPassword("1234qwery");
//        dto.setFirstName("testFirstName");
//        dto.setLastName("testLastName");
//
//        //  POST
//        mockMvc.perform(post("/ru/users")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(om.writeValueAsString(dto))
//                .header("X-Inertia", "true")
//                .header("Referer", "/ru/users/sign_up"))
//            .andExpect(status().isSeeOther())
//            // что у нас редирект на этот путь идет
//            .andExpect(header().string("Location", "/ru/users/sign_up"))
//            // и что во флэш атрибутах должна приходить ошибка по дублированию email - значить флэши проходят
//            .andExpect(flash().attributeExists("errors"))
//            .andExpect(flash().attribute("errors", hasKey("email")))
//            .andReturn();
//    }
//
//
//    @Test
//    void testInertiaSimplePassword() throws Exception {
//        // имя совпадает
//        var dto = new RegistrationRequestDTO();
//        dto.setEmail("test@gmail.com");
//        dto.setPassword("firstName");
//        dto.setFirstName("firstName");
//        dto.setLastName("lastName");
//
//        mockMvc.perform(post("/ru/users")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(om.writeValueAsString(dto))
//                .header("X-Inertia", "true")
//                .header("Referer", "/ru/users/sign_up"))
//            .andExpect(status().isSeeOther())
//            .andExpect(header().string("Location", "/ru/users/sign_up"))
//            // и что во флэш атрибутах должна приходить ошибка по дублированию
//            // password - значить флэши проходят
//            .andExpect(flash().attributeExists("errors"))
//            .andExpect(flash().attribute("errors", hasKey("password")))
//            .andReturn();
//    }
//
//    @Test
//    void testInertiaNotValidShortPassword() throws Exception {
//        var dto = new RegistrationRequestDTO();
//        dto.setEmail("test@gmail.com");
//        dto.setPassword("test_p");
//        dto.setFirstName("firstName");
//        dto.setLastName("lastName");
//
//        mockMvc.perform(post("/ru/users")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(om.writeValueAsString(dto))
//                .header("X-Inertia", "true")
//                .header("Referer", "/ru/users/sign_up"))
//            .andExpect(status().isSeeOther())
//            .andExpect(header().string("Location", "/ru/users/sign_up"))
//            .andExpect(flash().attributeExists("errors"))
//            .andExpect(flash().attribute("errors", hasKey("password")))
//            .andReturn();
//    }
//
//    @Test
//    void testInertiaNotCorrectEmail() throws Exception {
//        var dto = new RegistrationRequestDTO();
//        dto.setEmail("testgmail.com");
//        dto.setPassword("test_pass_123");
//        dto.setFirstName("firstName");
//        dto.setLastName("lastName");
//
//        mockMvc.perform(post("/ru/users")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(om.writeValueAsString(dto))
//                .header("X-Inertia", "true")
//                .header("Referer", "/ru/users/sign_up"))
//            .andExpect(status().isSeeOther())
//            .andExpect(header().string("Location", "/ru/users/sign_up"))
//            .andExpect(flash().attributeExists("errors"))
//            .andExpect(flash().attribute("errors", hasKey("email")))
//            .andReturn();
//    }
//
//    @Test
//    void testInertiaNonExistentEmail() throws Exception {
//        var dto = new RegistrationRequestDTO();
//        dto.setEmail("test@goopmal.com");
//        dto.setPassword("test_pass_123");
//        dto.setFirstName("firstName");
//        dto.setLastName("lastName");
//
//        mockMvc.perform(post("/ru/users")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(om.writeValueAsString(dto))
//                .header("X-Inertia", "true")
//                .header("Referer", "/ru/users/sign_up"))
//            .andExpect(status().isSeeOther())
//            .andExpect(header().string("Location", "/ru/users/sign_up"))
//            .andExpect(flash().attributeExists("errors"))
//            .andExpect(flash().attribute("errors", hasKey("email")))
//            .andReturn();
//    }
//
//    @Test
//    void testInertiaDisposableEmail() throws Exception {
//        var dto = new RegistrationRequestDTO();
//        dto.setEmail("test@sharklasers.com");
//        dto.setPassword("test_password123");
//        dto.setFirstName("firstName");
//        dto.setLastName("lastName");
//
//        mockMvc.perform(post("/ru/users")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(om.writeValueAsString(dto))
//                .header("X-Inertia", "true")
//                .header("Referer", "/ru/users/sign_up"))
//            .andExpect(status().isSeeOther())
//            .andExpect(header().string("Location", "/ru/users/sign_up"))
//            .andExpect(flash().attributeExists("errors"))
//            .andExpect(flash().attribute("errors", hasKey("email")))
//            .andReturn();
//    }
//
//    @Test
//    void testInertiaRegistrationUserCookies() throws Exception {
//        var dto = new RegistrationRequestDTO();
//        dto.setEmail("test@gmail.com");
//        dto.setPassword("test_password");
//        dto.setFirstName("firstName");
//        dto.setLastName("lastName");
//
//        mockMvc.perform(post("/ru/users")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(om.writeValueAsString(dto))
//                .header("X-Inertia", "true")
//                .header("Referer", "/ru/users/sign_up"))
//            .andExpect(status().isSeeOther())
//            .andExpect(header().string("Location", "/ru/dashboard"))
//            .andExpect(flash().attributeCount(0))
//            .andExpect(header().stringValues(HttpHeaders.SET_COOKIE,
//                Matchers.hasItem(Matchers.containsString("access_token"))))
//            .andExpect(header().stringValues(HttpHeaders.SET_COOKIE,
//                Matchers.hasItem(Matchers.containsString("refresh_token"))));
//    }
//}
