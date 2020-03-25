package igrad.logic.commands.requirement;

import igrad.logic.commands.Command;

/**
 * A generic Requirement command class.
 */
public abstract class RequirementCommand extends Command {
    public static final String REQUIREMENT_COMMAND_WORD = "requirement ";

    public static final String MESSAGE_REQUIREMENT_NON_EXISTENT =
        "Requirement does not exist. Please enter an existing requirement.";

    public static final String MESSAGE_REQUIREMENT_ALREADY_FULFILLED =
        "All MCs in Requirement has already been fulfilled. Please try another requirement.";

    public static final String MESSAGE_REQUIREMENT_POTENTIALLY_FULFILLED =
        "All MCs in Requirement would be be fulfilled, adding all these modules. Please try with fewer modules.";

    public static final String MESSAGE_MODULES_NON_EXISTENT =
        "Not all Modules exist in the system. Please try other modules.";

    public static final String MESSAGE_MODULES_ALREADY_EXIST_IN_REQUIREMENT =
        "Some Modules already exists in this requirement. Please try other modules.";
}
