package igrad.model;

import java.nio.file.Path;

import igrad.commons.core.GuiSettings;
import igrad.model.avatar.Avatar;

/**
 * Unmodifiable view of user prefs.
 */
public interface ReadOnlyUserPrefs {

    GuiSettings getGuiSettings();

    Path getCourseBookFilePath();

    Path getBackupCourseBookFilePath();

    Avatar getAvatar();
}
