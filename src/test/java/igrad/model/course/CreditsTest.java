package igrad.model.course;

//@@author nathanaelseen

import static igrad.logic.commands.course.CourseCommandTestUtil.VALID_COURSE_CREDITS_FULFILLED_BCOMPSCI;
import static igrad.logic.commands.course.CourseCommandTestUtil.VALID_COURSE_CREDITS_FULFILLED_BCOMPSEC;
import static igrad.logic.commands.course.CourseCommandTestUtil.VALID_COURSE_CREDITS_REQUIRED_BCOMPSCI;
import static igrad.logic.commands.course.CourseCommandTestUtil.VALID_COURSE_CREDITS_REQUIRED_BCOMPSEC;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import igrad.model.module.Module;
import igrad.testutil.ModuleBuilder;

public class CreditsTest {

    @Test
    public void constructor_invalidCredits_throwsIllegalArgumentException() {
        int invalidCreditsRequired;
        int invalidCreditsFulfilled;
        int validCreditsRequired;
        int validCreditsFulfilled;

        // invalid credits required, but valid credits fulfilled
        invalidCreditsRequired = -1;
        validCreditsFulfilled = 1;
        assertThrows(IllegalArgumentException.class, () -> new Credits(
            invalidCreditsRequired, validCreditsFulfilled));

        // invalid credits fulfilled, but valid credits required
        invalidCreditsFulfilled = -1;
        validCreditsRequired = 1;
        assertThrows(IllegalArgumentException.class, () -> new Credits(
            validCreditsRequired, invalidCreditsFulfilled));
    }

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

    @Test
    public void equals() {
        Credits creditsA;
        Credits creditsB;

        // null
        creditsA = new Credits(VALID_COURSE_CREDITS_REQUIRED_BCOMPSCI,
            VALID_COURSE_CREDITS_FULFILLED_BCOMPSCI);
        assertFalse(creditsA.equals(null));

        // same credits
        creditsA = new Credits(VALID_COURSE_CREDITS_REQUIRED_BCOMPSCI,
            VALID_COURSE_CREDITS_FULFILLED_BCOMPSCI);
        creditsB = new Credits(VALID_COURSE_CREDITS_REQUIRED_BCOMPSCI,
            VALID_COURSE_CREDITS_FULFILLED_BCOMPSCI);
        assertTrue(creditsA.equals(creditsB));

        // different type
        creditsA = new Credits(VALID_COURSE_CREDITS_REQUIRED_BCOMPSCI,
            VALID_COURSE_CREDITS_FULFILLED_BCOMPSCI);
        Module module = new ModuleBuilder().build();
        assertFalse(creditsA.equals(module));

        // same credits required but different credits fulfilled
        creditsA = new Credits(4, 3);
        creditsA = new Credits(4, 2);
        assertFalse(creditsA.equals(creditsB));

        // same credits fulfilled but different credits required
        creditsA = new Credits(3, 4);
        creditsA = new Credits(2, 4);
        assertFalse(creditsA.equals(creditsB));

        // different credits required and different credits fulfilled
        creditsA = new Credits(VALID_COURSE_CREDITS_REQUIRED_BCOMPSCI,
            VALID_COURSE_CREDITS_FULFILLED_BCOMPSCI);
        creditsB = new Credits(VALID_COURSE_CREDITS_REQUIRED_BCOMPSEC,
            VALID_COURSE_CREDITS_FULFILLED_BCOMPSEC);
        assertFalse(creditsA.equals(creditsB));
    }
}
