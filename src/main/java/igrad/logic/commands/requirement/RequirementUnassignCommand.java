package igrad.logic.commands.requirement;

//@@author nathanaelseen

import static igrad.commons.util.CollectionUtil.requireAllNonNull;
import static igrad.logic.parser.CliSyntax.PREFIX_MODULE_CODE;
import static igrad.logic.parser.CliSyntax.PREFIX_NAME;
import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.stream.Collectors;

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
 * Unassigns modules under a particular requirement.
 */
public class RequirementUnassignCommand extends RequirementCommand {
    public static final String REQUIREMENT_UNASSIGN_COMMAND_WORD = REQUIREMENT_COMMAND_WORD + SPACE
        + "unassign";

    public static final String MESSAGE_REQUIREMENT_UNASSIGN_DETAILS = REQUIREMENT_UNASSIGN_COMMAND_WORD
        + ": Unassigns the requirement identified with modules "
        + "by its requirement code. Existing requirement will be overwritten by the input values\n";

    public static final String MESSAGE_REQUIREMENT_UNASSIGN_USAGE = "Parameter(s): REQUIREMENT_CODE "
        + PREFIX_MODULE_CODE + "MODULE_CODE...\n"
        + "e.g. " + REQUIREMENT_UNASSIGN_COMMAND_WORD + " "
        + "UE1 " + PREFIX_NAME + "CS2030";

    public static final String MESSAGE_REQUIREMENT_UNASSIGN_HELP = MESSAGE_REQUIREMENT_UNASSIGN_DETAILS
        + MESSAGE_REQUIREMENT_UNASSIGN_USAGE;

    public static final String MESSAGE_REQUIREMENT_NO_MODULES = "There must be at least one modules unassigned.";

    public static final String MESSAGE_MODULES_NON_EXISTENT =
        "Some modules do not exist in the system:\n%1$s\nPlease try other modules instead.";

    public static final String MESSAGE_MODULES_NON_EXISTENT_IN_REQUIREMENT =
        "Some modules do not exist in the requirement (%1$s):\n%2$s\nPlease try other modules.";

    public static final String MESSAGE_REQUIREMENT_UNASSIGN_SUCCESS = "I have successfully unassigned the following "
        + "module(s) under requirement (%1$s):\n"
        + "%2$s";

    private static final String MODULE_CODE_DELIMITER = "\n";

    private RequirementCode requirementCode;
    private List<ModuleCode> moduleCodes;

    public RequirementUnassignCommand(RequirementCode requirementCode, List<ModuleCode> moduleCodes) {
        requireAllNonNull(requirementCode, moduleCodes);

        this.requirementCode = requirementCode;
        this.moduleCodes = moduleCodes;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        // Retrieve the requirement in question that we want to unassign modules under..

        // First check if the requirement exists in the course book
        Requirement requirementToEdit = model.getRequirement(requirementCode)
            .orElseThrow(() -> new CommandException(MESSAGE_REQUIREMENT_NON_EXISTENT));


        final List<Module> modulesToUnassign = model.getModules(moduleCodes);

        // First check, if all modules (codes) are existent modules in the course book (they should all be)
        if (modulesToUnassign.size() < moduleCodes.size()) {
            List<ModuleCode> moduleCodesToUnassign = modulesToUnassign.stream()
                .map(module -> module.getModuleCode())
                .collect(Collectors.toList());

            moduleCodes.removeIf(moduleCode -> moduleCodesToUnassign.contains(moduleCode));

            String formattedModuleCodes = getFormattedModuleCodesStr(moduleCodes, MODULE_CODE_DELIMITER);

            throw new CommandException(String.format(MESSAGE_MODULES_NON_EXISTENT, formattedModuleCodes));
        }

        // Now check, if all modules specified are existent in the requirement (they should be)
        if (!requirementToEdit.hasModules(modulesToUnassign)) {
            modulesToUnassign.removeIf(module -> requirementToEdit.hasModule(module));

            String formattedNonExistentModulesInReq = getFormattedModulesStr(modulesToUnassign,
                    MODULE_CODE_DELIMITER);
            throw new CommandException(String.format(MESSAGE_MODULES_NON_EXISTENT_IN_REQUIREMENT,
                        requirementToEdit.getRequirementCode(),
                        formattedNonExistentModulesInReq));
        }

        /*
         * Finally if everything alright, we can actually then unassign/'delete' the specified modules under
         * this requirement
         */
        Requirement editedRequirement = createEditedRequirement(requirementToEdit, modulesToUnassign);

        model.setRequirement(requirementToEdit, editedRequirement);

        /*
         * Now that we've assigned some modules under a particular Requirement to the system, we need to update
         * CourseInfo, specifically its creditsFulfilled property.
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

        String formattedModulesToUnassign = getFormattedModulesStr(modulesToUnassign, MODULE_CODE_DELIMITER);
        return new CommandResult(
            String.format(MESSAGE_REQUIREMENT_UNASSIGN_SUCCESS,
                editedRequirement.getRequirementCode(),
                formattedModulesToUnassign));
    }

    /**
     * Creates and returns a new {@code Requirement} with modules unassigned (specified by;
     * {@code modulesToUnassign}) to the original requirement; {@code requirementToEdit}
     */
    private static Requirement createEditedRequirement(Requirement requirementToEdit, List<Module> modulesToUnassign) {
        /*
         * Finally if everything alright, we can actually then unassign/'delete' the specified modules under
         * this requirement
         */
        requirementToEdit.removeModules(modulesToUnassign);

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

        // Get the most update module list (now with the new modules unassigned/'deleted')
        List<Module> modules = requirementToEdit.getModuleList();

        // Finally, create a new Requirement with all the updated information (details).
        return new Requirement(requirementCode, title, credits, modules);
    }

    @Override
    public boolean equals(Object other) {
        return other == this
            || (other instanceof RequirementUnassignCommand
            && ((RequirementUnassignCommand) other).requirementCode.equals(requirementCode)
            && ((RequirementUnassignCommand) other).moduleCodes.equals(moduleCodes));
    }
}
