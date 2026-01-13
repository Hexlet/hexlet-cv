package io.hexlet.cv.dto.knowledge;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class KnowledgeArticleDto {

    @NotNull
    private Long id;
    private String title;
    private String description;
    private String category;
    private String readingTime;
    private String imageUrl;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime publishedAt;
}
