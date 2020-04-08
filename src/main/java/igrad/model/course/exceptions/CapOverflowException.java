package igrad.model.course.exceptions;

public class CapOverflowException extends RuntimeException {
    public CapOverflowException(double cap) {
        super(String.format(
                "Unable to achieve desired C.A.P. as C.A.P. of %1$s to maintain per semester is invalid",
                cap));
    }
}
