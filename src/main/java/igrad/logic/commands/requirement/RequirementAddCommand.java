package igrad.logic.commands.requirement;

import static igrad.logic.parser.CliSyntax.PREFIX_CREDITS;
import static igrad.logic.parser.CliSyntax.PREFIX_TITLE;
import static java.util.Objects.requireNonNull;

import java.util.List;

import igrad.logic.commands.CommandResult;
import igrad.logic.commands.CommandUtil;
import igrad.logic.commands.exceptions.CommandException;
import igrad.model.Model;
import igrad.model.course.CourseInfo;
import igrad.model.requirement.Requirement;
import igrad.model.requirement.RequirementCode;

//@@author yjskrs

/**
 * Adds a requirement to the course.
 */
public class RequirementAddCommand extends RequirementCommand {
    public static final String REQUIREMENT_ADD_COMMAND_WORD = REQUIREMENT_COMMAND_WORD + SPACE + "add";

    public static final String MESSAGE_DETAILS = REQUIREMENT_ADD_COMMAND_WORD + ": Adds a requirement.\n";

    public static final String MESSAGE_USAGE = "Parameter(s): "
        + PREFIX_TITLE + "REQUIREMENT_TITLE "
        + PREFIX_CREDITS + "CREDITS_TO_FULFIL\n"
        + "e.g. " + REQUIREMENT_ADD_COMMAND_WORD + " "
        + PREFIX_TITLE + "Unrestricted Electives "
        + PREFIX_CREDITS + "24\n";

    public static final String MESSAGE_REQUIREMENT_ADD_HELP = MESSAGE_DETAILS + MESSAGE_USAGE;

    public static final String MESSAGE_REQUIREMENT_ADD_SUCCESS = "Got it! I have added this requirement for you:\n%1$s";
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

        // if the code of the requirement has already been used
        if (model.hasRequirement(requirementToAdd)) {
            throw new CommandException(MESSAGE_REQUIREMENT_DUPLICATE);
        }

        RequirementCode codeWithoutNumber = requirementToAdd.getRequirementCode();
        RequirementCode codeWithNumber = new RequirementCode(generateRequirementCode(model, codeWithoutNumber));

        Requirement requirement = new Requirement(codeWithNumber,
            requirementToAdd.getTitle(),
            requirementToAdd.getCredits());

        model.addRequirement(requirement);

        //@@author nathanaelseen

        /*
         * Now that we've added a new Requirement to the system, we need to update CourseInfo, specifically its
         * creditsRequired property.
         *
         * However, in the method below, we just recompute everything (field in course info).
         */
        CourseInfo courseToEdit = model.getCourseInfo();

        /*
         * A call to the retrieveLatestCourseInfo(..) helps to recompute latest course info,
         * based on information provided through Model (coursebook).
         */
        CourseInfo editedCourseInfo = CommandUtil.createEditedCourseInfo(courseToEdit, model);

        // Updating the model with the latest course info
        model.setCourseInfo(editedCourseInfo);

        return new CommandResult(String.format(MESSAGE_REQUIREMENT_ADD_SUCCESS, requirement));
    }

    //@@author yjskrs

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
                if (lastUsedNumber <= requirementNumber) {
                    lastUsedNumber = requirementNumber + 1;
                }
            }
        }

        return codeWithoutNumber.getAlphabets() + lastUsedNumber;
    }

    @Override
    public boolean equals(Object other) {
        return other == this
            || (other instanceof RequirementAddCommand
            && ((RequirementAddCommand) other).requirementToAdd.equals(requirementToAdd));
    }
}
