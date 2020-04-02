package igrad.logic.parser.course;

import static igrad.logic.commands.course.CourseAddCommand.MESSAGE_COURSE_ADD_HELP;
import static igrad.logic.parser.CliSyntax.PREFIX_NAME;

import java.util.Optional;

import igrad.commons.core.Messages;
import igrad.logic.commands.course.CourseAddCommand;
import igrad.logic.parser.ArgumentMultimap;
import igrad.logic.parser.ArgumentTokenizer;
import igrad.logic.parser.CourseCommandParser;
import igrad.logic.parser.Parser;
import igrad.logic.parser.exceptions.ParseException;
import igrad.model.course.Cap;
import igrad.model.course.CourseInfo;
import igrad.model.course.Name;

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
            ArgumentTokenizer.tokenize(args, PREFIX_NAME);

        /*
         * If all arguments in the command are empty; i.e, 'course add', and nothing else (except preambles), show
         * the help message for this command
         */
        if (argMultimap.isEmpty(false)) {
            throw new ParseException(String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT,
                MESSAGE_COURSE_ADD_HELP));
        }

        /*
         * course add n/NAME
         *
         * We have that; NAME is a compulsory field, so we're just validating for its
         * presence in the below.
         *
         * But actually we don't need to validate for course name argument, as we did
         * in module and requirement;
         * if (!ParserUtil.arePrefixesPresent(argMultimap, PREFIX_NAME)) {
         *       throw new ParseException(String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT,
                    MESSAGE_NOT_ADDED));
         * }.
         * This is because course has only one field; name, and fulfilling the above condition;
         * where {@code argMultimap.isEmpty(false)} would automatically mean that the map has the
         * argument we need, hence we don't need to re-validate for its presence.
         * (Please read the Javadoc comments for {@code argMultimap.isEmpty(boolean checkPreamble)} for more
         * details, if you would like to know more.)
         */

        Optional<Name> name = parseName(argMultimap.getValue(PREFIX_NAME).get());

        /*
         * A newly created course has no cap since there are no modules added to the system yet.
         * Hence, we set cap to be {@code Optional.empty()}
         */
        Optional<Cap> cap = Optional.empty();

        CourseInfo courseInfo = new CourseInfo(name, cap);
        return new CourseAddCommand(courseInfo);
    }
}
