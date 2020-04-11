package igrad.logic.parser.module;

//@@author nathanaelseen

import static igrad.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static igrad.commons.core.Messages.MESSAGE_SPECIFIER_INVALID;
import static igrad.commons.core.Messages.MESSAGE_SPECIFIER_NOT_SPECIFIED;
import static igrad.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static igrad.logic.commands.module.ModuleCommandTestUtil.INVALID_MODULE_CODE;
import static igrad.logic.commands.module.ModuleCommandTestUtil.INVALID_MODULE_GRADE_DESC;
import static igrad.logic.commands.module.ModuleCommandTestUtil.INVALID_MODULE_SEMESTER_DESC;
import static igrad.logic.commands.module.ModuleCommandTestUtil.MODULE_GRADE_DESC_CS1101S;
import static igrad.logic.commands.module.ModuleCommandTestUtil.MODULE_SEMESTER_DESC_CS1101S;
import static igrad.logic.commands.module.ModuleCommandTestUtil.VALID_MODULE_CODE_CS1101S;
import static igrad.logic.commands.module.ModuleCommandTestUtil.VALID_MODULE_GRADE_CS1101S;
import static igrad.logic.commands.module.ModuleCommandTestUtil.VALID_MODULE_SEMESTER_CS1101S;
import static igrad.logic.commands.module.ModuleDoneCommand.EditModuleDescriptor;
import static igrad.logic.commands.module.ModuleDoneCommand.MESSAGE_MODULE_DONE_HELP;
import static igrad.logic.commands.module.ModuleDoneCommand.MESSAGE_MODULE_NOT_EDITED;
import static igrad.logic.parser.CliSyntax.PREFIX_CREDITS;
import static igrad.logic.parser.CliSyntax.PREFIX_GRADE;
import static igrad.logic.parser.CommandParserTestUtil.assertParseFailure;
import static igrad.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import igrad.logic.commands.module.ModuleDoneCommand;
import igrad.model.module.Grade;
import igrad.model.module.ModuleCode;
import igrad.model.module.Semester;
import igrad.testutil.EditModuleDescriptorBuilder2;

