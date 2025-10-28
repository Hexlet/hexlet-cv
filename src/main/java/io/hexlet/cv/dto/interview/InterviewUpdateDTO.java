package io.hexlet.cv.dto.interview;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import org.openapitools.jackson.nullable.JsonNullable;

@Getter
@Setter
public class InterviewUpdateDTO {
    private JsonNullable<@NotBlank(message = "Title is required.") String> title;

    private JsonNullable<Long> speakerId;

    private JsonNullable<String> videoLink;

    private JsonNullable<Boolean> isPublished;
}
