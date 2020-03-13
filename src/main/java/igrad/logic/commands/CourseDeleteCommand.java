package igrad.logic.commands;

import static java.util.Objects.requireNonNull;
import igrad.logic.commands.exceptions.CommandException;
import igrad.model.Model;
import igrad.model.ReadOnlyCourseBook;

/**
 * Deletes the existing course (and all data within it).
 */
public class CourseDeleteCommand extends CourseCommand {

    public static final String COMMAND_WORD = COURSE_COMMAND_WORD + "delete";

    public static final String MESSAGE_USAGE = COMMAND_WORD
        + ": Deletes the course and clears all data in the application.\n"
        + "Parameters: -Nill\n"
        + "Example: " + COMMAND_WORD;

    public static final String MESSAGE_DELETE_COURSE_SUCCESS = "Deleted CourseInfo (all data cleared!): %1$s";

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        ReadOnlyCourseBook courseBookToDelete = model.getCourseBook();
        model.resetCourseBook(courseBookToDelete);
        return new CommandResult(String.format(MESSAGE_DELETE_COURSE_SUCCESS, courseBookToDelete));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof CourseDeleteCommand); // instanceof handles nulls
    }
}
