package igrad.services.exceptions;

/**
 * Represents an error which occurs during execution of a {@code Request}.
 */
public class ServiceException extends Exception {
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
