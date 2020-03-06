package igrad.model;

import javafx.collections.ObservableList;
import igrad.model.person.Person;

/**
 * Unmodifiable view of an address book
 */
public interface ReadOnlyCourseBook {

    /**
     * Returns an unmodifiable view of the persons list.
     * This list will not contain any duplicate persons.
     */
    ObservableList<Person> getPersonList();

}
