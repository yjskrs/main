package igrad.logic.parser.module;

import igrad.logic.commands.course.CourseAchieveCommand;
import igrad.logic.parser.Parser;
import igrad.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new ModuleDeleteCommand object.
 */

// TODO (Teri): Please complete this class, you may refer to how ModuleDeleteParser.java is done
public class CourseAchieveCommandParser implements Parser<CourseAchieveCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the ModuleDeleteCommand
     * and returns a ModuleDeleteCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public CourseAchieveCommand parse(String args) throws ParseException {
        return null;
    }

}
