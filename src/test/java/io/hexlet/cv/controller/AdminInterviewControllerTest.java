package io.hexlet.cv.controller;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.hexlet.cv.dto.interview.InterviewCreateDTO;
import io.hexlet.cv.dto.interview.InterviewUpdateDTO;
import io.hexlet.cv.model.Interview;
import io.hexlet.cv.model.User;
import io.hexlet.cv.model.enums.RoleType;
import io.hexlet.cv.repository.InterviewRepository;
import io.hexlet.cv.repository.UserRepository;
import io.hexlet.cv.util.JWTUtils;
import jakarta.servlet.http.Cookie;
import java.time.LocalDateTime;
import java.util.Optional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openapitools.jackson.nullable.JsonNullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

@SpringBootTest
@AutoConfigureMockMvc
class AdminInterviewControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private InterviewRepository interviewRepository;

    @Autowired
    private JWTUtils jwtUtils;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    private static final String ADMIN_EMAIL = "admin@example.com";
    private static final String ADMIN_PASSWORD = "admin_password";
    private static final String ADMIN_FIRSTNAME = "Admin";
    private static final String ADMIN_LASTNAME = "User";
    private static final String SPEAKER_EMAIL = "speaker@example.com";
    private static final String SPEAKER_PASSWORD = "speaker_password";
    private static final String SPEAKER_FIRSTNAME = "John";
    private static final String SPEAKER_LASTNAME = "Doe";

    private static final String TEST_INTERVIEW_TITLE = "Initial Test Interview";
    private static final String TEST_INTERVIEW_VIDEOLINK = "https://example.com/initial";

    private String adminToken;
    private User adminUser;
    private User testSpeaker;
    private Interview testInterview;

    @AfterEach
    void cleanUp() {
        interviewRepository.deleteAll();
        userRepository.deleteAll();
    }

    @BeforeEach
    void setUp() {
        cleanUp();

        // Создаем админа
        adminUser = new User();
        adminUser.setEmail(ADMIN_EMAIL);
        adminUser.setEncryptedPassword(passwordEncoder.encode(ADMIN_PASSWORD));
        adminUser.setRole(RoleType.ADMIN);
        adminUser.setFirstName(ADMIN_FIRSTNAME);
        adminUser.setLastName(ADMIN_LASTNAME);
        adminUser = userRepository.save(adminUser);

        adminToken = jwtUtils.generateAccessToken(ADMIN_EMAIL);

        // Создаем спикера для тестов
        testSpeaker = new User();
        testSpeaker.setEmail(SPEAKER_EMAIL);
        testSpeaker.setEncryptedPassword(passwordEncoder.encode(SPEAKER_PASSWORD));
        testSpeaker.setRole(RoleType.CANDIDATE);
        testSpeaker.setFirstName(SPEAKER_FIRSTNAME);
        testSpeaker.setLastName(SPEAKER_LASTNAME);
        testSpeaker = userRepository.save(testSpeaker);

        // Создаем тестовое интервью
        testInterview = Interview.builder()
                .title(TEST_INTERVIEW_TITLE)
                .speaker(testSpeaker)
                .videoLink(TEST_INTERVIEW_VIDEOLINK)
                .isPublished(true)
                .createdAt(LocalDateTime.now())
                .build();
        testInterview = interviewRepository.save(testInterview);
    }

    @Test
    void shouldCreateInterviewWithoutSpeaker() throws Exception {
        // given
        InterviewCreateDTO createDTO = new InterviewCreateDTO();
        createDTO.setTitle("Interview without speaker");
        createDTO.setVideoLink("https://example.com/video1");
        createDTO.setIsPublished(true);
        // speakerId не устанавливаем он будет null

        // when & then
        mockMvc.perform(post("/ru/admin/interview/create")
                        .cookie(new Cookie("access_token", adminToken))
                        .header("X-Inertia", "true")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createDTO)))
                .andExpect(status().isFound());

        // Проверяем, что интервью создалось в БД
        Optional<Interview> createdInterview = interviewRepository
                .findByTitle("Interview without speaker");
        assertThat(createdInterview).isPresent();
        assertThat(createdInterview.get().getSpeaker()).isNull();
        assertThat(createdInterview.get().getIsPublished()).isTrue();
    }

    @Test
    void shouldCreateInterviewWithSpeaker() throws Exception {
        // given
        InterviewCreateDTO createDTO = new InterviewCreateDTO();
        createDTO.setTitle("Interview with speaker");
        createDTO.setSpeakerId(testSpeaker.getId());
        createDTO.setVideoLink("https://example.com/video2");
        createDTO.setIsPublished(false);

        // when & then
        mockMvc.perform(post("/ru/admin/interview/create")
                        .cookie(new Cookie("access_token", adminToken))
                        .header("X-Inertia", "true")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createDTO)))
                .andExpect(status().isFound());

        // Проверяем в БД
        Optional<Interview> createdInterview = interviewRepository
                .findByTitle("Interview with speaker");
        assertThat(createdInterview).isPresent();
        assertThat(createdInterview.get().getSpeaker().getId())
                .isEqualTo(testSpeaker.getId());
        assertThat(createdInterview.get().getIsPublished()).isFalse();
    }

    @Test
    void shouldUpdateInterviewTitleAndSpeaker() throws Exception {
        // given
        // Создаем нового спикера
        User newSpeaker = new User();
        newSpeaker.setEmail("new_speaker@example.com");
        newSpeaker.setEncryptedPassword(passwordEncoder.encode("password"));
        newSpeaker.setRole(RoleType.CANDIDATE);
        newSpeaker.setFirstName("Jane");
        newSpeaker.setLastName("Smith");
        newSpeaker = userRepository.save(newSpeaker);

        InterviewUpdateDTO updateDTO = new InterviewUpdateDTO();
        updateDTO.setTitle(JsonNullable.of("Updated Title"));
        updateDTO.setSpeakerId(JsonNullable.of(newSpeaker.getId()));
        updateDTO.setVideoLink(JsonNullable.of("https://example.com/updated"));
        updateDTO.setIsPublished(JsonNullable.of(false));

        // when & then
        mockMvc.perform(put("/ru/admin/interview/{id}/edit", testInterview.getId())
                        .cookie(new Cookie("access_token", adminToken))
                        .header("X-Inertia", "true")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateDTO)))
                .andExpect(status().isSeeOther());

        // Проверяем обновление в БД
        Optional<Interview> updatedInterview = interviewRepository
                .findById(testInterview.getId());
        assertThat(updatedInterview).isPresent();
        assertThat(updatedInterview.get().getTitle()).isEqualTo("Updated Title");
        assertThat(updatedInterview.get().getSpeaker().getId())
                .isEqualTo(newSpeaker.getId());
        assertThat(updatedInterview.get().getIsPublished()).isFalse();
    }

    @Test
    void shouldRemoveSpeakerFromInterview() throws Exception {
        // given
        InterviewUpdateDTO updateDTO = new InterviewUpdateDTO();
        // Будем явно удалять спикера
        updateDTO.setSpeakerId(JsonNullable.of(null));

        // when & then
        mockMvc.perform(put("/ru/admin/interview/{id}/edit", testInterview.getId())
                        .cookie(new Cookie("access_token", adminToken))
                        .header("X-Inertia", "true")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateDTO)))
                .andExpect(status().isSeeOther());

        // Проверяем в БД
        Optional<Interview> updatedInterview = interviewRepository
                .findById(testInterview.getId());
        assertThat(updatedInterview).isPresent();
        assertThat(updatedInterview.get().getSpeaker()).isNull();
    }

    @Test
    void shouldDeleteInterview() throws Exception {
        // given
        Long interviewId = testInterview.getId();

        // when & then
        mockMvc.perform(delete("/ru/admin/interview/{id}", interviewId)
                        .cookie(new Cookie("access_token", adminToken))
                        .header("X-Inertia", "true"))
                .andExpect(status().isSeeOther());

        // Проверяем, что интервью удалено из БД
        Optional<Interview> deletedInterview = interviewRepository
                .findById(interviewId);
        assertThat(deletedInterview).isEmpty();
    }

    @Test
    void shouldFindInterviewsBySearchWord() throws Exception {
        // given
        // Создаем еще интервью для поиска
        Interview javaInterview = Interview.builder()
                .title("Java Programming Interview")
                .speaker(testSpeaker)
                .videoLink("https://example.com/java")
                .isPublished(true)
                .createdAt(LocalDateTime.now())
                .build();
        interviewRepository.save(javaInterview);

        Interview pythonInterview = Interview.builder()
                .title("Python for Beginners")
                .speaker(null)
                .videoLink("https://example.com/python")
                .isPublished(true)
                .createdAt(LocalDateTime.now())
                .build();
        interviewRepository.save(pythonInterview);

        // when & then
        MvcResult result = mockMvc.perform(get("/ru/admin/interview")
                        .cookie(new Cookie("access_token", adminToken))
                        .header("X-Inertia", "true")
                        .param("interviewSearchWord", "java"))
                .andExpect(status().isOk())
                .andReturn();

        String content = result.getResponse().getContentAsString();
        // Проверяем, что возвращаются корректные данные
        assertThat(content).contains("Java Programming Interview");
        assertThat(content).contains(testSpeaker.getFirstName());
    }

    @Test
    void shouldGetInterviewById() throws Exception {
        // when & then
        MvcResult result = mockMvc.perform(get("/ru/admin/interview/{id}", testInterview.getId())
                        .cookie(new Cookie("access_token", adminToken))
                        .header("X-Inertia", "true"))
                .andExpect(status().isOk())
                .andReturn();

        String content = result.getResponse().getContentAsString();
        assertThat(content).contains("Initial Test Interview");
        assertThat(content).contains(testSpeaker.getFirstName());
    }

    @Test
    void shouldReturnNotFoundForNonExistentInterview() throws Exception {
        // when & then
        mockMvc.perform(get("/ru/admin/interview/999999")
                        .cookie(new Cookie("access_token", adminToken))
                        .header("X-Inertia", "true"))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldReturnAllInterviewsWhenSearchWordIsEmpty() throws Exception {
        // given
        // Создаем несколько интервью
        for (int i = 1; i <= 3; i++) {
            Interview interview = Interview.builder()
                    .title("Interview " + i)
                    .speaker(i % 2 == 0 ? testSpeaker : null)
                    .videoLink("https://example.com/video" + i)
                    .isPublished(true)
                    .createdAt(LocalDateTime.now())
                    .build();
            interviewRepository.save(interview);
        }

        // when & then
        MvcResult result = mockMvc.perform(get("/ru/admin/interview")
                        .cookie(new Cookie("access_token", adminToken))
                        .header("X-Inertia", "true")
                        // Пустой поиск
                        .param("interviewSearchWord", ""))
                .andExpect(status().isOk())
                .andReturn();

        String content = result.getResponse().getContentAsString();
        // Должны видеть все интервью (1 изначальное + 3 новых = 4)
        assertThat(content).contains("Initial Test Interview");
        assertThat(content).contains("Interview 1");
        assertThat(content).contains("Interview 3");
    }

    @Test
    void shouldPaginateInterviews() throws Exception {
        // given
        // Создаем больше интервью, чем помещается на страницу
        for (int i = 1; i <= 15; i++) {
            Interview interview = Interview.builder()
                    .title("Interview Page " + i)
                    .speaker(testSpeaker)
                    .videoLink("https://example.com/page" + i)
                    .isPublished(true)
                    .createdAt(LocalDateTime.now())
                    .build();
            interviewRepository.save(interview);
        }

        // when & then - запросим вторую страницу
        MvcResult result = mockMvc.perform(get("/ru/admin/interview")
                        .cookie(new Cookie("access_token", adminToken))
                        .header("X-Inertia", "true")
                        // Вторая страница (нумерация с 0)
                        .param("page", "1")
                        // 5 элементов на странице
                        .param("size", "5"))
                .andExpect(status().isOk())
                .andReturn();

        String content = result.getResponse().getContentAsString();
        // Проверяем, что возвращаются данные пагинации
        assertThat(content).contains("\"currentPage\":1");
        // общее количество страниц
        assertThat(content).contains("\"totalPages\":");
    }

    @Test
    void shouldNotAllowAccessWithoutAdminRole() throws Exception {
        // given
        // Создаем пользователя с ролью CANDIDATE
        User candidate = new User();
        candidate.setEmail("candidate@example.com");
        candidate.setEncryptedPassword(passwordEncoder.encode("password"));
        candidate.setRole(RoleType.CANDIDATE);
        candidate = userRepository.save(candidate);

        String candidateToken = jwtUtils.generateAccessToken("candidate@example.com");

        // when & then - кандидат не должен иметь доступ
        mockMvc.perform(get("/ru/admin/interview")
                        .cookie(new Cookie("access_token", candidateToken))
                        .header("X-Inertia", "true"))
                .andExpect(status().isForbidden());
    }

    @Test
    void shouldNotAllowAccessWithoutAuthentication() throws Exception {
        // when & then - доступ без токена должен быть запрещен
        mockMvc.perform(get("/ru/admin/interview")
                        .header("X-Inertia", "true"))
                .andExpect(status().isUnauthorized());
    }
}
