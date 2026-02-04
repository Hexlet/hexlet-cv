package io.hexlet.cv.dto.marketing;

import lombok.Getter;
import lombok.Setter;
import org.openapitools.jackson.nullable.JsonNullable;

@Getter
@Setter
public class ArticleUpdateDto {
    private JsonNullable<String> title;
    private JsonNullable<String> content;
    private JsonNullable<String> imageUrl;
    private JsonNullable<String> author;
    private JsonNullable<Integer> readingTime;
    private JsonNullable<Boolean> isPublished;
    private JsonNullable<String> homeComponentId;
    private JsonNullable<Boolean> showOnHomepage;
    private JsonNullable<Integer> displayOrder;
}
