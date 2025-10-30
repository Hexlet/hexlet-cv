package io.hexlet.cv.dto.marketing;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ArticleCreateDTO {
    @NotBlank(message = "Название статьи обязательно")
    private String title;

    @NotBlank(message = "Содержание обязательно")
    private String content;

    @JsonProperty("image_url")
    private String imageUrl;

    private String author;

    @JsonProperty("reading_time")
    private Integer readingTime;

    @NotNull
    @JsonProperty("is_published")
    private Boolean isPublished = false;

    @JsonProperty("home_component_id")
    private String homeComponentId;

    @NotNull
    @JsonProperty("show_on_homepage")
    private Boolean showOnHomepage = false;

    @JsonProperty("display_order")
    private Integer displayOrder = 0;
}
