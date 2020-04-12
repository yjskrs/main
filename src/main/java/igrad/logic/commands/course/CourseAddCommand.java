package igrad.logic.commands.course;

//@@author nathanaelseen

import static igrad.commons.core.Messages.MESSAGE_COURSE_ALREADY_SET;
import static igrad.logic.parser.CliSyntax.PREFIX_NAME;
import static igrad.logic.parser.CliSyntax.PREFIX_SEMESTER;
import static java.util.Objects.requireNonNull;

import igrad.logic.commands.CommandResult;
import igrad.logic.commands.exceptions.CommandException;
import igrad.model.Model;
import igrad.model.course.CourseInfo;


/**
 * Adds a course to the application (there can only be one such course).
 */
public class CourseAddCommand extends CourseCommand {
    public static final String COURSE_ADD_COMMAND_WORD = COURSE_COMMAND_WORD + SPACE + "set";
    public static final String MESSAGE_COURSE_ADD_DETAILS = COURSE_ADD_COMMAND_WORD
        + ": Adds a course with relevant details specified.\n";
    public static final String MESSAGE_COURSE_ADD_USAGE = "Parameter(s): "
        + PREFIX_NAME + "COURSE_NAME " + PREFIX_SEMESTER + "TOTAL_SEMESTERS\n"
        + "e.g. " + COURSE_ADD_COMMAND_WORD + " "
        + PREFIX_NAME + "Computer Science "
        + PREFIX_SEMESTER + "8";
    public static final String MESSAGE_COURSE_ADD_HELP = MESSAGE_COURSE_ADD_DETAILS + MESSAGE_COURSE_ADD_USAGE;
    public static final String MESSAGE_COURSE_ADD_SUCCESS = "New course: %1$s has been set successfully!";
    public static final String MESSAGE_COURSE_NOT_ADDED = "All fields to be filled, course set n/COURSE_NAME "
        + "s/SEMESTERS";

    private final CourseInfo toAdd;

    /**
     * Creates an CourseInfo to add the Course Book
     */
    public CourseAddCommand(CourseInfo courseInfo) {
        requireNonNull(courseInfo);
        toAdd = courseInfo;
    }

    /**
     * Executes the command and returns the result message.
     *
     * @param model {@code Model} which the command should operate on.
     * @return feedback message of the operation result for display
     * @throws CommandException If an error occurs during command execution.
     */
    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (model.isCourseNameSet()) {
            throw new CommandException(MESSAGE_COURSE_ALREADY_SET);
        }

        model.addCourseInfo(toAdd);
        return new CommandResult(String.format(MESSAGE_COURSE_ADD_SUCCESS, toAdd),
            false, false, true);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof CourseAddCommand // instanceof handles nulls
            && toAdd.equals(((CourseAddCommand) other).toAdd));
    }
}
