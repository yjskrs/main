package igrad.testutil;

import static igrad.logic.parser.CliSyntax.PREFIX_CREDITS;
import static igrad.logic.parser.CliSyntax.PREFIX_MODULE_CODE;
import static igrad.logic.parser.CliSyntax.PREFIX_SEMESTER;
import static igrad.logic.parser.CliSyntax.PREFIX_TITLE;

import igrad.logic.commands.module.ModuleAddCommand;
import igrad.logic.commands.module.ModuleEditCommand.EditModuleDescriptor;
import igrad.model.module.Module;

/**
 * A utility class for Module.
 */
public class ModuleUtil {

    /**
     * Returns an add command string for adding the {@code module}.
     */
    public static String getAddCommand(Module module) {
        return ModuleAddCommand.MODULE_ADD_COMMAND_WORD + " " + getModuleDetails(module);
    }

    /**
     * Returns the part of command string for the given {@code module}'s details.
     */
    public static String getModuleDetails(Module module) {
        StringBuilder sb = new StringBuilder();
        sb.append(PREFIX_TITLE + module.getTitle().value + " ");
        sb.append(PREFIX_MODULE_CODE + module.getModuleCode().value + " ");
        sb.append(PREFIX_CREDITS + module.getCredits().value + " ");
        sb.append(PREFIX_SEMESTER + module.getSemester().get().value + " ");

        return sb.toString();
    }

    /**
     * Returns the part of command string for the given {@code EditModuleDescriptor}'s details.
     */
    public static String getEditModuleDescriptorDetails(EditModuleDescriptor descriptor) {
        StringBuilder sb = new StringBuilder();
        descriptor.getTitle().ifPresent(title -> sb.append(PREFIX_TITLE).append(title.value).append(" "));

        descriptor.getModuleCode().ifPresent(moduleCode -> sb.append(PREFIX_MODULE_CODE)
            .append(moduleCode.value).append(" "));

        descriptor.getCredits().ifPresent(credits -> sb.append(PREFIX_CREDITS)
            .append(credits.value).append(" "));

        descriptor.getSemester().ifPresent(semester -> sb.append(PREFIX_SEMESTER)
            .append(semester.get().value).append(" "));

        return sb.toString();
    }
}
