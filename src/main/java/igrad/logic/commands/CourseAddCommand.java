package igrad.logic.commands;

import static igrad.logic.parser.CliSyntax.PREFIX_NAME;
import static java.util.Objects.requireNonNull;
import igrad.logic.commands.exceptions.CommandException;
import igrad.model.Model;
import igrad.model.course.CourseInfo;

/**
 * Adds a course to the application (there can only be one such course).
 */
public class CourseAddCommand extends CourseCommand {
    public static final String COMMAND_WORD = COURSE_COMMAND_WORD + "add";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a course. "
        + "Parameters: "
        + PREFIX_NAME + "COURSE NAME "
        + "Example: " + COMMAND_WORD + " "
        + PREFIX_NAME + "Bachelor of Computing (Honours) in Computer Science ";

    public static final String MESSAGE_SUCCESS = "New course added: %1$s";

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

        model.addCourseInfo(toAdd);
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof ModuleAddCommand // instanceof handles nulls
            && toAdd.equals(((CourseAddCommand) other).toAdd));
    }
}
