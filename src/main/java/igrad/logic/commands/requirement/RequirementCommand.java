package igrad.logic.commands.requirement;

import java.util.List;

import igrad.logic.commands.Command;
import igrad.model.module.Module;
import igrad.model.module.ModuleCode;

/**
 * A generic Requirement command class.
 */
public abstract class RequirementCommand extends Command {
    public static final String REQUIREMENT_COMMAND_WORD = "requirement";

    public static final String MESSAGE_REQUIREMENT_NON_EXISTENT = "The requirement code provided is invalid.";

    //@@author nathanaelseen

    /**
     * Returns a formatted string of module codes from a given list of module codes; {@code moduleCodes},
     * delimited by {@code delimiter}.
     */
    public static String getFormattedModuleCodesStr(List<ModuleCode> moduleCodes, String delimiter) {
        final StringBuilder result = new StringBuilder();

        moduleCodes.stream()
            .forEach(moduleCode -> result.append(moduleCode.toString() + delimiter));

        return result.toString();
    }

    /**
     * Returns a formatted string of module codes from a given list of modules; {@code modules},
     * delimited by {@code delimiter}.
     */
    public static String getFormattedModulesStr(List<Module> modules, String delimiter) {
        final StringBuilder result = new StringBuilder();

        modules.stream()
            .forEach(module -> result.append(module.getModuleCode().toString() + delimiter));

        return result.toString();
    }
}
