package igrad.logic.commands.requirement;

import static java.util.Objects.requireNonNull;

import java.util.List;

import igrad.logic.commands.CommandResult;
import igrad.logic.commands.exceptions.CommandException;
import igrad.model.Model;
import igrad.model.requirement.Requirement;
import igrad.model.requirement.RequirementCode;

/**
 * Deletes an existing requirement from the course book.
 */
public class RequirementDeleteCommand extends RequirementCommand {

    public static final String COMMAND_WORD = REQUIREMENT_COMMAND_WORD + SPACE + "delete";

    public static final String MESSAGE_SUCCESS = "Deleted Requirement: %1$s";

    private final RequirementCode requirementCode;

    public RequirementDeleteCommand(RequirementCode requirementCode) {
        requireNonNull(requirementCode);

        this.requirementCode = requirementCode;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        List<Requirement> requirements = model.getRequirementList();

        Requirement requirementToDelete;

        // check if requirement exists in course book
        if (!requirements.stream().anyMatch(requirement -> requirement.getRequirementCode().equals(requirementCode))) {
            throw new CommandException(MESSAGE_REQUIREMENT_NON_EXISTENT);
        } else {
            requirementToDelete = requirements.stream()
                .filter(requirement -> requirement.getRequirementCode().equals(requirementCode))
                .findFirst().get();
        }

        model.deleteRequirement(requirementToDelete);

        return new CommandResult(
            String.format(MESSAGE_SUCCESS, requirementToDelete));
    }

}
