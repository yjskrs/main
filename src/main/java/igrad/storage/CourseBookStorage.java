package igrad.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import igrad.commons.exceptions.DataConversionException;
import igrad.model.CourseBook;
import igrad.model.ReadOnlyCourseBook;

/**
 * Represents a storage for {@link CourseBook}.
 */
public interface CourseBookStorage {

    /**
     * Returns the file path of the data file.
     */
    Path getCourseBookFilePath();

    /**
     * Returns CourseBook data as a {@link ReadOnlyCourseBook}.
     * Returns {@code Optional.empty()} if storage file is not found.
     *
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException             if there was any problem when reading from the storage.
     */
    Optional<ReadOnlyCourseBook> readCourseBook() throws DataConversionException, IOException;

    /**
     * @see #getCourseBookFilePath()
     */
    Optional<ReadOnlyCourseBook> readCourseBook(Path filePath) throws DataConversionException, IOException;

    /**
     * Saves the given {@link ReadOnlyCourseBook} to the storage.
     *
     * @param courseBook cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveCourseBook(ReadOnlyCourseBook courseBook) throws IOException;

    /**
     * @see #saveCourseBook(ReadOnlyCourseBook)
     */
    void saveCourseBook(ReadOnlyCourseBook courseBook, Path filePath) throws IOException;


}
