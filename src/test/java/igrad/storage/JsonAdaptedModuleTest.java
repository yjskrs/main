package igrad.storage;

import static igrad.testutil.TypicalModules.CS1101S;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class JsonAdaptedModuleTest {
    public static final String INVALID_TITLE = "Programming Methodology!";
    public static final String INVALID_MODULE_CODE = "CS2040S&";
    public static final String INVALID_CREDITS = "4%";
    public static final String INVALID_SEMESTER = "4%";

    public static final String VALID_TITLE = CS1101S.getTitle().toString();
    public static final String VALID_MODULE_CODE = CS1101S.getModuleCode().toString();
    public static final String VALID_CREDITS = CS1101S.getCredits().toString();
    //public static final String VALID_SEMESTER = PROGRAMMING_METHODOLOGY.getSemester().toString();

    // TODO: add more tests later
    @Test
    public void toModelType_validModuleDetails_returnsModule() throws Exception {
        JsonAdaptedModule module = new JsonAdaptedModule(CS1101S);
        assertEquals(CS1101S, module.toModelType());
    }

    @Test
    public void toModelType_invalidTitle_throwsIllegalValueException() {
        //JsonAdaptedModule person =
        //        new JsonAdaptedModule(INVALID_TITLE, VALID_MODULE_CODE, VALID_CREDITS, VALID_MEMO, VALID_SEMESTER,
        //                VALID_TAGS);
        //String expectedMessage = Title.MESSAGE_CONSTRAINTS;
        //Assert.assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

}
