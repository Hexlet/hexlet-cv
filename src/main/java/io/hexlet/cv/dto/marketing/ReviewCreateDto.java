package io.hexlet.cv.dto.marketing;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.URL;

@Getter
@Setter
public class ReviewCreateDto {
    @NotBlank(message = "Author name is required")
    private String author;

    @NotBlank(message = "Review content is required")
    private String content;

    @URL(message = "Avatar URL must be valid")
    private String avatarUrl;

    private Boolean isPublished = false;

    @NotNull(message = "Homepage visibility is required")
    private Boolean showOnHomepage;

    @Min(value = 0, message = "Display order must be positive")
    private Integer displayOrder = 0;
}
