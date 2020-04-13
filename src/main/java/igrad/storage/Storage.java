package igrad.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import igrad.commons.exceptions.DataConversionException;
import igrad.model.ReadOnlyCourseBook;
import igrad.model.ReadOnlyUserPrefs;
import igrad.model.UserPrefs;

/**
 * API of the Storage component.
 */
public interface Storage extends CourseBookStorage, UserPrefsStorage {

    @Override
    Optional<UserPrefs> readUserPrefs() throws DataConversionException, IOException;

    @Override
    void saveUserPrefs(ReadOnlyUserPrefs userPrefs) throws IOException;

    @Override
    Path getCourseBookFilePath();

    @Override
    Optional<ReadOnlyCourseBook> readCourseBook() throws DataConversionException, IOException;

    @Override
    void saveCourseBook(ReadOnlyCourseBook courseBook) throws IOException;
}
