package org.mathtrix.hackathon.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import org.mathtrix.hackathon.validation.validator.GitUrlValidator;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = GitUrlValidator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidateUrl {
    String message() default "Invalid Git repository URL";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
