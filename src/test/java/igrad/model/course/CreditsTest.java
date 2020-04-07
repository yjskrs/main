package igrad.model.course;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class CreditsTest {
    @Test
    public void isValidCreditsRequired() {
        // invalid credits required
        assertFalse(Credits.isValidCreditsRequired(-1)); // negative number
        assertFalse(Credits.isValidCreditsRequired(0)); // value 0

        // valid credits required
        assertTrue(Credits.isValidCreditsRequired(1)); // positive number
    }

    @Test
    public void isValidCreditsFulfilled() {
        // invalid credits fulfilled
        assertFalse(Credits.isValidCreditsFulfilled(-1)); // negative number

        // valid credits fulfilled
        assertTrue(Credits.isValidCreditsFulfilled(0)); // value 0
        assertTrue(Credits.isValidCreditsFulfilled(1)); // positive number
    }
}
