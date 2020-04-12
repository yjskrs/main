package igrad.model.course;

//@@author nathanaelseen

import static igrad.logic.commands.course.CourseCommandTestUtil.VALID_COURSE_CAP_BCOMPSCI;
import static igrad.logic.commands.course.CourseCommandTestUtil.VALID_COURSE_CAP_BCOMPSEC;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import igrad.model.module.Module;
import igrad.testutil.ModuleBuilder;

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
        assertFalse(Cap.isValidCap("5.01")); // double (cap) string  above 5
        assertFalse(Cap.isValidCap("-0.01")); // double (cap) string below 0

        // valid cap
        assertTrue(Cap.isValidCap("4")); // integer (cap) string
        assertTrue(Cap.isValidCap("4.40")); // double (cap) string
        assertTrue(Cap.isValidCap("4.4000000000000001")); // double (cap) string with many precisions
        assertTrue(Cap.isValidCap("0")); // integer (cap) string exactly 0
        assertTrue(Cap.isValidCap("0.0000000000000000")); // double (cap) string exactly 0, with many precisions
        assertTrue(Cap.isValidCap("5")); // integer (cap) string exactly 5
        assertTrue(Cap.isValidCap("5.0000000000000000")); // double (cap) string exactly 5, with many precisions


        // Testing overloaded Double method

        // invalid cap
        assertFalse(Cap.isValidCap(5.01)); // double (cap) above 5
        assertFalse(Cap.isValidCap(-0.01)); // double (cap) below 0


        // valid cap
        assertTrue(Cap.isValidCap(4)); // integer (cap)
        assertTrue(Cap.isValidCap(4.40)); // double (cap)
        assertTrue(Cap.isValidCap(4.4000000000000001)); // double (cap) with many precisions
        assertTrue(Cap.isValidCap(0)); // integer (cap) exactly 0
        assertTrue(Cap.isValidCap(0.0000000000000000)); // double (cap) exactly 0, with many precisions
        assertTrue(Cap.isValidCap(5)); // integer (cap) exactly 5
        assertTrue(Cap.isValidCap(5.0000000000000000)); // double (cap) exactly 5, with many precisions
    }

    @Test
    public void equals() {
        Cap capA;
        Cap capB;

        // null
        capA = new Cap(VALID_COURSE_CAP_BCOMPSCI);
        assertFalse(capA.equals(null));

        // same cap
        capA = new Cap(VALID_COURSE_CAP_BCOMPSCI);
        capB = new Cap(VALID_COURSE_CAP_BCOMPSCI);
        assertTrue(capA.equals(capB));

        // different type
        capA = new Cap(VALID_COURSE_CAP_BCOMPSCI);
        Module module = new ModuleBuilder().build();
        assertFalse(capA.equals(module));

        // different cap
        capA = new Cap(VALID_COURSE_CAP_BCOMPSCI);
        capB = new Cap(VALID_COURSE_CAP_BCOMPSEC);
        assertFalse(capA.equals(capB));
    }
}
