package igrad.model.requirement;

import igrad.model.module.Module;
import javafx.collections.ObservableList;

/**
 * Unmodifiable view of a requirement.
 */
public interface ReadOnlyRequirement {

    /**
     * Returns the name of the requirement.
     */
    Name getName();

    /**
     * Returns the credits of the requirement.
     */
    Credits getCredits();

    /**
     * Returns an unmodifiable view of the modules in this requirement.
     * This list will not contain any duplicate modules.
     */
    ObservableList<Module> getModuleList();

}
