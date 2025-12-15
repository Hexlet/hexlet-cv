package io.hexlet.cv.dto.learning;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserLessonProgressDTO {
    private Long id;

    @NotNull(message = "The completion status cannot be empty.")
    private Boolean isCompleted;

    @NotNull(message = "The start time cannot be empty")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime startedAt;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime completedAt;

    @PositiveOrZero(message = "The time spent cannot be negative")
    private Integer timeSpentMinutes;

    private Long lessonId;
    private Long programProgressId;
    private Long userId;

    @NotBlank(message = "The lesson title cannot be empty")
    private String lessonTitle;
}
