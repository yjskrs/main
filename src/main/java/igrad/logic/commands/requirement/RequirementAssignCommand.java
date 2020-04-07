package igrad.logic.commands.requirement;

import static igrad.commons.util.CollectionUtil.requireAllNonNull;
import static igrad.logic.parser.CliSyntax.PREFIX_MODULE_CODE;
import static java.util.Objects.requireNonNull;

import java.util.List;

import igrad.logic.commands.CommandResult;
import igrad.logic.commands.CommandUtil;
import igrad.logic.commands.exceptions.CommandException;
import igrad.model.Model;
import igrad.model.course.CourseInfo;
import igrad.model.module.Module;
import igrad.model.module.ModuleCode;
import igrad.model.requirement.Requirement;
import igrad.model.requirement.RequirementCode;
import igrad.model.requirement.Title;

/**
 * Assigns modules under a particular requirement.
 */
public class RequirementAssignCommand extends RequirementCommand {
    public static final String REQUIREMENT_ASSIGN_COMMAND_WORD = REQUIREMENT_COMMAND_WORD + SPACE + "assign";

    public static final String REQUIREMENT_ASSIGN_MESSAGE_DETAILS = REQUIREMENT_ASSIGN_COMMAND_WORD
        + ": Assigns the requirement identified with modules "
        + "by its requirement code. Existing requirement will be overwritten by the input values\n";

    public static final String REQUIREMENT_ASSIGN_MESSAGE_USAGE = "Parameter(s): REQUIREMENT_CODE "
        + PREFIX_MODULE_CODE + "MODULE_CODE]...\n";

    public static final String REQUIREMENT_ASSIGN_MESSAGE_HELP = REQUIREMENT_ASSIGN_MESSAGE_DETAILS
        + REQUIREMENT_ASSIGN_MESSAGE_USAGE;

    public static final String MESSAGE_REQUIREMENT_NO_MODULES = "There must be at least one modules assigned.";

    public static final String MESSAGE_MODULES_NON_EXISTENT =
        "Not all Modules exist in the system. Please try other modules.";

    public static final String MESSAGE_MODULES_ALREADY_EXIST_IN_REQUIREMENT =
        "Some Modules already exists in this requirement. Please try other modules.";
    public static final String MESSAGE_SUCCESS = "Modules assigned under Requirement:\n%1$s";

    private RequirementCode requirementCode;
    private List<ModuleCode> moduleCodes;

    public RequirementAssignCommand(RequirementCode requirementCode, List<ModuleCode> moduleCodes) {
        requireAllNonNull(requirementCode, moduleCodes);

        this.requirementCode = requirementCode;
        this.moduleCodes = moduleCodes;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        // Retrieve the requirement in question that we want to assign modules under..

        // First check if the requirement exists in the course book
        Requirement requirementToEdit = model.getRequirement(requirementCode)
            .orElseThrow(() -> new CommandException(MESSAGE_REQUIREMENT_NON_EXISTENT));


        final List<Module> modulesToAssign = model.getModules(moduleCodes);

        // First check, if all modules (codes) are existent modules in the course book (they should all be)
        if (modulesToAssign.size() < moduleCodes.size()) {
            throw new CommandException(MESSAGE_MODULES_NON_EXISTENT);
        }

        // Now filter out, modules which are already in the requirement, they should not be re-added again
        modulesToAssign.removeIf(module -> requirementToEdit.hasModule(module));

        // Finally if everything alright, we can actually then assign/add the specified modules under this requirement
        requirementToEdit.addModules(modulesToAssign);

        // First, we copy over all the old values of requirementToEdit
        RequirementCode requirementCode = requirementToEdit.getRequirementCode();
        Title title = requirementToEdit.getTitle();

        /*
         * Now given that we've added this list of new modules to requirement, we've to update (recompute)
         * creditsFulfilled, but since Requirement constructor already does it for us, based
         * on the module list passed in, we don't have to do anything here, just propage
         * the old credits value.
         */
        igrad.model.requirement.Credits credits = requirementToEdit.getCredits();

        // Get the most update module list (now with the new modules assigned/added)
        List<Module> modules = requirementToEdit.getModuleList();

        // Finally, create a new Requirement with all the updated information (details).
        Requirement editedRequirement = new Requirement(requirementCode, title, credits, modules);

        model.setRequirement(requirementToEdit, editedRequirement);

        /*
         * Now that we've assigned some modules under a particular Requirement to the system, we need to update
         * CourseInfo, specifically its creditsFulfilled property.
         *
         * However, in the method below, we just recompute everything (field in course info).
         */
        CourseInfo courseToEdit = model.getCourseInfo();

        /*
         * A call to the retrieveLatestCourseInfo(..) helps to recompute latest course info,
         * based on information provided through Model (coursebook).
         */
        CourseInfo editedCourseInfo = CommandUtil.retrieveLatestCourseInfo(courseToEdit, model);

        // Updating the model with the latest course info
        model.setCourseInfo(editedCourseInfo);

        return new CommandResult(
            String.format(MESSAGE_SUCCESS, editedRequirement));
    }
}
