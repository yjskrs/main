package igrad.model.requirement;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class RequirementCodeTest {
    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new RequirementCode(null));
    }

    @Test
    public void constructor_invalidRequirementCode_throwsIllegalArgumentException() {
        String invalidRequirementCode = "";
        assertThrows(IllegalArgumentException.class, () -> new RequirementCode(invalidRequirementCode));
    }

    @Test
    public void isValidRequirementCode() {
        // null RequirementCode
        assertThrows(NullPointerException.class, () -> RequirementCode.isValidRequirementCode(null));

        // invalid RequirementCode
        assertFalse(RequirementCode.isValidRequirementCode("")); // empty string

        // no alphabets or numbers
        assertFalse(RequirementCode.isValidRequirementCode("-"));

        // spaces
        assertFalse(RequirementCode.isValidRequirementCode(" CSF0"));
        assertFalse(RequirementCode.isValidRequirementCode("CSF0 "));
        assertFalse(RequirementCode.isValidRequirementCode(" "));

        // only numbers
        assertTrue(RequirementCode.isValidRequirementCode("12"));

        // valid RequirementCode
        assertTrue(RequirementCode.isValidRequirementCode("CSF0"));
    }

    @Test
    public void equals() {
        RequirementCode codeA = new RequirementCode("AS1");
        RequirementCode codeB = new RequirementCode("AS1");
        RequirementCode codeC = new RequirementCode("AS2");
        assertTrue(codeA.equals(codeA));
        assertTrue(codeA.equals(codeB));
        assertFalse(codeA.equals(codeC));
    }
}
