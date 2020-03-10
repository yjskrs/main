package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.storage.JsonAdapatedModule.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.BENSON;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.jupiter.api.Test;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.module.Credits;
import seedu.address.model.module.Memo;
import seedu.address.model.module.ModuleCode;
import seedu.address.model.module.Title;

public class JsonAdaptedModuleTest {
    private static final String INVALID_TITLE = "R@chel";
    private static final String INVALID_MODULE_CODE = "+651234";
    private static final String INVALID_CREDITS = " ";
    private static final String INVALID_SEMESTER = "example.com";
    private static final String INVALID_MEMO = "example.com";
    private static final String INVALID_DESCRIPTION = "example.com";
    private static final String INVALID_TAG = "#friend";

    private static final String VALID_TITLE = BENSON.getTitle().toString();
    private static final String VALID_MODULE_CODE = BENSON.getModuleCode().toString();
    private static final String VALID_CREDITS = BENSON.getCredits().toString();
    private static final String VALID_SEMESTER = BENSON.getMemo().toString();
    private static final String VALID_MEMO = BENSON.getMemo().toString();
    private static final String VALID_DESCRIPTION = BENSON.getMemo().toString();
    private static final List<JsonAdaptedTag> VALID_TAGS = BENSON.getTags().stream()
        .map(JsonAdaptedTag::new)
        .collect(Collectors.toList());

    @Test
    public void toModelType_validPersonDetails_returnsPerson() throws Exception {
        JsonAdapatedModule person = new JsonAdapatedModule(BENSON);
        assertEquals(BENSON, person.toModelType());
    }

    @Test
    public void toModelType_invalidName_throwsIllegalValueException() {
        JsonAdapatedModule person =
            new JsonAdapatedModule(INVALID_TITLE, VALID_MODULE_CODE, VALID_CREDITS, VALID_SEMESTER, VALID_MEMO, VALID_DESCRIPTION, VALID_TAGS);
        String expectedMessage = Title.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullName_throwsIllegalValueException() {
        JsonAdapatedModule person = new JsonAdapatedModule(null, VALID_MODULE_CODE, VALID_CREDITS, VALID_SEMESTER, VALID_MEMO, VALID_DESCRIPTION, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Title.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidPhone_throwsIllegalValueException() {
        JsonAdapatedModule person =
            new JsonAdapatedModule(VALID_TITLE, INVALID_MODULE_CODE, VALID_CREDITS, VALID_SEMESTER, VALID_MEMO, VALID_DESCRIPTION, VALID_TAGS);
        String expectedMessage = ModuleCode.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullPhone_throwsIllegalValueException() {
        JsonAdapatedModule person = new JsonAdapatedModule(VALID_TITLE, null, VALID_CREDITS, VALID_SEMESTER, VALID_MEMO, VALID_DESCRIPTION, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, ModuleCode.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidEmail_throwsIllegalValueException() {
        JsonAdapatedModule person =
            new JsonAdapatedModule(VALID_TITLE, VALID_MODULE_CODE, INVALID_SEMESTER, VALID_SEMESTER, VALID_MEMO, VALID_DESCRIPTION, VALID_TAGS);
        String expectedMessage = Credits.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullEmail_throwsIllegalValueException() {
        JsonAdapatedModule person = new JsonAdapatedModule(VALID_TITLE, VALID_MODULE_CODE, null, VALID_SEMESTER, VALID_MEMO, VALID_DESCRIPTION, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Credits.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidAddress_throwsIllegalValueException() {
        JsonAdapatedModule person =
            new JsonAdapatedModule(VALID_TITLE, VALID_MODULE_CODE, INVALID_CREDITS, VALID_MEMO, VALID_SEMESTER, VALID_DESCRIPTION, VALID_TAGS);
        String expectedMessage = Memo.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullAddress_throwsIllegalValueException() {
        JsonAdapatedModule person = new JsonAdapatedModule(VALID_TITLE, VALID_MODULE_CODE, VALID_CREDITS, null, VALID_SEMESTER, VALID_DESCRIPTION, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Memo.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidTags_throwsIllegalValueException() {
        List<JsonAdaptedTag> invalidTags = new ArrayList<>(VALID_TAGS);
        invalidTags.add(new JsonAdaptedTag(INVALID_TAG));
        JsonAdapatedModule person =
            new JsonAdapatedModule(VALID_TITLE, VALID_MODULE_CODE, VALID_CREDITS, VALID_MEMO, VALID_SEMESTER, VALID_DESCRIPTION, invalidTags);
        assertThrows(IllegalValueException.class, person::toModelType);
    }

}
