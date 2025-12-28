package io.hexlet.cv.dto.marketing;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.URL;

@Getter
@Setter
public class StoryCreateDto {
    @NotBlank(message = "Title is required")
    private String title;

    @NotBlank(message = "Content is required")
    private String content;

    @URL(message = "Image URL must be valid")
    private String imageUrl;
    private Boolean isPublished = false;

    @NotNull(message = "Homepage visibility is required")
    private Boolean showOnHomepage;

    @Min(value = 0, message = "Display order must be positive")
    private Integer displayOrder = 0;
}
