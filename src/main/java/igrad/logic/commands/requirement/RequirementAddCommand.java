package igrad.logic.commands.requirement;

import static igrad.logic.parser.CliSyntax.PREFIX_CREDITS;
import static igrad.logic.parser.CliSyntax.PREFIX_TITLE;
import static java.util.Objects.requireNonNull;

import java.util.List;

import igrad.logic.commands.CommandResult;
import igrad.logic.commands.exceptions.CommandException;
import igrad.model.Model;
import igrad.model.requirement.Requirement;
import igrad.model.requirement.RequirementCode;

/**
 * Adds a requirement to the course.
 */
public class RequirementAddCommand extends RequirementCommand {
    public static final String COMMAND_WORD = REQUIREMENT_COMMAND_WORD + SPACE + "add";

    public static final String MESSAGE_DETAILS = COMMAND_WORD + ": Adds a requirement.\n";

    public static final String MESSAGE_USAGE = "Parameters: "
        + PREFIX_TITLE + "TITLE "
        + PREFIX_CREDITS + "CREDITS_TO_FULFIL\n"
        + "Example: " + COMMAND_WORD + " "
        + PREFIX_TITLE + "Unrestricted Electives "
        + PREFIX_CREDITS + "24\n";

    public static final String MESSAGE_HELP = MESSAGE_DETAILS + MESSAGE_USAGE;

    public static final String MESSAGE_REQUIREMENT_ADD_SUCCESS = "New requirement added: %1$s";
    public static final String MESSAGE_REQUIREMENT_NOT_ADDED = "Added requirement must be provided with arguments "
        + PREFIX_TITLE + "TITLE " + PREFIX_CREDITS + "CREDITS ";
    public static final String MESSAGE_REQUIREMENT_DUPLICATE = "This requirement already exists in the course book.";

    private final Requirement requirementToAdd;

    public RequirementAddCommand(Requirement requirementToAdd) {
        requireNonNull(requirementToAdd);

        this.requirementToAdd = requirementToAdd;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        // if the name of the requirement has already been used
        if (model.hasRequirement(requirementToAdd)) {
            throw new CommandException(MESSAGE_REQUIREMENT_DUPLICATE);
        }

        List<Requirement> requirementList = model.getRequirementList();

        RequirementCode codeWithoutNumber = requirementToAdd.getRequirementCode();

        int lastUsedNumber = 0;

        for (Requirement requirement : requirementList) {
            RequirementCode requirementCode = requirement.getRequirementCode();

            if (requirementCode.hasSameAlphabets(codeWithoutNumber)) {
                int requirementNumber = requirementCode.getNumber();
                if (lastUsedNumber < requirementNumber) {
                    lastUsedNumber = requirementNumber;
                }
            }
        }

        RequirementCode codeWithNumber =
            new RequirementCode(codeWithoutNumber.getAlphabets() + String.valueOf(lastUsedNumber));

        Requirement requirement = new Requirement(codeWithNumber,
            requirementToAdd.getTitle(),
            requirementToAdd.getCredits());

        model.addRequirement(requirement);
        return new CommandResult(String.format(MESSAGE_REQUIREMENT_ADD_SUCCESS, requirement));
    }
}
