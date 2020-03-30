package igrad.commons.core;

/**
 * Container for generic and global user visible messages.
 */
public class Messages {

    public static final String MESSAGE_UNKNOWN_COMMAND = "Unknown command";
    public static final String MESSAGE_UNKNOWN_COURSE_COMMAND = "Unknown course command, possible options;\n"
        + "course add ...\n"
        + "course edit ...\n"
        + "course delete\n";
    public static final String MESSAGE_UNKNOWN_REQUIREMENT_COMMAND = "Unknown requirement command, possible options;\n"
        + "requirement add ...\n"
        + "requirement edit ...\n"
        + "requirement delete ...\n"
        + "requirement assign ...\n";
    public static final String MESSAGE_UNKNOWN_MODULE_COMMAND = "Unknown module command, possible options;\n"
        + "module add ...\n"
        + "module edit ...\n"
        + "module assign ...\n"
        + "module delete\n";
    public static final String MESSAGE_INVALID_COMMAND_FORMAT = "Invalid command format!\n%1$s";
    public static final String MESSAGE_COURSE_NOT_SET = "Sorry, you need to set a course first!";
    public static final String MESSAGE_COURSE_ALREADY_SET = "Course has been set! Only one course can be added";
    public static final String MESSAGE_SPECIFIER_NOT_SPECIFIED = "Please provide a non-empty specifier.\n%1$s";
    public static final String MESSAGE_SPECIFIER_INVALID = "Please enter a valid specifier.\n%1$s";
}
