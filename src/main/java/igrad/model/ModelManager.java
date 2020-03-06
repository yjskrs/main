package igrad.model;

import static java.util.Objects.requireNonNull;
import static igrad.commons.util.CollectionUtil.requireAllNonNull;

import java.nio.file.Path;
import java.util.function.Predicate;
import java.util.logging.Logger;

import igrad.commons.core.GuiSettings;
import igrad.commons.core.LogsCenter;
import igrad.commons.util.CollectionUtil;
import igrad.model.person.Person;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;

/**
 * Represents the in-memory model of the course book data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final CourseBook courseBook;
    private final UserPrefs userPrefs;
    private final FilteredList<Person> filteredPersons;

    /**
     * Initializes a ModelManager with the given courseBook and userPrefs.
     */
    public ModelManager(ReadOnlyCourseBook courseBook, ReadOnlyUserPrefs userPrefs) {
        super();
        CollectionUtil.requireAllNonNull(courseBook, userPrefs);

        logger.fine("Initializing with course book: " + courseBook + " and user prefs " + userPrefs);

        this.courseBook = new CourseBook(courseBook);
        this.userPrefs = new UserPrefs(userPrefs);
        filteredPersons = new FilteredList<>(this.courseBook.getPersonList());
    }

    public ModelManager() {
        this(new CourseBook(), new UserPrefs());
    }

    //=========== UserPrefs ==================================================================================

    @Override
    public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
        requireNonNull(userPrefs);
        this.userPrefs.resetData(userPrefs);
    }

    @Override
    public ReadOnlyUserPrefs getUserPrefs() {
        return userPrefs;
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

    @Override
    public void setCourseBook(ReadOnlyCourseBook courseBook) {
        this.courseBook.resetData(courseBook);
    }

    @Override
    public ReadOnlyCourseBook getCourseBook() {
        return courseBook;
    }

    @Override
    public boolean hasPerson(Person person) {
        requireNonNull(person);
        return courseBook.hasPerson(person);
    }

    @Override
    public void deletePerson(Person target) {
        courseBook.removePerson(target);
    }

    @Override
    public void addPerson(Person person) {
        courseBook.addPerson(person);
        updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
    }

    @Override
    public void setPerson(Person target, Person editedPerson) {
        CollectionUtil.requireAllNonNull(target, editedPerson);

        courseBook.setPerson(target, editedPerson);
    }

    //=========== Filtered Person List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Person} backed by the internal list of
     * {@code versionedCourseBook}
     */
    @Override
    public ObservableList<Person> getFilteredPersonList() {
        return filteredPersons;
    }

    @Override
    public void updateFilteredPersonList(Predicate<Person> predicate) {
        requireNonNull(predicate);
        filteredPersons.setPredicate(predicate);
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
                && filteredPersons.equals(other.filteredPersons);
    }

}
