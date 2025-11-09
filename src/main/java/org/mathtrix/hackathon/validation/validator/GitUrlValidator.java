package org.mathtrix.hackathon.validation.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.mathtrix.hackathon.validation.ValidateUrl;

import java.util.regex.Pattern;

public class GitUrlValidator implements ConstraintValidator<ValidateUrl, String> {

    // Supports GitHub, GitLab, Bitbucket, Azure DevOps — but NOT ending with .git
    private static final Pattern GIT_URL_PATTERN = Pattern.compile(
            "^(https://|git@)([\\w.-]+)(/|:)[\\w.-]+/[\\w.-]+/?$",
            Pattern.CASE_INSENSITIVE
    );

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || value.isBlank()) {
            return false;
        }
        return GIT_URL_PATTERN.matcher(value).matches();
    }
}
