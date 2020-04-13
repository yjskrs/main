package igrad.model.requirement;

import static igrad.commons.util.AppUtil.checkArgument;
import static java.util.Objects.requireNonNull;

//@@author yjskrs

/**
 * Represents a Requirement's title.
 * Guarantees: immutable, non-null and is valid as declared by {@link #isValidTitle(String)}
 */
public class Title {

    public static final String MESSAGE_CONSTRAINTS = "The Title provided for requirement is invalid!\n"
        + "Title should not start with a space, should not be blank and must contain at least one alphabet.";

    // The first character of the requirement title must not be a whitespace (" ").
    // The title must not contain only numbers or be blank.
    public static final String VALIDATION_REGEX = "^[^\\s].*";

    private final String value;

    /**
     * Constructs a {@code Title}.
     *
     * @param value A valid title string.
     */
    public Title(String value) {
        requireNonNull(value);
        checkArgument(isValidTitle(value), MESSAGE_CONSTRAINTS);

        this.value = value;
    }

    /**
     * Returns true if the given string {@code test} is a valid title.
     */
    public static boolean isValidTitle(String test) {
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
