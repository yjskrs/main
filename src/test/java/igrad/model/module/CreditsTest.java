package igrad.model.module;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class CreditsTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Credits(null));
    }

    @Test
    public void constructor_invalidCredits_throwsIllegalArgumentException() {
        String invalidCredits = "";
        assertThrows(IllegalArgumentException.class, () -> new Semester(invalidCredits));
    }

    @Test
    public void isValidCredits() {
        // null credits
        // assertThrows(NullPointerException.class, () -> Credits.isValidCredits(null));

        // invalid credits
        assertFalse(Credits.isValidCredits("")); // empty string

        // invalid credits
        assertFalse(Credits.isValidCredits("abc")); // malformed int

        // valid credits
        assertTrue(Credits.isValidCredits("4"));
    }
}
