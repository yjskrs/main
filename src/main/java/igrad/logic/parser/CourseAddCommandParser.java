package igrad.logic.parser;

import java.util.Set;
import java.util.stream.Stream;

import igrad.commons.core.Messages;
import igrad.logic.commands.AddCommand;
import igrad.logic.commands.CourseAddCommand;
import igrad.logic.parser.exceptions.ParseException;
import igrad.model.course.Course;
import igrad.model.course.Name;
import igrad.model.module.Credits;
import igrad.model.module.Memo;
import igrad.model.module.Module;
import igrad.model.module.ModuleCode;
import igrad.model.module.Semester;
import igrad.model.module.Title;
import igrad.model.tag.Tag;
import igrad.model.module.Description;

import static igrad.logic.parser.CliSyntax.*;

/**
 * Parses input arguments and creates a new AddCommand object.
 */
public class CourseAddCommandParser implements Parser<CourseAddCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the CourseAddCommand
     * and returns an CourseAddCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public CourseAddCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME);

        if (!arePrefixesPresent(argMultimap, PREFIX_NAME)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        }

        Name name = ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get());

        Course course = new Course(name);

        return new CourseAddCommand(course);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
