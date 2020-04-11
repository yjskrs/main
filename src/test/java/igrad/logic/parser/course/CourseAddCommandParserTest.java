package igrad.logic.parser.course;

//@@author nathanaelseen

import static igrad.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static igrad.logic.commands.course.CourseAddCommand.MESSAGE_COURSE_ADD_HELP;
import static igrad.logic.commands.course.CourseAddCommand.MESSAGE_COURSE_NOT_ADDED;
import static igrad.logic.commands.course.CourseCommandTestUtil.COURSE_NAME_DESC_BCOMPSCI;
import static igrad.logic.commands.course.CourseCommandTestUtil.COURSE_SEMESTERS_DESC_BCOMPSCI;
import static igrad.logic.commands.course.CourseCommandTestUtil.INVALID_COURSE_SEMESTERS_DESC;
import static igrad.logic.commands.course.CourseCommandTestUtil.VALID_COURSE_NAME_BCOMPSCI;
import static igrad.logic.commands.course.CourseCommandTestUtil.VALID_COURSE_SEMESTERS_BCOMPSCI;
import static igrad.logic.parser.CliSyntax.PREFIX_NAME;
import static igrad.logic.parser.CliSyntax.PREFIX_SEMESTER;
import static igrad.logic.parser.CommandParserTestUtil.assertParseFailure;
import static igrad.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import igrad.logic.commands.course.CourseAddCommand;
import igrad.model.course.CourseInfo;
import igrad.model.course.Name;
import igrad.model.course.Semesters;
import igrad.testutil.CourseInfoBuilder;

public class CourseAddCommandParserTest {
    private static final String INVALID_COMMAND_FORMAT =
        String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_COURSE_ADD_HELP);
    private static final String ARGUMENTS_NOT_SPECIFIED =
        String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_COURSE_NOT_ADDED);
    private static final String INVALID_NAME_FORMAT = Name.MESSAGE_CONSTRAINTS;
    private static final String INVALID_SEMESTERS_FORMAT = Semesters.MESSAGE_CONSTRAINTS;

    private CourseAddCommandParser parser = new CourseAddCommandParser();

    @Test
    public void parse_missingArguments_failure() {
        String input;

        // missing (mandatory) arguments only (i.e; the course name and total semesters):

        // 'course add'
        input = "";
        assertParseFailure(parser, input, INVALID_COMMAND_FORMAT); // just 'course add'


        // 'course add n/'
        input = " " + PREFIX_NAME;
        assertParseFailure(parser, input, ARGUMENTS_NOT_SPECIFIED); // empty string; "", course name argument

        // 'course add n/Bachelor of Computer Science'
        input = VALID_COURSE_NAME_BCOMPSCI + COURSE_NAME_DESC_BCOMPSCI;
        assertParseFailure(parser, input, ARGUMENTS_NOT_SPECIFIED); // missing semester argument

        // 'course add n/Bachelor of Computer Science s/'
        input = VALID_COURSE_NAME_BCOMPSCI + COURSE_NAME_DESC_BCOMPSCI + " " + PREFIX_SEMESTER;
        assertParseFailure(parser, input, ARGUMENTS_NOT_SPECIFIED); // empty string; "", course semesters argument
    }

    @Test
    public void parse_invalidArguments_failure() {
        String input;

        // invalid (mandatory) arguments, i.e, invalid course name and course semesters:

        // 'course add n/ Bachelor of Mathematics s/3'
        /*input = INVALID_COURSE_NAME_DESC + COURSE_SEMESTERS_DESC_BCOMPSCI;
        assertParseFailure(parser, input, INVALID_NAME_FORMAT); // invalid course name*/

        // 'course add n/Bachelor of Computer Science s/4<'
        input = COURSE_NAME_DESC_BCOMPSCI + INVALID_COURSE_SEMESTERS_DESC;
        assertParseFailure(parser, input, INVALID_SEMESTERS_FORMAT); // invalid course semesters
    }

    @Test
    public void parse_validAndPresentArguments_success() {
        String input;
        CourseInfo expectedCourse;

        // normal course add (with all mandatory arguments present and valid):

        // 'course add n/Bachelor of Computer Science s/3'
        input = COURSE_NAME_DESC_BCOMPSCI + COURSE_SEMESTERS_DESC_BCOMPSCI;
        expectedCourse = new CourseInfoBuilder()
            .withName(VALID_COURSE_NAME_BCOMPSCI)
            .withSemesters(VALID_COURSE_SEMESTERS_BCOMPSCI)
            .withCapOptional()
            .withCreditsOptional()
            .build();

        assertParseSuccess(parser, input, new CourseAddCommand(expectedCourse));
    }
}
