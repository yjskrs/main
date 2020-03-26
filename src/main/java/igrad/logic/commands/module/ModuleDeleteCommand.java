package igrad.logic.commands.module;

import static igrad.logic.parser.CliSyntax.PREFIX_MODULE_CODE;
import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.Optional;

import igrad.commons.core.Messages;
import igrad.logic.commands.CommandResult;
import igrad.logic.commands.exceptions.CommandException;
import igrad.model.Model;
import igrad.model.module.Module;
import igrad.model.module.ModuleCode;

/**
 * Deletes a module identified using it's displayed index from the course book.
 */
public class ModuleDeleteCommand extends ModuleCommand {

    public static final String COMMAND_WORD = MODULE_COMMAND_WORD + "delete";

    public static final String MESSAGE_USAGE = COMMAND_WORD
        + ": Deletes the module identified by its module code.\n"
        + "Parameters: MODULE_CODE\n"
        + "Example: " + COMMAND_WORD + " " + PREFIX_MODULE_CODE + "CS2103T";

    public static final String MESSAGE_DELETE_MODULE_SUCCESS = "Deleted Module: %1$s";

    private final ModuleCode moduleCode;

    public ModuleDeleteCommand(ModuleCode moduleCode) {
        this.moduleCode = moduleCode;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Module> lastShownList = model.getFilteredModuleList();

        Optional<Module> moduleToDeleteOpt = Optional.empty();

        for (Module module : lastShownList) {
            if (module.getModuleCode().equals(moduleCode)) {
                moduleToDeleteOpt = Optional.of(module);
            }
        }

        if (moduleToDeleteOpt.isEmpty()) {
            throw new CommandException(Messages.MESSAGE_INVALID_MODULE_DISPLAYED_INDEX);
        }

        Module moduleToDelete = moduleToDeleteOpt.get();

        model.deleteModule(moduleToDelete);
        return new CommandResult(String.format(MESSAGE_DELETE_MODULE_SUCCESS, moduleToDelete));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof ModuleDeleteCommand // instanceof handles nulls
            && moduleCode.equals(((ModuleDeleteCommand) other).moduleCode)); // state check
    }
}
