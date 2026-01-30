package io.hexlet.cv.dto.knowledge;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class KnowledgeArticleDto {

    @NotNull
    private Long id;

    @NotBlank(message = "Title cannot be empty")
    private String title;

    private String description;

    @NotBlank(message = "Category cannot be empty")
    private String category;
    private String readingTime;
    private String imageUrl;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime publishedAt;
}
