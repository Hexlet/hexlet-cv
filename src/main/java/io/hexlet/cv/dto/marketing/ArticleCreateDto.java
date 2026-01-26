package io.hexlet.cv.dto.marketing;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ArticleCreateDto {
    @NotBlank(message = "Title is required")
    private String title;

    @NotBlank(message = "Title is required")
    private String content;
    private String imageUrl;
    private String author;

    @Min(value = 1, message = "Reading time must be at least 1 minute")
    private Integer readingTime;

    @NotNull(message = "Published status is required")
    private Boolean isPublished = false;
    private String homeComponentId;

    @NotNull(message = "Homepage visibility is required")
    private Boolean showOnHomepage = false;

    @Min(value = 0, message = "Display order must be positive")
    private Integer displayOrder = 0;
}
