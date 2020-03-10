package igrad.logic.commands;

import static java.util.Objects.requireNonNull;

import igrad.model.CourseBook;
import igrad.model.Model;

/**
 * Clears the course book.
 */
public class ClearCommand extends Command {

    public static final String COMMAND_WORD = "clear";
    public static final String MESSAGE_SUCCESS = "CourseInfo book has been cleared!";


    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.setCourseBook(new CourseBook());
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
