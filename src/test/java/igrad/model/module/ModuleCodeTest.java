package igrad.model.module;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class ModuleCodeTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new ModuleCode(null));
    }

    @Test
    public void constructor_invalidModuleCode_throwsIllegalArgumentException() {
        String invalidModuleCode = "";
        assertThrows(IllegalArgumentException.class, () -> new Semester(invalidModuleCode));
    }

    @Test
    public void isValidModuleCode() {
        // null ModuleCode
        assertThrows(NullPointerException.class, () -> ModuleCode.isValidModuleCode(null));

        // invalid ModuleCode
        assertFalse(ModuleCode.isValidModuleCode("")); // empty string

        // invalid ModuleCode (only 4 digits, no 2 letters in front)
        assertFalse(ModuleCode.isValidModuleCode("1234"));

        // valid ModuleCode (2 letters in front, followed by 4 digits, but no (optional) letter behind)
        assertTrue(ModuleCode.isValidModuleCode("CS1231"));

        // valid ModuleCode
        assertTrue(ModuleCode.isValidModuleCode("CS2103T"));
    }
}
