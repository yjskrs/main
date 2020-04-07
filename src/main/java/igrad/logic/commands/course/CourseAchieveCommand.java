package igrad.logic.commands.course;

import static igrad.logic.parser.CliSyntax.PREFIX_CAP;
import static igrad.model.course.Cap.MESSAGE_CONSTRAINTS;
import static java.util.Objects.requireNonNull;

import igrad.logic.commands.CommandResult;
import igrad.logic.commands.exceptions.CommandException;
import igrad.model.Model;
import igrad.model.course.Cap;



/**
 * Adds a course to the application (there can only be one such course).
 */
public class CourseAchieveCommand extends CourseCommand {
    public static final String COURSE_ACHIEVE_COMMAND_WORD = COURSE_COMMAND_WORD + SPACE + "achieve";

    public static final String MESSAGE_COURSE_ACHIEVE_SUCCESS = "You need to maintain an average C.A.P. (per sem) "
        + "of: %1$s";

    public static final String MESSAGE_ACHIEVED_CAP_NOT_CALCULATED = "Please enter desired C.A.P.\n" +
            MESSAGE_CONSTRAINTS;

    public static final String MESSAGE_UNABLE_TO_ACHIEVE_CAP = "Unable to achieve desired C.A.P. as C.A.P. of %1$s to "
            + "maintain per semester is invalid";

    public static final String MESSAGE_COURSE_ACHIEVE_DETAILS = COURSE_ACHIEVE_COMMAND_WORD + ": Calculates average "
        + "C.A.P. needed per sem to achieve desired C.A.P.\n";

    public static final String MESSAGE_COURSE_ACHIEVE_USAGE = "Parameter(s): " + PREFIX_CAP + "DESIRED_C.A.P.";

    public static final String MESSAGE_COURSE_ACHIEVE_HELP = MESSAGE_COURSE_ACHIEVE_DETAILS
        + MESSAGE_COURSE_ACHIEVE_USAGE;

    private final Cap capToAchieve;

    public CourseAchieveCommand(Cap capToAchieve) {
        this.capToAchieve = capToAchieve;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        double estimatedCap = Cap.computeEstimatedCap(model, capToAchieve);

        if (!Cap.isValidCap(estimatedCap)) {
            throw new CommandException(String.format(MESSAGE_UNABLE_TO_ACHIEVE_CAP, estimatedCap));
        }

        return new CommandResult(String.format(MESSAGE_COURSE_ACHIEVE_SUCCESS, estimatedCap));
    }
}
