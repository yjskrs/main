package igrad.model.module;

/**
 * Represents a Module's memo in the course book.
 */
public class Memo {

    public static final String MESSAGE_CONSTRAINTS = "Memos can take any values, and is optional";

    public final String value;

    /**
     * Constructs an {@code Memo}.
     *
     * @param memo A valid memo.
     */
    public Memo(String memo) {
        value = memo;
    }

    public static boolean isValidMemo(String test) {
        return test.length() > 0;
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof Memo // instanceof handles nulls
            && value.equals(((Memo) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
