package io.hexlet.cv.dto.pagesection;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PageSectionCreateDTO {

    @NotBlank(message = "Техническое название обязательно")
    private String sectionKey;

    private String title;
    private String content;

    // Можно не указывать, включена ли секция - маппер установит true по умолчанию
    private Boolean active;
}
