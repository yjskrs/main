package seedu.address.services.exceptions;

public class ServiceException extends Exception{
    public ServiceException(String message) {
        super(message);
    }

    /**
     * Constructs a new {@code CommandException} with the specified detail {@code message} and {@code cause}.
     */
    public ServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}
