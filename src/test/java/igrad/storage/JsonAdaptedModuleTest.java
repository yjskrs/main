package igrad.storage;

import static igrad.logic.parser.CliSyntax.PREFIX_TAG;
import static igrad.testutil.TypicalModules.PROGRAMMING_METHODOLOGY;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import igrad.model.module.Title;
import org.junit.jupiter.api.Test;

import igrad.commons.exceptions.IllegalValueException;
import igrad.testutil.Assert;

public class JsonAdaptedModuleTest {
    public static final String INVALID_TITLE = "Programming Methodology!";
    public static final String INVALID_MODULE_CODE = "CS2040S&";
    public static final String INVALID_CREDITS = "4%";
    public static final String INVALID_SEMESTER = "4%";
    public static final String INVALID_TAG = " " + PREFIX_TAG + "easy*";

    public static final String VALID_TITLE = PROGRAMMING_METHODOLOGY.getTitle().toString();
    public static final String VALID_MODULE_CODE = PROGRAMMING_METHODOLOGY.getModuleCode().toString();
    public static final String VALID_CREDITS = PROGRAMMING_METHODOLOGY.getCredits().toString();
    public static final String VALID_MEMO = PROGRAMMING_METHODOLOGY.getMemo().toString();
    //public static final String VALID_SEMESTER = PROGRAMMING_METHODOLOGY.getSemester().toString();
    private static final List<JsonAdaptedTag> VALID_TAGS = PROGRAMMING_METHODOLOGY.getTags().stream()
            .map(JsonAdaptedTag::new)
            .collect(Collectors.toList());
    // TODO: add more tests later
    @Test
    public void toModelType_validModuleDetails_returnsModule() throws Exception {
        JsonAdaptedModule module = new JsonAdaptedModule(PROGRAMMING_METHODOLOGY);
        assertEquals(PROGRAMMING_METHODOLOGY, module.toModelType());
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
