package igrad.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import igrad.commons.exceptions.DataConversionException;
import igrad.commons.util.JsonUtil;
import igrad.model.ReadOnlyUserPrefs;
import igrad.model.UserPrefs;

/**
 * A class to access UserPrefs stored in the hard disk as a json file.
 */
public class JsonUserPrefsStorage implements UserPrefsStorage {

    private Path filePath;

    public JsonUserPrefsStorage(Path filePath) {
        this.filePath = filePath;
    }

    @Override
    public Path getUserPrefsFilePath() {
        return filePath;
    }

    @Override
    public Optional<UserPrefs> readUserPrefs() throws DataConversionException {
        return readUserPrefs(filePath);
    }

    /**
     * Similar to {@link #readUserPrefs()}
     *
     * @param filePath location of the data. Cannot be null.
     * @throws DataConversionException if the file format is not as expected.
     */
    public Optional<UserPrefs> readUserPrefs(Path filePath) throws DataConversionException {
        return JsonUtil.readJsonFile(filePath, UserPrefs.class);
    }

    @Override
    public void saveUserPrefs(ReadOnlyUserPrefs userPrefs) throws IOException {
        JsonUtil.saveJsonFile(userPrefs, filePath);
    }
}
