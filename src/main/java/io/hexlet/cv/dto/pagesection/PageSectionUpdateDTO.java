package io.hexlet.cv.dto.pagesection;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.openapitools.jackson.nullable.JsonNullable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PageSectionUpdateDTO {

    @NotBlank(message = "Техническое название страницы с секцией обязательно")
    private JsonNullable<String> pageKey;

    @NotBlank(message = "Техническое название обязательно")
    private JsonNullable<String> sectionKey;

    private JsonNullable<String> title;
    private JsonNullable<String> content;

    @NotNull(message = "Статус активности секции обязателен")
    private JsonNullable<Boolean> active;
}
