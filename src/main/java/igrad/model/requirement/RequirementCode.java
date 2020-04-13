package igrad.model.requirement;

import static igrad.commons.util.AppUtil.checkArgument;
import static java.util.Objects.requireNonNull;

/**
 * Represents a Requirement's requirement code in the course book.
 * Auto-generated based on algorithm.
 * Guarantees: immutable and non-null.
 */
public class RequirementCode {
    public static final String MESSAGE_CONSTRAINTS = "The Requirement Code provided for the requirement is invalid!\n"
        + "It should only contain uppercase alphabets and/or integers and should not be blank.\ne.g. UE1";

    // Requirement Code should have alphabets followed by some digits.
    public static final String VALIDATION_REGEX = "[A-Z0-9]+";

    public static final String STRIP_DIGITS_REGEX = "[0123456789]";
    public static final String STRIP_ALPHABETS_REGEX = "\\D+";

    private final String value;

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
     * Returns true if the given string {@code test} is a valid RequirementCode.
     */
    public static boolean isValidRequirementCode(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    /**
     * Checks if {@code otherCode} has same starting alphabets as this.
     */
    public boolean hasSameAlphabets(RequirementCode otherCode) {
        requireNonNull(otherCode);

        return otherCode.getAlphabets().equals(getAlphabets());
    }

    /**
     * Returns the first starting alphabets of the requirement code.
     */
    public String getAlphabets() {
        return value.replaceAll(STRIP_DIGITS_REGEX, "");
    }

    /**
     * Returns the identifying number of the requirement code.
     */
    public int getNumber() {
        String number = value.replaceAll(STRIP_ALPHABETS_REGEX, "");
        return Integer.parseInt(number);
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
