package igrad.model.requirement;

import static igrad.testutil.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class NameTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Name(null));
    }

    @Test
    public void constructor_invalidName_throwsIllegalArgumentException() {
        String invalidName = "";
        assertThrows(IllegalArgumentException.class, () -> new Name(invalidName));
    }

    @Test
    public void isValidName() {
        // null name
        assertThrows(NullPointerException.class, () -> Name.isValidName(null));

        // invalid name
        assertFalse(Name.isValidName("")); // empty string
        assertFalse(Name.isValidName(" ")); // spaces only
        assertFalse(Name.isValidName(" name")); // starts with a whitespace

        // valid name
        assertTrue(Name.isValidName("computersciencefoundation"));
        assertTrue(Name.isValidName("computer science foundation")); // contains spaces
        assertTrue(Name.isValidName("Computer Science Foundation")); // contains capital letters
        assertTrue(Name.isValidName("1")); // numbers only
        assertTrue(Name.isValidName("Computer Science Foundation 1")); // contains alphanumeric characters
        assertTrue(Name.isValidName("Requirements (for 2nd major)")); // contains non-alphanumeric characters
    }
}
