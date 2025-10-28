package io.hexlet.cv.dto.interview;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InterviewCreateDTO {
    @NotBlank(message = "Title is required.")
    private String title;

    private Long speakerId;

    private String videoLink = "";

    private Boolean isPublished = false;
}
