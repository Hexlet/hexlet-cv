package io.hexlet.cv.dto.marketing;

import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReviewDto {
    private Long id;
    private String author;
    private String content;
    private String avatarUrl;
    private Boolean isPublished;
    private Boolean showOnHomepage;
    private Integer displayOrder;
    private LocalDateTime publishedAt;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
