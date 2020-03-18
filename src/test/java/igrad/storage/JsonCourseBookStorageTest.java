package igrad.storage;

import static igrad.testutil.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import igrad.commons.exceptions.DataConversionException;
import igrad.model.CourseBook;
import igrad.model.ReadOnlyCourseBook;
import igrad.testutil.TypicalModules;

public class JsonCourseBookStorageTest {
    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonCourseBookStorageTest");

    @TempDir
    public Path testFolder;

    @Test
    public void readCourseBook_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> readCourseBook(null));
    }

    private java.util.Optional<ReadOnlyCourseBook> readCourseBook(String filePath) throws Exception {
        return new JsonCourseBookStorage(Paths.get(filePath)).readCourseBook(addToTestDataPathIfNotNull(filePath));
    }

    private Path addToTestDataPathIfNotNull(String prefsFileInTestDataFolder) {
        return prefsFileInTestDataFolder != null
            ? TEST_DATA_FOLDER.resolve(prefsFileInTestDataFolder)
            : null;
    }

    @Test
    public void read_missingFile_emptyResult() throws Exception {
        assertFalse(readCourseBook("NonExistentFile.json").isPresent());
    }

    @Test
    public void read_notJsonFormat_exceptionThrown() {
        assertThrows(DataConversionException.class, () -> readCourseBook("notJsonFormatCourseBook.json"));
    }

    @Test
    public void readCourseBook_invalidCourseBook_throwDataConversionException() {
        assertThrows(DataConversionException.class, () -> readCourseBook("invalidModuleCourseBook.json"));
    }

    @Test
    public void readCourseBook_invalidAndValidPersonCourseBook_throwDataConversionException() {
        assertThrows(DataConversionException.class, () -> readCourseBook("invalidAndValidModuleCourseBook.json"));
    }

    @Test
    public void saveCourseBook_nullCourseBook_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveCourseBook(null, "SomeFile.json"));
    }

    /**
     * Saves {@code courseBook} at the specified {@code filePath}.
     */
    private void saveCourseBook(ReadOnlyCourseBook courseBook, String filePath) {
        try {
            new JsonCourseBookStorage(Paths.get(filePath))
                .saveCourseBook(courseBook, addToTestDataPathIfNotNull(filePath));
        } catch (IOException ioe) {
            throw new AssertionError("There should not be an error writing to the file.", ioe);
        }
    }

    @Test
    public void saveCourseBook_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveCourseBook(new CourseBook(), null));
    }
}
