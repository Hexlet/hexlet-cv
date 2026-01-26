package io.hexlet.cv.config;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import io.hexlet.cv.dto.learning.UserLessonProgressDTO;
import io.hexlet.cv.dto.learning.UserProgramProgressDTO;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class JacksonConfigTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testTimestampSerializationDisabled() {
        boolean isEnabled = objectMapper.isEnabled(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        System.out.println("WRITE_DATES_AS_TIMESTAMPS enabled: " + isEnabled);

        System.out.println("Registered modules: " + objectMapper.getRegisteredModuleIds());

        assertThat(isEnabled).isFalse();
    }

    @Test
    public void testLocalDateTimeFormatIsCentralized() throws JsonProcessingException {
        TestDateTimeDto dto = new TestDateTimeDto();
        dto.setDateTime(LocalDateTime.of(2024, 1, 15, 14, 30));
        dto.setName("Тест формата даты-времени");

        String json = objectMapper.writeValueAsString(dto);

        System.out.println("JSON: " + json);

        assertThat(json)
                .contains("\"dateTime\":\"2024-01-15 14:30\"")
                .doesNotContain("2024-01-15T14:30:00")
                .doesNotContain("[2024,1,15,14,30]");
    }

    @Test
    public void testLocalDateFormat() throws JsonProcessingException {
        TestDateTimeDto dto = new TestDateTimeDto();
        dto.setDate(LocalDate.of(2024, 12, 31));
        dto.setName("Тест формата даты");

        String json = objectMapper.writeValueAsString(dto);

        System.out.println("JSON: " + json);

        assertThat(json)
                .contains("\"date\":\"2024-12-31\"");
    }

    @Test
    public void testLocalTimeFormat() throws JsonProcessingException {
        TestDateTimeDto dto = new TestDateTimeDto();
        dto.setTime(LocalTime.of(23, 59));
        dto.setName("Тест формата времени");

        String json = objectMapper.writeValueAsString(dto);

        System.out.println("JSON: " + json);

        assertThat(json)
                .contains("\"time\":\"23:59\"");
    }

    @Test
    public void testDeserializationWorks() throws JsonProcessingException {
        String json = "{\"dateTime\":\"2024-01-15 14:30\",\"name\":\"Десериализация\"}";

        TestDateTimeDto dto = objectMapper.readValue(json, TestDateTimeDto.class);

        assertThat(dto.getDateTime())
                .isEqualTo(LocalDateTime.of(2024, 1, 15, 14, 30));
        assertThat(dto.getName()).isEqualTo("Десериализация");
    }

    @Test
    public void testUserProgramProgressDTOFormat() throws JsonProcessingException {
        var dto = UserProgramProgressDTO.builder()
                .id(1L)
                .startedAt(LocalDateTime.of(2024, 1, 15, 9, 0))
                .lastActivityAt(LocalDateTime.of(2024, 1, 15, 17, 30))
                .programTitle("Программа обучения Java")
                .completedLessons(5)
                .totalLessons(10)
                .progressPercentage(50)
                .build();

        String json = objectMapper.writeValueAsString(dto);

        System.out.println("=== UserProgramProgressDTO Test ===");
        System.out.println("JSON: " + json);

        assertThat(json)
                .contains("\"startedAt\":\"2024-01-15 09:00\"")
                .contains("\"lastActivityAt\":\"2024-01-15 17:30\"")
                .contains("\"programTitle\":\"Программа обучения Java\"");
    }

    @Test
    public void testUserLessonProgressDTOFormat() throws JsonProcessingException {
        var dto = UserLessonProgressDTO.builder()
                .id(1L)
                .startedAt(LocalDateTime.of(2024, 1, 15, 10, 0))
                .completedAt(LocalDateTime.of(2024, 1, 15, 10, 45))
                .lessonTitle("Введение в Spring Boot")
                .timeSpentMinutes(45)
                .isCompleted(true)
                .build();

        String json = objectMapper.writeValueAsString(dto);

        assertThat(json)
                .contains("\"startedAt\":\"2024-01-15 10:00\"")
                .contains("\"completedAt\":\"2024-01-15 10:45\"")
                .contains("\"lessonTitle\":\"Введение в Spring Boot\"");
    }

    @Test
    public void testEdgeCases() throws JsonProcessingException {
        TestDateTimeDto dto = new TestDateTimeDto();

        dto.setDateTime(LocalDateTime.of(2024, 1, 1, 0, 0));
        String midnightJson = objectMapper.writeValueAsString(dto);
        assertThat(midnightJson).contains("\"dateTime\":\"2024-01-01 00:00\"");

        dto.setDateTime(LocalDateTime.of(2024, 12, 31, 23, 59));
        String endOfDayJson = objectMapper.writeValueAsString(dto);
        assertThat(endOfDayJson).contains("\"dateTime\":\"2024-12-31 23:59\"");
    }
}
