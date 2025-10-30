package io.hexlet.cv.dto.marketing;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReviewCreateDTO {
    @NotBlank(message = "Автор обязателен")
    private String author;

    @NotBlank(message = "Содержание обязательно")
    private String content;

    @JsonProperty("avatar_url")
    private String avatarUrl;

    @JsonProperty("is_published")
    private Boolean isPublished = false;

    @NotNull
    @JsonProperty("show_on_homepage")
    private Boolean showOnHomepage;

    @JsonProperty("display_order")
    private Integer displayOrder = 0;
}
