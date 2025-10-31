package io.hexlet.cv.mapper;

import io.hexlet.cv.dto.interview.InterviewCreateDTO;
import io.hexlet.cv.dto.interview.InterviewDTO;
import io.hexlet.cv.dto.interview.InterviewUpdateDTO;
import io.hexlet.cv.model.Interview;
import io.hexlet.cv.model.User;
import io.hexlet.cv.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openapitools.jackson.nullable.JsonNullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
@Import(InterviewMapperImpl.class)
class InterviewMapperTest {

    @Autowired
    private InterviewMapper interviewMapper;

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        interviewMapper = new InterviewMapperImpl();
        // Инжектим репозиторий в маппер через reflection
        org.springframework.test.util.ReflectionTestUtils.setField(
                interviewMapper, "userRepository", userRepository
        );
    }

    @Test
    void map_InterviewCreateDTO_To_Interview_WithOnlyTitle() {
        // given - минимальные данные (только обязательный title)
        InterviewCreateDTO createDTO = new InterviewCreateDTO();
        createDTO.setTitle("Test Interview");
        // speakerId, videoLink, isPublished - не устанавливаем (null)

        // when
        Interview interview = interviewMapper.map(createDTO);

        // then
        assertThat(interview).isNotNull();
        assertThat(interview.getTitle()).isEqualTo("Test Interview");
        assertThat(interview.getSpeaker()).isNull();
        assertThat(interview.getVideoLink()).isEqualTo(""); // default
        assertThat(interview.getIsPublished()).isFalse();   // default
    }

    @Test
    void map_InterviewCreateDTO_To_Interview_WithAllFields() {
        // given - все поля заполнены
        User speaker = new User();
        speaker.setEmail("speaker@test.com");
        speaker.setFirstName("John");
        speaker.setLastName("Doe");
        entityManager.persist(speaker);
        entityManager.flush();

        InterviewCreateDTO createDTO = new InterviewCreateDTO();
        createDTO.setTitle("Test Interview");
        createDTO.setSpeakerId(speaker.getId());
        createDTO.setVideoLink("http://test.com");
        createDTO.setIsPublished(true);

        // when
        Interview interview = interviewMapper.map(createDTO);

        // then
        assertThat(interview).isNotNull();
        assertThat(interview.getTitle()).isEqualTo("Test Interview");
        assertThat(interview.getSpeaker()).isNotNull();
        assertThat(interview.getSpeaker().getId()).isEqualTo(speaker.getId());
        assertThat(interview.getVideoLink()).isEqualTo("http://test.com");
        assertThat(interview.getIsPublished()).isTrue();
    }

    @Test
    void map_Interview_To_InterviewDTO_WithSpeaker() {
        // given
        User speaker = new User();
        speaker.setEmail("speaker@test.com");
        speaker.setFirstName("John");
        speaker.setLastName("Doe");
        entityManager.persist(speaker);
        entityManager.flush();

        Interview interview = new Interview();
        interview.setTitle("Test Interview");
        interview.setSpeaker(speaker);
        interview.setVideoLink("http://test.com");
        interview.setIsPublished(true);

        // when
        InterviewDTO interviewDTO = interviewMapper.map(interview);

        // then
        assertThat(interviewDTO).isNotNull();
        assertThat(interviewDTO.getTitle()).isEqualTo("Test Interview");
        assertThat(interviewDTO.getVideoLink()).isEqualTo("http://test.com");
        assertThat(interviewDTO.getIsPublished()).isTrue();
        assertThat(interviewDTO.getSpeaker()).isNotNull();
        assertThat(interviewDTO.getSpeaker().getFirstName()).isEqualTo("John");
        assertThat(interviewDTO.getSpeaker().getLastName()).isEqualTo("Doe");
    }

    @Test
    void map_Interview_To_InterviewDTO_WithoutSpeaker() {
        // given
        Interview interview = new Interview();
        interview.setTitle("Test Interview");
        interview.setSpeaker(null); // без спикера
        interview.setVideoLink("http://test.com");
        interview.setIsPublished(false);

        // when
        InterviewDTO interviewDTO = interviewMapper.map(interview);

        // then
        assertThat(interviewDTO).isNotNull();
        assertThat(interviewDTO.getTitle()).isEqualTo("Test Interview");
        assertThat(interviewDTO.getVideoLink()).isEqualTo("http://test.com");
        assertThat(interviewDTO.getIsPublished()).isFalse();
        assertThat(interviewDTO.getSpeaker()).isNull();
    }

    @Test
    void updateInterview_ShouldUpdateOnlySpecifiedFields() {
        // given
        User speaker = new User();
        speaker.setEmail("speaker@test.com");
        entityManager.persist(speaker);
        entityManager.flush();

        Interview interview = new Interview();
        interview.setTitle("Old Title");
        interview.setVideoLink("http://old.com");
        interview.setIsPublished(false);
        interview.setSpeaker(speaker);

        // when - обновляем только title
        InterviewUpdateDTO updateDTO = new InterviewUpdateDTO();
        updateDTO.setTitle(JsonNullable.of("New Title"));
        // videoLink, isPublished, speakerId - не трогаем

        interviewMapper.updateInterview(updateDTO, interview);

        // then
        assertThat(interview.getTitle()).isEqualTo("New Title");
        assertThat(interview.getVideoLink()).isEqualTo("http://old.com"); // не изменился
        assertThat(interview.getIsPublished()).isFalse();                 // не изменился
        assertThat(interview.getSpeaker()).isEqualTo(speaker);            // не изменился
    }

    @Test
    void updateInterview_WithSpeakerChanges_ShouldHandleSpeakerCorrectly() {
        // given
        User speaker1 = new User();
        speaker1.setEmail("speaker1@test.com");
        speaker1.setFirstName("John");
        entityManager.persist(speaker1);

        User speaker2 = new User();
        speaker2.setEmail("speaker2@test.com");
        speaker2.setFirstName("Jane");
        entityManager.persist(speaker2);
        entityManager.flush();

        Interview interview = new Interview();
        interview.setTitle("Interview");
        interview.setSpeaker(null); // начинаем без спикера

        // 1. Назначаем первого спикера
        InterviewUpdateDTO assignDTO = new InterviewUpdateDTO();
        assignDTO.setSpeakerId(JsonNullable.of(speaker1.getId()));
        interviewMapper.updateInterview(assignDTO, interview);
        assertThat(interview.getSpeaker()).isEqualTo(speaker1);

        // 2. Меняем на второго спикера
        InterviewUpdateDTO changeDTO = new InterviewUpdateDTO();
        changeDTO.setSpeakerId(JsonNullable.of(speaker2.getId()));
        interviewMapper.updateInterview(changeDTO, interview);
        assertThat(interview.getSpeaker()).isEqualTo(speaker2);

        // 3. Убираем спикера (явный null)
        InterviewUpdateDTO removeDTO = new InterviewUpdateDTO();
        removeDTO.setSpeakerId(JsonNullable.of(null));
        interviewMapper.updateInterview(removeDTO, interview);
        assertThat(interview.getSpeaker()).isNull();
    }
}