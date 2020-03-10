package igrad.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.nio.file.Path;

import org.junit.jupiter.api.BeforeEach;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import igrad.commons.core.GuiSettings;
import igrad.model.CourseBook;
import igrad.model.ReadOnlyCourseBook;
import igrad.model.UserPrefs;
import igrad.testutil.TypicalModules;

public class StorageManagerTest {

    @TempDir
    public Path testFolder;

    private StorageManager storageManager;

    @BeforeEach
    public void setUp() {
        JsonCourseBookStorage courseBookStorage = new JsonCourseBookStorage(getTempFilePath("ab"));
        JsonUserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(getTempFilePath("prefs"));
        storageManager = new StorageManager(courseBookStorage, userPrefsStorage);
    }

    private Path getTempFilePath(String fileName) {
        return testFolder.resolve(fileName);
    }

    @Test
    public void prefsReadSave() throws Exception {
        /*
         * Note: This is an integration test that verifies the StorageManager is properly wired to the
         * {@link JsonUserPrefsStorage} class.
         * More extensive testing of UserPref saving/reading is done in {@link JsonUserPrefsStorageTest} class.
         */
        UserPrefs original = new UserPrefs();
        original.setGuiSettings(new GuiSettings(300, 600, 4, 6));
        storageManager.saveUserPrefs(original);
        UserPrefs retrieved = storageManager.readUserPrefs().get();
        assertEquals(original, retrieved);
    }

    @Test
    public void courseBookReadSave() throws Exception {
        /*
         * Note: This is an integration test that verifies the StorageManager is properly wired to the
         * {@link JsonCourseBookStorage} class.
         * More extensive testing of UserPref saving/reading is done in {@link JsonCourseBookStorageTest} class.
         */
        CourseBook original = TypicalModules.getTypicalCourseBook();
        storageManager.saveCourseBook(original);
        ReadOnlyCourseBook retrieved = storageManager.readCourseBook().get();
        assertEquals(original, new CourseBook(retrieved));
    }

    @Test
    public void getCourseBookFilePath() {
        assertNotNull(storageManager.getCourseBookFilePath());
    }

}
