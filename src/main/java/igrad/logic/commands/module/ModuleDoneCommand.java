package igrad.logic.commands.module;

import igrad.logic.commands.CommandResult;
import igrad.logic.commands.exceptions.CommandException;
import igrad.model.Model;

/**
 * Marks the module as done, with a specified grade.
 */
public class ModuleDoneCommand extends ModuleCommand {
    public static final String COMMAND_WORD = MODULE_COMMAND_WORD + "done";

    @Override
    public CommandResult execute(Model model) throws CommandException {
        return null;
    }
}
