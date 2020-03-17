package igrad.logic.commands;

/**
 * A generic Requirement class.
 */
public abstract class RequirementCommand extends Command {
    public static final String REQUIREMENT_COMMAND_WORD = "requirement ";
    public static final String MESSAGE_REQUIREMENT_NON_EXISTENT =
        "Requirement does not exist. Please enter an existing requirement.";
}
