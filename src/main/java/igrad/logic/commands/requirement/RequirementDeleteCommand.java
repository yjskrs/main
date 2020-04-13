package igrad.logic.commands.requirement;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.Optional;

import igrad.logic.commands.CommandResult;
import igrad.logic.commands.CommandUtil;
import igrad.logic.commands.exceptions.CommandException;
import igrad.model.Model;
import igrad.model.course.CourseInfo;
import igrad.model.requirement.Requirement;
import igrad.model.requirement.RequirementCode;

//@@author yjskrs

/**
 * Deletes an existing requirement from the course book.
 */
public class RequirementDeleteCommand extends RequirementCommand {

    public static final String REQUIREMENT_DELETE_COMMAND_WORD = REQUIREMENT_COMMAND_WORD + SPACE + "delete";

    public static final String MESSAGE_DETAILS = REQUIREMENT_DELETE_COMMAND_WORD + ": Deletes the requirement "
        + "identified by its requirement code.\n";

    public static final String MESSAGE_USAGE = "Parameter(s): REQUIREMENT_CODE\n"
        + "e.g. " + REQUIREMENT_DELETE_COMMAND_WORD + " UE0";

    public static final String MESSAGE_REQUIREMENT_DELETE_HELP = MESSAGE_DETAILS + MESSAGE_USAGE;

    public static final String MESSAGE_REQUIREMENT_DELETE_SUCCESS = "Got it! This requirement has been "
            + "deleted successfully:\n%1$s";

    private final RequirementCode requirementCode;

    public RequirementDeleteCommand(RequirementCode requirementCode) {
        requireNonNull(requirementCode);

        this.requirementCode = requirementCode;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        List<Requirement> requirements = model.getRequirementList();

        // check if requirement exists in course book
        Optional<Requirement> requirementToDelete = requirements.stream()
            .filter(requirement -> requirement.getRequirementCode().equals(requirementCode)).findFirst();

        if (requirementToDelete.isEmpty()) {
            throw new CommandException(MESSAGE_REQUIREMENT_NON_EXISTENT);
        }

        Requirement toDelete = requirementToDelete.get();

        model.deleteRequirement(toDelete);

        //@@author nathanaelseen

        /*
         * Now that we've deleted a new Requirement in the system, we need to update CourseInfo, specifically its
         * creditsRequired and creditsFulfilled property.
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

        return new CommandResult(
            String.format(MESSAGE_REQUIREMENT_DELETE_SUCCESS, toDelete));
    }

    //@@author yjskrs

    @Override
    public boolean equals(Object other) {
        return other == this
            || (other instanceof RequirementDeleteCommand
            && ((RequirementDeleteCommand) other).requirementCode.equals(requirementCode));
    }
}
