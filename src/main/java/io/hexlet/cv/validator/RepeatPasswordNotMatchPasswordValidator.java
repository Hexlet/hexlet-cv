package io.hexlet.cv.validator;

import io.hexlet.cv.dto.user.UserPasswordDto;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class RepeatPasswordNotMatchPasswordValidator implements
        ConstraintValidator<RepeatPasswordNotMatchPassword, UserPasswordDto> {

    @Override
    public boolean isValid(UserPasswordDto dto, ConstraintValidatorContext context) {
        if (dto == null || dto.getNewPassword() == null || dto.getRepeatNewPassword() == null) {
            return true;
        }

        if (!dto.getNewPassword().equals(dto.getRepeatNewPassword())) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(context.getDefaultConstraintMessageTemplate())
                    .addPropertyNode("repeatNewPassword").addConstraintViolation();
            return false;
        }
        return true;
    }
}
