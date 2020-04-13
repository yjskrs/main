package igrad.model;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

import igrad.commons.core.GuiSettings;
import igrad.commons.exceptions.DataConversionException;
import igrad.model.avatar.Avatar;
import igrad.model.course.CourseInfo;
import igrad.model.module.Module;
import igrad.model.module.ModuleCode;
import igrad.model.requirement.Requirement;
import igrad.model.requirement.RequirementCode;
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
     * Returns the user prefs' backup course book file path.
     */
    Path getBackupCourseBookFilePath();

    /**
     * Resets {@code courseBook} data to a blank state with no data (e.g, modules, requirements, etc).
     */
    void resetCourseBook(ReadOnlyCourseBook courseBook);

    /**
     * Undoes the course book to the previous state.
     * Returns true if change was detected
     */
    boolean undoCourseBook() throws IOException, DataConversionException;

    /**
     * Exports the course book.
     * Returns a list of exported modules
     */
    List<Module> exportModuleList() throws IOException;

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
     * Checks if the prerequisites for a module exists
     * and is done in the filteredModuleList.
     * <p>
     * As long as one prerequisite is not fulfilled or not included,
     * this will return false.
     */
    boolean hasModulePrerequisites(Module module);

    /**
     * Checks if the preclusions for a module exists in the filteredModuleList.
     * <p>
     * As long as one preclusion is included, this will return true
     */
    boolean hasModulePreclusions(Module module);

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
     * Replaces the given module {@code target} with {@code editedModule}.
     * {@code target} must exist in the course book.
     * The module identity of {@code editedModule} must not be the same as another existing module in the course book.
     */
    void setCourseInfo(CourseInfo editedCourseInfo);

    /**
     * Checks if the course name has been set.
     */
    boolean isCourseNameSet();

    String getRandomQuoteString();

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
     * Retrieves the {@code Requirement} exists in the course book, by checking only its given
     * {@code RequirementCode}.
     * Returns the @{code Requirement} if it exists else {@code Optional.empty} otherwise.
     */
    Optional<Requirement> getRequirement(RequirementCode requirementCode);

    /**
     * Retrieves the {@code Requirement} whose {@code Module} list has the specified module
     * {@code module}
     */
    List<Requirement> getRequirementsWithModule(Module module);

    /**
     * Retrieves the {@code Module} exists in the course book, by checking only its given {@code ModuleCode}.
     * Returns the @{code Module} if it exists else {@code Optional.empty} otherwise.
     */
    Optional<Module> getModule(ModuleCode moduleCode);

    /**
     * Retrieves a list of {@code Module} which exists in the course book, by checking only its {@code ModuleCode}.
     */
    List<Module> getModules(List<ModuleCode> moduleCodes);

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
     * Returns an sorted {@code List} sorted according to {@code Comparator<Module>}.
     */
    List<Module> getSortedModuleList(Comparator<Module> comparator);

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
