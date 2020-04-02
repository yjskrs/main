package igrad.model;

import igrad.model.course.CourseInfo;
import igrad.model.module.Module;
import igrad.model.requirement.Requirement;
import javafx.collections.ObservableList;

/**
 * Unmodifiable view of a course book.
 */
public interface ReadOnlyCourseBook {
    /**
     * Returns an unmodifiable view of the modules list.
     * This list will not contain any duplicate modules.
     */
    ObservableList<Module> getModuleList();

    /**
     * Returns an unmodifiable view of the requirements in the course book.
     */
    ObservableList<Requirement> getRequirementList();

    /**
     * Returns the course info.
     */
    CourseInfo getCourseInfo();

    /**
     * Returns the number of requirements fulfilled. A fulfilled requirement is reflected by the condition
     * {@code Requirement#isFulfilled()}.
     *
     * @see Requirement#isFulfilled()
     */
    String requirementsFulfilled();

    /**
     * Checks if the course is complete. A course is complete when all its requirements are fulfilled.
     */
    boolean isComplete();
}
