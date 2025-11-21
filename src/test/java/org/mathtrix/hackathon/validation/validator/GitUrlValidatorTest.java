package org.mathtrix.hackathon.validation.validator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class GitUrlValidatorTest {

    private GitUrlValidator validator;

    @BeforeEach
    void setUp() {
        validator = new GitUrlValidator();
    }

    @Test
    void testIsValid_NullOrBlank() {
        assertFalse(validator.isValid(null, null), "Should be false for null input");
        assertFalse(validator.isValid("", null), "Should be false for empty input");
        assertFalse(validator.isValid("   ", null), "Should be false for blank input");
    }

    @Test
    void testIsValid_ValidHttpsUrls() {
        assertTrue(validator.isValid("https://github.com/username/repo", null));
        assertTrue(validator.isValid("https://gitlab.com/group/project", null));
        assertTrue(validator.isValid("https://bitbucket.org/user/repo", null));
        // Although comment says "NOT ending with .git", regex allows it. Testing actual behavior:
        assertTrue(validator.isValid("https://github.com/username/repo.git", null));
    }

    @Test
    void testIsValid_ValidSshUrls() {
        assertTrue(validator.isValid("git@github.com:username/repo", null));
        assertTrue(validator.isValid("git@gitlab.com:group/project", null));
        // Test with dots in repo name
        assertTrue(validator.isValid("git@bitbucket.org:user/repo.name", null));
    }

    @Test
    void testIsValid_InvalidUrls() {
        // HTTP not supported by regex (only https:// or git@)
        assertFalse(validator.isValid("http://github.com/username/repo", null));

        // Missing user/repo structure
        assertFalse(validator.isValid("https://github.com/username", null));
        assertFalse(validator.isValid("git@github.com:username", null));

        // Invalid start
        assertFalse(validator.isValid("ftp://github.com/username/repo", null));
        assertFalse(validator.isValid("ssh://github.com/username/repo", null));

        // Invalid separators or path
        assertFalse(validator.isValid("https://github.com/username//repo", null));
    }

    @Test
    void testIsValid_EdgeCases() {
        // Trailing slash allowed by regex /?$
        assertTrue(validator.isValid("https://github.com/username/repo/", null));

        // User/Repo with dots and dashes
        assertTrue(validator.isValid("https://github.com/user.name/repo-name", null));
        assertTrue(validator.isValid("git@github.com:user-name/repo.name", null));

        // Just separators
        assertFalse(validator.isValid("https:///", null));
    }
}
