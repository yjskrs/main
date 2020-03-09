package igrad.model;

import igrad.model.module.Module;
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

}
