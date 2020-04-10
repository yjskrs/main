package igrad.logic.parser.module;

//@@author nathanaelseen

import static igrad.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static igrad.commons.core.Messages.MESSAGE_SPECIFIER_INVALID;
import static igrad.commons.core.Messages.MESSAGE_SPECIFIER_NOT_SPECIFIED;
import static igrad.logic.commands.module.ModuleCommandTestUtil.MODULE_GRADE_DESC_CS1101S;
import static igrad.logic.commands.module.ModuleCommandTestUtil.VALID_MODULE_CODE_CS1101S;
import static igrad.logic.commands.module.ModuleDoneCommand.MESSAGE_MODULE_DONE_HELP;
import static igrad.logic.commands.module.ModuleDoneCommand.MESSAGE_MODULE_NOT_EDITED;
import static igrad.logic.parser.CliSyntax.PREFIX_GRADE;
import static igrad.logic.parser.CommandParserTestUtil.assertParseFailure;

import org.junit.jupiter.api.Test;

import igrad.model.module.Grade;
import igrad.model.module.ModuleCode;
import igrad.model.module.Semester;

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

        // missing specifier and arguments
        // 'module done'
        input = "";
        assertParseFailure(parser, input, INVALID_COMMAND_FORMAT); // just 'module done'

        // missing specifier only
        // 'module done g/A'
        input = MODULE_GRADE_DESC_CS1101S;
        assertParseFailure(parser, input, MISSING_SPECIFIER); // no specifier

        // missing (mandatory) arguments only (i.e; the grade)
        // 'module done CS1101S'
        input = VALID_MODULE_CODE_CS1101S;
        assertParseFailure(parser, input, ARGUMENTS_NOT_SPECIFIED); // no arguments (module codes)

        // 'module done CS1101S g/'
        input = VALID_MODULE_CODE_CS1101S + " " + PREFIX_GRADE;
        assertParseFailure(parser, input, ARGUMENTS_NOT_SPECIFIED); // one empty string module code
    }
}
