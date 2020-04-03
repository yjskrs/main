package igrad.logic.commands.requirement;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.Optional;

import igrad.logic.commands.CommandResult;
import igrad.logic.commands.exceptions.CommandException;
import igrad.model.Model;
import igrad.model.course.Cap;
import igrad.model.course.CourseInfo;
import igrad.model.course.Name;
import igrad.model.requirement.Requirement;
import igrad.model.requirement.RequirementCode;

/**
 * Deletes an existing requirement from the course book.
 */
public class RequirementDeleteCommand extends RequirementCommand {

    public static final String REQUIREMENT_DELETE_COMMAND_WORD = REQUIREMENT_COMMAND_WORD + SPACE + "delete";

    public static final String MESSAGE_DETAILS = REQUIREMENT_DELETE_COMMAND_WORD + ": Deletes the requirement "
        + "identified by its requirement code.\n";

    public static final String MESSAGE_USAGE = "Parameter(s): REQUIREMENT_CODE\n"
                                                   + "Example: " + REQUIREMENT_DELETE_COMMAND_WORD + " UE0";

    public static final String MESSAGE_REQUIREMENT_DELETE_HELP = MESSAGE_DETAILS + MESSAGE_USAGE;

    public static final String MESSAGE_REQUIREMENT_DELETE_SUCCESS = "Deleted Requirement:\n%1$s";

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

        model.deleteRequirement(requirementToDelete.get());

        /*
         * Now that we've added a new Requirement to the system, we need to update CourseInfo, specifically its
         * creditsRequired property.
         */
        CourseInfo courseToEdit = model.getCourseInfo();

        // Copy over all the old values of requirementToEdit
        Optional<Name> currentName = courseToEdit.getName();

        // Now we actually go to our model and recompute cap based on updated module list in model (coursebook)
        Optional<Cap> updatedCap = CourseInfo.computeCap(model.getFilteredModuleList(),
                model.getRequirementList());

        /*
         * Now given that we've updated a new module to requirement (as done), we've to update (recompute)
         * creditsFulfilled and creditsRequired
         */
        Optional<igrad.model.course.Credits> updatedCredits = CourseInfo.computeCredits(
                model.getRequirementList());

        CourseInfo editedCourseInfo = new CourseInfo(currentName, updatedCap, updatedCredits);

        // Updating the model with the latest course info (cap)
        model.setCourseInfo(editedCourseInfo);

        return new CommandResult(
            String.format(MESSAGE_REQUIREMENT_DELETE_SUCCESS, requirementToDelete));
    }

}
