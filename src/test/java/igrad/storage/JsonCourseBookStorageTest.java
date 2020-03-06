package igrad.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static igrad.testutil.Assert.assertThrows;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import igrad.model.ReadOnlyCourseBook;
import igrad.testutil.Assert;
import igrad.testutil.TypicalPersons;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import igrad.commons.exceptions.DataConversionException;
import igrad.model.CourseBook;

public class JsonCourseBookStorageTest {
    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonCourseBookStorageTest");

    @TempDir
    public Path testFolder;

    @Test
    public void readAddressBook_nullFilePath_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> readAddressBook(null));
    }

    private java.util.Optional<ReadOnlyCourseBook> readAddressBook(String filePath) throws Exception {
        return new JsonCourseBookStorage(Paths.get(filePath)).readCourseBook(addToTestDataPathIfNotNull(filePath));
    }

    private Path addToTestDataPathIfNotNull(String prefsFileInTestDataFolder) {
        return prefsFileInTestDataFolder != null
                ? TEST_DATA_FOLDER.resolve(prefsFileInTestDataFolder)
                : null;
    }

    @Test
    public void read_missingFile_emptyResult() throws Exception {
        assertFalse(readAddressBook("NonExistentFile.json").isPresent());
    }

    @Test
    public void read_notJsonFormat_exceptionThrown() {
        Assert.assertThrows(DataConversionException.class, () -> readAddressBook("notJsonFormatAddressBook.json"));
    }

    @Test
    public void readAddressBook_invalidPersonAddressBook_throwDataConversionException() {
        Assert.assertThrows(DataConversionException.class, () -> readAddressBook("invalidPersonAddressBook.json"));
    }

    @Test
    public void readAddressBook_invalidAndValidPersonAddressBook_throwDataConversionException() {
        Assert.assertThrows(DataConversionException.class, () -> readAddressBook("invalidAndValidPersonAddressBook.json"));
    }

    @Test
    public void readAndSaveAddressBook_allInOrder_success() throws Exception {
        Path filePath = testFolder.resolve("TempAddressBook.json");
        CourseBook original = TypicalPersons.getTypicalAddressBook();
        JsonCourseBookStorage jsonAddressBookStorage = new JsonCourseBookStorage(filePath);

        // Save in new file and read back
        jsonAddressBookStorage.saveCourseBook(original, filePath);
        ReadOnlyCourseBook readBack = jsonAddressBookStorage.readCourseBook(filePath).get();
        assertEquals(original, new CourseBook(readBack));

        // Modify data, overwrite exiting file, and read back
        original.addPerson(TypicalPersons.HOON);
        original.removePerson(TypicalPersons.ALICE);
        jsonAddressBookStorage.saveCourseBook(original, filePath);
        readBack = jsonAddressBookStorage.readCourseBook(filePath).get();
        assertEquals(original, new CourseBook(readBack));

        // Save and read without specifying file path
        original.addPerson(TypicalPersons.IDA);
        jsonAddressBookStorage.saveCourseBook(original); // file path not specified
        readBack = jsonAddressBookStorage.readCourseBook().get(); // file path not specified
        assertEquals(original, new CourseBook(readBack));

    }

    @Test
    public void saveAddressBook_nullAddressBook_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> saveAddressBook(null, "SomeFile.json"));
    }

    /**
     * Saves {@code addressBook} at the specified {@code filePath}.
     */
    private void saveAddressBook(ReadOnlyCourseBook addressBook, String filePath) {
        try {
            new JsonCourseBookStorage(Paths.get(filePath))
                    .saveCourseBook(addressBook, addToTestDataPathIfNotNull(filePath));
        } catch (IOException ioe) {
            throw new AssertionError("There should not be an error writing to the file.", ioe);
        }
    }

    @Test
    public void saveAddressBook_nullFilePath_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> saveAddressBook(new CourseBook(), null));
    }
}
