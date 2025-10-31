package io.hexlet.cv.mapper;

import io.hexlet.cv.dto.interview.InterviewCreateDTO;
import io.hexlet.cv.dto.interview.InterviewUpdateDTO;
import io.hexlet.cv.handler.exception.UserNotFoundException;
import io.hexlet.cv.model.Interview;
import io.hexlet.cv.model.User;
import io.hexlet.cv.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openapitools.jackson.nullable.JsonNullable;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

@DataJpaTest
class InterviewMapperTest {

    private InterviewMapper interviewMapper;

    @MockBean
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        interviewMapper = new InterviewMapperImpl();
        // Устанавливаем репозиторий через reflection
        org.springframework.test.util.ReflectionTestUtils.setField(
                interviewMapper, "userRepository", userRepository
        );
    }

    @Test
    void map_InterviewCreateDTO_To_Interview_WithOnlyTitle() {
        // given
        InterviewCreateDTO createDTO = new InterviewCreateDTO();
        createDTO.setTitle("Test Interview");

        // when
        Interview interview = interviewMapper.map(createDTO);

        // then
        assertThat(interview).isNotNull();
        assertThat(interview.getTitle()).isEqualTo("Test Interview");
        assertThat(interview.getSpeaker()).isNull();
        assertThat(interview.getVideoLink()).isEqualTo("");
        assertThat(interview.getIsPublished()).isFalse();

        verifyNoInteractions(userRepository);
    }

    @Test
    void map_InterviewCreateDTO_To_Interview_WithSpeaker() {
        // given
        User speaker = new User();
        speaker.setId(1L);
        speaker.setEmail("speaker@test.com");
        speaker.setFirstName("John");
        speaker.setLastName("Doe");

        when(userRepository.findById(1L)).thenReturn(Optional.of(speaker));

        InterviewCreateDTO createDTO = new InterviewCreateDTO();
        createDTO.setTitle("Test Interview");
        createDTO.setSpeakerId(1L);
        createDTO.setVideoLink("http://test.com");
        createDTO.setIsPublished(true);

        // when
        Interview interview = interviewMapper.map(createDTO);

        // then
        assertThat(interview).isNotNull();
        assertThat(interview.getTitle()).isEqualTo("Test Interview");
        assertThat(interview.getSpeaker()).isEqualTo(speaker);
        assertThat(interview.getVideoLink()).isEqualTo("http://test.com");
        assertThat(interview.getIsPublished()).isTrue();

        verify(userRepository).findById(1L);
    }

    @Test
    void map_InterviewCreateDTO_WithNonExistentSpeaker_ShouldThrowException() {
        // given
        when(userRepository.findById(999L)).thenReturn(Optional.empty());

        InterviewCreateDTO createDTO = new InterviewCreateDTO();
        createDTO.setTitle("Test Interview");
        createDTO.setSpeakerId(999L);

        // when & then
        assertThatThrownBy(() -> interviewMapper.map(createDTO))
                .isInstanceOf(UserNotFoundException.class)
                .hasMessageContaining("User with id: 999 not found");

        verify(userRepository).findById(999L);
    }

    @Test
    void updateInterview_ShouldUpdateOnlySpecifiedFields() {
        // given
        User speaker = new User();
        speaker.setId(1L);

        when(userRepository.findById(1L)).thenReturn(Optional.of(speaker));

        Interview interview = new Interview();
        interview.setTitle("Old Title");
        interview.setVideoLink("http://old.com");
        interview.setIsPublished(false);
        interview.setSpeaker(speaker);

        InterviewUpdateDTO updateDTO = new InterviewUpdateDTO();
        updateDTO.setTitle(JsonNullable.of("New Title"));

        // when
        interviewMapper.updateInterview(updateDTO, interview);

        // then
        assertThat(interview.getTitle()).isEqualTo("New Title");
        assertThat(interview.getVideoLink()).isEqualTo("http://old.com");
        assertThat(interview.getIsPublished()).isFalse();
        assertThat(interview.getSpeaker()).isEqualTo(speaker);

        verifyNoInteractions(userRepository); // speakerId не обновлялся
    }
}