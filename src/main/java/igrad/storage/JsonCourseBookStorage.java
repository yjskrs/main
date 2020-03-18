package igrad.storage;

import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import igrad.commons.core.LogsCenter;
import igrad.commons.exceptions.DataConversionException;
import igrad.commons.exceptions.IllegalValueException;
import igrad.commons.util.FileUtil;
import igrad.commons.util.JsonUtil;
import igrad.model.ReadOnlyCourseBook;

/**
 * A class to access CourseBook data stored as a json file on the hard disk.
 */
public class JsonCourseBookStorage implements CourseBookStorage {

    private static final Logger logger = LogsCenter.getLogger(JsonCourseBookStorage.class);

    private Path filePath;

    public JsonCourseBookStorage(Path filePath) {
        this.filePath = filePath;
    }

    public Path getCourseBookFilePath() {
        return filePath;
    }

    @Override
    public Optional<ReadOnlyCourseBook> readCourseBook() throws DataConversionException {
        return readCourseBook(filePath);
    }

    /**
     * Similar to {@link #readCourseBook()}.
     *
     * @param filePath location of the data. Cannot be null.
     * @throws DataConversionException if the file is not in the correct format.
     */
    @Override
    public Optional<ReadOnlyCourseBook> readCourseBook(Path filePath) throws DataConversionException {
        requireNonNull(filePath);

        Optional<JsonSerializableCourseBook> jsonCourseBook = JsonUtil.readJsonFile(
            filePath, JsonSerializableCourseBook.class);
        if (!jsonCourseBook.isPresent()) {
            return Optional.empty();
        }

        try {
            return Optional.of(jsonCourseBook.get().toModelType());
        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + filePath + ": " + ive.getMessage());
            throw new DataConversionException(ive);
        }
    }

    @Override
    public void saveCourseBook(ReadOnlyCourseBook courseBook) throws IOException {
        saveCourseBook(courseBook, filePath);
    }

    /**
     * Similar to {@link #saveCourseBook(ReadOnlyCourseBook)}.
     *
     * @param filePath location of the data. Cannot be null.
     */
    @Override
    public void saveCourseBook(ReadOnlyCourseBook courseBook, Path filePath) throws IOException {
        requireNonNull(courseBook);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        JsonUtil.saveJsonFile(new JsonSerializableCourseBook(courseBook), filePath);
    }

}
