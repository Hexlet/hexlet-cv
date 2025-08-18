package io.hexlet.cv.dto.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UserPasswordDto {
    @NotBlank(message = "Old password required")
    private String oldPassword;
    @NotBlank(message = "New password required")
    @Pattern(
            regexp = "^(?=.*[A-Za-z])(?=.*\\d).{8,}$",
            message = "Password must be at least 8 characters with at least 1 letter and 1 digit"
    )
    private String newPassword;
    @NotBlank(message = "Repeat new password required")
    private String repeatNewPassword;
}
