package io.hexlet.cv.dto.marketing;

import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ArticleDto {
    private Long id;
    private String title;
    private String content;
    private String imageUrl;
    private String author;
    private Integer readingTime;
    private Boolean isPublished;
    private String homeComponentId;
    private Boolean showOnHomepage;
    private Integer displayOrder;
    private LocalDateTime publishedAt;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
