package igrad.logic.commands;

import igrad.logic.commands.exceptions.CommandException;
import igrad.model.Model;
import igrad.model.ReadOnlyCourseBook;

import static java.util.Objects.requireNonNull;

public class CourseDeleteCommand extends Command {

    public static final String COMMAND_WORD = "course delete";

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
