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
    public void constructor_invalidCap_throwsIllegalArgumentException() {
        // Testing overloaded String constructor
        String invalidCapStr = "";
        assertThrows(IllegalArgumentException.class, () -> new Cap(invalidCapStr));

        // Testing overloaded Double constructor
        double invalidCapDouble = 6.0;
        assertThrows(IllegalArgumentException.class, () -> new Cap(invalidCapDouble));
    }

    @Test
    public void isValidCap() {
        // Testing overloaded String method

        // null cap
        assertThrows(NullPointerException.class, () -> Cap.isValidCap(null));

        // invalid cap
        assertFalse(Cap.isValidCap("")); // empty string
        assertFalse(Cap.isValidCap(" ")); // whitespace only
        assertFalse(Cap.isValidCap("a")); // alphabet only
        assertFalse(Cap.isValidCap(" 1")); // integer (cap) string starting with whitespace
        assertFalse(Cap.isValidCap("1 ")); // integer (cap) string ending with whitespace
        assertFalse(Cap.isValidCap(" 1.00")); // double (cap) string starting with whitespace
        assertFalse(Cap.isValidCap("1.00 ")); // double (cap) string ending with whitespace
        assertFalse(Cap.isValidCap("44")); // integer (cap) string more than one non-decimal digit
        assertFalse(Cap.isValidCap("44.00")); // double (cap) string more than one non-decimal digit
        assertFalse(Cap.isValidCap("4.4 0")); // space-separated double (cap) string
        assertFalse(Cap.isValidCap("4.4+0")); // special character-separated double (cap) string
        assertFalse(Cap.isValidCap("4..40")); // special character-separated double (cap) string
        assertFalse(Cap.isValidCap("4.40.40")); // double (cap) string multiple decimal segments
        assertFalse(Cap.isValidCap("4.")); // double (cap) string with only trailing decimal point
        assertFalse(Cap.isValidCap("5.01")); // cap above 5

        // valid cap
        assertTrue(Cap.isValidCap("4")); // integer (cap) string only
        assertTrue(Cap.isValidCap("4.40")); // double (cap) string
        assertTrue(Cap.isValidCap("4.4000000000000001")); // cap with many precisions

        // null credits
        assertThrows(NullPointerException.class, () -> Cap.isValidCap(null));

        // invalid credits

    }
}
