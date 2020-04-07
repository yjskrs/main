package igrad.model.course;

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
        assertFalse(Name.isValidName(" ")); // string with blank spaces
        assertFalse(Name.isValidName(" Bachelor of Computer Science")); // starts with a whitespace

        // valid name
        assertTrue(Name.isValidName("bachelorofcomputerscience")); // name without spaces
        assertTrue(Name.isValidName("bachelor of computer science")); // contains spaces
        assertTrue(Name.isValidName("Bachelor Of Computer Science")); // capitalise first letter of each word
        assertTrue(Name.isValidName("/Bachelor of Computer Science")); // starts with non-alphabet
        assertTrue(Name.isValidName("Bachelor of Computer Science 1")); // contains alphanumeric characters

        // contains non-alphanumeric characters
        assertTrue(Name.isValidName("Bachelor of Computer Science (with maths minor)"));
        assertTrue(Name.isValidName("计算机科学")); // contains only non-alphaneumeric characters
    }
}
