package io.hexlet.cv.dto.interview;

import lombok.Getter;
import lombok.Setter;
import org.openapitools.jackson.nullable.JsonNullable;

@Getter
@Setter
public class InterviewUpdateDTO {
    private JsonNullable<String> title;

    private JsonNullable<Long> speakerId;

    private JsonNullable<String> videoLink;

    private JsonNullable<Boolean> isPublished;
}
