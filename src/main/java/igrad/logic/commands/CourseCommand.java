package igrad.logic.commands;

/*
 * TODO (Teri): Please refactor CourseCommand, CourseAddCommand, CourseDeleteCommand, CourseEditCommand, into
 *  the logic.commands.course package (create a new one). This should give you some LOCs (lines of code) :p
 */

/**
 * Represents a generic course command.
 */
public abstract class CourseCommand extends Command {
    public static final String COURSE_COMMAND_WORD = "course ";
}
