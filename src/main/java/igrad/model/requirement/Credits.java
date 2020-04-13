package igrad.model.requirement;

import static igrad.commons.util.AppUtil.checkArgument;
import static java.util.Objects.requireNonNull;

//@@author yjskrs

/**
 * Represents a Requirement's credits information in the course book.
 * Guarantees: immutable, fields are non-null and valid as declared by {@link #isValidCredits(String)}
 */
public class Credits {

    public static final String MESSAGE_CONSTRAINTS = "The modular Credits provided for the requirement is invalid!\n"
        + "It should be a number more than 0 and less than 10000.";

    // allow any numbers more than or equals zero
    public static final String VALIDATION_REGEX = "^[0-9]\\d*$";

    // set max limit to 10000
    public static final int MAX_CREDITS = 10000;

    // set min limit to 0
    public static final int MIN_CREDITS = 0;

    private final int creditsRequired;
    private final int creditsAssigned;
    private final int creditsFulfilled;

    /**
     * Constructs a {@code Credits} with 0 fulfilled credits.
     *
     * @param creditsRequired A valid credits value (integer).
     */
    public Credits(String creditsRequired) {
        requireNonNull(creditsRequired);
        checkArgument(isValidCredits(creditsRequired), MESSAGE_CONSTRAINTS);

        this.creditsRequired = Integer.parseInt(creditsRequired);
        this.creditsAssigned = 0;
        this.creditsFulfilled = 0;
    }

    /**
     * Constructs a {@code Credits} with {@code creditsRequired} credits required,
     * {@code creditsAssigned} and {@code creditsFulfilled} creditsFulfilled.
     *
     * @param creditsRequired  A valid credits value (integer).
     * @param creditsAssigned  A valid credits value (integer).
     * @param creditsFulfilled A valid credits value (integer).
     */
    public Credits(int creditsRequired, int creditsAssigned, int creditsFulfilled) {
        checkArgument(isValidCredits(creditsRequired, creditsAssigned, creditsFulfilled), MESSAGE_CONSTRAINTS);

        this.creditsRequired = creditsRequired;
        this.creditsAssigned = creditsAssigned;
        this.creditsFulfilled = creditsFulfilled;
    }

    /**
     * Returns true if given String {@code test} is a valid credits
     * (i.e. integer more than 0 and less than 1000).
     */
    public static boolean isValidCredits(String test) {
        requireNonNull(test);

        return test.matches(VALIDATION_REGEX)
            && Integer.parseInt(test) > MIN_CREDITS
            && Integer.parseInt(test) < MAX_CREDITS;
    }

    /**
     * Returns true if given credits are valid.
     */
    public static boolean isValidCredits(int required, int assigned, int fulfilled) {
        // check if any of them are below the min limit
        if (required < MIN_CREDITS || assigned < MIN_CREDITS || fulfilled < MIN_CREDITS) {
            return false;
        }

        // check if any of them are over the max limit
        if (required > MAX_CREDITS || assigned > MAX_CREDITS || fulfilled > MAX_CREDITS) {
            return false;
        }

        // field specific checks
        return required != MIN_CREDITS && fulfilled <= assigned;
    }

    /**
     * Returns the credits (int) required to mark requirement as done.
     */
    public int getCreditsRequired() {
        return creditsRequired;
    }

    /**
     * Returns the credits (int) which are assigned to the requirement.
     */
    public int getCreditsAssigned() {
        return creditsAssigned;
    }

    /**
     * Returns the credits (int) fulfilled.
     */
    public int getCreditsFulfilled() {
        return creditsFulfilled;
    }

    /**
     * Returns true if requirement is fulfilled, i.e. credits fulfilled is more than or equals credits required.
     * Otherwise return false.
     */
    public boolean isFulfilled() {
        return creditsFulfilled >= creditsRequired;
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
            && creditsAssigned == ((Credits) other).creditsAssigned
            && creditsFulfilled == ((Credits) other).creditsFulfilled);
    }

    @Override
    public int hashCode() {
        return creditsRequired + creditsAssigned + creditsFulfilled;
    }

}
