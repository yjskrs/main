package igrad.logic.commands;

import static igrad.commons.util.CollectionUtil.requireAllNonNull;
import static igrad.logic.parser.CliSyntax.PREFIX_NAME;

import java.util.Optional;

import igrad.logic.commands.exceptions.CommandException;
import igrad.model.Model;
import igrad.model.course.Name;

/*
 * TODO (Teri): Please refactor CourseCommand, CourseAddCommand, CourseDeleteCommand, CourseEditCommand, into
 *  the logic.commands.course package (create a new one). This should give you some LOCs (lines of code) :p
 */

/**
 * Edits the details of an existing module in the course book.
 */
public class CourseEditCommand extends CourseCommand {
    /*
     * TODO (Teri): Now remember, your main task is to implement the course edit COURSE_NAME n/NEW_COURSE_NAME
     *  command, e.g, course edit Comp Sci n/Business, and you've already implemented the parsers.
     *  Hence, the next step would be to actually get the command to to work (i.e, contact the model and actually
     *  update the working copy of course name/info). Alright, I shall not overwhelm you with all the details,
     *  but, I shall give you a headstart by writing some code here!
     *  Now, how would one do that? Well, let's start from the Parsers. We need to get the parsers to recognise the
     *  Once again, the code is very similar to RequirementEditCommand.java, and Yijie has done a good job there,
     *  you may hence use that for reference.
     */
    public static final String COMMAND_WORD = COURSE_COMMAND_WORD + "edit";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits name of the course. "
        + "Parameters: "
        + PREFIX_NAME + "COURSE NAME "
        + "Example: " + COMMAND_WORD + " "
        + PREFIX_NAME + "Bachelor of Computing (Honours) in Computer Science ";
    public static final String MESSAGE_EDIT_COURSE_SUCCESS = "Edited Course: %1$s";

    private final Name originalName;

    private final Optional<Name> newName;

    public CourseEditCommand(Name originalName,
                             Optional<Name> newName) {
        requireAllNonNull(originalName, newName);

        this.originalName = originalName;
        this.newName = newName;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        /*requireNonNull(model);
        Name editedName = ...
        CourseInfo editedCourse = ...
        ...
        (Hint: an if-block here to check if both requirment to edit and edited requirement hold the same values,
        if they do, then we flag an error to the user.)
        ...
        model.set...
        return null;
         */
        /*
         * TODO (Teri): Congrats on successfully implementing the command! I think you're pretty much done with this
         *  feature! Now verify it works by running the GUI, first make sure a course is added (you have to do it
         *  anyway since i added that validation). And then type; course edit n/
         */

        // Please comment this out after implementation
        return null;
    }
}
