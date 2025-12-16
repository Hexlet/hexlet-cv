package io.hexlet.cv.dto.marketing;

import com.fasterxml.jackson.annotation.JsonFormat;
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
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime publishedAt;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime createdAt;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime updatedAt;
}
