package io.hexlet.cv.dto.reset;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.URL;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmailForm {

    @NotBlank(message = "Email cannot be blank")
    @Email(message = "Email should be valid")
    private String email;

    @NotBlank(message = "Client URL cannot be blank")
    @URL(message = "Client URL must be a valid URL")
    private String clientUrl;

    @Pattern(regexp = "en|ru", message = "Language must be one of: en, ru")
    private String language = "en";
}
