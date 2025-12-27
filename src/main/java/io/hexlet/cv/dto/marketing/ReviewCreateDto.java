package io.hexlet.cv.dto.marketing;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReviewCreateDto {
    private String author;
    private String content;
    private String avatarUrl;
    private Boolean isPublished = false;

    @NotNull
    private Boolean showOnHomepage;
    private Integer displayOrder = 0;
}
