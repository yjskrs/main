package seedu.address.model.module;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class TitleTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Title(null));
    }

    @Test
    public void constructor_invalidName_throwsIllegalArgumentException() {
        String invalidName = "";
        assertThrows(IllegalArgumentException.class, () -> new Title(invalidName));
    }

    @Test
    public void isValidName() {
        // null name
        assertThrows(NullPointerException.class, () -> Title.isValidName(null));

        // invalid name
        assertFalse(Title.isValidName("")); // empty string
        assertFalse(Title.isValidName(" ")); // spaces only
        assertFalse(Title.isValidName("^")); // only non-alphanumeric characters
        assertFalse(Title.isValidName("peter*")); // contains non-alphanumeric characters

        // valid name
        assertTrue(Title.isValidName("peter jack")); // alphabets only
        assertTrue(Title.isValidName("12345")); // numbers only
        assertTrue(Title.isValidName("peter the 2nd")); // alphanumeric characters
        assertTrue(Title.isValidName("Capital Tan")); // with capital letters
        assertTrue(Title.isValidName("David Roger Jackson Ray Jr 2nd")); // long names
    }
}
