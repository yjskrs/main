package igrad.logic.parser.course;

//@@author nathanaelseen

import static igrad.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static igrad.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static igrad.logic.commands.course.CourseCommandTestUtil.COURSE_NAME_DESC_BCOMPSCI;
import static igrad.logic.commands.course.CourseCommandTestUtil.COURSE_SEMESTERS_DESC_BCOMPSCI;
import static igrad.logic.commands.course.CourseCommandTestUtil.INVALID_COURSE_SEMESTERS_DESC;
import static igrad.logic.commands.course.CourseCommandTestUtil.VALID_COURSE_NAME_BCOMPSCI;
import static igrad.logic.commands.course.CourseCommandTestUtil.VALID_COURSE_SEMESTERS_BCOMPSCI;
import static igrad.logic.commands.course.CourseEditCommand.EditCourseDescriptor;
import static igrad.logic.commands.course.CourseEditCommand.MESSAGE_COURSE_EDIT_HELP;
import static igrad.logic.parser.CliSyntax.PREFIX_NAME;
import static igrad.logic.parser.CliSyntax.PREFIX_SEMESTER;
import static igrad.logic.parser.CommandParserTestUtil.assertParseFailure;
import static igrad.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import igrad.logic.commands.course.CourseEditCommand;
import igrad.model.course.Name;
import igrad.model.course.Semesters;
import igrad.testutil.EditCourseDescriptorBuilder;

public class CourseEditCommandParserTest {
    private static final String INVALID_COMMAND_FORMAT =
        String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_COURSE_EDIT_HELP);
    private static final String ARGUMENTS_NOT_SPECIFIED =
        String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_COURSE_EDIT_HELP);
    private static final String INVALID_NAME_FORMAT = Name.MESSAGE_CONSTRAINTS;
    private static final String INVALID_SEMESTERS_FORMAT = Semesters.MESSAGE_CONSTRAINTS;

    private CourseEditCommandParser parser = new CourseEditCommandParser();

    @Test
    public void parse_missingArguments_failure() {
        String input;

        // at least one course argument; course name and/or total semesters must be present:

        // 'course edit'
        input = "";
        assertParseFailure(parser, input, INVALID_COMMAND_FORMAT); // just 'course edit'


        // 'course edit n/'
        input = " " + PREFIX_NAME;
        assertParseFailure(parser, input, ARGUMENTS_NOT_SPECIFIED); // empty string; "", course name argument

        // 'course edit s/'
        input = " " + PREFIX_SEMESTER;
        assertParseFailure(parser, input, ARGUMENTS_NOT_SPECIFIED); // empty string; "", course semesters argument
    }

    @Test
    public void parse_invalidArguments_failure() {
        String input;

        // invalid (mandatory) arguments, i.e, invalid course name and course semesters:

        // 'course edit n/ Bachelor of Mathematics s/3'
        /*input = INVALID_COURSE_NAME_DESC + COURSE_SEMESTERS_DESC_BCOMPSCI;
        assertParseFailure(parser, input, INVALID_NAME_FORMAT); // invalid course name*/

        // 'course edit n/Bachelor of Computer Science s/4<'
        input = COURSE_NAME_DESC_BCOMPSCI + INVALID_COURSE_SEMESTERS_DESC;
        assertParseFailure(parser, input, INVALID_SEMESTERS_FORMAT); // invalid course semesters
    }

    @Test
    public void parse_validAndPresentArguments_success() {
        String input;
        EditCourseDescriptor descriptor;

        // normal course edit, name field only:

        // 'course edit n/Bachelor of Computer Science'
        input = COURSE_NAME_DESC_BCOMPSCI;
        descriptor = new EditCourseDescriptorBuilder()
            .withName(VALID_COURSE_NAME_BCOMPSCI)
            .build();
        assertParseSuccess(parser, input, new CourseEditCommand(descriptor));

        // with whitespace preamble (name field only):

        // 'course edit       n/Bachelor of Computer Science'
        input = PREAMBLE_WHITESPACE + COURSE_NAME_DESC_BCOMPSCI;
        descriptor = new EditCourseDescriptorBuilder()
            .withName(VALID_COURSE_NAME_BCOMPSCI)
            .build();
        assertParseSuccess(parser, input, new CourseEditCommand(descriptor));

        // normal course edit, semesters field only:

        // 'course edit s/3'
        input = COURSE_SEMESTERS_DESC_BCOMPSCI;
        descriptor = new EditCourseDescriptorBuilder()
            .withSemesters(VALID_COURSE_SEMESTERS_BCOMPSCI)
            .build();
        assertParseSuccess(parser, input, new CourseEditCommand(descriptor));

        // with whitespace preamble (semesters field only):

        // 'course edit       s/3'
        input = PREAMBLE_WHITESPACE + COURSE_SEMESTERS_DESC_BCOMPSCI;
        descriptor = new EditCourseDescriptorBuilder()
            .withSemesters(VALID_COURSE_SEMESTERS_BCOMPSCI)
            .build();
        assertParseSuccess(parser, input, new CourseEditCommand(descriptor));

        // normal course edit, both name and semesters field:

        // 'course edit n/Bachelor of Computer Science s/3'
        input = COURSE_NAME_DESC_BCOMPSCI + COURSE_SEMESTERS_DESC_BCOMPSCI;
        descriptor = new EditCourseDescriptorBuilder()
            .withName(VALID_COURSE_NAME_BCOMPSCI)
            .withSemesters(VALID_COURSE_SEMESTERS_BCOMPSCI)
            .build();
        assertParseSuccess(parser, input, new CourseEditCommand(descriptor));

        // with whitespace preamble (both name and semesters field):

        // 'course edit       n/Bachelor of Computer Science s/3'
        input = PREAMBLE_WHITESPACE + COURSE_NAME_DESC_BCOMPSCI + COURSE_SEMESTERS_DESC_BCOMPSCI;
        descriptor = new EditCourseDescriptorBuilder()
            .withName(VALID_COURSE_NAME_BCOMPSCI)
            .withSemesters(VALID_COURSE_SEMESTERS_BCOMPSCI)
            .build();
        assertParseSuccess(parser, input, new CourseEditCommand(descriptor));
    }
}
