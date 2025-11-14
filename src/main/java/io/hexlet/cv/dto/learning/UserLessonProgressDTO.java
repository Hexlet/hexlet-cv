package io.hexlet.cv.dto.learning;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserLessonProgressDTO {
    private Long id;

    @NotNull(message = "Статус завершения не может быть пустым")
    @JsonProperty("is_completed")
    private Boolean isCompleted;

    @NotNull(message = "Время начала не может быть пустым")
    @JsonProperty("started_at")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSSSS")
    private LocalDateTime startedAt;

    @JsonProperty("completed_at")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSSSS")
    private LocalDateTime completedAt;

    @PositiveOrZero(message = "Потраченное время не может быть отрицательным")
    private Integer timeSpentMinutes;

    private Long lessonId;
    private Long programProgressId;
    private Long userId;

    @NotBlank(message = "Название урока не может быть пустым")
    @JsonProperty("lesson_title")
    private String lessonTitle;
}
