package igrad.logic.commands;

import static igrad.logic.parser.CliSyntax.PREFIX_NAME;
import static java.util.Objects.requireNonNull;

import igrad.logic.commands.exceptions.CommandException;
import igrad.model.Model;
import igrad.model.course.CourseInfo;

/**
 * Edits the details of an existing module in the course book.
 */
public class CourseModifyCommand extends Command {

    public static final String COMMAND_WORD = "course edit";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Modifies name of the course. "
        + "Parameters: "
        + PREFIX_NAME + "COURSE NAME "
        + "Example: " + COMMAND_WORD + " "
        + PREFIX_NAME + "Bachelor of Computing (Honours) in Computer Science ";
    public static final String MESSAGE_EDIT_COURSE_SUCCESS = "Edited Course: %1$s";

    private final CourseInfo toModify;

    public CourseModifyCommand(CourseInfo courseInfo) {
        requireNonNull(courseInfo);
        toModify = courseInfo;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        model.modifyCourseInfo(toModify);
        return new CommandResult(String.format(MESSAGE_EDIT_COURSE_SUCCESS, toModify));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof ModuleAddCommand // instanceof handles nulls
            && toModify.equals(((CourseModifyCommand) other).toModify));
    }
}
