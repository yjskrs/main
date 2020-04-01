package igrad.logic.commands.requirement;

import static igrad.commons.util.CollectionUtil.requireAllNonNull;
import static igrad.logic.parser.CliSyntax.PREFIX_CREDITS;
import static igrad.logic.parser.CliSyntax.PREFIX_TITLE;
import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.Optional;

import igrad.logic.commands.CommandResult;
import igrad.logic.commands.exceptions.CommandException;
import igrad.model.Model;
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

    public static final String MESSAGE_USAGE = "Parameters: REQUIREMENT_CODE "
        + "[" + PREFIX_TITLE + "TITLE] "
        + "[" + PREFIX_CREDITS + "CREDITS]\n"
        + "Example: " + COMMAND_WORD + " UE0 "
        + PREFIX_TITLE + "Unrestricted Electives";

    public static final String MESSAGE_HELP = MESSAGE_DETAILS + MESSAGE_USAGE;

    public static final String MESSAGE_SUCCESS = "Edited Requirement: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_REQUIREMENT_SAME_PARAMETERS = "Please change the title and/or the credits.";


    private final RequirementCode requirementCode;

    private final Optional<Title> newTitle;

    private final Optional<Credits> newCredits;

    public RequirementEditCommand(RequirementCode requirementCode,
                                  Optional<Title> newTitle, Optional<Credits> newCredits) {
        requireAllNonNull(requirementCode, newTitle, newCredits);

        this.requirementCode = requirementCode;
        this.newTitle = newTitle;
        this.newCredits = newCredits;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        List<Requirement> requirements = model.getRequirementList();

        /*
         * TODO (yijie): change to model.getRequirementByRequirementCode(RequirementCode requirementCode),
         *  which I've created and used in Requirement Assign
         *  ~ nathanael
         */
        Requirement requirementToEdit = requirements.stream()
            .filter(requirement -> requirement.getRequirementCode().equals(requirementCode))
            .findFirst()
            .orElseThrow(() -> new CommandException(MESSAGE_REQUIREMENT_NON_EXISTENT));

        // Note: requirementCode should never be edited, its the unique ID
        Title editedTitle = newTitle.orElse(requirementToEdit.getTitle());
        Credits editedCredits = newCredits.orElse(requirementToEdit.getCredits());
        Requirement editedRequirement = new Requirement(requirementToEdit.getRequirementCode(),
            editedTitle, editedCredits, requirementToEdit.getModuleList());

        // If the provided title is same as before and/or if the provided credits is same as before
        /*
         * TODO: Somehow I feel you could be abit more lenient on this; if i go to FB to change my phone number and
         *  its still the same, I don't think it'll flag an error. Moreover, to be consistent with the rest of the
         *  app, Module does not have this constraint where you have to neccessarily update a field.
         *  However, one thing you could actually do is to check in RequirementEditCommandParser whether any fields
         *  have been 'touched' (please take a look at how ModuleEdit works), and if no fields are 'touched' then
         *  you throw exception. Tbh, i feel that even such constraints doesn't make sense, but oh well just follow
         *  what's existing and given to us.
         *  And if you were to follow the EditModuleDescriptor thing in the RequirementEditCommandParser, which I've
         *  proposed (you could read the comments there too), you could actually check whether any field has been
         *  'touched' over in parser class
         *  ~ nathanael
         */
        if (newTitle.isPresent() && requirementToEdit.hasSameTitle(editedRequirement)
            || newCredits.isPresent() && requirementToEdit.hasSameCredits(editedRequirement)) {
            throw new CommandException(MESSAGE_REQUIREMENT_SAME_PARAMETERS);
        }

        model.setRequirement(requirementToEdit, editedRequirement);
        model.updateRequirementList(Model.PREDICATE_SHOW_ALL_REQUIREMENTS);

        return new CommandResult(
            String.format(MESSAGE_SUCCESS, editedRequirement));
    }
}
