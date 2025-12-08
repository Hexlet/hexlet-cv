package io.hexlet.cv.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyMap;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import io.github.inertia4j.spring.Inertia;
import io.hexlet.cv.dto.interview.InterviewCreateDTO;
import io.hexlet.cv.dto.interview.InterviewDTO;
import io.hexlet.cv.dto.interview.InterviewUpdateDTO;
import io.hexlet.cv.dto.user.UserDTO;
import io.hexlet.cv.handler.exception.InterviewNotFoundException;
import io.hexlet.cv.service.FlashPropsService;
import io.hexlet.cv.service.InterviewService;
import io.hexlet.cv.service.UserService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
class AdminInterviewControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private Inertia inertia;

    @MockBean
    private FlashPropsService flashPropsService;

    @MockBean
    private UserService userService;

    @MockBean
    private InterviewService interviewService;

    @BeforeEach
    void setUp() {
        when(flashPropsService.buildProps(anyString(), any()))
                .thenAnswer(invocation -> {
                    String locale = invocation.getArgument(0);
                    Map<String, Object> props = new HashMap<>();
                    props.put("locale", locale);
                    return props;
                });

        when(inertia.render(anyString(), anyMap())).thenReturn(ResponseEntity.ok("OK"));
        when(inertia.redirect(anyString())).thenReturn(ResponseEntity.status(302).build());
    }

    //CREATE WITHOUT SPEAKER

    @Test
    @WithMockUser(username = "admin@example.com", roles = {"ADMIN"})
    void shouldCreateInterviewWithoutSpeaker() throws Exception {
        // given
        String locale = "ru";

        InterviewDTO createdInterview = createInterviewDTO(1L, "Interview without speaker");
        // явно null
        createdInterview.setSpeaker(null);

        when(interviewService.create(any(InterviewCreateDTO.class)))
                .thenReturn(createdInterview);

        // when & then
        mockMvc.perform(post("/{locale}/admin/interview/create", locale)
                        .header("X-Inertia", "true")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                            {
                                "title": "Interview without speaker",
                                "videoLink": "https://example.com/video1",
                                "isPublished": true
                            }
                            """))
                // 302 - успешное создание
                .andExpect(status().isFound());

        verify(interviewService).create(argThat(dto ->
                dto.getTitle().equals("Interview without speaker")
                        && dto.getSpeakerId() == null
        ));
    }

    //CREATE WITH SPEAKER

    @Test
    @WithMockUser(username = "admin@example.com", roles = {"ADMIN"})
    void shouldCreateInterviewWithSpeaker() throws Exception {
        // given
        String locale = "ru";

        UserDTO speaker = createUserDTO(5L, "speaker@test.com", "John", "Doe");
        InterviewDTO createdInterview = createInterviewDTO(2L, "Interview with speaker");
        createdInterview.setSpeaker(speaker);

        when(interviewService.create(any(InterviewCreateDTO.class)))
                .thenReturn(createdInterview);

        // when & then
        mockMvc.perform(post("/{locale}/admin/interview/create", locale)
                        .header("X-Inertia", "true")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                            {
                                "title": "Interview with speaker",
                                "speakerId": 5,
                                "videoLink": "https://example.com/video2",
                                "isPublished": false
                            }
                            """))
                .andExpect(status().isFound());

        verify(interviewService).create(argThat(dto ->
                dto.getTitle().equals("Interview with speaker")
                        && dto.getSpeakerId() == 5L
        ));
    }

    //EDIT

    @Test
    @WithMockUser(username = "admin@example.com", roles = {"ADMIN"})
    void shouldUpdateInterviewTitleAndSpeaker() throws Exception {
        // given
        String locale = "ru";
        Long interviewId = 1L;

        // Обновленное интервью с новым спикером
        UserDTO newSpeaker = createUserDTO(10L, "new@test.com", "New", "Speaker");
        InterviewDTO updatedInterview = createInterviewDTO(interviewId, "Updated Title");
        updatedInterview.setSpeaker(newSpeaker);

        when(interviewService.update(any(InterviewUpdateDTO.class), eq(interviewId)))
                .thenReturn(updatedInterview);

        // when & then
        mockMvc.perform(put("/{locale}/admin/interview/{id}/edit", locale, interviewId)
                        .header("X-Inertia", "true")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                            {
                                "title": "Updated Title",
                                "speakerId": 10
                            }
                            """))
                .andExpect(status().isFound());

        verify(interviewService).update(argThat(dto ->
                dto.getTitle().get().equals("Updated Title")
                        && dto.getSpeakerId().get() == 10L
        ), eq(interviewId));
    }

    //REMOVE SPEAKER

    @Test
    @WithMockUser(username = "admin@example.com", roles = {"ADMIN"})
    void shouldRemoveSpeakerFromInterview() throws Exception {
        // given
        String locale = "ru";
        Long interviewId = 1L;

        InterviewDTO updatedInterview = createInterviewDTO(interviewId, "Interview without speaker now");
        updatedInterview.setSpeaker(null);

        when(interviewService.update(any(InterviewUpdateDTO.class), eq(interviewId)))
                .thenReturn(updatedInterview);

        mockMvc.perform(put("/{locale}/admin/interview/{id}/edit", locale, interviewId)
                        .header("X-Inertia", "true")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                            {
                                "speakerId": null
                            }
                            """))
                .andExpect(status().isFound());

        verify(interviewService).update(argThat(dto ->
                dto.getSpeakerId().get() == null
        ), eq(interviewId));
    }

    //DELETE

    @Test
    @WithMockUser(username = "admin@example.com", roles = {"ADMIN"})
    void shouldDeleteInterview() throws Exception {
        // given
        String locale = "ru";
        Long interviewId = 1L;

        doNothing().when(interviewService).delete(interviewId);

        // when & then
        mockMvc.perform(delete("/{locale}/admin/interview/{id}", locale, interviewId)
                        .header("X-Inertia", "true"))
                .andExpect(status().isFound());

        verify(interviewService).delete(interviewId);
    }

    //SEARCH INTERVIEW

    @Test
    @WithMockUser(username = "admin@example.com", roles = {"ADMIN"})
    void shouldFindInterviewsBySearchWord() throws Exception {
        // given
        String locale = "ru";
        String searchWord = "java";

        List<InterviewDTO> javaInterviews = List.of(
                createInterviewDTO(1L, "Java Programming Interview"),
                createInterviewDTO(2L, "Advanced Java Concepts")
        );
        Page<InterviewDTO> searchResult = new PageImpl<>(javaInterviews);

        when(interviewService.search(eq(searchWord), any(Pageable.class)))
                .thenReturn(searchResult);

        // when & then
        mockMvc.perform(get("/{locale}/admin/interview", locale)
                        .header("X-Inertia", "true")
                        .param("interviewSearchWord", searchWord))
                .andExpect(status().isOk());

        verify(interviewService).search(eq("java"), any(Pageable.class));
    }

    @Test
    @WithMockUser(username = "admin@example.com", roles = {"ADMIN"})
    void shouldFindOnlyOneInterviewWhenSearchingForPython() throws Exception {
        // given
        String locale = "ru";
        String searchWord = "python";

        List<InterviewDTO> pythonInterviews = List.of(
                createInterviewDTO(3L, "Python for Beginners")
        );
        Page<InterviewDTO> searchResult = new PageImpl<>(pythonInterviews);

        when(interviewService.search(eq(searchWord), any(Pageable.class)))
                .thenReturn(searchResult);

        // when & then
        mockMvc.perform(get("/{locale}/admin/interview", locale)
                        .header("X-Inertia", "true")
                        .param("interviewSearchWord", searchWord))
                .andExpect(status().isOk());

        verify(interviewService).search(eq("python"), any(Pageable.class));
    }

    @Test
    @WithMockUser(username = "admin@example.com", roles = {"ADMIN"})
    void shouldReturnAllInterviewsWhenSearchWordIsEmpty() throws Exception {
        // given
        String locale = "ru";
        String searchWord = "";

        List<InterviewDTO> allInterviews = List.of(
                createInterviewDTO(1L, "Java Programming Interview"),
                createInterviewDTO(2L, "Advanced Java Concepts"),
                createInterviewDTO(3L, "Python for Beginners")
        );
        Page<InterviewDTO> allResults = new PageImpl<>(allInterviews);

        when(interviewService.getAll(any(Pageable.class)))
                .thenReturn(allResults);

        // when & then
        mockMvc.perform(get("/{locale}/admin/interview", locale)
                        .header("X-Inertia", "true")
                        .param("interviewSearchWord", searchWord))
                .andExpect(status().isOk());

        // Проверяем что вызван getAll, а не search
        verify(interviewService).getAll(any(Pageable.class));
        verify(interviewService, never()).search(anyString(), any(Pageable.class));
    }

    //GET BY ID

    @Test
    @WithMockUser(username = "admin@example.com", roles = {"ADMIN"})
    void shouldGetInterviewById() throws Exception {
        // given
        String locale = "ru";
        Long interviewId = 1L;

        UserDTO speaker = createUserDTO(5L, "speaker@test.com", "John", "Doe");
        InterviewDTO interview = createInterviewDTO(interviewId, "Test Interview");
        interview.setSpeaker(speaker);

        when(interviewService.findById(interviewId)).thenReturn(interview);

        // when & then
        mockMvc.perform(get("/{locale}/admin/interview/{id}", locale, interviewId)
                        .header("X-Inertia", "true"))
                .andExpect(status().isOk());

        verify(interviewService).findById(interviewId);
    }

    @Test
    @WithMockUser(username = "admin@example.com", roles = {"ADMIN"})
    void shouldReturnNotFoundForNonExistentInterview() throws Exception {
        // given
        String locale = "ru";
        Long interviewId = 999L;

        when(interviewService.findById(interviewId))
                .thenThrow(new InterviewNotFoundException("Interview not found"));

        // when & then
        mockMvc.perform(get("/{locale}/admin/interview/{id}", locale, interviewId)
                        .header("X-Inertia", "true"))
                .andExpect(status().isNotFound());

        verify(interviewService).findById(interviewId);
    }

    //ВСПОМОГАТЕЛЬНЫЕ

    private InterviewDTO createInterviewDTO(Long id, String title) {
        InterviewDTO dto = InterviewDTO.builder()
                .id(id)
                .title(title)
                .videoLink("")
                .isPublished(false)
                .build();
        return dto;
    }

    private UserDTO createUserDTO(Long id, String email, String firstName, String lastName) {
        UserDTO dto = new UserDTO();
        dto.setId(id);
        dto.setEmail(email);
        dto.setFirstName(firstName);
        dto.setLastName(lastName);
        return dto;
    }
}
