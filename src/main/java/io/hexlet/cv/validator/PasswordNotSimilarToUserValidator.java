package io.hexlet.cv.validator;


import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import io.hexlet.cv.dto.registration.RegInputDTO;

public class PasswordNotSimilarToUserValidator implements ConstraintValidator<PasswordNotSimilarToUser, RegInputDTO> {

    @Override
    public boolean isValid(RegInputDTO dto, ConstraintValidatorContext context) {
        if (dto == null) return true;

        String password = dto.getPassword();
        if (password == null) return true;

        String email = dto.getEmail() != null ? dto.getEmail().toLowerCase() : "";
        String firstName = dto.getFirstName() != null ? dto.getFirstName().toLowerCase() : "";
        String lastName = dto.getLastName() != null ? dto.getLastName().toLowerCase() : "";

        String pwdLower = password.toLowerCase();

        if (pwdLower.contains(email) || pwdLower.contains(firstName) || pwdLower.contains(lastName)) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(context.getDefaultConstraintMessageTemplate())
                    .addPropertyNode("password")
                    .addConstraintViolation();
            return false;
        }

        return true;
    }
}