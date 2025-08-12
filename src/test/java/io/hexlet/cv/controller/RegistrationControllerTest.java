package io.hexlet.cv.controller;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.hexlet.cv.dto.registration.RegInputDTO;
import io.hexlet.cv.mapper.RegistrationMapper;
import io.hexlet.cv.model.enums.RoleType;
import io.hexlet.cv.repository.UserRepository;
import java.nio.charset.StandardCharsets;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@SpringBootTest
@AutoConfigureMockMvc
public class RegistrationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext wac;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ObjectMapper om;

    @Autowired
    private RegistrationMapper registrationMapper;

    @AfterEach
    public void garbageDbDelete() {
        userRepository.deleteAll();
    }

    @BeforeEach
    public void setUp() {

        userRepository.deleteAll();

        mockMvc = MockMvcBuilders.webAppContextSetup(wac).defaultResponseCharacterEncoding(StandardCharsets.UTF_8)
                .apply(springSecurity()).build();
    }

    // если все поля валидные то создастся пользователь -----------------
    @Test
    public void testCreateUser() throws Exception {
        var data = new RegInputDTO();
        data.setEmail("test@gmail.com");
        data.setPassword("test_password");
        data.setFirstName("firstName");
        data.setLastName("lastName");

        var request = post("/users/registration").contentType(MediaType.APPLICATION_JSON)
                .content(om.writeValueAsString(data));

        mockMvc.perform(request).andExpect(status().isCreated());

        var user = userRepository.findByEmail(data.getEmail()).orElse(null);
        assertNotNull(user);
        assertThat(user.getFirstName()).isEqualTo(data.getFirstName());
        assertThat(user.getLastName()).isEqualTo(data.getLastName());
        assertNotNull(user.getId());
    }

    // email одноразовый------------
    @Test
    public void testDisposableEmail() throws Exception {
        var data = new RegInputDTO();
        data.setEmail("test@sharklasers.com");
        data.setPassword("test_pass_123");
        data.setFirstName("firstName");
        data.setLastName("lastName");

        var request = post("/users/registration").contentType(MediaType.APPLICATION_JSON)
                .content(om.writeValueAsString(data));

        mockMvc.perform(request).andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.errors.email").value("Запрещено использовать одноразовые email"));
    }

    // если юзер уже есть в базе, email не уникальный- то 409 ------------
    @Test
    public void testEmailPresentInDB() throws Exception {
        var data = new RegInputDTO();
        data.setEmail("test@gmail.com");
        data.setPassword("test_password123");
        data.setFirstName("firstName");
        data.setLastName("lastName");

        var newUserData = registrationMapper.map(data);
        newUserData.setEncryptedPassword("123456");
        newUserData.setRole(RoleType.CANDIDATE);

        userRepository.save(newUserData);

        var request = post("/users/registration").contentType(MediaType.APPLICATION_JSON)
                .content(om.writeValueAsString(data));

        mockMvc.perform(request).andExpect(status().isConflict());
    }

    // проверим что вообще отвечает MainPageController - потом можно убрать
    @Test
    public void testIndex() throws Exception {
        mockMvc.perform(get("/")).andExpect(status().isOk()).andReturn().getResponse();
    }

    // email несуществующий домен------------
    @Test
    public void testNonExistentEmail() throws Exception {
        var data = new RegInputDTO();
        data.setEmail("test@gmail.su");
        data.setPassword("test_pass_123");
        data.setFirstName("firstName");
        data.setLastName("lastName");

        var request = post("/users/registration").contentType(MediaType.APPLICATION_JSON)
                .content(om.writeValueAsString(data));

        mockMvc.perform(request).andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.errors.email").value("Домен в email не существует"));
    }

    // email введен неправильно ------------
    @Test
    public void testNotCorrectEmail() throws Exception {
        var data = new RegInputDTO();
        data.setEmail("testgmail.com");
        data.setPassword("test_pass_123");
        data.setFirstName("firstName");
        data.setLastName("lastName");

        var request = post("/users/registration").contentType(MediaType.APPLICATION_JSON)
                .content(om.writeValueAsString(data));

        mockMvc.perform(request).andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.errors.email").value("Укажите корректный email-адрес"));
    }
    // email с односимвольным TLD ------------
    // проверять нет смысла потому что валидация через запрос в
    // https://cloudflare-dns.com/ отсечет такие email
    // смысл только если запрос не пройдет
    // !!!!!!!!!!!!!!
    /*
    @Test
    public void testNotCorrectTLDEmail() throws Exception {
        var data = new RegInputDTO(); data.setEmail("test@gmail.a");
        data.setPassword("test_pass_123"); data.setFirstName("firstName");
        data.setLastName("lastName");

        var request = post("/users/registration")
                .contentType(MediaType.APPLICATION_JSON)
                .content(om.writeValueAsString(data));

        mockMvc.perform(request) .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.errors.email")
                        .value("TLD email должен содержать как минимум 2 символа")); //
     // .value("Домен в email не существует"));
    }
*/

    // если пароль менее 8 символов - невалидный пароль ------------
    @Test
    public void testNotValidShortPassword() throws Exception {
        var data = new RegInputDTO();
        data.setEmail("test@gmail.com");
        data.setPassword("test_p");
        data.setFirstName("firstName");
        data.setLastName("lastName");

        var request = post("/users/registration").contentType(MediaType.APPLICATION_JSON)
                .content(om.writeValueAsString(data));

        mockMvc.perform(request).andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.errors.password").value("Пароль должен быть не менее 8 символов"));
    }

    // если пароль похож на имя фамилию email - то 400 ------------
    @Test
    public void testSimplePassword() throws Exception {
        // имя совпадает
        var data = new RegInputDTO();
        data.setEmail("test@gmail.com");
        data.setPassword("firstName");
        data.setFirstName("firstName");
        data.setLastName("lastName");

        var request = post("/users/registration").contentType(MediaType.APPLICATION_JSON)
                .content(om.writeValueAsString(data));

        mockMvc.perform(request).andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON)).andExpect(jsonPath("$.errors.password")
                        .value("Пароль слишком простой — не должен совпадать с email или именем"));

        // фамилия совпадает
        data.setEmail("test@gmail.com");
        data.setPassword("lastName");
        data.setFirstName("firstName");
        data.setLastName("lastName");

        request = post("/users/registration").contentType(MediaType.APPLICATION_JSON)
                .content(om.writeValueAsString(data));

        mockMvc.perform(request).andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON)).andExpect(jsonPath("$.errors.password")
                        .value("Пароль слишком простой — не должен совпадать с email или именем"));
        // email совпадает
        data.setEmail("test@gmail.com");
        data.setPassword("test@gmail.com");
        data.setFirstName("firstName");
        data.setLastName("lastName");

        request = post("/users/registration").contentType(MediaType.APPLICATION_JSON)
                .content(om.writeValueAsString(data));

        mockMvc.perform(request).andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON)).andExpect(jsonPath("$.errors.password")
                        .value("Пароль слишком простой — не должен совпадать с email или именем"));
    }
}
