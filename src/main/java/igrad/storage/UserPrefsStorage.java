package igrad.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import igrad.commons.exceptions.DataConversionException;
import igrad.model.ReadOnlyUserPrefs;
import igrad.model.UserPrefs;

/**
 * Represents a storage for {@link UserPrefs}.
 */
public interface UserPrefsStorage {

    /**
     * Returns the file path of the UserPrefs data file.
     */
    Path getUserPrefsFilePath();

    /**
     * Returns UserPrefs data from storage.
     * Returns {@code Optional.empty()} if storage file is not found.
     *
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException             if there was any problem when reading from the storage.
     */
    Optional<UserPrefs> readUserPrefs() throws DataConversionException, IOException;

    /**
     * <<<<<<< HEAD:src/main/java/igrad/storage/UserPrefsStorage.java
     * Saves the given {@link ReadOnlyUserPrefs} to the storage.
     * =======
     * Saves the given {@link seedu.address.model.ReadOnlyUserPrefs} to the storage.
     * <p>
     * >>>>>>> 84059592b7b892e5ace57b05f105da81353f9be3:src/main/java/seedu/address/storage/UserPrefsStorage.java
     *
     * @param userPrefs cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveUserPrefs(ReadOnlyUserPrefs userPrefs) throws IOException;

}
