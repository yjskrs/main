package igrad.logic.commands.requirement;

import static igrad.commons.util.CollectionUtil.requireAllNonNull;
import static igrad.logic.parser.CliSyntax.PREFIX_CREDITS;
import static igrad.logic.parser.CliSyntax.PREFIX_NAME;
import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.Optional;

import igrad.logic.commands.CommandResult;
import igrad.logic.commands.exceptions.CommandException;
import igrad.model.Model;
import igrad.model.requirement.Credits;
import igrad.model.requirement.Name;
import igrad.model.requirement.Requirement;

/**
 * Modifies an existing requirement in the course book.
 */
public class RequirementEditCommand extends RequirementCommand {
    public static final String COMMAND_WORD = REQUIREMENT_COMMAND_WORD + "edit";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the requirement title. "
        + "Existing title will be overwritten by the input title.\n"
        + "Parameter: "
        + "[" + PREFIX_NAME + "NEW_TITLE] "
        + "[" + PREFIX_CREDITS + "NEW_CREDITS]\n"
        + "Example: " + COMMAND_WORD + " Unrestrained Elves "
        + PREFIX_NAME + "Unrestricted Electives";

    public static final String MESSAGE_REQUIREMENT_EDIT_SUCCESS = "Edited Requirement: %1$s";
    public static final String MESSAGE_REQUIREMENT_NOT_EDITED = "Edited requirement must be provided with prefix "
        + "[" + PREFIX_NAME + "] and/or "
        + "[" + PREFIX_CREDITS + "].";
    public static final String MESSAGE_REQUIREMENT_EMPTY_PARAMETERS = "Please provide a non-empty alphanumeric string.";
    public static final String MESSAGE_REQUIREMENT_SAME_PARAMETERS = "Please change the title or the credits.";
    public static final String MESSAGE_REQUIREMENT_DUPLICATE = "This requirement already exists. "
        + "Please rename to a different title.";

    private final Name originalName;

    private final Optional<Name> newName;

    private final Optional<Credits> newCredits;

    public RequirementEditCommand(Name originalName,
                                  Optional<Name> newName, Optional<Credits> newCredits) {
        requireAllNonNull(originalName, newName, newCredits);

        this.originalName = originalName;
        this.newName = newName;
        this.newCredits = newCredits;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        List<Requirement> requirements = model.getRequirementList();

        Requirement requirementToEdit = requirements.stream()
            .filter(requirement -> requirement.getName().equals(originalName))
            .findFirst()
            .orElseThrow(() -> new CommandException(MESSAGE_REQUIREMENT_NON_EXISTENT));

        Name editedName = newName.orElse(requirementToEdit.getName());
        Credits editedCredits = newCredits.orElse(requirementToEdit.getCredits());
        Requirement editedRequirement = new Requirement(editedName, editedCredits, requirementToEdit.getModuleList());

        // If the edited title and the edited credits are the same as before
        // TODO: Possible duplicate, remove if it is
        /*if (requirementToEdit.hasSameName(editedRequirement) && requirementToEdit.hasSameCredits(editedRequirement)) {
            throw new CommandException(MESSAGE_REQUIREMENT_SAME_PARAMETERS);
        }*/

        // If the title is edited and same as before OR if the credits is edited and same as before
        if (newName.isPresent() && requirementToEdit.hasSameName(editedRequirement)
            || newCredits.isPresent() && requirementToEdit.hasSameCredits(editedRequirement)) {
            throw new CommandException(MESSAGE_REQUIREMENT_SAME_PARAMETERS);
        }

        // If changed title is the same as an existing title
        // TODO: Possible duplicate, remove if it is
        /*if (requirements.stream()
                .anyMatch(requirement -> !requirement.getName().equals(originalName)
                                             && requirement.hasSameName(editedRequirement))) {
            throw new CommandException(MESSAGE_REQUIREMENT_DUPLICATE);
        }*/

        model.setRequirement(requirementToEdit, editedRequirement);
        model.updateRequirementList(Model.PREDICATE_SHOW_ALL_REQUIREMENTS);

        return new CommandResult(String.format(MESSAGE_REQUIREMENT_EDIT_SUCCESS, editedRequirement));
    }
}
