package igrad.logic.commands.requirement;

import static igrad.logic.parser.CliSyntax.PREFIX_CREDITS;
import static igrad.logic.parser.CliSyntax.PREFIX_NAME;
import static java.util.Objects.requireNonNull;

import igrad.logic.commands.CommandResult;
import igrad.logic.commands.exceptions.CommandException;
import igrad.model.Model;
import igrad.model.requirement.Requirement;

/**
 * Adds a requirement to the course.
 */
public class RequirementAddCommand extends RequirementCommand {
    public static final String COMMAND_WORD = REQUIREMENT_COMMAND_WORD + "add";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a requirement.\n"
        + "Parameter: "
        + PREFIX_NAME + "NAME "
        + PREFIX_CREDITS + "CREDITS_NEEDED\n"
        + "Example: " + COMMAND_WORD + " "
        + PREFIX_NAME + "Unrestricted Electives "
        + PREFIX_CREDITS + "24\n";

    public static final String MESSAGE_REQUIREMENT_ADD_SUCCESS = "New requirement added: %1$s";
    public static final String MESSAGE_REQUIREMENT_NOT_ADDED = "Added requirement must be provided with arguments "
        + PREFIX_NAME + "TITLE " + PREFIX_CREDITS + "CREDITS ";
    public static final String MESSAGE_REQUIREMENT_DUPLICATE = "This requirement already exists in the course book.";

    private final Requirement requirementToAdd;

    public RequirementAddCommand(Requirement requirementToAdd) {
        requireNonNull(requirementToAdd);

        this.requirementToAdd = requirementToAdd;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        // if the title of the requirement has already been used
        if (model.hasRequirement(requirementToAdd)) {
            throw new CommandException(MESSAGE_REQUIREMENT_DUPLICATE);
        }

        model.addRequirement(requirementToAdd);
        return new CommandResult(String.format(MESSAGE_REQUIREMENT_ADD_SUCCESS, requirementToAdd));
    }
}
