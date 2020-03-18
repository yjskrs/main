package igrad.model;

import java.nio.file.Path;
import java.util.function.Predicate;

import igrad.commons.core.GuiSettings;
import igrad.model.avatar.Avatar;
import igrad.model.course.CourseInfo;
import igrad.model.module.Module;
import igrad.model.requirement.Requirement;
import javafx.collections.ObservableList;

/**
 * The API of the Model component.
 */
public interface Model {
    /**
     * {@code Predicate} that always evaluate to true
     */
    Predicate<Module> PREDICATE_SHOW_ALL_MODULES = unused -> true;

    /**
     * {@code Predicate} that always evaluate to true
     */
    Predicate<Requirement> PREDICATE_SHOW_ALL_REQUIREMENTS = unused -> true;

    /**
     * Returns the user prefs.
     */
    ReadOnlyUserPrefs getUserPrefs();

    /**
     * Replaces user prefs data with the data in {@code userPrefs}.
     */
    void setUserPrefs(ReadOnlyUserPrefs userPrefs);

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
     * Resets {@code courseBook} data to a blank state with no data (e.g, modules, requirements, etc).
     */
    void resetCourseBook(ReadOnlyCourseBook courseBook);

    /**
     * Returns the Avatar
     */
    Avatar getAvatar();

    /**
     * Sets the Avatar
     */
    void setAvatar(Avatar avatar);

    /**
     * Checks whether the Avatar is a sample Avatar (i.e, Avatar not set yet)
     */
    boolean isSampleAvatar();

    /**
     * Returns the CourseBook
     */
    ReadOnlyCourseBook getCourseBook();

    /**
     * Replaces course book data with the data in {@code courseBook}.
     */
    void setCourseBook(ReadOnlyCourseBook courseBook);

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
     * Adds the given {@code courseInfo} to the course book.
     */
    void addCourseInfo(CourseInfo courseInfo);

    /**
     * Returns the courseInfo.
     */
    CourseInfo getCourseInfo();

    /**
     * Modifies the name of the course to {@code courseInfo}.
     */
    void modifyCourseInfo(CourseInfo courseInfo);

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

    /**
     * Checks if the {@code requirement} exists in the course book.
     * Returns true if it exists and false otherwise.
     */
    boolean hasRequirement(Requirement requirement);

    /**
     * Adds the given requirement.
     * {@code requirement} must not already exist in the course book.
     */
    void addRequirement(Requirement requirement);

    /**
     * Replaces the given requirement {@code target} with {@code editedRequirement}.
     * {@code target} must exist in the course book and {@code editedRequirement} must not
     * have the same title as another requirement in the course book.
     */
    void setRequirement(Requirement target, Requirement editedRequirement);

    /**
     * Deletes the given {@code requirement}.
     */
    void deleteRequirement(Requirement requirement);

    /**
     * Returns an unmodifiable view of the filtered module list.
     */
    ObservableList<Module> getFilteredModuleList();

    /**
     * Returns an unmodifiable view of the requirements list.
     */
    ObservableList<Requirement> getRequirementList();

    /**
     * Updates the filter of the filtered module list to filter by the given {@code predicate}.
     *
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredModuleList(Predicate<Module> predicate);

    /**
     * Updates the filter of the filtered requirement list to filter by the given {@code predicate}.
     *
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateRequirementList(Predicate<Requirement> predicate);
}
