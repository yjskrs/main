package igrad.model.requirement;

import static igrad.testutil.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertFalse;
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
        assertThrows(IllegalArgumentException.class, () -> new Credits(invalidCredits));
    }

    @Test
    public void isValidCredits() {
        // null credits
        assertThrows(NullPointerException.class, () -> Credits.isValidCredits(null));

        // invalid credits
        assertFalse(Credits.isValidCredits("")); // empty string
        assertFalse(Credits.isValidCredits(" ")); // whitespace only
        assertFalse(Credits.isValidCredits("a")); // alphabet only
        assertFalse(Credits.isValidCredits(" 123")); // integer string starting with whitespace
        assertFalse(Credits.isValidCredits("123 ")); // integer string ending with whitespace
        assertFalse(Credits.isValidCredits("12 12")); // space-separated integer string
        assertFalse(Credits.isValidCredits("12.3")); // non-integer number
        assertFalse(Credits.isValidCredits("12+4")); // special character

        // valid credits
        assertTrue(Credits.isValidCredits("1"));
        assertTrue(Credits.isValidCredits("10"));
        assertTrue(Credits.isValidCredits("0"));
        assertTrue(Credits.isValidCredits("01")); // starts with 0
        assertTrue(Credits.isValidCredits("010")); // starts with 0
    }

    @Test
    public void isFulfilled_creditsFulfilledLessThanCreditsRequired_returnsFalse() {
        Credits creditsWithOneParameterConstructor = new Credits("40");
        assertFalse(creditsWithOneParameterConstructor.isFulfilled());

        Credits creditsWithTwoParameterConstructor = new Credits(20, 4);
        assertFalse(creditsWithTwoParameterConstructor.isFulfilled());
    }

    @Test
    public void isFulfilled_creditsFulfilledMoreThanOrEqualsCreditsRequired_returnsTrue() {
        Credits creditsWithOneParameterConstructor = new Credits("0");
        assertTrue(creditsWithOneParameterConstructor.isFulfilled());

        Credits creditsWithTwoParameterSameArguments = new Credits(20, 20);
        assertTrue(creditsWithTwoParameterSameArguments.isFulfilled());

        Credits creditsWithTwoParameterDifferentArguments = new Credits(20, 32);
        assertTrue(creditsWithTwoParameterDifferentArguments.isFulfilled());
    }
}
