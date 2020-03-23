package igrad.model.requirement;

import static igrad.commons.util.AppUtil.checkArgument;
import static java.util.Objects.requireNonNull;

/**
 * Represents a Requirement's name.
 * Guarantees: immutable, non-null and is valid as declared by {@link #isValidName(String)}
 */
public class Title {

    public static final String MESSAGE_CONSTRAINTS = "Names should not start with a space and should not be blank.";

    public static final String VALIDATION_REGEX = "^[^\\s].*";

    public final String value;

    /**
     * Constructs a {@code Name}.
     *
     * @param value A valid name string.
     */
    public Title(String value) {
        requireNonNull(value);
        checkArgument(isValidName(value), MESSAGE_CONSTRAINTS);

        this.value = value;
    }

    /**
     * Returns true if a given string is a valid name.
     */
    public static boolean isValidName(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object, else check
                   || (other instanceof Title && value.equals(((Title) other).value)); // check same type and value
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
