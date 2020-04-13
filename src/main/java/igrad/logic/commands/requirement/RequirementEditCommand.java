package igrad.logic.commands.requirement;

import static igrad.commons.util.CollectionUtil.requireAllNonNull;
import static igrad.logic.parser.CliSyntax.PREFIX_CREDITS;
import static igrad.logic.parser.CliSyntax.PREFIX_TITLE;
import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.Optional;

import igrad.commons.util.CollectionUtil;
import igrad.logic.commands.CommandResult;
import igrad.logic.commands.CommandUtil;
import igrad.logic.commands.exceptions.CommandException;
import igrad.model.Model;
import igrad.model.course.CourseInfo;
import igrad.model.module.Module;
import igrad.model.requirement.Credits;
import igrad.model.requirement.Requirement;
import igrad.model.requirement.RequirementCode;
import igrad.model.requirement.Title;

//@@author yjskrs

/**
 * Modifies an existing requirement in the course book.
 */
public class RequirementEditCommand extends RequirementCommand {
    public static final String REQUIREMENT_EDIT_COMMAND_WORD = REQUIREMENT_COMMAND_WORD + SPACE + "edit";

    public static final String MESSAGE_DETAILS = REQUIREMENT_EDIT_COMMAND_WORD + ": Edits the requirement identified "
        + "by its requirement code. Existing requirement will be overwritten by the input values.\n";

    public static final String MESSAGE_USAGE = "Parameter(s): REQUIREMENT_CODE "
        + "[" + PREFIX_TITLE + "REQUIREMENT_TITLE] "
        + "[" + PREFIX_CREDITS + "CREDITS]\n"
        + "e.g. " + REQUIREMENT_EDIT_COMMAND_WORD + " UE0 "
        + PREFIX_TITLE + "Unrestricted Electives";

    public static final String MESSAGE_REQUIREMENT_EDIT_HELP = MESSAGE_DETAILS + MESSAGE_USAGE;

    public static final String MESSAGE_REQUIREMENT_EDIT_SUCCESS = "Got it! This requirement has been "
        + "edited successfully:\n%1$s";

    public static final String MESSAGE_REQUIREMENT_NOT_EDITED = "At least one field to edit must be provided.\n"
        + "[" + PREFIX_TITLE + "REQUIREMENT_TITLE] "
        + "[" + PREFIX_CREDITS + "CREDITS]";

    private final RequirementCode requirementCode;

    private final EditRequirementDescriptor requirementDescriptor;

    public RequirementEditCommand(RequirementCode requirementCode,
                                  EditRequirementDescriptor requirementDescriptor) {
        requireAllNonNull(requirementCode, requirementDescriptor);

        this.requirementCode = requirementCode;
        this.requirementDescriptor = new EditRequirementDescriptor(requirementDescriptor);
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

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        Requirement requirementToEdit = model.getRequirement(requirementCode)
            .orElseThrow(() -> new CommandException(MESSAGE_REQUIREMENT_NON_EXISTENT));

        Requirement editedRequirement = createEditedRequirement(requirementToEdit, requirementDescriptor);

        // If none of the parameters have been modified
        if (editedRequirement.equals(requirementToEdit)) {
            throw new CommandException(MESSAGE_REQUIREMENT_NOT_EDITED);
        }

        model.setRequirement(requirementToEdit, editedRequirement);
        //model.updateRequirementList(Model.PREDICATE_SHOW_ALL_REQUIREMENTS);

        //@@author nathanaelseen

        /*
         * Now that we've edited a new Requirement in the system, we need to update CourseInfo, specifically its
         * creditsRequired property.
         *
         * However, in the method below, we just recompute everything (field in course info).
         */
        CourseInfo courseInfoToEdit = model.getCourseInfo();

        /*
         * A call to the retrieveLatestCourseInfo(..) helps to recompute latest course info,
         * based on information provided through Model (coursebook).
         */
        CourseInfo editedCourseInfo = CommandUtil.createEditedCourseInfo(courseInfoToEdit, model);

        // Updating the model with the latest course info
        model.setCourseInfo(editedCourseInfo);

        return new CommandResult(String.format(MESSAGE_REQUIREMENT_EDIT_SUCCESS, editedRequirement));
    }

    //@@author yjskrs

    @Override
    public boolean equals(Object other) {
        return other == this
            || (other instanceof RequirementEditCommand
            && ((RequirementEditCommand) other).requirementDescriptor.equals(requirementDescriptor)
            && ((RequirementEditCommand) other).requirementCode.equals(requirementCode));
    }

    /**
     * Stores the details to edit the requirement with. Each non-empty field value will replace the
     * corresponding field value of the requirement.
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
            requireNonNull(toCopy);

            setTitle(toCopy.title);
            setCredits(toCopy.credits);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(title, credits);
        }

        public Optional<Title> getTitle() {
            return Optional.ofNullable(title);
        }

        public void setTitle(Title title) {
            this.title = title;
        }

        public Optional<Credits> getCredits() {
            return Optional.ofNullable(credits);
        }

        public void setCredits(Credits credits) {
            this.credits = credits;
        }

        @Override
        public boolean equals(Object other) {
            if (!(other instanceof EditRequirementDescriptor)) {
                return false;
            }

            EditRequirementDescriptor e = (EditRequirementDescriptor) other;

            return other == this
                || (getCredits().equals(e.getCredits())
                && getTitle().equals(e.getTitle()));
        }
    }
}
