package io.hexlet.cv.dto.pagesection;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class PageSectionCreateDTO {

    // "main", "profile" и др.
    @NotBlank(message = "Техническое название страницы с секцией обязательно")
    private String pageKey;

    // "about_us", "team", "pricing" и др.
    @NotBlank(message = "Техническое название секции обязательно")
    private String sectionKey;

    private String title;
    private String content;

    // Можно не указывать, включена ли секция - сервис установит true по умолчанию
    private Boolean active;
}
