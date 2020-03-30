package igrad.logic.commands.requirement;

import static igrad.commons.util.CollectionUtil.requireAllNonNull;
import static igrad.logic.parser.CliSyntax.PREFIX_MODULE_CODE;
import static java.util.Objects.requireNonNull;

import java.util.List;

import igrad.logic.commands.CommandResult;
import igrad.logic.commands.exceptions.CommandException;
import igrad.model.Model;
import igrad.model.module.Module;
import igrad.model.module.ModuleCode;
import igrad.model.requirement.Requirement;
import igrad.model.requirement.RequirementCode;

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
    public static final String MESSAGE_SUCCESS = "Modules assigned under Requirement: %1$s";

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
        Requirement requirementToAssign = model.getRequirementByRequirementCode(requirementCode)
            .orElseThrow(() -> new CommandException(MESSAGE_REQUIREMENT_NON_EXISTENT));

        Requirement editedRequirement = new Requirement(requirementToAssign);

        final List<Module> modulesToAssign = model.getModulesByModuleCode(moduleCodes);

        // First check, if all modules (codes) are existent modules in the course book (they should all be)
        if (modulesToAssign.size() < moduleCodes.size()) {
            throw new CommandException(MESSAGE_MODULES_NON_EXISTENT);
        }

        // Now check, if any modules specified are existent in the requirement (they should not)
        if (editedRequirement.hasModule(modulesToAssign)) {
            throw new CommandException(MESSAGE_MODULES_ALREADY_EXIST_IN_REQUIREMENT);
        }

        // Finally if everything alright, we can actually then assign the specified modules under this requirement
        editedRequirement.addModules(modulesToAssign);

        model.setRequirement(requirementToAssign, editedRequirement);

        return new CommandResult(
            String.format(MESSAGE_SUCCESS, editedRequirement));
    }
}
