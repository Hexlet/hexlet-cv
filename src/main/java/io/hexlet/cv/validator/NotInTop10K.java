package io.hexlet.cv.validator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.annotation.ElementType;

@Documented
@Constraint(validatedBy = NotInTop10KValidator.class)
@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface NotInTop10K {
    String message() default "Слишком распространённый пароль";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}