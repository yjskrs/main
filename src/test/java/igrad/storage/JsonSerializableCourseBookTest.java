package igrad.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static igrad.testutil.Assert.assertThrows;

import java.nio.file.Path;
import java.nio.file.Paths;

import igrad.testutil.Assert;
import igrad.testutil.TypicalPersons;
import org.junit.jupiter.api.Test;

import igrad.commons.exceptions.IllegalValueException;
import igrad.commons.util.JsonUtil;
import igrad.model.CourseBook;

public class JsonSerializableCourseBookTest {

    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonSerializableCourseBookTest");
    private static final Path TYPICAL_PERSONS_FILE = TEST_DATA_FOLDER.resolve("typicalPersonsAddressBook.json");
    private static final Path INVALID_PERSON_FILE = TEST_DATA_FOLDER.resolve("invalidPersonAddressBook.json");
    private static final Path DUPLICATE_PERSON_FILE = TEST_DATA_FOLDER.resolve("duplicatePersonAddressBook.json");

    @Test
    public void toModelType_typicalPersonsFile_success() throws Exception {
        JsonSerializableAddressBook dataFromFile = JsonUtil.readJsonFile(TYPICAL_PERSONS_FILE,
                JsonSerializableAddressBook.class).get();
        CourseBook courseBookFromFile = dataFromFile.toModelType();
        CourseBook typicalPersonsCourseBook = TypicalPersons.getTypicalAddressBook();
        assertEquals(courseBookFromFile, typicalPersonsCourseBook);
    }

    @Test
    public void toModelType_invalidPersonFile_throwsIllegalValueException() throws Exception {
        JsonSerializableAddressBook dataFromFile = JsonUtil.readJsonFile(INVALID_PERSON_FILE,
                JsonSerializableAddressBook.class).get();
        Assert.assertThrows(IllegalValueException.class, dataFromFile::toModelType);
    }

    @Test
    public void toModelType_duplicatePersons_throwsIllegalValueException() throws Exception {
        JsonSerializableAddressBook dataFromFile = JsonUtil.readJsonFile(DUPLICATE_PERSON_FILE,
                JsonSerializableAddressBook.class).get();
        Assert.assertThrows(IllegalValueException.class, JsonSerializableAddressBook.MESSAGE_DUPLICATE_PERSON,
                dataFromFile::toModelType);
    }

}
