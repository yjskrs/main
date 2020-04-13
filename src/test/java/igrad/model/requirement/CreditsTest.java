package igrad.model.requirement;

//@@author yjskrs

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
        assertThrows(IllegalArgumentException.class, () -> new Credits(0, 8, 0));
        assertThrows(IllegalArgumentException.class, () -> new Credits(8, 0, 8));
    }

    @Test
    public void isValidCreditsOneParameter() {
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
        assertFalse(Credits.isValidCredits("0")); // value 0
        assertFalse(Credits.isValidCredits("-1")); // negative

        // valid credits
        assertTrue(Credits.isValidCredits("1"));
        assertTrue(Credits.isValidCredits("10"));
        assertTrue(Credits.isValidCredits("010")); // starts with 0
    }

    @Test
    public void isValidCreditsThreeParameters() {
        int zeroValue = 0;
        int negativeValue = -1;
        int valueA = 40; // A is bigger than B
        int valueB = 20; // B is bigger than C
        int valueC = 10; // C is the smallest of ABC

        // test that required credits cant be 0
        assertFalse(Credits.isValidCredits(zeroValue, valueB, valueC));

        // test that the other two fields can be 0
        assertTrue(Credits.isValidCredits(valueA, zeroValue, zeroValue));

        // test that all fields cant be less than 0
        assertFalse(Credits.isValidCredits(negativeValue, valueB, valueC));
        assertFalse(Credits.isValidCredits(valueA, negativeValue, valueC));
        assertFalse(Credits.isValidCredits(valueA, valueB, negativeValue));

        // test that fulfilled must not be more than assigned
        assertFalse(Credits.isValidCredits(valueA, valueB, valueA));

        // test that fulfilled and assigned can be equal
        assertTrue(Credits.isValidCredits(valueA, valueB, valueB));

        // test that all three can be equal
        assertTrue(Credits.isValidCredits(valueB, valueB, valueB));
    }

    @Test
    public void isFulfilled_creditsFulfilledLessThanCreditsRequired_returnsFalse() {
        Credits creditsWithOneParameterConstructor = new Credits("40");
        assertFalse(creditsWithOneParameterConstructor.isFulfilled());

        Credits creditsWithTwoParameterConstructor = new Credits(20, 4, 4);
        assertFalse(creditsWithTwoParameterConstructor.isFulfilled());
    }

    @Test
    public void isFulfilled_creditsFulfilledMoreThanOrEqualsCreditsRequired_returnsTrue() {
        Credits creditsWithTwoParameterSameArguments = new Credits(20, 20, 20);
        assertTrue(creditsWithTwoParameterSameArguments.isFulfilled());

        Credits creditsWithTwoParameterDifferentArguments = new Credits(20, 32, 32);
        assertTrue(creditsWithTwoParameterDifferentArguments.isFulfilled());
    }

    @Test
    public void equals() {
        Credits credA = new Credits("4");
        Credits credB = new Credits("4");
        Credits credC = new Credits(4, 0, 0);
        Credits credD = new Credits(4, 4, 0);
        Credits credE = new Credits(4, 4, 4);
        Credits credF = new Credits("8");

        assertTrue(credA.equals(credA));
        assertTrue(credA.equals(credB));
        assertTrue(credA.equals(credC));
        assertFalse(credA.equals(credD));
        assertFalse(credA.equals(credE));
        assertFalse(credA.equals(credF));
    }
}
