package igrad.model.course;

import static igrad.commons.util.AppUtil.checkArgument;
import static java.util.Objects.requireNonNull;

/**
 * Represents a Course Info's cap in the course book.
 * Guarantees: immutable; is valid as declared in {@link #isValidCap(String)}
 */
public class Cap {
    public static final String MESSAGE_CONSTRAINTS = "C.A.P. should not start with a space or slash and should not "
        + "be blank.\n"
        + "C.A.P. should not be negative and should be within value of 5.0";

    // The first character of the cap must not be a whitespace, " ", slash; /, or blank.
    public static final String VALIDATION_REGEX = "[0-5](\\.[0-9]+)?";

    public static final Cap CAP_ZERO = new Cap(0);

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
        return test >= 0;
    }

    /**
     * Returns true if a given double is a valid cap.
     */
    public static boolean isValidCap(String test) {
        requireNonNull(test);

        return test.matches(VALIDATION_REGEX) && Double.parseDouble(test) >= 0;
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
