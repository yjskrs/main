package seedu.address.model.module;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;


public class AddressTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Memo(null));
    }

    @Test
    public void constructor_invalidAddress_throwsIllegalArgumentException() {
        String invalidAddress = "";
        assertThrows(IllegalArgumentException.class, () -> new Memo(invalidAddress));
    }

    @Test
    public void isValidAddress() {
        // null address
        assertThrows(NullPointerException.class, () -> Memo.isValidMemo(null));

        // invalid addresses
        assertFalse(Memo.isValidMemo("")); // empty string
        assertFalse(Memo.isValidMemo(" ")); // spaces only

        // valid addresses
        assertTrue(Memo.isValidMemo("Blk 456, Den Road, #01-355"));
        assertTrue(Memo.isValidMemo("-")); // one character
        assertTrue(Memo.isValidMemo("Leng Inc; 1234 Market St; San Francisco CA 2349879; USA")); // long address
    }
}
