package igrad.model.module;

import static igrad.commons.util.AppUtil.checkArgument;
import static java.util.Objects.requireNonNull;

/**
 * Represents a Module's grade.
 */
public class Grade {
    public static final String MESSAGE_CONSTRAINTS = "Grade should be a valid format";

    public static final String VALIDATION_REGEX = "(A\\+)|(A)|(A-)|(B\\+)|(B)|(B-)|(C\\+)|(C)|(D)|(F)|(S)|(U)";

    public final String value;

    /**
     * Constructs a default {@code Grade}.
     */
    // TODO: If Grade is Optional in Module, we should not be having default values here, can just use isPresent
    public Grade() {
        value = "-";
    }

    /**
     * Constructs an {@code Grade}.
     *
     * @param grade A valid grade value.
     */
    public Grade(String grade) {
        requireNonNull(grade);
        checkArgument(isValidGrade(grade), MESSAGE_CONSTRAINTS);

        value = grade;
    }

    /**
     * Returns true if {@code String grade} is valid grade.
     */
    public static boolean isValidGrade(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof Grade // instanceof handles nulls
            && value.equals(((Grade) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
