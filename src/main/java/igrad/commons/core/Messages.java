package igrad.commons.core;

//@@author teriaiw

import igrad.logic.commands.course.CourseAddCommand;

/**
 * Container for generic and global user visible messages.
 */
public class Messages {

    public static final String MESSAGE_UNKNOWN_COMMAND = "I don't know this command, "
        + "you may key in `help` to get a list of commands!";
    public static final String MESSAGE_UNKNOWN_COURSE_COMMAND = "Here's a list of course commands "
        + "you might want to try:\n"
        + "course set | course edit | course achieve | course delete\n";
    public static final String MESSAGE_UNKNOWN_REQUIREMENT_COMMAND = "Here's a list of requirement commands "
        + "you might want to try:\n"
        + "requirement add | requirement edit | requirement delete | requirement assign | requirement unassign\n";
    public static final String MESSAGE_UNKNOWN_MODULE_COMMAND = "Here's a list of module commands "
        + "you might want to try:\n"
        + "module add | module edit | module done | module delete\n";
    public static final String MESSAGE_INVALID_COMMAND_FORMAT = "Invalid command format!\n%1$s";
    public static final String MESSAGE_COURSE_NOT_SET = "Oh no, you don't have a course set yet! "
        + "Set it using this command:\n"
        + "course set n/COURSE_NAME s/TOTAL_SEMESTERS";
    public static final String MESSAGE_COURSE_ALREADY_SET = "Sorry, you have already set a course. "
        + "Use this command instead:\n"
        + "course edit [n/COURSE_NAME] [s/TOTAL_SEMESTERS]";

    public static final String MESSAGE_SPECIFIER_NOT_SPECIFIED = "Please provide a non-empty specifier.\n%1$s";
    public static final String MESSAGE_SPECIFIER_INVALID = "Please enter a valid specifier.\n%1$s";

    public static final String MESSAGE_REQUEST_FAILED = "ERROR: Request failed for %s\n";

    public static final String MESSAGE_ADD_COURSE = "You don't have a course yet! Enter your course in the format:\n"
            + CourseAddCommand.MESSAGE_COURSE_ADD_USAGE;
    public static final String MESSAGE_WELCOME_BACK = "Welcome back! Hope your studies are going well!";
}
