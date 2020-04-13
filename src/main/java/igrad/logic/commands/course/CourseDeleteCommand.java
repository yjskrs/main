package igrad.logic.commands.course;

//@@author teriaiw

import static java.util.Objects.requireNonNull;

import igrad.logic.commands.CommandResult;
import igrad.model.Model;
import igrad.model.ReadOnlyCourseBook;
import igrad.model.course.CourseInfo;

/**
 * Deletes the existing {@code Course} (and all data within it, e.g, {@code Module}, {@code Requirement}).
 */
public class CourseDeleteCommand extends CourseCommand {

    public static final String COURSE_DELETE_COMMAND_WORD = COURSE_COMMAND_WORD + SPACE + "delete";

    public static final String MESSAGE_COURSE_DELETE_SUCCESS = "Course: %1$s has been deleted successfully!\n"
            + "All data cleared! If you made a mistake, use: undo";

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);

        ReadOnlyCourseBook courseBookToDelete = model.getCourseBook();

        // We have to make a copy of the previous course info as they would be deleted by garbage collector
        CourseInfo oldCourseInfo = new CourseInfo(model.getCourseInfo());

        model.resetCourseBook(courseBookToDelete);

        return new CommandResult(String.format(MESSAGE_COURSE_DELETE_SUCCESS, oldCourseInfo));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof CourseDeleteCommand); // instanceof handles nulls
    }
}
