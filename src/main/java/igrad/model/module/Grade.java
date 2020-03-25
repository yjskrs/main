package igrad.model.module;

import static igrad.commons.util.AppUtil.checkArgument;

/**
 * Represents a Module's grade.
 */
public class Grade {
    public static final String MESSAGE_CONSTRAINTS = "Grade should be.";

    // public static final String VALIDATION_REGEX = ".+";

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
        /*
         * If argument passed into constructor non-null, we've to check if its valid grade format.
         * Note that this is unlike the Description and other optional fields where no valid format
         * is neccessary
         */
        if (grade != null) {
            checkArgument(isValidGrade(grade), MESSAGE_CONSTRAINTS);
        }

        value = grade;
    }

    /**
     * Returns true if {@code String grade} is valid grade.
     */
    public static boolean isValidGrade(String grade) {
        switch (grade) {
        case "A+":
        case "A":
        case "A-":
        case "B+":
        case "B":
        case "B-":
        case "C+":
        case "C":
        case "D":
        case "F":
        case "S":
        case "U":
        case "-": // ungraded, default value
            return true;
        default:
            return false;
        }
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
