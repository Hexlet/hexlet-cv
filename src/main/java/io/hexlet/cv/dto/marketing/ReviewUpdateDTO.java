package io.hexlet.cv.dto.marketing;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import org.openapitools.jackson.nullable.JsonNullable;

@Getter
@Setter
public class ReviewUpdateDTO {
    @NotBlank(message = "Автор обязателен")
    private JsonNullable<String> author;

    @NotBlank(message = "Содержание обязательно")
    private JsonNullable<String> content;

    @JsonProperty("avatar_url")
    private JsonNullable<String> avatarUrl;

    @JsonProperty("is_published")
    private JsonNullable<Boolean> isPublished;

    @JsonProperty("show_on_homepage")
    private JsonNullable<Boolean> showOnHomepage;

    @JsonProperty("display_order")
    private JsonNullable<Integer> displayOrder;
}
