package io.hexlet.cv.dto.interview;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InterviewDTO {
    private String title;
    private UserInterviewSummary userInterviewSummary;
    private String videoLink;
    private Boolean isPublished;
}
