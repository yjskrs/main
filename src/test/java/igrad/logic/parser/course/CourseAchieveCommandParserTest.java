package igrad.logic.parser.course;

//@@author teriaiw

import static igrad.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static igrad.logic.commands.course.CourseAchieveCommand.MESSAGE_COURSE_ACHIEVE_HELP;
import static igrad.logic.commands.course.CourseCommandTestUtil.INVALID_CAP_DESC;
import static igrad.logic.commands.course.CourseCommandTestUtil.VALID_CAP_DESC;
import static igrad.logic.parser.CliSyntax.PREFIX_NAME;
import static igrad.logic.parser.CommandParserTestUtil.assertParseFailure;
import static igrad.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Optional;

import org.junit.jupiter.api.Test;

import igrad.logic.commands.course.CourseAchieveCommand;
import igrad.model.course.Cap;

public class CourseAchieveCommandParserTest {
    private static final String INVALID_COMMAND_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_COURSE_ACHIEVE_HELP);
    private static final String ARGUMENTS_NOT_SPECIFIED =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_COURSE_ACHIEVE_HELP);
    private static final String INVALID_CAP = Cap.MESSAGE_CONSTRAINTS;

    private CourseAchieveCommandParser parser = new CourseAchieveCommandParser();

    @Test
    public void parse_missingArguments_failure() {
        String input;

        //'course achieve'
        input = "";
        assertParseFailure(parser, input, INVALID_COMMAND_FORMAT); //just 'course achieve'

        // 'course achieve c/'
        input = " " + PREFIX_NAME;
        assertParseFailure(parser, input, ARGUMENTS_NOT_SPECIFIED); // empty string; "", cap to achieve argument
    }

    @Test
    public void parse_invalidArguments_failure() {
        String input;

        // 'course achieve s/6'
        input = INVALID_CAP_DESC;
        assertParseFailure(parser, input, INVALID_CAP); // invalid cap
    }

    @Test
    public void parse_validAndPresentArguments_success() {
        String input;
        Optional<Cap> cap;

        // 'course achieve s/4.5'
        input = VALID_CAP_DESC;
        cap = Optional.of(new Cap("4.5"));

        assertParseSuccess(parser, input, new CourseAchieveCommand(cap));
    }
}
