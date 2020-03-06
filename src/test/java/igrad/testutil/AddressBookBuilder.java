package igrad.testutil;

import igrad.model.CourseBook;
import igrad.model.person.Person;

/**
 * A utility class to help with building Addressbook objects.
 * Example usage: <br>
 *     {@code CourseBook ab = new AddressBookBuilder().withPerson("John", "Doe").build();}
 */
public class AddressBookBuilder {

    private CourseBook courseBook;

    public AddressBookBuilder() {
        courseBook = new CourseBook();
    }

    public AddressBookBuilder(CourseBook courseBook) {
        this.courseBook = courseBook;
    }

    /**
     * Adds a new {@code Person} to the {@code CourseBook} that we are building.
     */
    public AddressBookBuilder withPerson(Person person) {
        courseBook.addPerson(person);
        return this;
    }

    public CourseBook build() {
        return courseBook;
    }
}
