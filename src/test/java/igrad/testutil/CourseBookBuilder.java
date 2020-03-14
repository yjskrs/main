package igrad.testutil;

import igrad.model.CourseBook;
import igrad.model.module.Module;

/**
 * A utility class to help with building CourseBook objects.
 * Example usage: <br>
 * {@code CourseBook ab = new CourseBookBuilder().withPerson("John", "Doe").build();}
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
    public CourseBookBuilder withPerson(Module module) {
        courseBook.addModule(module);
        return this;
    }

    public CourseBook build() {
        return courseBook;
    }
}
