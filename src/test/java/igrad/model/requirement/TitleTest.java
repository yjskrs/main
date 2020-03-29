package igrad.model.requirement;

import static igrad.testutil.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
        assertFalse(Title.isValidName(" Foundation")); // starts with a whitespace

        // valid name
        assertTrue(Title.isValidName("computersciencefoundation"));
        assertTrue(Title.isValidName("computer science foundation")); // contains spaces
        assertTrue(Title.isValidName("Computer Science Foundation")); // contains capital letters
        assertTrue(Title.isValidName("1")); // numbers only
        assertTrue(Title.isValidName("Computer Science Foundation 1")); // contains alphanumeric characters
        assertTrue(Title.isValidName("Requirements (for 2nd major)")); // contains non-alphanumeric characters
        assertTrue(Title.isValidName("日本研究学科")); // contains only non-alphaneumeric characters
    }
}
