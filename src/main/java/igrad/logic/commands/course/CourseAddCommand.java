package igrad.logic.commands.course;

import static igrad.commons.core.Messages.MESSAGE_COURSE_ALREADY_SET;
import static igrad.logic.parser.CliSyntax.PREFIX_NAME;
import static java.util.Objects.requireNonNull;

import igrad.logic.commands.CommandResult;
import igrad.logic.commands.exceptions.CommandException;
import igrad.logic.commands.module.ModuleAddCommand;
import igrad.model.Model;
import igrad.model.course.CourseInfo;

/**
 * Adds a course to the application (there can only be one such course).
 */
public class CourseAddCommand extends CourseCommand {
    public static final String COMMAND_WORD = COURSE_COMMAND_WORD + SPACE + "add";
    public static final String MESSAGE_DETAILS = COMMAND_WORD + ": Adds a course with relevant details specified.\n";

    public static final String MESSAGE_USAGE = "Parameter(s): "
        + PREFIX_NAME + "COURSE_NAME\n"
        + "Example: " + COMMAND_WORD + " "
        + PREFIX_NAME + "Bachelor of Computing (Honours) in Computer Science ";

    public static final String MESSAGE_HELP = MESSAGE_DETAILS + MESSAGE_USAGE;

    public static final String MESSAGE_SUCCESS = "New course added: %1$s";
    public static final String MESSAGE_NOT_ADDED = "Added course must be provided with at least these argument(s) "
        + PREFIX_NAME + "NAME ";
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
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd), false, false, true);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof ModuleAddCommand // instanceof handles nulls
            && toAdd.equals(((CourseAddCommand) other).toAdd));
    }
}
