package igrad.logic.commands.requirement;

import igrad.logic.commands.Command;

/**
 * A generic Requirement command class.
 */
public abstract class RequirementCommand extends Command {
    public static final String REQUIREMENT_COMMAND_WORD = "requirement ";
    public static final String MESSAGE_REQUIREMENT_NON_EXISTENT =
        "Requirement does not exist. Please enter an existing requirement.";
}
