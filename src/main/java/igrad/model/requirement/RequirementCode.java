package igrad.model.requirement;

import static java.util.Objects.requireNonNull;

/**
 * Represents a Requirement's requirement code in the course book
 * Auto-generated based on algorithm
 * Guarantees: immutable;
 */
public class RequirementCode {

    public final String value;

    /**
     * Constructs a {@code requirementCode}.
     *
     * @param requirementCode A requirement code.
     */
    public RequirementCode(String requirementCode) {
        requireNonNull(requirementCode);

        value = requirementCode;

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
