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
        assertThrows(NullPointerException.class, () -> Memo.isValidAddress(null));

        // invalid addresses
        assertFalse( Memo.isValidAddress("")); // empty string
        assertFalse( Memo.isValidAddress(" ")); // spaces only

        // valid addresses
        assertTrue( Memo.isValidAddress("Blk 456, Den Road, #01-355"));
        assertTrue( Memo.isValidAddress("-")); // one character
        assertTrue( Memo.isValidAddress("Leng Inc; 1234 Market St; San Francisco CA 2349879; USA")); // long address
    }
}
