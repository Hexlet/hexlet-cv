package io.hexlet.cv.dto.marketing;

import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StoryDto {
    private Long id;
    private String title;
    private String content;
    private String imageUrl;
    private Boolean isPublished;
    private Boolean showOnHomepage;
    private Integer displayOrder;
    private LocalDateTime publishedAt;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
