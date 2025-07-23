package io.hexlet.cv.validator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.lang.annotation.RetentionPolicy;


@Documented
@Constraint(validatedBy = PasswordNotSimilarToUserValidator.class)
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface PasswordNotSimilarToUser {
    String message() default "Пароль слишком простой — не должен совпадать с email или именем";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}