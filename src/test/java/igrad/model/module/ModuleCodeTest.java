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

        // invalid ModuleCode
        assertFalse(ModuleCode.isValidModuleCode("1234"));

        // valid ModuleCode
        assertTrue(ModuleCode.isValidModuleCode("CS2103T"));
    }
}
