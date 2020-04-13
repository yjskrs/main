package igrad.model.course;

//@@author teriaiw

import static igrad.commons.util.AppUtil.checkArgument;
import static java.util.Objects.requireNonNull;

/**
 * Represents a {@code CourseInfo}'s semester information in the course book.
 * Guarantees: immutable, fields are non-null and valid as declared by {@link #isValidSemesters(String)}
 */
public class Semesters {

    public static final String MESSAGE_CONSTRAINTS = "Total Semesters of a course provided is invalid!\n"
        + "It should be a number more than 0 and less than 100.";

    public static final String MESSAGE_INVALID_SEMS = "Remaining semester count invalid";

    public static final String VALIDATION_REGEX = "^[0-9]\\d*$";

    // set max limit to 100
    public static final int MAX_SEMS = 100;

    // set min limit to 0
    public static final int MIN_SEMS = 0;

    public final int totalSemesters;

    public final int remainingSemesters;

    /**
     * Constructs a {@code Semesters} with remaining semesters as total semesters.
     *
     * @param totalSemesters A valid semester count (integer).
     */
    public Semesters(String totalSemesters) {
        requireNonNull(totalSemesters);
        checkArgument(isValidSemesters(totalSemesters), MESSAGE_CONSTRAINTS);

        this.totalSemesters = Integer.parseInt(totalSemesters);
        this.remainingSemesters = this.totalSemesters;
    }

    /**
     * Constructs a {@code Semesters} with {@code totalSemesters} total semesters and
     * {@code remainingSemesters} remaining semesters.
     *
     * @param totalSemesters     A valid semester count (integer).
     * @param remainingSemesters A valid semester count (integer).
     */
    public Semesters(int totalSemesters, int remainingSemesters) {
        requireNonNull(totalSemesters);
        checkArgument(isValidTotalSemesters(totalSemesters), MESSAGE_CONSTRAINTS);
        checkArgument(isValidRemainingSemesters(remainingSemesters), MESSAGE_INVALID_SEMS);

        this.totalSemesters = totalSemesters;
        this.remainingSemesters = remainingSemesters;
    }

    /**
     * Returns true if given String {@code test} is a valid Semesters count (i.e. integer more than 0).
     */
    public static boolean isValidSemesters(String test) {
        requireNonNull(test);
        try {
            return test.matches(VALIDATION_REGEX)
                    && Integer.parseInt(test) > MIN_SEMS
                    && Integer.parseInt(test) < MAX_SEMS;

        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * Returns true if given integer {@code test} is a valid total Semesters count (i.e. more than or equals 0).
     */
    public static boolean isValidTotalSemesters(int test) {
        try {
            return test > MIN_SEMS && test < MAX_SEMS;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * Returns true if given integer {@code test} is a valid remaining Semesters count (i.e. more than or equals 0).
     */
    public static boolean isValidRemainingSemesters(int test) {
        try {
            return test >= MIN_SEMS && test < MAX_SEMS;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public int getTotalSemesters() {
        return totalSemesters;
    }

    public int getRemainingSemesters() {
        return remainingSemesters;
    }

    @Override
    public String toString() {
        return String.valueOf(totalSemesters);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // return true if same object, else check
            || (other instanceof Semesters
            && totalSemesters == ((Semesters) other).totalSemesters
            && remainingSemesters == ((Semesters) other).remainingSemesters);
    }

    @Override
    public int hashCode() {
        return totalSemesters;
    }
}
