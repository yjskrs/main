package igrad.model;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.Optional;

import igrad.model.course.CourseInfo;
import igrad.model.module.Module;
import igrad.model.module.UniqueModuleList;
import igrad.model.requirement.Credits;
import igrad.model.requirement.Requirement;
import igrad.model.requirement.RequirementCode;
import igrad.model.requirement.Title;
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
        courseInfo = new CourseInfo(Optional.empty(), Optional.empty());
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
     * Replaces the contents of the requirement list (which consists a list of modules) with {@code requirements}.
     * {@code requirements} must not contain duplicate modules.
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
        setCourseInfo(newData.getCourseInfo());
    }

    // Course-level operations

    /**
     * Adds the given courseInfo (only one courseInfo can exist/ever be created in the system).
     */
    public void addCourseInfo(CourseInfo c) {
        courseInfo = c;
    }

    /**
     * Replaces the courseInfo with {@code editedCourseInfo}.
     */
    public void editCourseInfo(CourseInfo editedCourseInfo) {
        this.courseInfo = editedCourseInfo;
    }

    public CourseInfo getCourseInfo() {
        return courseInfo;
    }

    public void setCourseInfo(CourseInfo c) {
        this.courseInfo = c;
    }

    // Module-level operations

    /**
     * Returns true if a module with the same identity as {@code module} exists in the course book.
     */
    public boolean hasModule(Module module) {
        requireNonNull(module);
        return modules.contains(module);
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

    // Requirement-level operations

    /**
     * Returns true if a requirement with the same identity as {@code requirement} exists in the course book.
     */
    public boolean hasRequirement(Requirement requirement) {
        requireNonNull(requirement);
        return requirements.contains(requirement);
    }

    /**
     * Adds a requirement to the course book.
     * The requirement must not already exist in the course book.
     */
    public void addRequirement(Requirement requirement) {
        requirements.add(requirement);
    }

    /**
     * Replaces the given requirement {@code target} in the list with {@code editedRequirement}.
     * {@code target} must exist in the course book.
     * The module identity of {@code editedRequirement} must not be the same as another existing requirement
     * in the course book.
     */
    public void setRequirement(Requirement target, Requirement editedRequirement) {
        requireNonNull(editedRequirement);

        requirements.setRequirement(target, editedRequirement);
    }

    /**
     * Removes {@code requirement} from this {@code CourseBook}.
     * {@code requirement} must exist in the course book.
     */
    public void removeRequirement(Requirement requirement) {
        requirements.remove(requirement);
    }

    /**
     * Removes a {@code Module} from all {@code Requirement} which contains it
     */
    public void removeModuleFromRequirement(Module module) {

        for (Requirement requirement : requirements) {
            ObservableList<Module> moduleList = requirement.getModuleList();
            Credits credits = requirement.getCredits();

            if (moduleList.contains(module)) {
                requirement.removeModule(module);

                int creditsRequired = requirement.getCreditsRequired();
                int creditsFulfilled = credits.getCreditsFulfilled() - module.getCredits().toInteger();
                Credits updatedCredits = new Credits(creditsRequired, creditsFulfilled);

                // TODO: Improve design of this part, can move logic to CourseBook itself maybe hmm

                // Copy all other requirement fields over
                Title title = requirement.getTitle();
                List<Module> modules = requirement.getModuleList();
                RequirementCode requirementCode = requirement.getRequirementCode();

                Requirement updatedRequirement = new Requirement(requirementCode, title, updatedCredits, modules);
                setRequirement(requirement, updatedRequirement);
            }
        }

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
    public String requirementsFulfilled() {
        return
    }

    /**
     * Checks if the course is complete. A course is complete when all its requirements are fulfilled.
     */
    boolean isComplete();

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof CourseBook // instanceof handles nulls
            && modules.equals(((CourseBook) other).modules)
            && requirements.equals(((CourseBook) other).requirements)
            && courseInfo.equals(((CourseBook) other).courseInfo));
    }

    @Override
    public int hashCode() {
        return modules.hashCode();
    }
}
