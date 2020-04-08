package igrad.model.course;

import static igrad.commons.util.AppUtil.checkArgument;
import static java.util.Objects.requireNonNull;

import java.util.Optional;

import igrad.model.course.exceptions.CapOverflowException;

/**
 * Represents a Course Info's cap in the course book.
 * Guarantees: immutable; is valid as declared in {@link #isValidCap(String)}
 */
public class Cap {
    public static final String MESSAGE_CONSTRAINTS = "C.A.P. should not start with a space or slash and should not "
        + "be blank.\nC.A.P. should not be negative and should be within value of 5.0";

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
        return (test >= 0) && (test <= 5.0);
    }

    /**
     * Returns true if a given double is a valid cap.
     */
    public static boolean isValidCap(String test) {
        requireNonNull(test);

        if (test.isEmpty()) {
            return false;
        }

        return (Double.parseDouble(test) >= 0) && (Double.parseDouble(test) <= 5.0);
    }

    /**
     * Returns an estimated Cap (Double) based on {@code Model} and {@code Cap} object passed in.
     */
    public static Optional<Cap> computeEstimatedCap(CourseInfo courseInfo, Cap capToAchieve) {
        Optional<Semesters> semesters = courseInfo.getSemesters();
        int totalSemesters = semesters.get().getTotalSemesters();
        int remainingSemesters = semesters.get().getRemainingSemesters();

        Optional<Cap> current = courseInfo.getCap();

        if (current.isEmpty()) {
            return Optional.of(capToAchieve);
        } else {
            totalSemesters = remainingSemesters + 1;
        }

        Cap currentCap = courseInfo.getCap().orElse(CAP_ZERO);
        double capWanted = capToAchieve.value;
        double capNow = currentCap.value;

        double estimatedCapEachSem = ((capWanted * totalSemesters) - capNow) / remainingSemesters;

        if (!isValidCap(estimatedCapEachSem)) {
            throw new CapOverflowException(estimatedCapEachSem);
        }

        return Optional.of(new Cap(Double.toString(estimatedCapEachSem)));
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
