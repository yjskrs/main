package igrad.commons.core;

/**
 * Container for generic and global user visible messages.
 */
public class Messages {

    public static final String MESSAGE_UNKNOWN_COMMAND = "I don't know this command, "
        + "you may key in `help` to get a list of commands!";
    public static final String MESSAGE_UNKNOWN_COURSE_COMMAND = "I don't know this course command, "
        + "you might want to try:\n"
        + "course add ...\n"
        + "course edit ...\n"
        + "course achieve ...\n"
        + "course delete\n";
    public static final String MESSAGE_UNKNOWN_REQUIREMENT_COMMAND = "I don't know this requirement command, "
        + "you might want to try:\n"
        + "requirement add ...\n"
        + "requirement edit ...\n"
        + "requirement delete ...\n"
        + "requirement assign ...\n";
    public static final String MESSAGE_UNKNOWN_MODULE_COMMAND = "I don't know this module command, "
        + "you might want to try:\n"
        + "module add ...\n"
        + "module edit ...\n"
        + "module done ...\n"
        + "module delete\n";
    public static final String MESSAGE_INVALID_COMMAND_FORMAT = "Invalid command format!\n%1$s";
    public static final String MESSAGE_COURSE_NOT_SET = "You need to set a course first! Use this command:\n"
        + "course add n/COURSE_NAME";
    public static final String MESSAGE_COURSE_ALREADY_SET = "Course has been set! Only one course can be added.";
    public static final String MESSAGE_COURSE_SEMESTER_NOT_SET = "You need to set total number of semesters first!\n";
    public static final String MESSAGE_SPECIFIER_NOT_SPECIFIED = "Please provide a non-empty specifier.\n%1$s";
    public static final String MESSAGE_SPECIFIER_INVALID = "Please enter a valid specifier.\n%1$s";

    public static final String MESSAGE_REQUEST_FAILED = "ERROR: Request failed for %s\n";

    public static final String MESSAGE_ADD_COURSE = "You don't have a course! Enter your course in the format: "
        + "'course add n/<NAME OF COURSE> s/<SEMESTERS LEFT>'";
    public static final String MESSAGE_WELCOME_BACK = "Welcome back! Hope your studies are going well!";
}
