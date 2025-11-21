package org.mathtrix.hackathon.validation.validator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class BranchValidatorTest {

    private BranchValidator branchValidator;

    @BeforeEach
    void setUp() {
        branchValidator = new BranchValidator();
    }

    @Test
    void testIsValid_NullValue() {
        assertFalse(branchValidator.isValid(null, null), "Should return false for null value");
    }

    @Test
    void testIsValid_EmptyOrBlankValue() {
        assertFalse(branchValidator.isValid("", null), "Should return false for empty value");
        assertFalse(branchValidator.isValid("   ", null), "Should return false for blank value");
    }

    @Test
    void testIsValid_ValidBranches() {
        // Exact matches
        assertTrue(branchValidator.isValid("MASTER", null), "Should return true for MASTER");
        assertTrue(branchValidator.isValid("MAIN", null), "Should return true for MAIN");

        // Case insensitivity
        assertTrue(branchValidator.isValid("master", null), "Should return true for master (lowercase)");
        assertTrue(branchValidator.isValid("main", null), "Should return true for main (lowercase)");
        assertTrue(branchValidator.isValid("MaStEr", null), "Should return true for MaStEr (mixed case)");

        // Trimming
        assertTrue(branchValidator.isValid(" MASTER ", null), "Should return true for MASTER with spaces");
        assertTrue(branchValidator.isValid(" main ", null), "Should return true for main with spaces");
    }

    @Test
    void testIsValid_InvalidBranch() {
        assertFalse(branchValidator.isValid("DEVELOP", null), "Should return false for DEVELOP");
        assertFalse(branchValidator.isValid("feature/abc", null), "Should return false for feature/abc");
        assertFalse(branchValidator.isValid("unknown", null), "Should return false for unknown branch");
    }
}