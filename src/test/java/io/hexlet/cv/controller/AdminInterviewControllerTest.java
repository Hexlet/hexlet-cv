package io.hexlet.cv.controller;

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

    @Test
    @WithMockUser(username = "admin@example.com", roles = {"ADMIN"})
    void indexShouldReturnInterviewsList() throws Exception {
        // given
        String locale = "ru";
        Page<InterviewDTO> interviewPage = new PageImpl<>(List.of(
                createInterviewDTO(1L, "Interview 1"),
                createInterviewDTO(2L, "Interview 2")
        ));

        when(interviewService.getAll(any(Pageable.class))).thenReturn(interviewPage);

        // when & then
        mockMvc.perform(get("/{locale}/admin/interview", locale)
                        .header("X-Inertia", "true"))
                .andExpect(status().isOk());

        verify(interviewService).getAll(any(Pageable.class));
        verify(inertia).render(eq("Admin/Interviews/Index"), anyMap());
    }

    @Test
    @WithMockUser(username = "admin@example.com", roles = {"ADMIN"})
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
                        .header("X-Inertia", "true")
                        .param("interviewSearchWord", searchWord))
                .andExpect(status().isOk());

        verify(interviewService).search(eq(searchWord), any(Pageable.class));
    }

    @Test
    @WithMockUser(username = "admin@example.com", roles = {"ADMIN"})
    void showShouldReturnInterview() throws Exception {
        // given
        String locale = "ru";
        Long interviewId = 1L;
        InterviewDTO interviewDTO = createInterviewDTO(interviewId, "Test Interview");

        when(interviewService.findById(interviewId)).thenReturn(interviewDTO);

        // when & then
        mockMvc.perform(get("/{locale}/admin/interview/{id}", locale, interviewId)
                        .header("X-Inertia", "true"))
                .andExpect(status().isOk());

        verify(interviewService).findById(interviewId);
        verify(inertia).render(eq("Admin/Interviews/Show"), anyMap());
    }

    @Test
    @WithMockUser(username = "admin@example.com", roles = {"ADMIN"})
    void createFormShouldReturnFormWithSpeakers() throws Exception {
        // given
        String locale = "ru";
        List<UserDTO> speakers = List.of(
                createUserDTO(1L, "john@test.com", "John", "Doe")
        );

        when(userService.getPotentialInterviewSpeakers()).thenReturn(speakers);

        // when & then
        mockMvc.perform(get("/{locale}/admin/interview/create", locale)
                        .header("X-Inertia", "true"))
                .andExpect(status().isOk());

        verify(userService).getPotentialInterviewSpeakers();
        verify(inertia).render(eq("Admin/Interviews/Create"), anyMap());
    }

    @Test
    @WithMockUser(username = "admin@example.com", roles = {"ADMIN"})
    void createInterviewShouldCreateAndRedirect() throws Exception {
        // given
        String locale = "ru";

        when(interviewService.create(any(InterviewCreateDTO.class))).thenReturn(createInterviewDTO(1L,
                "New Interview"));

        // when & then
        mockMvc.perform(post("/{locale}/admin/interview/create", locale)
                        .header("X-Inertia", "true")
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
    @WithMockUser(username = "admin@example.com", roles = {"ADMIN"})
    void editFormShouldReturnEditForm() throws Exception {
        // given
        String locale = "ru";
        Long interviewId = 1L;
        InterviewDTO interviewDTO = createInterviewDTO(interviewId, "Test Interview");
        List<UserDTO> speakers = List.of(createUserDTO(1L, "john@test.com", "John", "Doe"));

        when(interviewService.findById(interviewId)).thenReturn(interviewDTO);
        when(userService.getPotentialInterviewSpeakers()).thenReturn(speakers);

        // when & then
        mockMvc.perform(get("/{locale}/admin/interview/{id}/edit", locale, interviewId)
                        .header("X-Inertia", "true"))
                .andExpect(status().isOk());

        verify(interviewService).findById(interviewId);
        verify(userService).getPotentialInterviewSpeakers();
        verify(inertia).render(eq("Admin/Interviews/Edit"), anyMap());
    }

    @Test
    @WithMockUser(username = "admin@example.com", roles = {"ADMIN"})
    void editShouldUpdateAndRedirect() throws Exception {
        // given
        String locale = "ru";
        Long interviewId = 1L;

        when(interviewService.update(any(InterviewUpdateDTO.class), eq(interviewId)))
                .thenReturn(createInterviewDTO(interviewId, "Updated Interview"));

        // when & then
        mockMvc.perform(put("/{locale}/admin/interview/{id}/edit", locale, interviewId)
                        .header("X-Inertia", "true")
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
    @WithMockUser(username = "admin@example.com", roles = {"ADMIN"})
    void deleteShouldDeleteAndRedirect() throws Exception {
        // given
        String locale = "ru";
        Long interviewId = 1L;

        doNothing().when(interviewService).delete(interviewId);

        // when & then
        mockMvc.perform(delete("/{locale}/admin/interview/{id}", locale, interviewId)
                        .header("X-Inertia", "true"))
                .andExpect(status().isFound());

        verify(interviewService).delete(interviewId);
        verify(inertia).redirect("/" + locale + "/admin/interview");
    }

    @Test
    @WithMockUser(username = "admin@example.com", roles = {"ADMIN"})
    void showWhenInterviewNotFoundShouldReturnErrorPage() throws Exception {
        // given
        String locale = "ru";
        Long interviewId = 999L;

        when(interviewService.findById(interviewId))
                .thenThrow(new InterviewNotFoundException("Interview with id: " + interviewId + " not found."));

        // when & then
        mockMvc.perform(get("/{locale}/admin/interview/{id}", locale, interviewId)
                        .header("X-Inertia", "true"))
                .andExpect(status().isNotFound());

        verify(inertia).render(eq("Error/InterviewNotFound"), anyMap());
    }

    @Test
    @WithMockUser(username = "admin@example.com", roles = {"CANDIDATE"})
    void accessAsNonAdminShouldBeForbidden() throws Exception {
        // given
        String locale = "ru";

        // when & then
        mockMvc.perform(get("/{locale}/admin/interview", locale)
                        .header("X-Inertia", "true"))
                .andExpect(status().isForbidden());
    }

    @Test
    void accessWithoutAuthShouldBeUnauthorized() throws Exception {
        // given
        String locale = "ru";

        // when & then
        mockMvc.perform(get("/{locale}/admin/interview", locale)
                        .header("X-Inertia", "true"))
                .andExpect(status().isUnauthorized());
    }

    private InterviewDTO createInterviewDTO(Long id, String title) {
        InterviewDTO dto = new InterviewDTO();
        dto.setId(id);
        dto.setTitle(title);
        dto.setVideoLink("");
        dto.setIsPublished(false);
        return dto;
    }

    private UserDTO createUserDTO(Long id,
                                  String email,
                                  String firstName,
                                  String lastName) {
        UserDTO dto = new UserDTO();
        dto.setId(id);
        dto.setEmail(email);
        dto.setFirstName(firstName);
        dto.setLastName(lastName);
        return dto;
    }
}
