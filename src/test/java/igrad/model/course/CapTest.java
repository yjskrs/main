package igrad.model.course;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class CapTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Cap(null));
    }

    @Test
    public void constructor_invalidName_throwsIllegalArgumentException() {
        String invalidCap = "";
        assertThrows(IllegalArgumentException.class, () -> new Cap(invalidCap));
    }

    @Test
    public void isValidCap() {
        // null cap
        assertThrows(NullPointerException.class, () -> Cap.isValidCap(null));

        // invalid cap
        assertFalse(Cap.isValidCap("")); // empty string
        assertFalse(Cap.isValidCap("-1.0")); //negative value

        // valid cap
        assertTrue(Cap.isValidCap("4.5"));
    }
}
