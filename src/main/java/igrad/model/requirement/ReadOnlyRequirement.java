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
     * Returns the credits required for the requirement.
     */
    String getCreditsRequired();

    /**
     * Returns the credits fulfilled for the requirement.
     */
    String getCreditsFulfilled();

    /**
     * Returns an unmodifiable view of the modules in this requirement.
     * This list will not contain any duplicate modules.
     */
    ObservableList<Module> getModuleList();

    /**
     * Returns a boolean value to indicate whether the requirement is fulfilled.
     * Returns true if requirement is fulfilled (credits are fulfilled), else return false.
     */
    boolean isFulfilled();

}
