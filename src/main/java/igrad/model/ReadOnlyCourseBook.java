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
}
