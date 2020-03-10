package seedu.address.model.tags;

import static seedu.address.testutil.Assert.assertThrows;
import org.junit.jupiter.api.Test;

public class TagsTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Tags(null));
    }

    @Test
    public void constructor_invalidTagName_throwsIllegalArgumentException() {
        String invalidTagName = "";
        assertThrows(IllegalArgumentException.class, () -> new Tags(invalidTagName));
    }

    @Test
    public void isValidSemester() {
        // null tag name
        assertThrows(NullPointerException.class, () -> Tags.isValidSemester(null));
    }

}
