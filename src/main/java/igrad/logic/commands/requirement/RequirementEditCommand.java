package igrad.logic.commands.requirement;

import static igrad.commons.util.CollectionUtil.requireAllNonNull;
import static igrad.logic.parser.CliSyntax.PREFIX_CREDITS;
import static igrad.logic.parser.CliSyntax.PREFIX_TITLE;
import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.Optional;

import igrad.commons.util.CollectionUtil;
import igrad.logic.commands.CommandResult;
import igrad.logic.commands.exceptions.CommandException;
import igrad.model.Model;
import igrad.model.module.Module;
import igrad.model.requirement.Credits;
import igrad.model.requirement.Requirement;
import igrad.model.requirement.RequirementCode;
import igrad.model.requirement.Title;

/**
 * Modifies an existing requirement in the course book.
 */
public class RequirementEditCommand extends RequirementCommand {
    public static final String COMMAND_WORD = REQUIREMENT_COMMAND_WORD + SPACE + "edit";

    public static final String MESSAGE_DETAILS = COMMAND_WORD + ": Edits the requirement identified "
        + "by its requirement code. Existing requirement will be overwritten by the input values.\n";

    public static final String MESSAGE_USAGE = "Parameter(s): REQUIREMENT_CODE "
        + "[" + PREFIX_TITLE + "TITLE] "
        + "[" + PREFIX_CREDITS + "CREDITS]\n"
        + "Example: " + COMMAND_WORD + " UE0 "
        + PREFIX_TITLE + "Unrestricted Electives";

    public static final String MESSAGE_REQUIREMENT_EDIT_HELP = MESSAGE_DETAILS + MESSAGE_USAGE;

    public static final String MESSAGE_REQUIREMENT_EDIT_SUCCESS = "Edited Requirement: %1$s";
    public static final String MESSAGE_REQUIREMENT_NOT_EDITED = "At least one field must be modified.";

    private final RequirementCode requirementCode;

    private final EditRequirementDescriptor requirementDescriptor;

    public RequirementEditCommand(RequirementCode requirementCode,
                                  EditRequirementDescriptor requirementDescriptor) {
        requireAllNonNull(requirementCode, requirementDescriptor);

        this.requirementCode = requirementCode;
        this.requirementDescriptor = new EditRequirementDescriptor(requirementDescriptor);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        Requirement requirementToEdit = model.getRequirementByRequirementCode(requirementCode)
            .orElseThrow(() -> new CommandException(MESSAGE_REQUIREMENT_NON_EXISTENT));

        Requirement editedRequirement = createEditedRequirement(requirementToEdit, requirementDescriptor);

        // If none of the parameters have been modified
        if (editedRequirement.equals(requirementToEdit)) {
            throw new CommandException(MESSAGE_REQUIREMENT_NOT_EDITED);
        }

        model.setRequirement(requirementToEdit, editedRequirement);
        model.updateRequirementList(Model.PREDICATE_SHOW_ALL_REQUIREMENTS);

        return new CommandResult(String.format(MESSAGE_REQUIREMENT_EDIT_SUCCESS, editedRequirement));
    }

    /**
     * Creates and returns a {@code Requirement} with the details of {@code requirementToEdit}
     * edited with {@code editRequirementDescriptor}.
     */
    private static Requirement createEditedRequirement(Requirement requirementToEdit,
                                                       EditRequirementDescriptor editRequirementDescriptor) {
        assert requirementToEdit != null;
        assert editRequirementDescriptor != null;

        Title updatedTitle = editRequirementDescriptor.getTitle().orElse(requirementToEdit.getTitle());
        Credits updatedCredits = editRequirementDescriptor.getCredits().orElse(requirementToEdit.getCredits());
        RequirementCode requirementCode = requirementToEdit.getRequirementCode();
        List<Module> moduleList = requirementToEdit.getModuleList();

        return new Requirement(requirementCode, updatedTitle, updatedCredits, moduleList);
    }

    /**
     * Stores the details to edit the requirement with. Each non-empty field value will replace the
     * corresponding field value of the person.
     */
    public static class EditRequirementDescriptor {
        private Title title;
        private Credits credits;

        public EditRequirementDescriptor() {
        }

        /**
         * Makes a copy of a EditRequirementDescriptor.
         */
        public EditRequirementDescriptor(EditRequirementDescriptor toCopy) {
            setTitle(toCopy.title);
            setCredits(toCopy.credits);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(title, credits);
        }

        public void setTitle(Title title) {
            this.title = title;
        }

        public Optional<Title> getTitle() {
            return Optional.ofNullable(title);
        }

        public void setCredits(Credits credits) {
            this.credits = credits;
        }

        public Optional<Credits> getCredits() {
            return Optional.ofNullable(credits);
        }
    }
}
