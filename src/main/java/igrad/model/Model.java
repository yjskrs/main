package igrad.model;

import java.nio.file.Path;
import java.util.function.Predicate;

import igrad.commons.core.GuiSettings;
import igrad.model.module.Module;
import javafx.collections.ObservableList;

/**
 * The API of the Model component.
 */
public interface Model {
    /** {@code Predicate} that always evaluate to true */
    Predicate<Module> PREDICATE_SHOW_ALL_MODULES = unused -> true;

    /**
     * Replaces user prefs data with the data in {@code userPrefs}.
     */
    void setUserPrefs(ReadOnlyUserPrefs userPrefs);

    /**
     * Returns the user prefs.
     */
    ReadOnlyUserPrefs getUserPrefs();

    /**
     * Returns the user prefs' GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Sets the user prefs' GUI settings.
     */
    void setGuiSettings(GuiSettings guiSettings);

    /**
     * Returns the user prefs' course book file path.
     */
    Path getCourseBookFilePath();

    /**
     * Sets the user prefs' course book file path.
     */
    void setCourseBookFilePath(Path courseBookFilePath);

    /**
     * Replaces course book data with the data in {@code courseBook}.
     */
    void setCourseBook(ReadOnlyCourseBook courseBook);

    /** Returns the CourseBook */
    ReadOnlyCourseBook getCourseBook();

    /**
     * Returns true if a module with the same identity as {@code module} exists in the course book.
     */
    boolean hasModule(Module module);

    /**
     * Deletes the given module.
     * The module must exist in the course book.
     */
    void deleteModule(Module target);

    /**
     * Adds the given module.
     * {@code module} must not already exist in the course book.
     */
    void addModule(Module module);

    /**
     * Replaces the given module {@code target} with {@code editedModule}.
     * {@code target} must exist in the course book.
     * The module identity of {@code editedModule} must not be the same as another existing module in the course book.
     */
    void setModule(Module target, Module editedModule);

    /** Returns an unmodifiable view of the filtered module list */
    ObservableList<Module> getFilteredModuleList();

    /**
     * Updates the filter of the filtered module list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredModuleList(Predicate<Module> predicate);
}
