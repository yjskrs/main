package igrad.logic.commands.module;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.Optional;

import igrad.logic.commands.CommandResult;
import igrad.logic.commands.exceptions.CommandException;
import igrad.model.Model;
import igrad.model.module.Module;
import igrad.model.module.ModuleCode;

/**
 * Deletes a {@code Module} identified using it's displayed index from the course book.
 */
public class ModuleDeleteCommand extends ModuleCommand {

    public static final String COMMAND_WORD = MODULE_COMMAND_WORD + SPACE + "delete";

    public static final String MESSAGE_SUCCESS = "Deleted Module: %1$s";

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
            throw new CommandException(MESSAGE_MODULE_NON_EXISTENT);
        }

        Module moduleToDelete = moduleToDeleteOpt.get();

        model.deleteModule(moduleToDelete);
        return new CommandResult(String.format(MESSAGE_SUCCESS, moduleToDelete));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof ModuleDeleteCommand // instanceof handles nulls
            && moduleCode.equals(((ModuleDeleteCommand) other).moduleCode)); // state check
    }
}
