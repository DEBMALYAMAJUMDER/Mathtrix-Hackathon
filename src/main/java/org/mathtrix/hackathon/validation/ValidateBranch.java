package org.mathtrix.hackathon.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import org.mathtrix.hackathon.validation.validator.BranchValidator;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = BranchValidator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidateBranch {
    String message() default "Branch must be either 'main' or 'master'";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
