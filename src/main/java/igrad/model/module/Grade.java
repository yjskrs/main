package igrad.model.module;

import static igrad.commons.util.AppUtil.checkArgument;
import static java.util.Objects.requireNonNull;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

//@@author nathanaelseen

/**
 * Represents a Module's grade.
 */
public class Grade {
    public static final String MESSAGE_CONSTRAINTS = "The Grade provided for the module is invalid!\n"
        + "It should be in the valid format: A+, A, A-, B+, B-, C+, C, D, D+, F, S, U";

    public static final String VALIDATION_REGEX = "(A\\+)|(A)|(A-)|(B\\+)|(B)|(B-)|(C\\+)|(C)|(D)|(D\\+)|(F)|(S)|(U)";

    /*
     * This is a mapping from grade to cap (double), since an SU grade does not have any cap value,
     * we set it to Optional.empty
     */
    private static final Map<String, Optional<Double>> SU_GRADE_CAP_MAP = new HashMap<>() {
        {
            put("S", Optional.empty());
            put("U", Optional.empty());
        }
    };

    /*
     * This is a mapping from grade to cap (double), including SU grades.
     */
    private static final Map<String, Optional<Double>> GRADE_CAP_MAP = new HashMap<>() {
        {
            put("A+", Optional.of(5.0));
            put("A", Optional.of(5.0));
            put("A-", Optional.of(4.5));
            put("B+", Optional.of(4.0));
            put("B", Optional.of(3.5));
            put("B-", Optional.of(3.0));
            put("C+", Optional.of(2.5));
            put("C", Optional.of(2.0));
            put("D+", Optional.of(1.5));
            put("D", Optional.of(1.0));
            put("F", Optional.of(0.0));
            putAll(SU_GRADE_CAP_MAP);
        }
    };

    public final String value;

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

    // @@author teriaiw

    /**
     * Returns true if {@code String grade} is valid grade.
     */
    public static boolean isValidGrade(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    /**
     * @return cap which represents a particular grade
     */
    public double getGradeValue() {
        return GRADE_CAP_MAP.get(value).orElse(0.0);
    }

    // @@author nathanaelseen

    /**
     * Determines whether a grade is SU or not.
     */
    public boolean isSuGrade() {
        return GRADE_CAP_MAP.get(value).isEmpty();
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
