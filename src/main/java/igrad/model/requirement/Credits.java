package igrad.model.requirement;

import static igrad.commons.util.AppUtil.checkArgument;
import static java.util.Objects.requireNonNull;

/**
 * Represents a Requirement's required credit units in the course book.
 * Guarantees: immutable; is valid as declared in {@link #isValidCredits(String)}
 */
public class Credits {

    public static final String MESSAGE_CONSTRAINTS = "Modular credits should contain one or more digits!";

    public static final String VALIDATION_REGEX = "\\d+";

    public final String value;

    /**
     * Constructs a {@code Credits}.
     *
     * @param credits A valid credits address.
     */
    public Credits(String credits) {
        requireNonNull(credits);
        checkArgument(isValidCredits(credits), MESSAGE_CONSTRAINTS);
        value = credits;
    }

    /**
     * Returns true if a given string is a valid credits.
     */
    public static boolean isValidCredits(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof Credits // instanceof handles nulls
            && value.equals(((Credits) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
