package igrad.storage;

import static igrad.testutil.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import igrad.commons.exceptions.DataConversionException;
import igrad.model.CourseBook;
import igrad.model.ReadOnlyCourseBook;
import igrad.model.course.Cap;
import igrad.model.course.CourseInfo;
import igrad.model.course.Credits;
import igrad.model.course.Name;
import igrad.model.course.Semesters;
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
    public void readAndSaveCourseBook_allInOrder_success() throws Exception {
        Path filePath = testFolder.resolve("TempCourseBook.json");
        CourseBook original = TypicalModules.getTypicalCourseBook();

        /*
         * Creating a course info for this test, because a course book with modules must have a course info
         * Also, since there are modules in a course book, the cap has to be computed as well
         * based on those modules and placed in the course info
         */
        Optional<Name> name = Optional.of(new Name("abc"));
        Optional<Cap> cap = CourseInfo.computeCap(original.getModuleList(), original.getRequirementList());
        Optional<Credits> credits = CourseInfo.computeCredits(original.getRequirementList());
        Optional<Semesters> semesters = CourseInfo.computeSemesters(Optional.of(new Semesters("5")),
            original.getModuleList());
        CourseInfo courseInfo = new CourseInfo(name, cap, credits, semesters);
        original.setCourseInfo(courseInfo);

        JsonCourseBookStorage jsonCourseBookStorage = new JsonCourseBookStorage(filePath);

        // Save in new file and read back
        jsonCourseBookStorage.saveCourseBook(original, filePath);
        ReadOnlyCourseBook readBack = jsonCourseBookStorage.readCourseBook(filePath).get();

        assertEquals(original, new CourseBook(readBack));

        // Modify data, overwrite existing file, and read back
        //original.addModule(TypicalModules.PROGRAMMING_METHODOLOGY);
        //original.removeModule(TypicalModules.COMPUTER_ORGANISATION);
        //jsonCourseBookStorage.saveCourseBook(original, filePath);
        //readBack = jsonCourseBookStorage.readCourseBook(filePath).get();
        //assertEquals(original, new CourseBook(readBack));

        // Save and read without specifying file path
        //original.addModule(TypicalModules.PROGRAMMING_METHODOLOGY);
        //jsonCourseBookStorage.saveCourseBook(original); // file path not specified
        //readBack = jsonCourseBookStorage.readCourseBook().get(); // file path not specified
        //assertEquals(original, new CourseBook(readBack));

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
