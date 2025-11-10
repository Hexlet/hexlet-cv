package io.hexlet.cv.dto.marketing;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import org.openapitools.jackson.nullable.JsonNullable;

@Getter
@Setter
public class StoryUpdateDTO {

    @NotBlank(message = "Заголовок обязателен")
    private JsonNullable<String> title;

    @NotBlank(message = "Содержание обязательно")
    private JsonNullable<String> content;

    @JsonProperty("image_url")
    private JsonNullable<String> imageUrl;

    @JsonProperty("is_published")
    private JsonNullable<Boolean> isPublished;

    @JsonProperty("show_on_homepage")
    private JsonNullable<Boolean> showHomepage;

    @JsonProperty("display_order")
    private JsonNullable<Integer> displayOrder;
}
