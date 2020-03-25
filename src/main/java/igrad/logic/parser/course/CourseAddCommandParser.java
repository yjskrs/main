package igrad.logic.parser.course;

import static igrad.logic.parser.CliSyntax.PREFIX_NAME;
import static java.util.Objects.requireNonNull;

import java.util.Optional;
import java.util.stream.Stream;

import igrad.commons.core.Messages;
import igrad.logic.commands.course.CourseAddCommand;
import igrad.logic.commands.module.ModuleAddCommand;
import igrad.logic.parser.ArgumentMultimap;
import igrad.logic.parser.ArgumentTokenizer;
import igrad.logic.parser.CourseCommandParser;
import igrad.logic.parser.Parser;
import igrad.logic.parser.Prefix;
import igrad.logic.parser.exceptions.ParseException;
import igrad.model.course.CourseInfo;
import igrad.model.course.Name;

/**
 * Parses input arguments and creates a new CourseAddCommand object.
 */
public class CourseAddCommandParser extends CourseCommandParser implements Parser<CourseAddCommand> {

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

    /**
     * Parses a {@code String name} into a {@code Name}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code title} is invalid.
     */
    public static Name parseName(String name) throws ParseException {
        requireNonNull(name);
        String trimmedName = name.trim();

        if (!Name.isValidName(trimmedName)) {
            throw new ParseException(igrad.model.module.Title.MESSAGE_CONSTRAINTS);
        }
        return new Name(trimmedName);
    }

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
            throw new ParseException(String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT,
                ModuleAddCommand.MESSAGE_USAGE));
        }

        Name name = parseName(argMultimap.getValue(PREFIX_NAME).get());

        CourseInfo courseInfo = new CourseInfo(Optional.of(name));

        return new CourseAddCommand(courseInfo);
    }
}
