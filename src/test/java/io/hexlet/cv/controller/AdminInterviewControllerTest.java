package io.hexlet.cv.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyMap;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
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
import io.hexlet.cv.handler.exception.ResourceNotFoundException;
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
@WithMockUser(authorities = "ADMIN")
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
        // Настраиваем поведение FlashPropsService для всех тестов
        when(flashPropsService.buildProps(anyString(), any()))
                .thenAnswer(invocation -> {
                    String locale = invocation.getArgument(0);
                    Map<String, Object> props = new HashMap<>();
                    props.put("locale", locale);
                    return props;
                });

        // Настраиваем поведение Inertia для всех тестов
        when(inertia.render(anyString(), anyMap())).thenReturn(ResponseEntity.ok("OK"));
        when(inertia.redirect(anyString())).thenReturn(ResponseEntity.status(302).build());
    }

    @Test
    void indexShouldReturnInterviewsList() throws Exception {
        // given
        String locale = "ru";
        Page<InterviewDTO> interviewPage = new PageImpl<>(List.of(
                createInterviewDTO(1L, "Interview 1"),
                createInterviewDTO(2L, "Interview 2")
        ));

        when(interviewService.getAll(any(Pageable.class))).thenReturn(interviewPage);

        // when & then
        mockMvc.perform(get("/{locale}/admin/interview", locale))
                .andExpect(status().isOk());

        verify(interviewService).getAll(any(Pageable.class));
        verify(inertia).render(eq("Admin/Interviews/Index"), anyMap());
    }

    @Test
    void indexWithSearchByTitleShouldReturnFilteredResults() throws Exception {
        // given
        String locale = "ru";
        String searchWord = "java";
        Page<InterviewDTO> interviewPage = new PageImpl<>(List.of(
                createInterviewDTO(1L, "Java Programming Interview")
        ));

        when(interviewService.search(eq(searchWord), any(Pageable.class))).thenReturn(interviewPage);

        // when & then
        mockMvc.perform(get("/{locale}/admin/interview", locale)
                        .param("interviewSearchWord", searchWord))
                .andExpect(status().isOk());

        verify(interviewService).search(eq(searchWord), any(Pageable.class));
    }

    @Test
    void showShouldReturnInterview() throws Exception {
        // given
        String locale = "ru";
        Long interviewId = 1L;
        InterviewDTO interviewDTO = createInterviewDTO(interviewId, "Test Interview");

        when(interviewService.findById(interviewId)).thenReturn(interviewDTO);

        // when & then
        mockMvc.perform(get("/{locale}/admin/interview/{id}", locale, interviewId))
                .andExpect(status().isOk());

        verify(interviewService).findById(interviewId);
        verify(inertia).render(eq("Admin/Interviews/Show"), anyMap());
    }

    @Test
    void createFormShouldReturnFormWithSpeakers() throws Exception {
        // given
        String locale = "ru";
        List<UserDTO> speakers = List.of(
                createUserDTO(1L, "john@test.com", "John", "Doe")
        );

        when(userService.getPotentialInterviewSpeakers()).thenReturn(speakers);

        // when & then
        mockMvc.perform(get("/{locale}/admin/interview/create", locale))
                .andExpect(status().isOk());

        verify(userService).getPotentialInterviewSpeakers();
        verify(inertia).render(eq("Admin/Interviews/Create"), anyMap());
    }

    @Test
    void createInterviewShouldCreateAndRedirect() throws Exception {
        // given
        String locale = "ru";

        when(interviewService.create(any(InterviewCreateDTO.class))).thenReturn(createInterviewDTO(1L,
                "New Interview"));

        // when & then
        mockMvc.perform(post("/{locale}/admin/interview/create", locale)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                    {
                        "title": "New Interview"
                    }
                    """))
                .andExpect(status().isFound());

        verify(interviewService).create(any(InterviewCreateDTO.class));
        verify(inertia).redirect("/" + locale + "/admin/interview");
    }

    @Test
    void editFormShouldReturnEditForm() throws Exception {
        // given
        String locale = "ru";
        Long interviewId = 1L;
        InterviewDTO interviewDTO = createInterviewDTO(interviewId, "Test Interview");
        List<UserDTO> speakers = List.of(createUserDTO(1L, "john@test.com", "John", "Doe"));

        when(interviewService.findById(interviewId)).thenReturn(interviewDTO);
        when(userService.getPotentialInterviewSpeakers()).thenReturn(speakers);

        // when & then
        mockMvc.perform(get("/{locale}/admin/interview/{id}/edit", locale, interviewId))
                .andExpect(status().isOk());

        verify(interviewService).findById(interviewId);
        verify(userService).getPotentialInterviewSpeakers();
        verify(inertia).render(eq("Admin/Interviews/Edit"), anyMap());
    }

    @Test
    void editShouldUpdateAndRedirect() throws Exception {
        // given
        String locale = "ru";
        Long interviewId = 1L;

        when(interviewService.update(any(InterviewUpdateDTO.class), eq(interviewId)))
                .thenReturn(createInterviewDTO(interviewId, "Updated Interview"));

        // when & then
        mockMvc.perform(put("/{locale}/admin/interview/{id}/edit", locale, interviewId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                    {
                        "title": "Updated Interview"
                    }
                    """))
                .andExpect(status().isFound());

        verify(interviewService).update(any(InterviewUpdateDTO.class), eq(interviewId));
        verify(inertia).redirect("/" + locale + "/admin/interview/" + interviewId);
    }

    @Test
    void deleteShouldDeleteAndRedirect() throws Exception {
        // given
        String locale = "ru";
        Long interviewId = 1L;

        doNothing().when(interviewService).delete(interviewId);

        // when & then
        mockMvc.perform(delete("/{locale}/admin/interview/{id}", locale, interviewId))
                .andExpect(status().isFound());

        verify(interviewService).delete(interviewId);
        verify(inertia).redirect("/" + locale + "/admin/interview");
    }

    @Test
    void showWhenInterviewNotFoundShouldReturnErrorPage() throws Exception {
        // given
        String locale = "ru";
        Long interviewId = 999L;

        when(interviewService.findById(interviewId))
                .thenThrow(new ResourceNotFoundException("Interview not found"));

        // when & then
        mockMvc.perform(get("/{locale}/admin/interview/{id}", locale, interviewId))
                .andExpect(status().isOk());

        verify(inertia).render(eq("Error/InterviewNotFound"), anyMap());
    }

    private InterviewDTO createInterviewDTO(Long id, String title) {
        InterviewDTO dto = new InterviewDTO();
        dto.setId(id);
        dto.setTitle(title);
        dto.setVideoLink("");
        dto.setIsPublished(false);
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
