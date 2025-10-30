package io.hexlet.cv.dto.marketing;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StoryCreateDTO {
    @NotBlank(message = "Заголовок обязателен")
    private String title;

    @NotBlank(message = "Содержание обязательно")
    private String content;

    @JsonProperty("image_url")
    private String imageUrl;

    @JsonProperty("is_published")
    private Boolean isPublished = false;

    @NotNull
    @JsonProperty("show_on_homepage")
    private Boolean showOnHomePage;

    @JsonProperty("display_order")
    private Integer displayOrder = 0;
}
