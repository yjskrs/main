package igrad.logic.commands.module;

import static igrad.logic.parser.CliSyntax.PREFIX_CREDITS;
import static igrad.logic.parser.CliSyntax.PREFIX_MEMO;
import static igrad.logic.parser.CliSyntax.PREFIX_MODULE_CODE;
import static igrad.logic.parser.CliSyntax.PREFIX_SEMESTER;
import static igrad.logic.parser.CliSyntax.PREFIX_TITLE;
import static java.util.Objects.requireNonNull;

import igrad.logic.commands.CommandResult;
import igrad.logic.commands.exceptions.CommandException;
import igrad.model.Model;
import igrad.model.module.Module;
import igrad.model.module.ModuleCode;

/**
 * Adds a module to the course book.
 */
public class ModuleAddAutoCommand extends ModuleCommand {

    public static final String COMMAND_WORD = MODULE_COMMAND_WORD + SPACE + "add";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a module. "
        + "Parameters: "
        + PREFIX_TITLE + "MODULE TITLE "
        + PREFIX_MODULE_CODE + "MODULE CODE "
        + PREFIX_CREDITS + "CREDITS "
        + PREFIX_MEMO + "MEMO "
        + "[" + PREFIX_SEMESTER + "SEMESTER]...\n"
        + "Example: " + COMMAND_WORD + " "
        + PREFIX_TITLE + "Software Engineering "
        + PREFIX_MODULE_CODE + "CS2103T "
        + PREFIX_CREDITS + "4 "
        + PREFIX_MEMO + "Hard module. Good teachers. "
        + PREFIX_SEMESTER + "Y2S2 ";

    public static final String MESSAGE_SUCCESS = "New module added based on NUSMods data: %1$s";
    public static final String MESSAGE_DUPLICATE_MODULE = "This module already exists in the course book";
    public static final String MESSAGE_PREREQUISITE_NOT_PRESENT =
            "WARNING: A prerequisite module %s has not been added into the course book.";
    public static final String MESSAGE_PRECLUSION_PRESENT =
            "WARNING: A preclusion for this module %s already exists in the course book.";

    private final Module toAdd;
    private final String[] preclusionModules;
    private final String[] prerequisiteModules;

    /**
     * Creates an ModuleAddCommand to add the specified {@code Module}
     */
    public ModuleAddAutoCommand(Module module, String[] preclusionModules, String[] prerequisiteModules) {
        requireNonNull(module);
        toAdd = module;
        this.preclusionModules = preclusionModules;
        this.prerequisiteModules = prerequisiteModules;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        String messageSuccess = MESSAGE_SUCCESS;

        if (model.hasModule(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_MODULE);
        }

        for (String prerequisite: prerequisiteModules) {
            boolean isPrereqPresent = false;
            ModuleCode prerequisiteModuleCode = new ModuleCode(prerequisite);
            for (Module module: model.getFilteredModuleList()) {
                if (module.hasModuleCodeOf(prerequisiteModuleCode)) {
                    isPrereqPresent = true;
                    break;
                }
            }

            if (!isPrereqPresent) {
                String exceptionMessage = formatPrerequisiteExceptionMessage(prerequisiteModuleCode);
                messageSuccess = exceptionMessage + "\n" + messageSuccess;
            }
        }

        for (String preclusion: preclusionModules) {
            for (Module module: model.getFilteredModuleList()) {
                ModuleCode preclusionModuleCode = new ModuleCode(preclusion);
                if (module.hasModuleCodeOf(preclusionModuleCode)) {
                    String exceptionMessage = formatPreclusionExceptionMessage(preclusionModuleCode);
                    messageSuccess = exceptionMessage + "\n" + messageSuccess;
                }
            }
        }

        model.addModule(toAdd);
        return new CommandResult(String.format(messageSuccess, toAdd));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof ModuleAddAutoCommand // instanceof handles nulls
            && toAdd.equals(((ModuleAddAutoCommand) other).toAdd));
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
