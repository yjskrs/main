package igrad.logic.commands;

import java.io.IOException;
import java.util.Optional;

import igrad.commons.exceptions.DataConversionException;
import igrad.logic.commands.exceptions.CommandException;
import igrad.model.Model;
import igrad.model.ReadOnlyCourseBook;
import igrad.storage.CourseBookStorage;
import igrad.storage.JsonCourseBookStorage;

/**
 * Undoes the previous action taken.
 */
public class UndoCommand extends Command {

    public static final String COMMAND_WORD = "undo";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Undoes latest action.\n"
        + "Example: " + COMMAND_WORD;

    public static final String MESSAGE_SUCCESS = "Undid last command.";
    public static final String MESSAGE_ERROR = "Unable to undo the last comamand.";
    public static final String MESSAGE_NO_ACTION = "Nothing to undo";

    @Override
    public CommandResult execute(Model model) throws CommandException {

        CourseBookStorage courseBookStorage = new JsonCourseBookStorage(
            model.getCourseBookFilePath(),
            model.getBackupCourseBookFilePath()
        );

        try {
            Optional<ReadOnlyCourseBook> backupCourseBook = courseBookStorage.readBackupCourseBook();
            Optional<ReadOnlyCourseBook> courseBook = courseBookStorage.readCourseBook();

            if (courseBook.equals(backupCourseBook)) {
                throw new CommandException(MESSAGE_NO_ACTION);
            } else {
                if (backupCourseBook.isPresent()) {
                    model.setCourseBook(backupCourseBook.get());
                } else {
                    throw new CommandException(MESSAGE_ERROR);
                }
            }


        } catch (DataConversionException | IOException e) {
            throw new CommandException(MESSAGE_ERROR);
        }

        return new CommandResult(MESSAGE_SUCCESS, false, false, false);
    }
}
