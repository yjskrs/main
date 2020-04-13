package igrad.model;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.Optional;

import igrad.model.course.CourseInfo;
import igrad.model.module.Module;
import igrad.model.module.ModuleCode;
import igrad.model.module.UniqueModuleList;
import igrad.model.requirement.Requirement;
import igrad.model.requirement.RequirementCode;
import igrad.model.requirement.UniqueRequirementList;
import javafx.collections.ObservableList;

/**
 * Wraps all data at the course book level.
 */
public class CourseBook implements ReadOnlyCourseBook {

    private final UniqueModuleList modules = new UniqueModuleList();
    private final UniqueRequirementList requirements = new UniqueRequirementList();
    private CourseInfo courseInfo = new CourseInfo();

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

    // Course (info)-level operations

    /**
     * Adds the given courseInfo (only one courseInfo can exist/ever be created in the system).
     */
    public void addCourseInfo(CourseInfo c) {
        courseInfo = c;
    }

    /**
     * Retrieves the current course info, in the course book.
     */
    public CourseInfo getCourseInfo() {
        return courseInfo;
    }

    /**
     * Replaces the courseInfo with {@code editedCourseInfo}.
     */
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
     * Returns a module which has module code; {@code moduleCode}
     * {@code Optional.empty} otherwise.
     */
    public Optional<Module> getModule(ModuleCode moduleCode) {
        requireNonNull(moduleCode);
        return modules.getByModuleCode(moduleCode);
    }

    /**
     * Returns all modules which has module code; {@code moduleCode}
     * {@code Optional.empty} otherwise.
     */
    /**
     * Returns all modules (in a module list), whose module code matches the module codes
     * in; {@code moduleCodes}.
     */
    public List<Module> getModules(List<ModuleCode> moduleCodes) {
        requireNonNull(moduleCodes);
        return modules.getByModuleCodes(moduleCodes);
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
     * Returns a requirement which has requirement code; {@code requirementCode}, and
     * {@code Optional.empty} otherwise.
     */
    public Optional<Requirement> getRequirement(RequirementCode requirementCode) {
        requireNonNull(requirementCode);

        return requirements.getByRequirementCode(requirementCode);
    }

    /**
     * Returns all requirements (in a requirement list), which has the module; {@code module}.
     */
    public List<Requirement> getRequirementsWithModule(Module module) {
        requireNonNull(module);

        return requirements.getByModule(module);
    }

    /**
     * Adds a requirement to the course book.
     * The requirement must not already exist in the course book.
     */
    public void addRequirement(Requirement requirement) {
        requireNonNull(requirement);

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
        requireNonNull(requirement);

        requirements.remove(requirement);
    }

    // util methods

    @Override
    public String toString() {
        return modules.asUnmodifiableObservableList().size() + " modules";
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
        return requirements.countFulfilled();
    }

    /**
     * Checks if the course is complete. A course is complete when all its requirements are fulfilled.
     */
    @Override
    public boolean isComplete() {
        return requirements.areAllFulfilled();
    }

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
