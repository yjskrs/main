package igrad.logic.commands;

import igrad.logic.commands.exceptions.CommandException;
import igrad.model.Model;
import static java.util.Objects.requireNonNull;

public class CourseDeleteCommand extends Command {
    //TODO: (Teri) Here's a stub class (with partially implemented methods), please implement them

    public static final String COMMAND_WORD = "course delete";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the course and clears all data in the application.\n"
            + "Parameters: -Nill\n"
            + "Example: " + COMMAND_WORD;

    public static final String MESSAGE_DELETE_COURSE_SUCCESS = "Deleted CourseInfo (all data cleared!): %1$s";

    // This method would be automatically called
    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        /*
           TODO: this portion is incomplete, please uncomment and fill in the blanks

           ReadOnlyCourseBook courseBookToDelete = ... hint: refer to ModuleDeleteCommand
           model.resetCourseBook(... hint: this should be pretty obvious);
           return new CommandResult(String.format(MESSAGE_DELETE_COURSE_SUCCESS, ... hint: refer to ModuleDeleteCommand));
         */

        // Delete this line too, when you're done
        return null;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof CourseDeleteCommand); // instanceof handles nulls
    }
}
