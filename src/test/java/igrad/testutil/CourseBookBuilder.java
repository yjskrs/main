package igrad.testutil;

import igrad.model.CourseBook;
import igrad.model.person.Person;

/**
 * A utility class to help with building CourseBook objects.
 * Example usage: <br>
 *     {@code CourseBook ab = new CourseBookBuilder().withPerson("John", "Doe").build();}
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
     * Adds a new {@code Person} to the {@code CourseBook} that we are building.
     */
    public CourseBookBuilder withPerson(Person person) {
        courseBook.addPerson(person);
        return this;
    }

    public CourseBook build() {
        return courseBook;
    }
}
