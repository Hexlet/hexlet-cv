package io.hexlet.cv.dto.learning;

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
    private LocalDateTime startedAt;

    private LocalDateTime completedAt;

    @PositiveOrZero(message = "The time spent cannot be negative")
    private Integer timeSpentMinutes;

    private Long lessonId;
    private Long programProgressId;
    private Long userId;

    @NotBlank(message = "The lesson title cannot be empty")
    private String lessonTitle;
}
