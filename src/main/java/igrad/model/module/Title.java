package igrad.model.module;

//@@author waynewee

import static igrad.commons.util.AppUtil.checkArgument;
import static java.util.Objects.requireNonNull;

/**
 * Represents a Module's title in the course book.
 * Guarantees: immutable; is valid as declared in {@link #isValidTitle(String)}
 */
public class Title {
    public static final String MESSAGE_CONSTRAINTS = "The Title provided for the module is invalid!\n"
        + "It should not start with a space or slash and should not be blank.";

    // The first character of the module title must not be a whitespace (" ").
    // The title must not be blank.
    public static final String VALIDATION_REGEX = "^[^\\s].*";

    public final String value;

    /**
     * Constructs a {@code Title}.
     *
     * @param title A valid title.
     */
    public Title(String title) {
        requireNonNull(title);
        checkArgument(isValidTitle(title), MESSAGE_CONSTRAINTS);
        value = title;
    }

    /**
     * Returns true if a given string is a valid title.
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
        return other == this // short circuit if same object
            || (other instanceof Title // instanceof handles nulls
            && value.equals(((Title) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
