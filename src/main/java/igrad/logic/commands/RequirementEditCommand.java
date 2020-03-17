package igrad.logic.commands;

import static igrad.commons.util.CollectionUtil.requireAllNonNull;
import static igrad.logic.parser.CliSyntax.PREFIX_NAME;
import static java.util.Objects.requireNonNull;

import igrad.logic.commands.exceptions.CommandException;
import igrad.model.Model;
import igrad.model.requirement.Requirement;
import igrad.model.requirement.Title;

/**
 * Modifies an existing requirement in the course book.
 */
public class RequirementEditCommand extends RequirementCommand {
    public static final String COMMAND_WORD = REQUIREMENT_COMMAND_WORD + "edit";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the requirement by the title of the requirement."
                                                   + "Existing title will be overwritten by the input title.\n"
                                                   + "Parameter:"
                                                   + PREFIX_NAME + "TITLE\n"
                                                   + "Example: " + COMMAND_WORD + " Unrestrained Elves "
                                                   + PREFIX_NAME + "Unrestricted Electives";

    public static final String MESSAGE_REQUIREMENT_EDIT_SUCCESS = "Edited Requirement: %1$s";
    public static final String MESSAGE_REQUIREMENT_NOT_EDITED = "Edited title must be provided with prefix "
                                                                    + PREFIX_NAME;
    public static final String MESSAGE_REQUIREMENT_SAME_TITLE = "Please rename to a different title.";
    public static final String MESSAGE_REQUIREMENT_DUPLICATE = "This requirement title already exists."
                                                                    + "Please rename to a different title.";
    public static final String MESSAGE_EDIT_IDENTIFIER_MISSING = "Requirement does not exist. "
                                                                     + "Please enter an existing requirement";

    private final Title originalTitle;

    private final Title newTitle;

    public RequirementEditCommand(Title originalTitle, Title newTitle) {
        requireAllNonNull(originalTitle, newTitle);

        this.originalTitle = originalTitle;
        this.newTitle = newTitle;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        Requirement requirementToEdit = model.getRequirementList().stream()
                                            .filter(requirement -> requirement.getTitle().equals(originalTitle))
                                            .findFirst()
                                            .orElseThrow(() -> new CommandException(MESSAGE_EDIT_IDENTIFIER_MISSING));
        if (originalTitle.equals(newTitle)) {
            throw new CommandException(MESSAGE_REQUIREMENT_SAME_TITLE);
        }

        Requirement editedRequirement = new Requirement(newTitle, requirementToEdit.getModuleList());

        if (model.hasRequirement(editedRequirement)) {
            throw new CommandException(MESSAGE_REQUIREMENT_DUPLICATE);
        }

        model.setRequirement(requirementToEdit, editedRequirement);
        model.updateRequirementList(Model.PREDICATE_SHOW_ALL_REQUIREMENTS);

        return new CommandResult(String.format(MESSAGE_REQUIREMENT_EDIT_SUCCESS, editedRequirement));
    }
}
