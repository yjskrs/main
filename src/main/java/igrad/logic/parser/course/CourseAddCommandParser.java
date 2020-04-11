package igrad.logic.parser.course;

import static igrad.logic.commands.course.CourseAddCommand.MESSAGE_COURSE_ADD_HELP;
import static igrad.logic.commands.course.CourseAddCommand.MESSAGE_COURSE_NOT_ADDED;
import static igrad.logic.parser.CliSyntax.PREFIX_NAME;
import static igrad.logic.parser.CliSyntax.PREFIX_SEMESTER;

import java.util.Optional;

import igrad.commons.core.Messages;
import igrad.logic.commands.course.CourseAddCommand;
import igrad.logic.parser.ArgumentMultimap;
import igrad.logic.parser.ArgumentTokenizer;
import igrad.logic.parser.Parser;
import igrad.logic.parser.ParserUtil;
import igrad.logic.parser.exceptions.ParseException;
import igrad.model.course.Cap;
import igrad.model.course.CourseInfo;
import igrad.model.course.Credits;
import igrad.model.course.Name;
import igrad.model.course.Semesters;

//@@author nathanaelseen

/**
 * Parses input arguments and creates a new CourseAddCommand object.
 */
public class CourseAddCommandParser extends CourseCommandParser implements Parser<CourseAddCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the CourseAddCommand
     * and returns an CourseAddCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public CourseAddCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
            ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_SEMESTER);

        /*
         * If all arguments in the command are empty; i.e, 'course add', and nothing else (except preambles), show
         * the help message for this command
         */
        if (argMultimap.isEmpty(false)) {
            throw new ParseException(String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT,
                MESSAGE_COURSE_ADD_HELP));
        }

        if (!ParserUtil.arePrefixesPresent(argMultimap, PREFIX_NAME, PREFIX_SEMESTER)) {
            throw new ParseException(String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT,
                MESSAGE_COURSE_NOT_ADDED));
        }

        /*
         * Parsing and setting the name of our new course
         */
        Optional<Name> name = parseName(argMultimap.getValue(PREFIX_NAME).get());

        /*
         * A newly created course has no (graded) modules added to it yet, thus, it does not make sense to set any
         * cap, even 0, so we're setting it to optional here.
         */
        Optional<Cap> cap = Optional.empty();

        /*
         * Similarly,a newly created course has no requirements tagged to it and hence it does not make sense to talk
         * about credits (required or fulfilled), hence we set it to Optional here
         */
        Optional<Credits> credits = Optional.empty();

        /*
         * A newly created course has no semesters added to it yet as different students have different length of study.
         * Hence, it does not make sense to set any semester, so it is set to Optional.
         */
        //Optional<Semesters> semesters = Optional.empty();
        Optional<Semesters> semesters = parseSemesters(argMultimap.getValue(PREFIX_SEMESTER).get());

        CourseInfo courseInfo = new CourseInfo(name, cap, credits, semesters);

        return new CourseAddCommand(courseInfo);
    }
}
