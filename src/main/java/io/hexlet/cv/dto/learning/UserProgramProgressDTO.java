package io.hexlet.cv.dto.learning;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserProgramProgressDTO {
    private Long id;
    private Integer completedLessons;
    private Boolean isCompleted;
    private LocalDateTime startedAt;
    private LocalDateTime lastActivityAt;
    private String programTitle;
    private String lastLessonTitle;
    private Integer totalLessons;
    private Integer progressPercentage;
}
