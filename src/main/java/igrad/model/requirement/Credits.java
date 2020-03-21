package igrad.model.requirement;

import static igrad.commons.util.AppUtil.checkArgument;
import static java.util.Objects.requireNonNull;

/**
 * Represents a Requirement's required credit units in the course book.
 * Guarantees: immutable, non-null and is valid as declared by {@link #isValidCredits(String)}
 */
public class Credits {

    public static final String MESSAGE_CONSTRAINTS =
        "Credits needed to satisfy requirement should be more than 0.";

    public static final String VALIDATION_REGEX = "^[0-9]\\d*$"; // allows any numbers more than or equals zero

    public final String value;

    /**
     * Constructs a {@code Credits}.
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
        return test.matches(VALIDATION_REGEX);
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
        return other == this // return true if same object, else check
            || (other instanceof Credits && value.equals(((Credits) other).value)); // check type and value
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
