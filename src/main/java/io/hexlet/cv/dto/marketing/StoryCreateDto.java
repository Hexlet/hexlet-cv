package io.hexlet.cv.dto.marketing;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StoryCreateDto {
    private String title;
    private String content;
    private String imageUrl;
    private Boolean isPublished = false;

    @NotNull
    private Boolean showOnHomepage;
    private Integer displayOrder = 0;
}
