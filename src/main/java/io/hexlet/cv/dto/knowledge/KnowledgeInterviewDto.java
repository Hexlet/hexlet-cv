package io.hexlet.cv.dto.knowledge;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class KnowledgeInterviewDto {
    @NotNull(message = "The interview ID cannot be empty")
    private Long id;

    @NotBlank(message = "Title cannot be empty")
    private String title;

    private String description;

    @NotBlank(message = "Category cannot be empty")
    private String category;
    private String duration;

    @NotNull(message = "The number of questions cannot be empty")
    @Min(value = 1, message = "The number of questions should be positive")
    private Integer questionsCount;

    private String imageUrl;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime publishedAt;
}
