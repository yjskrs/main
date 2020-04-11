package igrad.logic.commands.course;

import igrad.logic.commands.Command;

//@@author nathanaelseen

/**
 * Represents a generic course command.
 */
public abstract class CourseCommand extends Command {
    public static final String COURSE_COMMAND_WORD = "course";
    public static final String MESSAGE_COURSE_NON_EXISTENT =
        "Course does not exist. Please enter an existing course.";
}
