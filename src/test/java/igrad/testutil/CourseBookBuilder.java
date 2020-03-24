package igrad.testutil;

import igrad.model.CourseBook;
import igrad.model.module.Module;
import igrad.model.requirement.Requirement;

/**
 * A utility class to help with building CourseBook objects.
 * Example usage: <br>
 * {@code CourseBook ab = new CourseBookBuilder().withModule(module).build();}
 */
public class CourseBookBuilder {

    private CourseBook courseBook;

    public CourseBookBuilder() {
        courseBook = new CourseBook();
    }

    public CourseBookBuilder(CourseBook courseBook) {
        this.courseBook = courseBook;
    }

    /**
     * Adds a new {@code Module} to the {@code CourseBook} that we are building.
     */
    public CourseBookBuilder withModule(Module module) {
        courseBook.addModule(module);
        return this;
    }

    /**
     * Adds a new {@code Requirement} to the {@code CourseBook} that we are building.
     */
    public CourseBookBuilder requirement(Requirement requirement) {
        courseBook.addRequirement(requirement);
        return this;
    }

    public CourseBook build() {
        return courseBook;
    }

}
