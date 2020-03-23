package igrad.logic.commands.course;

import static igrad.commons.util.CollectionUtil.requireAllNonNull;
import static igrad.logic.parser.CliSyntax.PREFIX_NAME;
import static java.util.Objects.requireNonNull;

import java.util.Optional;

import igrad.logic.commands.CommandResult;
import igrad.logic.commands.exceptions.CommandException;
import igrad.model.Model;
import igrad.model.course.CourseInfo;
import igrad.model.course.Name;

/**
 * Edits the details of an existing module in the course book.
 */
public class CourseEditCommand extends CourseCommand {

    public static final String COMMAND_WORD = COURSE_COMMAND_WORD + "edit";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits name of the course. "
        + "Parameters: "
        + PREFIX_NAME + "COURSE NAME "
        + "Example: " + COMMAND_WORD + " "
        + PREFIX_NAME + "Bachelor of Computing (Honours) in Computer Science ";
    public static final String MESSAGE_EDIT_COURSE_SUCCESS = "Edited Course: %1$s";
    public static final String MESSAGE_EDIT_COURSE_SAME_PARAMETERS = "Please change the name of the course";
    public static final String MESSAGE_COURSE_NOT_EDITED = "Edited course must be provided with prefix "
            + "[" + PREFIX_NAME + "]" ;

    //private final Name originalName;

    private final Optional<Name> newName;

    public CourseEditCommand(Optional<Name> newName) {
        requireAllNonNull(newName);

        //this.originalName = originalName;
        this.newName = newName;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        CourseInfo courseToEdit = model.getCourseInfo();
        Name editedName = newName.orElse(courseToEdit.getName().get());
        CourseInfo editedCourse = new CourseInfo(Optional.ofNullable(editedName));

        if (newName.isPresent() && courseToEdit.getName().equals(editedName)) {
            throw new CommandException(MESSAGE_EDIT_COURSE_SAME_PARAMETERS);
        }

        model.editCourseInfo(editedCourse);
        return new CommandResult(String.format(MESSAGE_EDIT_COURSE_SUCCESS, editedCourse));
    }
}
