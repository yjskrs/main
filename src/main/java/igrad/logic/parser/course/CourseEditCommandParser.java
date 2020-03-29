package igrad.logic.parser.course;

import static igrad.logic.commands.course.CourseEditCommand.MESSAGE_COURSE_NOT_EDITED;
import static igrad.logic.parser.CliSyntax.PREFIX_NAME;
import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.util.Optional;

import igrad.logic.commands.course.CourseEditCommand;
import igrad.logic.parser.ArgumentMultimap;
import igrad.logic.parser.ArgumentTokenizer;
import igrad.logic.parser.CourseCommandParser;
import igrad.logic.parser.Parser;
import igrad.logic.parser.exceptions.ParseException;
import igrad.model.course.Name;
import igrad.services.exceptions.ServiceException;

/**
 * Parses input arguments and creates a new CourseEditCommand object.
 */
public class CourseEditCommandParser extends CourseCommandParser implements Parser<CourseEditCommand> {

    /**
     * Parses the given string of arguments {@code args} in the context of the
     * CourseEditCommand and returns a CourseEditCommand object for execution.
     *
     * @throws ParseException If the user input does not conform the expected format.
     */
    @Override
    public CourseEditCommand parse(String args) throws ParseException, IOException, ServiceException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_NAME);

        /*
         * Course is special, unlike Module and Requirement, it does not need a specifier, because there
         * is only one course in the system. Hence the command syntax for course edit, goes like this;
         * course edit n/NEW_COURSE_NAME
         */
        if (!argMultimap.getValue(PREFIX_NAME).isPresent()) {
            throw new ParseException(MESSAGE_COURSE_NOT_EDITED);
        }

        Optional<Name> name = parseName(argMultimap.getValue(PREFIX_NAME).get());

        return new CourseEditCommand(name);
    }
}
