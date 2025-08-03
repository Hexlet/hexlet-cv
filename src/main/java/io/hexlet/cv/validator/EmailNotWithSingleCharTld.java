package io.hexlet.cv.validator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = EmailNotWithSingleCharTldValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface EmailNotWithSingleCharTld {
    String message() default "TLD email должен содержать как минимум 2 символа";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}