package igrad.model.course.exceptions;

/**
 * Signals that the operation will result in an overflow of Semesters.
 */
public class SemsOverflowException extends RuntimeException {
    public SemsOverflowException() {
        super("Semester input exceeds total semester count!\n"
                + "Please input valid Y_S_ for module or update total semesters with 'course edit'");
    }
}
