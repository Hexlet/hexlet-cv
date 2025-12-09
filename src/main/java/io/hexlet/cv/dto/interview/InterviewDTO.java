package io.hexlet.cv.dto.interview;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.hexlet.cv.dto.user.UserDTO;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class InterviewDTO {
    private Long id;
    private String title;
    private UserDTO speaker;
    private String videoLink;
    private Boolean isPublished;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime createdAt;
}
