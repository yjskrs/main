package igrad.logic.commands;

import java.util.Optional;

import igrad.commons.exceptions.DataConversionException;
import igrad.logic.commands.exceptions.CommandException;
import igrad.model.Model;
import igrad.model.ReadOnlyCourseBook;
import igrad.storage.JsonCourseBookStorage;

/**
 * Undoes the previous action taken.
 */
public class UndoCommand extends Command {

    public static final String COMMAND_WORD = "undo";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Undoes latest action.\n"
        + "Example: " + COMMAND_WORD;

    public static final String SHOWING_UNDO_MESSAGE = "Undid last command.";
    public static final String UNDO_ERROR_MESSAGE = "Unable to undo the last comamand.";

    @Override
    public CommandResult execute(Model model) throws CommandException {

        JsonCourseBookStorage courseBookStorage = new JsonCourseBookStorage(
            model.getCourseBookFilePath(),
            model.getBackupCourseBookFilePath()
        );

        try {
            Optional<ReadOnlyCourseBook> backupCourseBook = courseBookStorage.readBackupCourseBook();

            if (backupCourseBook.isPresent()) {
                model.setCourseBook(backupCourseBook.get());
            } else {
                throw new CommandException(UNDO_ERROR_MESSAGE);
            }

        } catch (DataConversionException e) {
            throw new CommandException(UNDO_ERROR_MESSAGE);
        }

        return new CommandResult(SHOWING_UNDO_MESSAGE, false, false, false);
    }
}
