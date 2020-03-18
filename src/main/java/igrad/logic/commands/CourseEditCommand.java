package igrad.logic.commands;

import static igrad.logic.parser.CliSyntax.PREFIX_NAME;
import static java.util.Objects.requireNonNull;

import igrad.logic.commands.exceptions.CommandException;
import igrad.model.Model;
import igrad.model.course.CourseInfo;

/*
 * TODO (Teri): Please refactor CourseCommand, CourseAddCommand, CourseDeleteCommand, CourseEditCommand, into
 *  the logic.commands.course package (create a new one). This should give you some LOCs (lines of code) :p
 */
/**
 * Edits the details of an existing module in the course book.
 */
public class CourseEditCommand extends CourseCommand {
    /*
     * TODO (Teri): Your main task is to implement the course edit COURSE_NAME n/NEW_COURSE_NAME command,
     *  e.g, course edit Comp Sci n/Business.
     *  Now, how would one do that? Well, let's start from the Parsers. We need to get the parsers to recognise the
     *  'course edit' command.
     *  The code is very similar to RequirementEditCommandParser.java (parse() method), you can use that
     *  for reference.
     */
    public static final String COMMAND_WORD = COURSE_COMMAND_WORD + "edit";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits name of the course. "
        + "Parameters: "
        + PREFIX_NAME + "COURSE NAME "
        + "Example: " + COMMAND_WORD + " "
        + PREFIX_NAME + "Bachelor of Computing (Honours) in Computer Science ";
    public static final String MESSAGE_EDIT_COURSE_SUCCESS = "Edited Course: %1$s";

    private final CourseInfo toModify;

    public CourseEditCommand(CourseInfo courseInfo) {
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
            && toModify.equals(((CourseEditCommand) other).toModify));
    }
}
