package io.hexlet.cv.dto.marketing;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

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

    @JsonProperty("show_on_homepage")
    private Boolean showOnHomepage;

    @JsonProperty("display_order")
    private Integer displayOrder;

    @JsonProperty("published_at")
    private LocalDateTime publishedAt;

    @JsonProperty("created_at")
    private LocalDateTime createdAt;

    @JsonProperty("updated_at")
    private LocalDateTime updatedAt;
}
