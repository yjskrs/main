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
    public void isValidTitle() {
        // null title
        assertThrows(NullPointerException.class, () -> Title.isValidTitle(null));

        // invalid title
        assertFalse(Title.isValidTitle("")); // empty string
        assertFalse(Title.isValidTitle(" ")); // spaces only
        assertFalse(Title.isValidTitle(" Foundation")); // starts with a whitespace

        // valid title
        assertTrue(Title.isValidTitle("computersciencefoundation"));
        assertTrue(Title.isValidTitle("computer science foundation")); // contains spaces
        assertTrue(Title.isValidTitle("Computer Science Foundation")); // contains capital letters
        assertTrue(Title.isValidTitle("1")); // numbers only
        assertTrue(Title.isValidTitle("Computer Science Foundation 1")); // contains alphanumeric characters
        assertTrue(Title.isValidTitle("Requirements (for 2nd major)")); // contains non-alphanumeric characters
        assertTrue(Title.isValidTitle("日本研究学科")); // contains only non-alphaneumeric characters
    }
}
