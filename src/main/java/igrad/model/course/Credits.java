package igrad.model.course;

import static igrad.commons.util.AppUtil.checkArgument;

//@@author nathanaelseen

/**
 * Represents a {@code CourseInfo}'s credits information in the course book.
 * Guarantees: immutable, fields are non-null and valid as declared by {@link #isValidCredits(String)}
 */
public class Credits {

    public static final String MESSAGE_CONSTRAINTS = "Total modular Credits provided for the course is invalid!\n"
        + "It should be a number more than 0.";

    public static final String VALIDATION_REGEX = "^[0-9]\\d*$";

    private final int creditsRequired;

    private final int creditsFulfilled;

    /**
     * Constructs a {@code Credits} with {@code creditsRequired} credits required and
     * {@code creditsFulfilled} creditsFulfilled.
     *
     * @param creditsRequired  A valid credits value (integer).
     * @param creditsFulfilled A valid credits value (integer).
     */
    public Credits(int creditsRequired, int creditsFulfilled) {
        checkArgument(isValidCreditsRequired(creditsRequired), MESSAGE_CONSTRAINTS);
        checkArgument(isValidCreditsFulfilled(creditsFulfilled), MESSAGE_CONSTRAINTS);

        this.creditsRequired = creditsRequired;
        this.creditsFulfilled = creditsFulfilled;
    }

    /**
     * Returns true if given integer {@code test} is a valid credits required (i.e. more than 0).
     */
    public static boolean isValidCreditsRequired(int test) {
        return test > 0;
    }

    /**
     * Returns true if given integer {@code test} is a valid credits fulfilled (i.e. more than or equals 0).
     */
    public static boolean isValidCreditsFulfilled(int test) {
        return test >= 0;
    }

    /**
     * Returns the credits (int) required to mark requirement as done.
     */
    public int getCreditsRequired() {
        return creditsRequired;
    }

    /**
     * Returns the credits (int) fulfilled.
     */
    public int getCreditsFulfilled() {
        return creditsFulfilled;
    }

    @Override
    public String toString() {
        return String.valueOf(creditsRequired);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // return true if same object, else check
            || (other instanceof Credits
            && creditsRequired == ((Credits) other).creditsRequired
            && creditsFulfilled == ((Credits) other).creditsFulfilled);
    }

    @Override
    public int hashCode() {
        return (creditsRequired + creditsFulfilled);
    }

}
