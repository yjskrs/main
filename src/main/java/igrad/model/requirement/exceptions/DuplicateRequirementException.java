package igrad.model.requirement.exceptions;

/**
 * Signals that the operation will result in duplicate requirements with the same title.
 */
public class DuplicateRequirementException extends RuntimeException {
    public DuplicateRequirementException() {
        super("Operation would result in duplicate requirements");
    }
}
