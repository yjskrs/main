package igrad.logic.commands.requirement;

import static igrad.commons.util.CollectionUtil.requireAllNonNull;
import static igrad.logic.parser.CliSyntax.PREFIX_MODULE_CODE;
import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.Optional;

import igrad.logic.commands.CommandResult;
import igrad.logic.commands.exceptions.CommandException;
import igrad.model.Model;
import igrad.model.course.Cap;
import igrad.model.course.CourseInfo;
import igrad.model.course.Name;
import igrad.model.module.Module;
import igrad.model.module.ModuleCode;
import igrad.model.requirement.Requirement;
import igrad.model.requirement.RequirementCode;
import igrad.model.requirement.Title;

/**
 * Assigns modules under a particular requirement.
 */
public class RequirementAssignCommand extends RequirementCommand {
    public static final String COMMAND_WORD = REQUIREMENT_COMMAND_WORD + SPACE + "assign";

    public static final String MESSAGE_DETAILS = COMMAND_WORD + ": Assigns the requirement identified with modules "
        + "by its requirement code. Existing requirement will be overwritten by the input values\n";

    public static final String MESSAGE_USAGE = "Parameter(s): REQUIREMENT_CODE "
        + PREFIX_MODULE_CODE + "MODULE_CODE]...\n";

    public static final String MESSAGE_HELP = MESSAGE_DETAILS + MESSAGE_USAGE;

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
        Requirement requirementToAssign = model.getRequirement(requirementCode)
            .orElseThrow(() -> new CommandException(MESSAGE_REQUIREMENT_NON_EXISTENT));


        final List<Module> modulesToAssign = model.getModulesByModuleCode(moduleCodes);

        // First check, if all modules (codes) are existent modules in the course book (they should all be)
        if (modulesToAssign.size() < moduleCodes.size()) {
            throw new CommandException(MESSAGE_MODULES_NON_EXISTENT);
        }

        // Now check, if any modules specified are existent in the requirement (they should not)
        if (requirementToAssign.hasModule(modulesToAssign)) {
            throw new CommandException(MESSAGE_MODULES_ALREADY_EXIST_IN_REQUIREMENT);
        }

        // Finally if everything alright, we can actually then assign/add the specified modules under this requirement
        requirementToAssign.addModules(modulesToAssign);

        // First, we copy over all the old values of requirementToAssign
        RequirementCode requirementCode = requirementToAssign.getRequirementCode();
        Title title = requirementToAssign.getTitle();

        /*
         * Now given that we've added this list of new modules to requirement, we've to update (recompute)
         * creditsFulfilled, but since Requirement constructor already does it for us, based
         * on the module list passed in, we don't have to do anything here, just propage
         * the old credits value.
         */
        igrad.model.requirement.Credits credits = requirementToAssign.getCredits();

        // Get the most update module list (now with the new modules assigned/added)
        List<Module> modules = requirementToAssign.getModuleList();

        // Finally, create a new Requirement with all the updated information (details).
        Requirement editedRequirement = new Requirement(requirementCode, title, credits, modules);

        model.setRequirement(requirementToAssign, editedRequirement);

        /*
         * Now that we've edited this Requirement to the system, we need to update CourseInfo, specifically its
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
            String.format(MESSAGE_SUCCESS, editedRequirement));
    }
}
