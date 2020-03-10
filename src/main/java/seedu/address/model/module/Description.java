package seedu.address.model.module;

/**
 * Represents a Person's address in the address book.
 */
public class Description {

    public static final String MESSAGE_CONSTRAINTS = "Descriptions can take any values, and is optional";

    public final String value;

    /**
     * Constructs an {@code Description}.
     *
     * @param description A valid description.
     */
    public Description(String description) {
        value = description;
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof Description // instanceof handles nulls
            && value.equals(((Description) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
