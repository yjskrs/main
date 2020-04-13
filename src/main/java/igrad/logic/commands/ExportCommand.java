package igrad.logic.commands;

import java.io.IOException;
import java.util.List;

import igrad.logic.commands.exceptions.CommandException;
import igrad.model.Model;
import igrad.model.module.Module;

/**
 * Format full help instructions for every command for display.
 */
public class ExportCommand extends Command {

    public static final String COMMAND_WORD = "export";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Exports data to a CSV file.\n"
        + "Example: " + COMMAND_WORD;

    public static final String SHOWING_EXPORT_MESSAGE = "I've exported your data to a CSV file."
        + " You can find it in the same folder as this app's executable!";
    public static final String EXPORT_ERROR_MESSAGE = "Sorry, I was unable to export data to CSV file."
        + " Please ensure that you do not have the file 'study_plan.csv' open";

    public static final String NO_MODULE_WITH_SEMESTERS_ERROR_MESSAGE =
        "Sorry, I couldn't find any modules that are tagged to a semester!"
        + " I can only export modules that are tagged with a semester.";

    @Override
    public CommandResult execute(Model model) throws CommandException {

        try {

            List<Module> moduleList = model.exportModuleList();

            if (moduleList.size() == 0) {
                throw new CommandException(NO_MODULE_WITH_SEMESTERS_ERROR_MESSAGE);
            }

        } catch (IOException | NumberFormatException e) {
            throw new CommandException(EXPORT_ERROR_MESSAGE);
        }

        return new CommandResult(SHOWING_EXPORT_MESSAGE);
    }
}
