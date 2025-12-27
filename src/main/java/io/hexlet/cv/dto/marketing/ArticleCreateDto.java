package io.hexlet.cv.dto.marketing;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ArticleCreateDto {
    private String title;
    private String content;
    private String imageUrl;
    private String author;
    private Integer readingTime;

    @NotNull
    private Boolean isPublished = false;
    private String homeComponentId;

    @NotNull
    private Boolean showOnHomepage = false;
    private Integer displayOrder = 0;
}
