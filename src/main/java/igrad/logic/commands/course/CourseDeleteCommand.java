package igrad.logic.commands.course;

import static java.util.Objects.requireNonNull;

import igrad.logic.commands.CommandResult;
import igrad.logic.commands.exceptions.CommandException;
import igrad.model.Model;
import igrad.model.ReadOnlyCourseBook;
import igrad.model.course.CourseInfo;

/**
 * Deletes the existing {@code Course} (and all data within it, e.g, {@code Module}, {@code Requirement}).
 */
public class CourseDeleteCommand extends CourseCommand {

    public static final String COMMAND_WORD = COURSE_COMMAND_WORD + SPACE + "delete";

    public static final String MESSAGE_SUCCESS = "Deleted Course: %1$s. All data cleared!";

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        ReadOnlyCourseBook courseBookToDelete = model.getCourseBook();

        CourseInfo oldCourseInfo = model.getCourseInfo();

        model.resetCourseBook(courseBookToDelete);
        return new CommandResult(String.format(MESSAGE_SUCCESS, oldCourseInfo));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof CourseDeleteCommand); // instanceof handles nulls
    }
}
