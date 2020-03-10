package igrad.model.module;

import static igrad.commons.util.AppUtil.checkArgument;

/**
 * Represents the semester a module was taken.
 * Guarantees: immutable; is valid as declared in {@link #isValidSemester(String)}
 */
public class Semester {


    public static final String MESSAGE_CONSTRAINTS =
        "Semester should be in the format Y_S_ e.g. Y1S2";
    public static final String VALIDATION_REGEX = "(?i)Y[1-4]S[1-2]";
    public final String value;

    /**
     * Constructs a {@code Semester}.
     *
     * @param semester A valid module code.
     */
    public Semester(String semester) {
        checkArgument(isValidSemester(semester), MESSAGE_CONSTRAINTS);
        value = semester;
    }

    /**
     * Returns true if a given string is a valid module code.
     */
    public static boolean isValidSemester(String test) {
        return test == null || test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof Semester // instanceof handles nulls
            && value.equals(((Semester) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
