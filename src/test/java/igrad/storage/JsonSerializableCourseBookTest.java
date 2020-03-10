package igrad.storage;

import static igrad.testutil.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

import igrad.commons.exceptions.IllegalValueException;
import igrad.commons.util.JsonUtil;
import igrad.model.CourseBook;
import igrad.testutil.TypicalModules;

public class JsonSerializableCourseBookTest {

    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonSerializableModuleBookTest");
    private static final Path TYPICAL_MODULES_FILE = TEST_DATA_FOLDER.resolve("typicalModulesCourseBook.json");
    private static final Path INVALID_MODULE_FILE = TEST_DATA_FOLDER.resolve("invalidModuleCourseBook.json");
    private static final Path DUPLICATE_MODULE_FILE = TEST_DATA_FOLDER.resolve("duplicateModuleCourseBook.json");

    @Test
    public void toModelType_typicalPersonsFile_success() throws Exception {
        JsonSerializableCourseBook dataFromFile = JsonUtil.readJsonFile(TYPICAL_MODULES_FILE,
                JsonSerializableCourseBook.class).get();
        CourseBook courseBookFromFile = dataFromFile.toModelType();
        CourseBook typicalPersonsCourseBook = TypicalModules.getTypicalCourseBook();
        assertEquals(courseBookFromFile, typicalPersonsCourseBook);
    }

    @Test
    public void toModelType_invalidPersonFile_throwsIllegalValueException() throws Exception {
        JsonSerializableCourseBook dataFromFile = JsonUtil.readJsonFile(INVALID_MODULE_FILE,
                JsonSerializableCourseBook.class).get();
        assertThrows(IllegalValueException.class, dataFromFile::toModelType);
    }

    @Test
    public void toModelType_duplicatePersons_throwsIllegalValueException() throws Exception {
        JsonSerializableCourseBook dataFromFile = JsonUtil.readJsonFile(DUPLICATE_MODULE_FILE,
                JsonSerializableCourseBook.class).get();
        assertThrows(IllegalValueException.class, JsonSerializableCourseBook.MESSAGE_DUPLICATE_MODULE,
                dataFromFile::toModelType);
    }

}
