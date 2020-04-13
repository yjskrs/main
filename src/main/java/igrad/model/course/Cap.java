package igrad.model.course;

import static igrad.commons.util.AppUtil.checkArgument;
import static java.util.Objects.requireNonNull;

//@@author nathanaelseen

/**
 * Represents a Course Info's cap in the course book.
 * Guarantees: immutable; is valid as declared in {@link #isValidCap(String)}
 */
public class Cap {
    public static final String MESSAGE_CONSTRAINTS = "The C.A.P. provided for the course is invalid!\n"
        + "It should not start with a space or slash and should not "
        + "be blank.\nIt should be a non-negative number and should be within value of 5.0.";

    public static final String VALIDATION_REGEX = "^[0-5](\\.[0-9]+){0,1}$";

    // set max cap limit to 5
    private static final double MAX_CAP_LIMIT = 5.0;
    public static final Cap MAX_CAP = new Cap(MAX_CAP_LIMIT);
    // set min cap limit to 0
    private static final double MIN_CAP_LIMIT = 0.0;
    public static final Cap CAP_ZERO = new Cap(MIN_CAP_LIMIT);
    public static final Cap MIN_CAP = new Cap(MIN_CAP_LIMIT);
    public final double value;

    /**
     * Constructs a {@code Cap}.
     *
     * @param cap A valid cap (double).
     */
    public Cap(String cap) {
        requireNonNull(cap);
        checkArgument(isValidCap(cap), MESSAGE_CONSTRAINTS);
        value = Double.parseDouble(cap);
    }

    /**
     * Constructs a {@code Cap}.
     *
     * @param cap A valid cap (double).
     */
    public Cap(double cap) {
        checkArgument(isValidCap(cap), MESSAGE_CONSTRAINTS);
        value = cap;
    }

    /**
     * Returns true if a given double is a valid cap.
     */
    public static boolean isValidCap(double test) {
        return (test >= MIN_CAP_LIMIT) && (test <= MAX_CAP_LIMIT);
    }

    /**
     * Returns true if a given String is a valid cap.
     */
    public static boolean isValidCap(String test) {
        requireNonNull(test);

        return test.matches(VALIDATION_REGEX)
            && (Double.parseDouble(test) >= MIN_CAP_LIMIT && Double.parseDouble(test) <= MAX_CAP_LIMIT);
    }

    @Override
    public String toString() {
        String twoDpTrunc = String.format("%.2f", value);

        return twoDpTrunc;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof Cap // instanceof handles nulls
            && value == (((Cap) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return ((Double) value).hashCode();
    }

}
