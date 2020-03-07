package igrad.model;

import igrad.model.person.Person;
import javafx.collections.ObservableList;

/**
 * Unmodifiable view of a course book.
 */
public interface ReadOnlyCourseBook {

    /**
     * Returns an unmodifiable view of the persons list.
     * This list will not contain any duplicate persons.
     */
    ObservableList<Person> getPersonList();

}
