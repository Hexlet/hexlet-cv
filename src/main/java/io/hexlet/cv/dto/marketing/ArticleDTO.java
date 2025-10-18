package io.hexlet.cv.dto.marketing;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ArticleDTO {
    private Long id;
    private String title;
    private String content;

    @JsonProperty("image_url")
    private String imageUrl;

    private String author;

    @JsonProperty("reading_time")
    private Integer readingTime;

    @JsonProperty("is_published")
    private Boolean isPublished;

    @JsonProperty("home_component_id")
    private String homeComponentId;

    @JsonProperty("display_order")
    private Integer displayOrder;

    @JsonProperty("publishedAt")
    private LocalDateTime publishedAt;

    @JsonProperty("created_at")
    private LocalDateTime createdAt;

    @JsonProperty("updatedAt")
    private LocalDateTime updatedAt;
}
