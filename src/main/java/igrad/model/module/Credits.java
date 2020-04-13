package igrad.model.module;

import static igrad.commons.util.AppUtil.checkArgument;
import static java.util.Objects.requireNonNull;

/**
 * Represents a Module's credit units in the course book.
 * Guarantees: immutable; is valid as declared in {@link #isValidCredits(String)}
 */
public class Credits {

    public static final String MESSAGE_CONSTRAINTS = "The Module Credits provided for the module is invalid!\n"
            + "It should be a number more than 0 and less than 200.";

    public static final String VALIDATION_REGEX = "\\d+";

    // set max limit to 200
    public static final int MAX_CREDITS = 200;

    // set min limit to 0
    public static final int MIN_CREDITS = 0;

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
            return test.matches(VALIDATION_REGEX)
                && Integer.parseInt(test) > MIN_CREDITS
                && Integer.parseInt(test) < MAX_CREDITS;

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
