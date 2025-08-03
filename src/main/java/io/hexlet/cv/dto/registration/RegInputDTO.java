package io.hexlet.cv.dto.registration;

import io.hexlet.cv.validator.EmailDomainViaDnsApi;
import io.hexlet.cv.validator.EmailNotWithSingleCharTld;
import io.hexlet.cv.validator.NotInDisposableEmailDomains;
import io.hexlet.cv.validator.NotInTop10K;
import io.hexlet.cv.validator.PasswordNotSimilarToUser;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@PasswordNotSimilarToUser
public class RegInputDTO {
    @NotBlank(message = "Email обязателен")
    @Email(message = "Укажите корректный email-адрес")
    @EmailNotWithSingleCharTld
    @NotInDisposableEmailDomains
    @EmailDomainViaDnsApi
    private String email;

    @NotBlank(message = "Пароль обязателен")
    @Size(min = 8, message = "Пароль должен быть не менее 8 символов")
    @NotInTop10K
    private String password;

    @NotBlank(message = "Имя обязательно")
    private String firstName;

    @NotBlank(message = "Фамилия обязательна")
    private String lastName;
}
