package igrad.logic.commands.course;

import igrad.logic.commands.Command;

/**
 * Represents a generic course command.
 */
public abstract class CourseCommand extends Command {
    public static final String COURSE_COMMAND_WORD = "course";
    public static final String MESSAGE_COURSE_INFO_NON_EXISTENT =
        "Course info does not exist. Please enter an existing course.";
}
