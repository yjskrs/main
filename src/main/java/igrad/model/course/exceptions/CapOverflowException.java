package igrad.model.course.exceptions;

//@@author teriaiw

/**
 * Signals that the operation will result in overflow of Cap.
 */
public class CapOverflowException extends RuntimeException {
    public CapOverflowException(double cap) {
        super(String.format(
            "Unable to achieve desired C.A.P. as C.A.P. of %1$s to maintain per semester is invalid",
            cap));
    }
}
