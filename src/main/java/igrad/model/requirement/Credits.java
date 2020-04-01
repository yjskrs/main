package igrad.model.requirement;

import static igrad.commons.util.AppUtil.checkArgument;
import static igrad.commons.util.CollectionUtil.requireAllNonNull;
import static java.util.Objects.requireNonNull;

/**
 * Represents a Requirement's credits information in the course book.
 * Guarantees: immutable, fields are non-null and valid as declared by {@link #isValidCredits(String)}
 */
public class Credits {

    public static final String MESSAGE_CONSTRAINTS =
        "Modular credits needed to satisfy requirement should be a number more than 0.";

    // TODO
    public static final String VALIDATION_REGEX = "^[0-9]\\d*$"; // allows any numbers more than or equals zero

    private final String creditsRequired;

    private String creditsFulfilled;

    /**
     * Constructs a {@code Credits} with 0 fulfilled credits.
     *
     * @param creditsRequired A valid credits value (integer).
     */
    public Credits(String creditsRequired) {
        requireNonNull(creditsRequired);
        checkArgument(isValidCredits(creditsRequired), MESSAGE_CONSTRAINTS);

        this.creditsRequired = creditsRequired;
        this.creditsFulfilled = "0";
    }

    /**
     * Constructs a {@code Credits} with {@code creditsRequired} credits required and
     * {@code creditsFulfilled} creditsFulfilled.
     *
     * @param creditsRequired  A valid credits value (integer).
     * @param creditsFulfilled A valid credits value (integer).
     */
    public Credits(String creditsRequired, String creditsFulfilled) {
        requireAllNonNull(creditsRequired, creditsFulfilled);
        checkArgument(isValidCredits(creditsRequired), MESSAGE_CONSTRAINTS);
        checkArgument(isValidCredits(creditsFulfilled), MESSAGE_CONSTRAINTS);

        this.creditsRequired = creditsRequired;
        this.creditsFulfilled = creditsFulfilled;
    }

    /**
     * Returns true if given string {@code test} is a valid credits (i.e. more than or equals 0).
     */
    public static boolean isValidCredits(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    /**
     * Returns the credits required to mark requirement as done.
     */
    public String getCreditsRequired() {
        return creditsRequired;
    }

    /**
     * Returns the {@code String creditsRequired} as integer.
     */
    public int getCreditsRequiredInteger() {
        return Integer.parseInt(creditsRequired);
    }

    /**
     * Returns the credits fulfilled.
     */
    public String getCreditsFulfilled() {
        return creditsFulfilled;
    }

    /**
     * Returns the {@code String creditsFulfilled} as integer.
     */
    public int getCreditsFulfilledInteger() {
        return Integer.parseInt(creditsFulfilled);
    }

    /**
     * Returns true if requirement is fulfilled, i.e. credits fulfilled is more than or equals credits required.
     * Otherwise return false.
     */
    public boolean isFulfilled() {
        int required = Integer.parseInt(creditsRequired);
        int fulfilled = Integer.parseInt(creditsFulfilled);
        return fulfilled >= required;
    }

    @Override
    public String toString() {
        return creditsRequired;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // return true if same object, else check
            || (other instanceof Credits
            && creditsRequired.equals(((Credits) other).creditsRequired)
            && creditsFulfilled.equals(((Credits) other).creditsFulfilled));
    }

    @Override
    public int hashCode() {
        return creditsRequired.hashCode();
    }

}
