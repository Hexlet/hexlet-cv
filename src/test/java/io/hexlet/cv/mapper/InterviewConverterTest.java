package io.hexlet.cv.mapper;

import io.hexlet.cv.dto.interview.InterviewCreateDTO;
import io.hexlet.cv.dto.interview.InterviewDTO;
import io.hexlet.cv.dto.interview.InterviewUpdateDTO;
import io.hexlet.cv.dto.user.UserDTO;
import io.hexlet.cv.handler.exception.UserNotFoundException;
import io.hexlet.cv.model.Interview;
import io.hexlet.cv.model.User;
import io.hexlet.cv.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.openapitools.jackson.nullable.JsonNullable;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class InterviewConverterTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private InterviewConverter interviewConverter;

    private static final String TEST_USER_EMAIL = "john.doe@example.com";
    private static final String TEST_USER_FIRSTNAME = "John";
    private static final String TEST_USER_LASTNAME = "Doe";
    private static final Long TEST_USER_ID = 1L;
    private static final Long TEST_INTERVIEW_ID = 1L;
    private static final String TEST_INTERVIEW_TITLE = "Test Interview";
    private static final String TEST_INTERVIEW_VIDEOLINK = "https://example.com/video";

    private User testUser = new User();
    private UserDTO testUserDTO = new UserDTO();

    @BeforeEach
    void setUp() {
        testUser.setId(TEST_USER_ID);
        testUser.setEmail(TEST_USER_EMAIL);
        testUser.setFirstName(TEST_USER_FIRSTNAME);
        testUser.setLastName(TEST_USER_LASTNAME);

        testUserDTO.setId(TEST_USER_ID);
        testUserDTO.setEmail(TEST_USER_EMAIL);
        testUserDTO.setFirstName(TEST_USER_FIRSTNAME);
        testUserDTO.setLastName(TEST_USER_LASTNAME);
    }

    @Test
    void convertCreateDtoToEntity_shouldConvertAllFields() {
        // given
        InterviewCreateDTO createDTO = new InterviewCreateDTO();
        createDTO.setTitle(TEST_INTERVIEW_TITLE);
        createDTO.setSpeakerId(TEST_USER_ID);
        createDTO.setVideoLink(TEST_INTERVIEW_VIDEOLINK);
        createDTO.setIsPublished(true);

        when(userRepository.findById(TEST_USER_ID)).thenReturn(Optional.of(testUser));

        // when
        Interview result = interviewConverter.convertCreateDtoToEntity(createDTO);

        // then
        assertThat(result.getTitle()).isEqualTo(TEST_INTERVIEW_TITLE);
        assertThat(result.getSpeaker()).isEqualTo(testUser);
        assertThat(result.getVideoLink()).isEqualTo(TEST_INTERVIEW_VIDEOLINK);
        assertThat(result.getIsPublished()).isTrue();
    }

    @Test
    void convertCreateDtoToEntity_withoutSpeaker_shouldSetNull() {
        // given
        InterviewCreateDTO createDTO = new InterviewCreateDTO();
        createDTO.setTitle(TEST_INTERVIEW_TITLE);
        createDTO.setSpeakerId(null);
        createDTO.setVideoLink("");
        createDTO.setIsPublished(false);

        // when
        Interview result = interviewConverter.convertCreateDtoToEntity(createDTO);

        // then
        assertThat(result.getTitle()).isEqualTo(TEST_INTERVIEW_TITLE);
        assertThat(result.getSpeaker()).isNull();
        assertThat(result.getVideoLink()).isEmpty();
        assertThat(result.getIsPublished()).isFalse();
    }

    @Test
    void convertCreateDtoToEntity_withDefaultValues_shouldUseDefaults() {
        // given
        // speakerId, videoLink, isPublished не установлены - будут дефолты из DTO
        InterviewCreateDTO createDTO = new InterviewCreateDTO();
        createDTO.setTitle(TEST_INTERVIEW_TITLE);

        // when
        Interview result = interviewConverter.convertCreateDtoToEntity(createDTO);

        // then
        assertThat(result.getTitle()).isEqualTo(TEST_INTERVIEW_TITLE);
        assertThat(result.getSpeaker()).isNull();
        // дефолт из DTO
        assertThat(result.getVideoLink()).isEmpty();
        // дефолт из DTO
        assertThat(result.getIsPublished()).isFalse();
    }

    @Test
    void convertEntityToDto_shouldConvertAllFields() {
        // given
        Interview interview = Interview.builder()
                //изменить
                .id(TEST_INTERVIEW_ID)
                .title(TEST_INTERVIEW_TITLE)
                .speaker(testUser)
                .videoLink(TEST_INTERVIEW_VIDEOLINK)
                .isPublished(true)
                .build();

        // when
        InterviewDTO result = interviewConverter.convertEntityToDto(interview);

        // then
        assertThat(result.getId()).isEqualTo(TEST_INTERVIEW_ID);
        assertThat(result.getTitle()).isEqualTo(TEST_INTERVIEW_TITLE);
        assertThat(result.getVideoLink()).isEqualTo(TEST_INTERVIEW_VIDEOLINK);
        assertThat(result.getIsPublished()).isTrue();
        assertThat(result.getSpeaker()).isNotNull();
        assertThat(result.getSpeaker().getId()).isEqualTo(TEST_USER_ID);
        assertThat(result.getSpeaker().getEmail()).isEqualTo(TEST_USER_EMAIL);
    }

    @Test
    void convertEntityToDto_withoutSpeaker_shouldSetNull() {
        // given
        Interview interview = Interview.builder()
                .id(TEST_INTERVIEW_ID)
                .title(TEST_INTERVIEW_TITLE)
                .speaker(null)
                .videoLink("")
                .isPublished(false)
                .build();

        // when
        InterviewDTO result = interviewConverter.convertEntityToDto(interview);

        // then
        assertThat(result.getId()).isEqualTo(TEST_INTERVIEW_ID);
        assertThat(result.getTitle()).isEqualTo(TEST_INTERVIEW_TITLE);
        assertThat(result.getSpeaker()).isNull();
        assertThat(result.getVideoLink()).isEmpty();
        assertThat(result.getIsPublished()).isFalse();
    }

    @Test
    void convertDtoToEntity_shouldConvertAllFields() {
        // given
        InterviewDTO dto = InterviewDTO.builder()
                .id(TEST_INTERVIEW_ID)
                .title(TEST_INTERVIEW_TITLE)
                .speaker(testUserDTO)
                .videoLink(TEST_INTERVIEW_VIDEOLINK)
                .isPublished(true)
                .build();

        when(userRepository.findById(TEST_USER_ID)).thenReturn(Optional.of(testUser));

        // when
        Interview result = interviewConverter.convertDtoToEntity(dto);

        // then
        assertThat(result.getId()).isEqualTo(TEST_INTERVIEW_ID);
        assertThat(result.getTitle()).isEqualTo(TEST_INTERVIEW_TITLE);
        assertThat(result.getSpeaker()).isEqualTo(testUser);
        assertThat(result.getVideoLink()).isEqualTo(TEST_INTERVIEW_VIDEOLINK);
        assertThat(result.getIsPublished()).isTrue();
    }

    @Test
    void convertDtoToEntity_withoutSpeaker_shouldSetNull() {
        // given
        InterviewDTO dto = InterviewDTO.builder()
                .id(TEST_INTERVIEW_ID)
                .title(TEST_INTERVIEW_TITLE)
                .speaker(null)
                .videoLink("")
                .isPublished(false)
                .build();

        // when
        Interview result = interviewConverter.convertDtoToEntity(dto);

        // then
        assertThat(result.getId()).isEqualTo(TEST_INTERVIEW_ID);
        assertThat(result.getTitle()).isEqualTo(TEST_INTERVIEW_TITLE);
        assertThat(result.getSpeaker()).isNull();
        assertThat(result.getVideoLink()).isEmpty();
        assertThat(result.getIsPublished()).isFalse();
    }

    @Test
    void updateEntityWithUpdateDto_shouldUpdateAllFields() {
        // given
        Interview interview = Interview.builder()
                .id(TEST_INTERVIEW_ID)
                .title("Old Title")
                .speaker(null)
                .videoLink("old-link")
                .isPublished(false)
                .build();

        InterviewUpdateDTO updateDTO = new InterviewUpdateDTO();
        updateDTO.setTitle(JsonNullable.of("New Title"));
        updateDTO.setSpeakerId(JsonNullable.of(1L));
        updateDTO.setVideoLink(JsonNullable.of("new-link"));
        updateDTO.setIsPublished(JsonNullable.of(true));

        when(userRepository.findById(1L)).thenReturn(Optional.of(testUser));

        // when
        interviewConverter.updateEntityWithUpdateDto(updateDTO, interview);

        // then
        assertThat(interview.getTitle()).isEqualTo("New Title");
        assertThat(interview.getSpeaker()).isEqualTo(testUser);
        assertThat(interview.getVideoLink()).isEqualTo("new-link");
        assertThat(interview.getIsPublished()).isTrue();
    }

    @Test
    void updateEntityWithUpdateDto_shouldUpdateOnlyProvidedFields() {
        // given
        Interview interview = Interview.builder()
                .id(TEST_INTERVIEW_ID)
                .title("Old Title")
                .speaker(null)
                .videoLink("old-link")
                .isPublished(false)
                .build();

        // Обновляется только title
        // остальные поля не передаем (JsonNullable.undefined() то есть они неявно null)
        InterviewUpdateDTO updateDTO = new InterviewUpdateDTO();
        updateDTO.setTitle(JsonNullable.of("New Title"));

        // when
        interviewConverter.updateEntityWithUpdateDto(updateDTO, interview);

        // then
        assertThat(interview.getTitle()).isEqualTo("New Title");
        // не изменилось
        assertThat(interview.getSpeaker()).isNull();
        // не изменилось
        assertThat(interview.getVideoLink()).isEqualTo("old-link");
        // не изменилось
        assertThat(interview.getIsPublished()).isFalse();
    }

    @Test
    void updateEntityWithUpdateDto_shouldRemoveSpeakerWhenNull() {
        // given
        Interview interview = Interview.builder()
                .id(TEST_INTERVIEW_ID)
                .title(TEST_INTERVIEW_TITLE)
                .speaker(testUser)
                .videoLink("link")
                .isPublished(true)
                .build();

        InterviewUpdateDTO updateDTO = new InterviewUpdateDTO();
        // Явно null для удаления спикера
        updateDTO.setSpeakerId(JsonNullable.of(null));

        // when
        interviewConverter.updateEntityWithUpdateDto(updateDTO, interview);

        // then
        // спикер удален
        assertThat(interview.getSpeaker()).isNull();
    }

    @Test
    void updateEntityWithUpdateDto_shouldThrowWhenTitleIsNull() {
        // given
        Interview interview = new Interview();
        InterviewUpdateDTO updateDTO = new InterviewUpdateDTO();
        // явный null
        updateDTO.setTitle(JsonNullable.of(null));

        // when & then
        assertThatThrownBy(() -> interviewConverter.updateEntityWithUpdateDto(updateDTO, interview))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Title in InterviewUpdateDTO cannot be null");
    }

    @Test
    void updateEntityWithUpdateDto_shouldThrowWhenVideoLinkIsNull() {
        // given
        Interview interview = new Interview();
        InterviewUpdateDTO updateDTO = new InterviewUpdateDTO();
        // явный null
        updateDTO.setVideoLink(JsonNullable.of(null));

        // when & then
        assertThatThrownBy(() -> interviewConverter.updateEntityWithUpdateDto(updateDTO, interview))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("VideoLink in UpdateInterviewDto cannot be null");
    }

    @Test
    void updateEntityWithUpdateDto_shouldThrowWhenIsPublishedIsNull() {
        // given
        Interview interview = new Interview();
        InterviewUpdateDTO updateDTO = new InterviewUpdateDTO();
        // явный null
        updateDTO.setIsPublished(JsonNullable.of(null));

        // when & then
        assertThatThrownBy(() -> interviewConverter.updateEntityWithUpdateDto(updateDTO, interview))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("IsPublished flag in InterviewUpdateDto cannot be null");
    }

    @Test
    void speakerIdToSpeaker_shouldReturnUserWhenExists() {
        // given
        when(userRepository.findById(1L)).thenReturn(Optional.of(testUser));

        // when
        User result = interviewConverter.speakerIdToSpeaker(1L);

        // then
        assertThat(result).isEqualTo(testUser);
    }

    @Test
    void speakerIdToSpeaker_shouldReturnNullWhenSpeakerIdIsNull() {
        // when
        User result = interviewConverter.speakerIdToSpeaker(null);

        // then
        assertThat(result).isNull();
    }

    @Test
    void speakerIdToSpeaker_shouldThrowWhenUserNotFound() {
        // given
        when(userRepository.findById(999L)).thenReturn(Optional.empty());

        // when & then
        assertThatThrownBy(() -> interviewConverter.speakerIdToSpeaker(999L))
                .isInstanceOf(UserNotFoundException.class)
                .hasMessageContaining("User with id: 999 not found");
    }

    @Test
    void speakerToSummary_shouldConvertUserToDTO() {
        // when
        UserDTO result = interviewConverter.speakerToSummary(testUser);

        // then
        assertThat(result.getId()).isEqualTo(TEST_USER_ID);
        assertThat(result.getEmail()).isEqualTo(TEST_USER_EMAIL);
        assertThat(result.getFirstName()).isEqualTo(TEST_USER_FIRSTNAME);
        assertThat(result.getLastName()).isEqualTo(TEST_USER_LASTNAME);
    }

    @Test
    void speakerToSummary_shouldReturnNullWhenSpeakerIsNull() {
        // when
        UserDTO result = interviewConverter.speakerToSummary(null);

        // then
        assertThat(result).isNull();
    }

    @Test
    void summaryToSpeaker_shouldConvertDTOToUser() {
        // given
        when(userRepository.findById(TEST_USER_ID)).thenReturn(Optional.of(testUser));

        // when
        User result = interviewConverter.summaryToSpeaker(testUserDTO);

        // then
        assertThat(result).isEqualTo(testUser);
    }

    @Test
    void summaryToSpeaker_shouldReturnNullWhenDTOIsNull() {
        // when
        User result = interviewConverter.summaryToSpeaker(null);

        // then
        assertThat(result).isNull();
    }

    @Test
    void summaryToSpeaker_shouldReturnNullWhenDTOIdIsNull() {
        // given
        UserDTO dto = new UserDTO();
        dto.setId(null);

        // when
        User result = interviewConverter.summaryToSpeaker(dto);

        // then
        assertThat(result).isNull();
    }

    @Test
    void summaryToSpeaker_shouldThrowWhenUserNotFound() {
        // given
        when(userRepository.findById(999L)).thenReturn(Optional.empty());

        UserDTO dto = new UserDTO();
        dto.setId(999L);

        // when & then
        assertThatThrownBy(() -> interviewConverter.summaryToSpeaker(dto))
                .isInstanceOf(UserNotFoundException.class)
                .hasMessageContaining("User with id: 999 not found");
    }
}