package io.hexlet.cv.dto.reset;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PasswordResetForm {

    @NotBlank(message = "Token cannot be blank")
    private String token;

    @NotBlank(message = "Password cannot be blank")
    @Size(min = 8, message = "Password must be at least 8 characters")
    @Pattern(
            regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$",
            message = "Password must contain at least one uppercase letter, one lowercase letter,"
                    + " one number and one special character"
    )
    private String password;


    @NotBlank(message = "Password confirmation cannot be blank")
    private String confirmPassword;

    private boolean autoGenerate = false;

    @AssertTrue(message = "Passwords do not match")
    public boolean isPasswordsMatch() {
        if (autoGenerate) {
            return true;
        }
        return password != null && password.equals(confirmPassword);
    }

    @AssertTrue(message = "Password cannot be blank when autoGenerate is false")
    public boolean isPasswordValidWhenNotAutoGenerate() {
        if (autoGenerate) {
            return true;
        }
        return password != null && !password.isBlank();
    }
}
