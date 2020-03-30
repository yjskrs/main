package igrad.logic.commands.course;

import igrad.logic.commands.CommandResult;
import igrad.logic.commands.exceptions.CommandException;
import igrad.model.Model;
import igrad.model.course.Cap;

/**
 * Adds a course to the application (there can only be one such course).
 */

// TODO (Teri): Please complete this class, you may refer to how ModuleDeleteCommand.java is done.
// (Hint: you may create a method in Model.java; model.computeEstimatedCap(Cap capToAchieve), which computes the
// estimated CAP you need to maintain each sem (given a capToAchieve)
public class CourseAchieveCommand extends CourseCommand {
    public static final String COMMAND_WORD = COURSE_COMMAND_WORD + SPACE + "achieve";

    public static final String MESSAGE_SUCCESS = "You need to maintain an average CAP (per sem) of: %1$";

    private final Cap cap = null;

    @Override
    public CommandResult execute(Model model) throws CommandException {
        return null;
    }
}
