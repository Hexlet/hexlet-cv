package io.hexlet.cv.dto.marketing;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import org.openapitools.jackson.nullable.JsonNullable;

@Getter
@Setter
public class ArticleUpdateDTO {
    private JsonNullable<String> title;
    private JsonNullable<String> content;

    @JsonProperty("image_url")
    private JsonNullable<String> imageUrl;

    private JsonNullable<String> author;

    @JsonProperty("reading_time")
    private JsonNullable<Integer> readingTime;

    @JsonProperty("is_published")
    private JsonNullable<Boolean> isPublished;

    @JsonProperty("home_component_id")
    private JsonNullable<String> homeComponentId;

    @JsonProperty("show_on_homePage")
    private JsonNullable<Boolean> showOnHomepage;

    @JsonProperty("display_order")
    private JsonNullable<Integer> displayOrder;
}
