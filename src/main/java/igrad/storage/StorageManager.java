package igrad.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import igrad.commons.core.LogsCenter;
import igrad.commons.exceptions.DataConversionException;
import igrad.model.ReadOnlyCourseBook;
import igrad.model.ReadOnlyUserPrefs;
import igrad.model.UserPrefs;

/**
 * Manages storage of CourseBook data in local storage.
 */
public class StorageManager implements Storage {

    private static final Logger logger = LogsCenter.getLogger(StorageManager.class);
    private CourseBookStorage courseBookStorage;
    private UserPrefsStorage userPrefsStorage;


    public StorageManager(CourseBookStorage courseBookStorage, UserPrefsStorage userPrefsStorage) {
        super();
        this.courseBookStorage = courseBookStorage;
        this.userPrefsStorage = userPrefsStorage;
    }

    // ================ UserPrefs methods ==============================

    @Override
    public Path getUserPrefsFilePath() {
        return userPrefsStorage.getUserPrefsFilePath();
    }

    @Override
    public Optional<UserPrefs> readUserPrefs() throws DataConversionException, IOException {
        return userPrefsStorage.readUserPrefs();
    }

    @Override
    public void saveUserPrefs(ReadOnlyUserPrefs userPrefs) throws IOException {
        userPrefsStorage.saveUserPrefs(userPrefs);
    }

    // ================ CourseBook methods ==============================

    @Override
    public Path getCourseBookFilePath() {
        return courseBookStorage.getCourseBookFilePath();
    }

    @Override
    public Optional<ReadOnlyCourseBook> readCourseBook() throws DataConversionException, IOException {
        return readCourseBook(courseBookStorage.getCourseBookFilePath());
    }

    @Override
    public Optional<ReadOnlyCourseBook> readCourseBook(Path filePath) throws DataConversionException, IOException {
        logger.fine("Attempting to read data from file: " + filePath);
        return courseBookStorage.readCourseBook(filePath);
    }

    @Override
    public void saveCourseBook(ReadOnlyCourseBook courseBook) throws IOException {
        saveCourseBook(courseBook, courseBookStorage.getCourseBookFilePath());
    }


    @Override
    public void saveCourseBook(ReadOnlyCourseBook courseBook, Path filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        courseBookStorage.saveCourseBook(courseBook, filePath);
    }

}
