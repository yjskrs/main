package igrad.logic.commands;

import igrad.model.Model;

/**
 * Terminates the program.
 */
public class ExitCommand extends Command {

    public static final String COMMAND_WORD = "exit";

    public static final String MESSAGE_EXIT_ACKNOWLEDGEMENT = "Exiting iGrad as requested ...";

    @Override
    public CommandResult execute(Model model) {
        /*
         * TODO: Some code to fix here, when you exit, and restart app and undo, should not be able
         * to undo. In other words, when we exit the app, we need to sync the current course book
         * with backup course book (if current course book and backup course book exists)
         */
        return new CommandResult(MESSAGE_EXIT_ACKNOWLEDGEMENT, false, true, false);
    }

}
