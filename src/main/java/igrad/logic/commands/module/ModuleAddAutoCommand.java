package igrad.logic.commands.module;

import static igrad.logic.parser.CliSyntax.PREFIX_CREDITS;
import static igrad.logic.parser.CliSyntax.PREFIX_MODULE_CODE;
import static igrad.logic.parser.CliSyntax.PREFIX_SEMESTER;
import static igrad.logic.parser.CliSyntax.PREFIX_TITLE;
import static java.util.Objects.requireNonNull;

import java.util.ArrayList;

import igrad.logic.commands.CommandResult;
import igrad.model.Model;
import igrad.model.module.Module;
import igrad.model.module.ModuleCode;

/**
 * Adds a module to the course book.
 */
public class ModuleAddAutoCommand extends ModuleCommand {

    public static final String COMMAND_WORD = MODULE_COMMAND_WORD + SPACE + "add";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a module. "
        + "Parameter(s): "
        + PREFIX_TITLE + "MODULE_TITLE "
        + PREFIX_MODULE_CODE + "MODULE_CODE "
        + PREFIX_CREDITS + "CREDITS "
        + "[" + PREFIX_SEMESTER + "SEMESTER]...\n"
        + "Example: " + COMMAND_WORD + " "
        + PREFIX_TITLE + "Software Engineering "
        + PREFIX_MODULE_CODE + "CS2103T "
        + PREFIX_CREDITS + "4 "
        + PREFIX_SEMESTER + "Y2S2 ";

    public static final String MESSAGE_COMPLETE = "%d module(s) added through NUSMods API.\n";
    public static final String MESSAGE_SUCCESS = "Added module: %s\n";
    public static final String MESSAGE_DUPLICATE_MODULE = "ERROR: Duplicate detected: %s\n";
    public static final String MESSAGE_PREREQUISITE_NOT_PRESENT =
        "WARNING: Prerequisite not found!\n";
    public static final String MESSAGE_PRECLUSION_PRESENT =
        "WARNING: Preclusion found!\n";

    private final ArrayList<Module> toAddList;
    private final String messageAdditional;

    /**
     * Creates an ModuleAddCommand to add the specified {@code Module}
     */
    public ModuleAddAutoCommand(ArrayList<Module> modules, String messageAdditional) {
        requireNonNull(modules);

        toAddList = modules;
        this.messageAdditional = messageAdditional;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);

        StringBuilder message = new StringBuilder();

        int modulesAdded = 0;

        for (Module module : toAddList) {

            if (model.hasModule(module)) {
                message.append(String.format(MESSAGE_DUPLICATE_MODULE, module.toString()));
            } else {
                message.append(String.format(MESSAGE_SUCCESS, module.toString()));

                if (!model.hasModulePrerequisites(module)) {
                    message.append(MESSAGE_PREREQUISITE_NOT_PRESENT);
                }

                if (model.hasModulePreclusions(module)) {
                    message.append(MESSAGE_PRECLUSION_PRESENT);
                }

                model.addModule(module);
                modulesAdded++;
            }
        }

        message.append(String.format(MESSAGE_COMPLETE, modulesAdded));
        message.append(messageAdditional);

        return new CommandResult(message.toString());
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof ModuleAddAutoCommand // instanceof handles nulls
            && toAddList.equals(((ModuleAddAutoCommand) other).toAddList));
    }

    /**
     * Formats the exception message for when a preclusion module is present in the model.
     */
    private String formatPreclusionExceptionMessage(ModuleCode moduleCode) {
        String moduleCodeString = "(" + moduleCode.toString() + ")";

        return String.format(MESSAGE_PRECLUSION_PRESENT, moduleCodeString);
    }

    /**
     * Formats the exception message for when a prerequisite module is not present in the model.
     */
    private String formatPrerequisiteExceptionMessage(ModuleCode moduleCode) {
        String moduleCodeString = "(" + moduleCode.toString() + ")";

        return String.format(MESSAGE_PREREQUISITE_NOT_PRESENT, moduleCodeString);
    }
}
