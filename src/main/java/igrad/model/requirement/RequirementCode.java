package igrad.model.requirement;

import static igrad.commons.util.AppUtil.checkArgument;
import static java.util.Objects.requireNonNull;

/**
 * Represents a Requirement's requirement code in the course book
 * Auto-generated based on algorithm
 * Guarantees: immutable;
 */
public class RequirementCode {
    public static final String MESSAGE_CONSTRAINTS = "Requirement Code should not start with a space and "
        + "should not be blank.";

    public static final String VALIDATION_REGEX = "[A-Za-z0-9]+";

    public final String value;

    /**
     * Constructs a {@code requirementCode}.
     *
     * @param value A requirement code string.
     */
    public RequirementCode(String value) {
        requireNonNull(value);
        checkArgument(isValidRequirementCode(value), MESSAGE_CONSTRAINTS);

        this.value = value;
    }

    /**
     * Returns true if a given string is a valid title.
     */
    public static boolean isValidRequirementCode(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof RequirementCode // instanceof handles nulls
            && value.equals(((RequirementCode) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
