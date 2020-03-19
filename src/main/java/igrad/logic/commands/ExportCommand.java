package igrad.logic.commands;

import java.io.IOException;
import igrad.commons.exceptions.DataConversionException;
import igrad.csv_writer.CsvWriter;
import igrad.logic.commands.exceptions.CommandException;
import igrad.model.Model;

/**
 * Format full help instructions for every command for display.
 */
public class ExportCommand extends Command {

    public static final String COMMAND_WORD = "export";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Exports data to a CSV file.\n"
        + "Example: " + COMMAND_WORD;

    public static final String SHOWING_EXPORT_MESSAGE = "Exported data to CSV file.";
    public static final String EXPORT_ERROR_MESSAGE = "Unable to export data to CSV file. Please ensure that you do not have the file <study_plan.csv> open.";

    @Override
    public CommandResult execute(Model model) throws CommandException {

        try {
            CsvWriter csvWriter = new CsvWriter(model.getFilteredModuleList());
            csvWriter.write();
        } catch (IOException e ){
            throw new CommandException(EXPORT_ERROR_MESSAGE);
        }

        return new CommandResult(SHOWING_EXPORT_MESSAGE);
    }
}
