package igrad.model.course;

import static igrad.commons.util.AppUtil.checkArgument;
import static java.util.Objects.requireNonNull;

//@@author nathanaelseen

/**
 * Represents a Course Info's name in the course book.
 * Guarantees: immutable; is valid as declared in {@link #isValidName(String)}
 */
public class Name {
    public static final String MESSAGE_CONSTRAINTS = "Name provided for the course is invalid!\n"
        + "It should not start with a space or slash and should not be blank.";

    // The first character of the course name must not be a whitespace (" ").
    // The name must not be blank.
    public static final String VALIDATION_REGEX = "^[^\\s].*";

    public final String value;

    /**
     * Constructs a {@code Name}.
     *
     * @param name A valid name.
     */
    public Name(String name) {
        requireNonNull(name);
        checkArgument(isValidName(name), MESSAGE_CONSTRAINTS);
        value = name;
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
        return other == this // short circuit if same object
            || (other instanceof Name // instanceof handles nulls
            && value.equals(((Name) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
