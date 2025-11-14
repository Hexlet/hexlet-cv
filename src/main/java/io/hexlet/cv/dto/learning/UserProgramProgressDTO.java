package io.hexlet.cv.dto.learning;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserProgramProgressDTO {
    private Long id;

    @JsonProperty("completed_lessons")
    private Integer completedLessons;

    @JsonProperty("is_completed")
    private Boolean isCompleted;

    @JsonProperty("started_at")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime startedAt;

    @JsonProperty("last_activity_at")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime lastActivityAt;

    @JsonProperty("program_title")
    private String programTitle;

    @JsonProperty("last_lesson_title")
    private String lastLessonTitle;

    @JsonProperty("total_lessons")
    private Integer totalLessons;

    @JsonProperty("progress_percentage")
    private Integer progressPercentage;
}
