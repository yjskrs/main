package igrad.model;

import static igrad.commons.util.CollectionUtil.requireAllNonNull;
import static java.util.Objects.requireNonNull;

import java.nio.file.Path;
import java.util.function.Predicate;
import java.util.logging.Logger;

import igrad.commons.core.GuiSettings;
import igrad.commons.core.LogsCenter;
import igrad.model.course.CourseInfo;
import igrad.model.module.Module;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;

/**
 * Represents the in-memory model of the course book data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final CourseBook courseBook;
    private final UserPrefs userPrefs;
    private final FilteredList<Module> filteredModules;

    /**
     * Initializes a ModelManager with the given courseBook and userPrefs.
     */
    public ModelManager(ReadOnlyCourseBook courseBook, ReadOnlyUserPrefs userPrefs) {
        super();
        requireAllNonNull(courseBook, userPrefs);

        logger.fine("Initializing with course book: " + courseBook + " and user prefs " + userPrefs);

        this.courseBook = new CourseBook(courseBook);
        this.userPrefs = new UserPrefs(userPrefs);
        filteredModules = new FilteredList<>(this.courseBook.getModuleList());
    }

    public ModelManager() {
        this(new CourseBook(), new UserPrefs());
    }

    //=========== UserPrefs ==================================================================================

    @Override
    public ReadOnlyUserPrefs getUserPrefs() {
        return userPrefs;
    }

    @Override
    public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
        requireNonNull(userPrefs);
        this.userPrefs.resetData(userPrefs);
    }

    @Override
    public GuiSettings getGuiSettings() {
        return userPrefs.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        requireNonNull(guiSettings);
        userPrefs.setGuiSettings(guiSettings);
    }

    @Override
    public Path getCourseBookFilePath() {
        return userPrefs.getCourseBookFilePath();
    }

    @Override
    public void setCourseBookFilePath(Path courseBookFilePath) {
        requireNonNull(courseBookFilePath);
        userPrefs.setCourseBookFilePath(courseBookFilePath);
    }

    //=========== CourseBook ================================================================================

    /**
     * Resets the course book data to a blank state with no data (e.g, modules, requirements, etc).
     *
     * @param courseBook
     */
    @Override
    public void resetCourseBook(ReadOnlyCourseBook courseBook) {
        this.setCourseBook(new CourseBook());
    }

    @Override
    public ReadOnlyCourseBook getCourseBook() {
        return courseBook;
    }

    @Override
    public void setCourseBook(ReadOnlyCourseBook courseBook) {
        this.courseBook.resetData(courseBook);
    }

    @Override
    public boolean hasModule(Module module) {
        requireNonNull(module);
        return courseBook.hasModule(module);
    }

    @Override
    public void deleteModule(Module target) {
        courseBook.removeModule(target);
    }

    /**
     * Adds the given courseInfo to the course book.
     *
     * @param courseInfo
     */
    @Override
    public void addCourseInfo(CourseInfo courseInfo) {
        courseBook.addCourseInfo(courseInfo);
    }

    /**
     * Modifies the name of the course.
     *
     * @param courseInfo
     */
    @Override
    public void modifyCourseInfo(CourseInfo courseInfo) {
        courseBook.modifyCourseInfo(courseInfo);
    }

    @Override
    public void addModule(Module module) {
        courseBook.addModule(module);
        updateFilteredModuleList(PREDICATE_SHOW_ALL_MODULES);
    }

    @Override
    public void setModule(Module target, Module editedModule) {
        requireAllNonNull(target, editedModule);

        courseBook.setModule(target, editedModule);
    }

    //=========== Filtered Module List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Module} backed by the internal list of
     * {@code versionedCourseBook}
     */
    @Override
    public ObservableList<Module> getFilteredModuleList() {
        return filteredModules;
    }

    @Override
    public void updateFilteredModuleList(Predicate<Module> predicate) {
        requireNonNull(predicate);
        filteredModules.setPredicate(predicate);
    }

    @Override
    public boolean equals(Object obj) {
        // short circuit if same object
        if (obj == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(obj instanceof ModelManager)) {
            return false;
        }

        // state check
        ModelManager other = (ModelManager) obj;
        return courseBook.equals(other.courseBook)
            && userPrefs.equals(other.userPrefs)
            && filteredModules.equals(other.filteredModules);
    }

}
