package io.hexlet.cv.mapper;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.Mockito.when;

import io.hexlet.cv.converter.InterviewConverter;
import io.hexlet.cv.dto.interview.InterviewCreateDTO;
import io.hexlet.cv.dto.interview.InterviewDTO;
import io.hexlet.cv.dto.interview.InterviewUpdateDTO;
import io.hexlet.cv.dto.user.UserDTO;
import io.hexlet.cv.handler.exception.UserNotFoundException;
import io.hexlet.cv.model.Interview;
import io.hexlet.cv.model.User;
import io.hexlet.cv.repository.UserRepository;
import java.time.LocalDateTime;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.openapitools.jackson.nullable.JsonNullable;

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
    private static final LocalDateTime CREATE_DATETIME = LocalDateTime.of(2025, 12, 9, 12, 5, 0);

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
    void convertCreateDtoToEntityShouldConvertAllFields() {
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
    void convertCreateDtoToEntityWithoutSpeakerShouldSetNull() {
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
    void convertCreateDtoToEntityWithDefaultValuesShouldUseDefaults() {
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
    void convertEntityToDtoShouldConvertAllFields() {
        // given
        Interview interview = Interview.builder()
                //изменить
                .id(TEST_INTERVIEW_ID)
                .title(TEST_INTERVIEW_TITLE)
                .speaker(testUser)
                .videoLink(TEST_INTERVIEW_VIDEOLINK)
                .isPublished(true)
                .createdAt(CREATE_DATETIME)
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
        assertThat(result.getCreatedAt()).isEqualTo(CREATE_DATETIME);
    }

    @Test
    void convertEntityToDtoWithoutSpeakerShouldSetNull() {
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

    /*@Test
    void convertDtoToEntityShouldConvertAllFields() {
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
    void convertDtoToEntityWithoutSpeakerShouldSetNull() {
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
    }*/

    @Test
    void updateEntityWithUpdateDtoShouldUpdateAllFields() {
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
    void updateEntityWithUpdateDtoShouldUpdateOnlyProvidedFields() {
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
    void updateEntityWithUpdateDtoShouldRemoveSpeakerWhenNull() {
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
    void updateEntityWithUpdateDtoShouldThrowWhenTitleIsNull() {
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
    void updateEntityWithUpdateDtoShouldThrowWhenVideoLinkIsNull() {
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
    void updateEntityWithUpdateDtoShouldThrowWhenIsPublishedIsNull() {
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
    void speakerIdToSpeakerShouldReturnUserWhenExists() {
        // given
        when(userRepository.findById(1L)).thenReturn(Optional.of(testUser));

        // when
        User result = interviewConverter.speakerIdToSpeaker(1L);

        // then
        assertThat(result).isEqualTo(testUser);
    }

    @Test
    void speakerIdToSpeakerShouldReturnNullWhenSpeakerIdIsNull() {
        // when
        User result = interviewConverter.speakerIdToSpeaker(null);

        // then
        assertThat(result).isNull();
    }

    @Test
    void speakerIdToSpeakerShouldThrowWhenUserNotFound() {
        // given
        when(userRepository.findById(999L)).thenReturn(Optional.empty());

        // when & then
        assertThatThrownBy(() -> interviewConverter.speakerIdToSpeaker(999L))
                .isInstanceOf(UserNotFoundException.class)
                .hasMessageContaining("User with id: 999 not found");
    }

    @Test
    void speakerToSummaryShouldConvertUserToDTO() {
        // when
        UserDTO result = interviewConverter.speakerToSummary(testUser);

        // then
        assertThat(result.getId()).isEqualTo(TEST_USER_ID);
        assertThat(result.getEmail()).isEqualTo(TEST_USER_EMAIL);
        assertThat(result.getFirstName()).isEqualTo(TEST_USER_FIRSTNAME);
        assertThat(result.getLastName()).isEqualTo(TEST_USER_LASTNAME);
    }

    @Test
    void speakerToSummaryShouldReturnNullWhenSpeakerIsNull() {
        // when
        UserDTO result = interviewConverter.speakerToSummary(null);

        // then
        assertThat(result).isNull();
    }

    @Test
    void summaryToSpeakerShouldConvertDTOToUser() {
        // given
        when(userRepository.findById(TEST_USER_ID)).thenReturn(Optional.of(testUser));

        // when
        User result = interviewConverter.summaryToSpeaker(testUserDTO);

        // then
        assertThat(result).isEqualTo(testUser);
    }

    @Test
    void summaryToSpeakerShouldReturnNullWhenDTOIsNull() {
        // when
        User result = interviewConverter.summaryToSpeaker(null);

        // then
        assertThat(result).isNull();
    }

    @Test
    void summaryToSpeakerShouldReturnNullWhenDTOIdIsNull() {
        // given
        UserDTO dto = new UserDTO();
        dto.setId(null);

        // when
        User result = interviewConverter.summaryToSpeaker(dto);

        // then
        assertThat(result).isNull();
    }

    @Test
    void summaryToSpeakerShouldThrowWhenUserNotFound() {
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
