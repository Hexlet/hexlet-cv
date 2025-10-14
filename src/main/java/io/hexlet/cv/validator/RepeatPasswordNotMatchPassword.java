package io.hexlet.cv.validator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Constraint(validatedBy = RepeatPasswordNotMatchPasswordValidator.class)
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface RepeatPasswordNotMatchPassword {
    Class<?>[] groups() default {};
    String message() default "The repeated password must match the password";
    Class<? extends Payload>[] payload() default {};
}
