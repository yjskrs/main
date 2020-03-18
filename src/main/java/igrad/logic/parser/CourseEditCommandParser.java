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
public class CourseEditCommandParser implements Parser<CourseEditCommand> {
    /*
     * TODO (Teri): Your main task is to implement the course edit COURSE_NAME n/NEW_COURSE_NAME command,
     *  e.g, course edit Comp Sci n/Business.
     *  Now, how would one do that? Well, let's start from the Parsers. We need to get the parsers to recognise the
     *  'course edit' command.
     *  The code is very similar to RequirementEditCommandParser.java (parse() method), you can use that
     *  for reference.
     */
    /**
     * Parses the given string of arguments {@code args} in the context of the
     * CourseEditCommand and returns a CourseEditCommand object for execution.
     * @throws ParseException If the user input does not conform the expected format.
     */
    @Override
    public CourseEditCommand parse(String userInput) throws ParseException, IOException, ServiceException {
        return null;

        /*
         * TODO (Teri): Congrats on successfully implementing the parser! Now's the CourseEditCommand object
         *  (which this parse method returns). You'll need to write some code there as well!
         */
    }
}
