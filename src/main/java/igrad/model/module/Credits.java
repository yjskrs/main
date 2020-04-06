package igrad.model.module;

import static igrad.commons.util.AppUtil.checkArgument;
import static java.util.Objects.requireNonNull;

/**
 * Represents a Module's credit units in the course book.
 * Guarantees: immutable; is valid as declared in {@link #isValidCredits(String)}
 */
public class Credits {

    public static final String MESSAGE_CONSTRAINTS = "Modular credits should be valid!";

    public static final String VALIDATION_REGEX = "\\d+";

    public final String value;

    /**
     * Constructs an {@code Credits}.
     *
     * @param credits A valid credits value.
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
        try {
            Integer.parseInt(test);
            return test.matches(VALIDATION_REGEX);
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * Converts the string credits {@code value} to integer.
     */
    public int toInteger() {
        return Integer.parseInt(value);
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
