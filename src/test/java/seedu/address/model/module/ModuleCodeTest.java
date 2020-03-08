package seedu.address.model.module;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class ModuleCodeTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new ModuleCode(null));
    }

    @Test
    public void constructor_invalidPhone_throwsIllegalArgumentException() {
        String invalidPhone = "";
        assertThrows(IllegalArgumentException.class, () -> new ModuleCode(invalidPhone));
    }

    @Test
    public void isValidPhone() {
        // null phone number
        assertThrows(NullPointerException.class, () -> ModuleCode.isValidModuleCode(null));

        // invalid phone numbers
        assertFalse( ModuleCode.isValidModuleCode("")); // empty string
        assertFalse( ModuleCode.isValidModuleCode(" ")); // spaces only
        assertFalse( ModuleCode.isValidModuleCode("91")); // less than 3 numbers
        assertFalse( ModuleCode.isValidModuleCode("phone")); // non-numeric
        assertFalse( ModuleCode.isValidModuleCode("9011p041")); // alphabets within digits
        assertFalse( ModuleCode.isValidModuleCode("9312 1534")); // spaces within digits

        // valid phone numbers
        assertTrue( ModuleCode.isValidModuleCode("911")); // exactly 3 numbers
        assertTrue( ModuleCode.isValidModuleCode("93121534"));
        assertTrue( ModuleCode.isValidModuleCode("124293842033123")); // long phone numbers
    }
}
