package igrad.model.module;

//@@author waynewee

import static igrad.commons.util.AppUtil.checkArgument;
import static java.util.Objects.requireNonNull;

/**
 * Represents the semester a module was taken.
 * Guarantees: immutable; is valid as declared in {@link #isValidSemester(String)}
 */
public class Semester {


    public static final String MESSAGE_CONSTRAINTS = "The Semester provided for the module is invalid!\n"
        + "Semester should be in the format Y_S_\ne.g. Y1S2";

    public static final String VALIDATION_REGEX = "(?i)Y[1-9]S[1-2]";

    public final String value;

    /**
     * Constructs a {@code Semester}.
     *
     * @param semester A valid module code.
     */
    public Semester(String semester) {
        requireNonNull(semester);
        checkArgument(isValidSemester(semester), MESSAGE_CONSTRAINTS);

        value = semester.toUpperCase();
    }

    /**
     * Returns true if a given string is a valid module code.
     */
    public static boolean isValidSemester(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    public int getValue() {
        String intValue = value.replaceAll("[^0-9]", "");

        return Integer.parseInt(intValue);
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
