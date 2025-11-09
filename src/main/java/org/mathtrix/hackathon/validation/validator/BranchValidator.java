package org.mathtrix.hackathon.validation.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.mathtrix.hackathon.validation.ValidateBranch;

import java.util.Set;

public class BranchValidator implements ConstraintValidator<ValidateBranch, String> {
    private static final Set<String> ALLOWED_BRANCHES = Set.of("main", "master");

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || value.isBlank()) {
            return false;
        }
        return ALLOWED_BRANCHES.contains(value.trim());
    }
}
