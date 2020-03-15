package igrad.model;

import static java.util.Objects.requireNonNull;

import java.util.List;

import igrad.model.course.CourseInfo;
import igrad.model.module.Module;
import igrad.model.module.UniqueModuleList;
import igrad.model.requirement.Requirement;
import igrad.model.requirement.UniqueRequirementList;
import javafx.collections.ObservableList;

/**
 * Wraps all data at the course book level.
 */
public class CourseBook implements ReadOnlyCourseBook {

    private final UniqueRequirementList requirements;
    private final UniqueModuleList modules;
    private CourseInfo courseInfo;

    /*
     * The 'unusual' code block below is a non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        modules = new UniqueModuleList();
        requirements = new UniqueRequirementList();
        courseInfo = null; // TODO: change to Optional or default value
    }

    public CourseBook() {
    }

    /**
     * Creates an CourseBook using the Modules in the {@code toBeCopied}.
     */
    public CourseBook(ReadOnlyCourseBook toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    // list overwrite operations

    /**
     * Replaces the contents of the module list with {@code modules}.
     * {@code modules} must not contain duplicate modules.
     */
    public void setModules(List<Module> modules) {
        this.modules.setModules(modules);
    }

    /**
     * Replaces the contents of the module list with {@code modules}.
     * {@code modules} must not contain duplicate modules.
     */
    public void setRequirements(List<Requirement> requirements) {
        this.requirements.setRequirements(requirements);
    }

    /**
     * Resets the existing data of this {@code CourseBook} with {@code newData}.
     */
    public void resetData(ReadOnlyCourseBook newData) {
        requireNonNull(newData);

        setModules(newData.getModuleList());
        setRequirements(newData.getRequirementList());
    }

    // module-level operations

    /**
     * Returns true if a module with the same identity as {@code module} exists in the course book.
     */
    public boolean hasModule(Module module) {
        requireNonNull(module);
        return modules.contains(module);
    }

    /**
     * Adds the given courseInfo (only one courseInfo can exist/ever be created in the system).
     */
    public void addCourseInfo(CourseInfo c) {
        // TODO: Restrict to one
        courseInfo = c;
    }

    /**
     * Replaces the courseInfo with {@code editedCourseInfo}.
     */
    public void modifyCourseInfo(CourseInfo editedCourseInfo) {
        this.courseInfo = editedCourseInfo;
    }

    public CourseInfo getCourseInfo() {
        return courseInfo;
    }

    /**
     * Adds a module to the course book.
     * The module must not already exist in the course book.
     */
    public void addModule(Module m) {
        modules.add(m);
    }

    /**
     * Replaces the given module {@code target} in the list with {@code editedModule}.
     * {@code target} must exist in the course book.
     * The module identity of {@code editedModule} must not be the same as another existing module
     * in the course book.
     */
    public void setModule(Module target, Module editedModule) {
        requireNonNull(editedModule);

        modules.setModule(target, editedModule);
    }

    /**
     * Removes {@code key} from this {@code CourseBook}.
     * {@code key} must exist in the course book.
     */
    public void removeModule(Module key) {
        modules.remove(key);
    }

    // util methods

    @Override
    public String toString() {
        return modules.asUnmodifiableObservableList().size() + " persons";
        // TODO: refine later
    }

    @Override
    public ObservableList<Module> getModuleList() {
        return modules.asUnmodifiableObservableList();
    }

    @Override
    public ObservableList<Requirement> getRequirementList() {
        return requirements.asUnmodifiableObservableList();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof CourseBook // instanceof handles nulls
            && modules.equals(((CourseBook) other).modules)
            && courseInfo.equals(((CourseBook) other).courseInfo));
    }

    @Override
    public int hashCode() {
        return modules.hashCode();
    }
}
