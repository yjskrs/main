package igrad.logic.parser.course;

import static igrad.logic.commands.course.CourseEditCommand.MESSAGE_COURSE_EDIT_HELP;
import static igrad.logic.parser.CliSyntax.PREFIX_NAME;
import static igrad.logic.parser.CliSyntax.PREFIX_SEMESTER;
import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.util.Optional;

import igrad.commons.core.Messages;
import igrad.logic.commands.course.CourseEditCommand;
import igrad.logic.commands.course.CourseEditCommand.EditCourseDescriptor;
import igrad.logic.parser.ArgumentMultimap;
import igrad.logic.parser.ArgumentTokenizer;
import igrad.logic.parser.Parser;
import igrad.logic.parser.exceptions.ParseException;
import igrad.model.course.Name;
import igrad.model.course.Semesters;
import igrad.services.exceptions.ServiceException;

//@@author teriaiw

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
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_SEMESTER);

        /*
         * Course is special, unlike Module and Requirement, it does not need a specifier, because there
         * is only one course in the system. Hence the command syntax for course edit, goes like this;
         * course edit n/NEW_COURSE_NAME.
         * Hence we don't have to parse for a specifier as there's none.
         */

        if (argMultimap.isEmpty(true)) {
            throw new ParseException(String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_COURSE_EDIT_HELP));
        }

        EditCourseDescriptor editCourseDescriptor = parseEditedCourse(argMultimap);

        return new CourseEditCommand(editCourseDescriptor);
    }

    /**
     * Parses grade and/or semesters from {@code argMultimap} into {@code EditCourseDescriptor}.
     *
     * @throws ParseException If user input does not conform to the expected format.
     */
    private EditCourseDescriptor parseEditedCourse(ArgumentMultimap argMultimap) throws ParseException {
        EditCourseDescriptor editCourseDescriptor = new EditCourseDescriptor();

        Optional<String> nameString = argMultimap.getValue(PREFIX_NAME);
        Optional<String> semestersString = argMultimap.getValue(PREFIX_SEMESTER);

        // If neither name nor semesters is specified, we flag an error to the user
        if ((nameString.isEmpty() || nameString.get().isEmpty())
            && (semestersString.isEmpty() || semestersString.get().isEmpty())) {
            throw new ParseException(String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_COURSE_EDIT_HELP));
        }

        // If name is specified, add it into our editCourseDescriptor
        if (nameString.isPresent()) {
            Optional<Name> name = parseName(nameString.get());
            editCourseDescriptor.setName(name);
        }

        // If semesters is specified, add it into our editCourseDescriptor
        if (semestersString.isPresent()) {
            Optional<Semesters> semesters = parseSemesters(semestersString.get());
            editCourseDescriptor.setSemesters(semesters);
        }

        return editCourseDescriptor;
    }
}
