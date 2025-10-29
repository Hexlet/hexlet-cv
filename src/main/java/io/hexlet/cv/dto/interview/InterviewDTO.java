package io.hexlet.cv.dto.interview;

import io.hexlet.cv.dto.user.UserDTO;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InterviewDTO {
    private Long id;
    private String title;
    private UserDTO speaker;
    private String videoLink;
    private Boolean isPublished;
}
