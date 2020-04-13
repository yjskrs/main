package igrad.logic.commands.course;

//@@author teriaiw

import static igrad.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static igrad.logic.parser.CliSyntax.PREFIX_CAP;
import static igrad.model.course.Cap.MESSAGE_CONSTRAINTS;
import static java.util.Objects.requireNonNull;

import java.util.Optional;

import igrad.logic.commands.CommandResult;
import igrad.logic.commands.exceptions.CommandException;
import igrad.model.Model;
import igrad.model.course.Cap;
import igrad.model.course.CourseInfo;
import igrad.model.course.exceptions.CapOverflowException;


/**
 * Adds a course to the application (there can only be one such course).
 */
public class CourseAchieveCommand extends CourseCommand {
    public static final String COURSE_ACHIEVE_COMMAND_WORD = COURSE_COMMAND_WORD + SPACE + "achieve";
    public static final String MESSAGE_COURSE_ACHIEVE_SUCCESS = "Maintain an average C.A.P. (per sem) "
        + "of: %1$s to get there! You can do it!";
    public static final String MESSAGE_ACHIEVED_CAP_NOT_CALCULATED = "Please enter desired C.A.P.\n"
        + MESSAGE_CONSTRAINTS;
    public static final String MESSAGE_COURSE_ACHIEVE_DETAILS = COURSE_ACHIEVE_COMMAND_WORD + ": Calculates average "
        + "C.A.P. needed per sem to achieve desired C.A.P.\n";
    public static final String MESSAGE_COURSE_ACHIEVE_USAGE = "Parameter(s): " + PREFIX_CAP + "DESIRED_CAP\n"
            + "e.g. " + COURSE_ACHIEVE_COMMAND_WORD + " "
            + PREFIX_CAP + "5";
    public static final String MESSAGE_COURSE_ACHIEVE_HELP = MESSAGE_COURSE_ACHIEVE_DETAILS
        + MESSAGE_COURSE_ACHIEVE_USAGE;

    private final Optional<Cap> capToAchieve;

    public CourseAchieveCommand(Optional<Cap> capToAchieve) {
        requireNonNull(capToAchieve);
        this.capToAchieve = capToAchieve;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (capToAchieve.toString().isEmpty()) {
            throw new CommandException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    MESSAGE_ACHIEVED_CAP_NOT_CALCULATED));
        }

        try {
            Optional<Cap> estimatedCap = CourseInfo.computeEstimatedCap(model.getCourseInfo(), capToAchieve.get());
            return new CommandResult(String.format(MESSAGE_COURSE_ACHIEVE_SUCCESS, estimatedCap.get()));

        } catch (CapOverflowException e) {
            throw new CommandException(e.getMessage());
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof CourseAchieveCommand) // instanceof handles nulls
            && (capToAchieve.equals(((CourseAchieveCommand) other).capToAchieve));
    }
}
