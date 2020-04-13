package igrad.logic.commands.module;

import igrad.logic.commands.Command;

//@@author nathanaelseen

/**
 * Represents a generic module command.
 */
public abstract class ModuleCommand extends Command {
    public static final String MODULE_COMMAND_WORD = "module";

    public static final String MESSAGE_MODULE_NON_EXISTENT = "Sorry, I was unable to find this module: %s\n";
}
