package io.hexlet.cv.dto.marketing;

import lombok.Getter;
import lombok.Setter;
import org.openapitools.jackson.nullable.JsonNullable;

@Getter
@Setter
public class StoryUpdateDto {

    private JsonNullable<String> title;
    private JsonNullable<String> content;
    private JsonNullable<String> imageUrl;
    private JsonNullable<Boolean> isPublished;
    private JsonNullable<Boolean> showOnHomepage;
    private JsonNullable<Integer> displayOrder;
}
