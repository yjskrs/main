package igrad.logic.commands.requirement;

import static igrad.logic.parser.CliSyntax.PREFIX_CREDITS;
import static igrad.logic.parser.CliSyntax.PREFIX_TITLE;
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
 * Adds a requirement to the course.
 */
public class RequirementAddCommand extends RequirementCommand {
    public static final String REQUIREMENT_ADD_COMMAND_WORD = REQUIREMENT_COMMAND_WORD + SPACE + "add";

    public static final String MESSAGE_DETAILS = REQUIREMENT_ADD_COMMAND_WORD + ": Adds a requirement.\n";

    public static final String MESSAGE_USAGE = "Parameter(s): "
        + PREFIX_TITLE + "TITLE "
        + PREFIX_CREDITS + "CREDITS_TO_FULFIL\n"
        + "Example: " + REQUIREMENT_ADD_COMMAND_WORD + " "
        + PREFIX_TITLE + "Unrestricted Electives "
        + PREFIX_CREDITS + "24\n";

    public static final String MESSAGE_REQUIREMENT_ADD_HELP = MESSAGE_DETAILS + MESSAGE_USAGE;

    public static final String MESSAGE_REQUIREMENT_ADD_SUCCESS = "New requirement added:\n%1$s";
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

        RequirementCode codeWithoutNumber = requirementToAdd.getRequirementCode();
        RequirementCode codeWithNumber = new RequirementCode(generateRequirementCode(model, codeWithoutNumber));

        Requirement requirement = new Requirement(codeWithNumber,
            requirementToAdd.getTitle(),
            requirementToAdd.getCredits());

        model.addRequirement(requirement);

        /*
         * Now that we've added a new Requirement to the system, we need to update CourseInfo, specifically its
         * creditsRequired property.
         */
        CourseInfo courseToEdit = model.getCourseInfo();

        // Copy over all the old values of requirementToEdit
        Optional<Name> currentName = courseToEdit.getName();

        // Now we actually go to our model and recompute cap based on updated module list in model (coursebook)
        Optional<Cap> updatedCap = CourseInfo.computeCap(model.getFilteredModuleList());

        /*
         * Now given that we've updated a new module to requirement (as done), we've to update (recompute)
         * creditsFulfilled and creditsRequired
         */
        Optional<igrad.model.course.Credits> updatedCredits = CourseInfo.computeCredits(
                model.getRequirementList());

        CourseInfo editedCourseInfo = new CourseInfo(currentName, updatedCap, updatedCredits);

        // Updating the model with the latest course info (cap)
        model.setCourseInfo(editedCourseInfo);
        return new CommandResult(String.format(MESSAGE_REQUIREMENT_ADD_SUCCESS, requirement));
    }

    /**
     * Generates the requirement code based on the number of previous requirements that hold the same
     * alphabetical part of the code.
     */
    private String generateRequirementCode(Model model, RequirementCode codeWithoutNumber) {
        List<Requirement> requirementList = model.getRequirementList();

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

        return codeWithoutNumber.getAlphabets() + (lastUsedNumber + 1);
    }
}
