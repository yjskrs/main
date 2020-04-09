package igrad.model.requirement;

import igrad.model.module.Module;
import javafx.collections.ObservableList;

//@@author yjskrs

/**
 * Unmodifiable view of a requirement.
 */
public interface ReadOnlyRequirement {
    /**
     * Returns the requirement code of the requirement
     */
    RequirementCode getRequirementCode();

    /**
     * Returns the name of the requirement.
     */
    Title getTitle();

    /**
     * Returns the credits of the requirement.
     */
    Credits getCredits();

    /**
     * Returns the credits required for the requirement.
     */
    int getCreditsRequired();

    /**
     * Returns the credits assigned for the requirement.
     */
    int getCreditsAssigned();

    /**
     * Returns the credits fulfilled for the requirement.
     */
    int getCreditsFulfilled();

    /**
     * Returns an unmodifiable view of the modules in this requirement.
     * This list will not contain any duplicate modules.
     */
    ObservableList<Module> getModuleList();

    /**
     * Checks if {@code otherRequirement} is the same requirement as this.
     * The check is done by checking the requirement code, which basically represents the identity
     * of the requirement.
     */
    boolean isSameRequirement(Requirement otherRequirement);

    /**
     * Returns a boolean value to indicate whether the requirement is already fulfilled.
     * Returns true if requirement is fulfilled (credits are fulfilled), else return false.
     */
    boolean isFulfilled();
}
