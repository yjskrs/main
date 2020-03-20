package igrad.logic.parser;

import java.io.IOException;

import igrad.logic.commands.CourseEditCommand;
import igrad.logic.parser.exceptions.ParseException;
import igrad.services.exceptions.ServiceException;

/*
 * TODO (Teri): Please refactor CourseAddCommandParser, and CourseEditCommandParser,
 *  into the logic.parser.course package (create a new one)
 */

/**
 * Parses input arguments and creates a new CourseEditCommand object.
 */
public class CourseEditCommandParser extends CourseCommandParser implements Parser<CourseEditCommand> {
    /*
     * TODO (Teri): Your main task is to implement the course edit COURSE_NAME n/NEW_COURSE_NAME command,
     *  e.g, course edit n/Business (changes the name of the current course, say 'Comp Sci', to 'Business').
     *  Now, how would one do that? Well, let's start from the Parsers. We need to get the parsers to recognise the
     *  'course edit' command.
     *  The code is very similar to RequirementEditCommandParser.java (parse() method), you can use that
     *  for reference. But there's a slight twist here. Notice requirement is something like this;
     *  requirement CURRENT_REQ_NAME n/NEW_REQ_NAME u/NEW_REQ_MCs?
     *  Since there is only one course in the system, the course edit command is only;
     *  course edit n/NEW_COURSE_NAME,
     *  and you don't have to specify the CURRENT_COURSE_NAME that you want to modify.
     *  (Hint: no need to use Specify like in Requirement, but instead use what you have in model)
     */

    /**
     * Parses the given string of arguments {@code args} in the context of the
     * CourseEditCommand and returns a CourseEditCommand object for execution.
     *
     * @throws ParseException If the user input does not conform the expected format.
     */
    @Override
    public CourseEditCommand parse(String args) throws ParseException, IOException, ServiceException {
        /*
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_NAME);

        Name name;
        ...
        (Hint: an if-block here to check if the n/ tag is in the command)
        ...
        name = ...
        return new CourseEditCommand(name);
         */
        /*
         * TODO (Teri): Congrats on successfully implementing the parser! Now's the CourseEditCommand object
         *  (which this parse method returns). You'll need to write some code there as well!
         */

        // Please comment this out after implementation
        return null;
    }
}