public class ModuleDoneCommandParserTest {
    private static final String INVALID_COMMAND_FORMAT =
        String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_MODULE_DONE_HELP);
    private static final String MISSING_SPECIFIER =
        String.format(MESSAGE_SPECIFIER_NOT_SPECIFIED, ModuleCode.MESSAGE_CONSTRAINTS);
    private static final String INVALID_SPECIFIER =
        String.format(MESSAGE_SPECIFIER_INVALID, ModuleCode.MESSAGE_CONSTRAINTS);
    private static final String ARGUMENTS_NOT_SPECIFIED = MESSAGE_MODULE_NOT_EDITED;
    private static final String INVALID_GRADE_FORMAT = Grade.MESSAGE_CONSTRAINTS;
    private static final String INVALID_SEMESTER_FORMAT = Semester.MESSAGE_CONSTRAINTS;

    private ModuleDoneCommandParser parser = new ModuleDoneCommandParser();

    @Test
    public void parse_missingSpecifierOrMissingArguments_failure() {
        String input;

        // missing specifier and arguments:

        // 'module done'
        input = "";
        assertParseFailure(parser, input, INVALID_COMMAND_FORMAT); // just 'module done'

        // missing specifier only:

        // 'module done g/A'
        input = MODULE_GRADE_DESC_CS1101S;
        assertParseFailure(parser, input, MISSING_SPECIFIER); // no specifier

        // missing (mandatory) arguments only (i.e; the grade):

        // 'module done CS1101S'
        input = VALID_MODULE_CODE_CS1101S;
        assertParseFailure(parser, input, ARGUMENTS_NOT_SPECIFIED); // no arguments (module codes)

        // 'module done CS1101S g/'
        input = VALID_MODULE_CODE_CS1101S + " " + PREFIX_GRADE;
        assertParseFailure(parser, input, ARGUMENTS_NOT_SPECIFIED); // one empty string module code

        // 'module done CS1101S s/Y1S1'
        input = VALID_MODULE_CODE_CS1101S + MODULE_SEMESTER_DESC_CS1101S;
        assertParseFailure(parser, input, ARGUMENTS_NOT_SPECIFIED); // semesters (optional) provided but not module code
    }

    @Test
    public void parse_invalidSpecifierOrInvalidArguments_failure() {
        String input;

        // invalid specifier:

        // 'module done CS2040S&'
        input = INVALID_MODULE_CODE;
        assertParseFailure(parser, input, INVALID_SPECIFIER);

        // wrong start prefix, causing it to be interpreted as (invalid) specifier:

        // 'module done CS1101S u/
        input = VALID_MODULE_CODE_CS1101S + PREFIX_CREDITS;
        assertParseFailure(parser, input, INVALID_SPECIFIER);

        // wrong start prefix, but subsequent correct prefix, but nonetheless same as case above:

        // 'module done CS1101S u/ g/CS1101S'
        input = VALID_MODULE_CODE_CS1101S + PREFIX_CREDITS + MODULE_GRADE_DESC_CS1101S;
        assertParseFailure(parser, input, INVALID_SPECIFIER);

        // invalid (mandatory) arguments, i.e, invalid module grade:

        // 'module done CS1101S g/A*'
        input = VALID_MODULE_CODE_CS1101S + INVALID_MODULE_GRADE_DESC;
        assertParseFailure(parser, input, INVALID_GRADE_FORMAT);

        // invalid (optoinal) arguments, i.e, invalid module semester:

        // 'module done CS1101S g/A s/4%'
        input = VALID_MODULE_CODE_CS1101S + MODULE_GRADE_DESC_CS1101S + INVALID_MODULE_SEMESTER_DESC;
        assertParseFailure(parser, input, INVALID_SEMESTER_FORMAT);
    }

    @Test
    public void parse_validAndPresentSpecifierAndArguments_success() {
        String input;
        EditModuleDescriptor descriptor;
        ModuleCode moduleCode = new ModuleCode(VALID_MODULE_CODE_CS1101S);

        // normal module done (without semester):

        // 'module done CS1101S g/A'
        input = VALID_MODULE_CODE_CS1101S + MODULE_GRADE_DESC_CS1101S;
        descriptor = new EditModuleDescriptorBuilder2()
            .withGrade(VALID_MODULE_GRADE_CS1101S)
            .build();
        assertParseSuccess(parser, input, new ModuleDoneCommand(moduleCode, descriptor));

        // with white space preamble (without semester):

        // 'module done       CS1101S g/A'
        input = PREAMBLE_WHITESPACE + VALID_MODULE_CODE_CS1101S + MODULE_GRADE_DESC_CS1101S;

        descriptor = new EditModuleDescriptorBuilder2()
            .withGrade(VALID_MODULE_GRADE_CS1101S)
            .build();
        assertParseSuccess(parser, input, new ModuleDoneCommand(moduleCode, descriptor));

        // with optional fields (semester):

        // 'module done CS1101S g/A s/Y1S1'
        input = VALID_MODULE_CODE_CS1101S + MODULE_GRADE_DESC_CS1101S + MODULE_SEMESTER_DESC_CS1101S;
        descriptor = new EditModuleDescriptorBuilder2()
            .withGrade(VALID_MODULE_GRADE_CS1101S)
            .withSemester(VALID_MODULE_SEMESTER_CS1101S)
            .build();
        assertParseSuccess(parser, input, new ModuleDoneCommand(moduleCode, descriptor));

        // with white space preamble (with optional fields; semester):

        // 'module done      CS1101S g/A s/Y1S1'
        input = PREAMBLE_WHITESPACE + VALID_MODULE_CODE_CS1101S + MODULE_GRADE_DESC_CS1101S
            + MODULE_SEMESTER_DESC_CS1101S;
        descriptor = new EditModuleDescriptorBuilder2()
            .withGrade(VALID_MODULE_GRADE_CS1101S)
            .withSemester(VALID_MODULE_SEMESTER_CS1101S)
            .build();
        assertParseSuccess(parser, input, new ModuleDoneCommand(moduleCode, descriptor));
    }
}
