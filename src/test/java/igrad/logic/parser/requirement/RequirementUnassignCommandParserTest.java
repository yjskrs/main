package igrad.logic.parser.requirement;

//@@author nathanaelseen

import static igrad.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static igrad.commons.core.Messages.MESSAGE_SPECIFIER_INVALID;
import static igrad.commons.core.Messages.MESSAGE_SPECIFIER_NOT_SPECIFIED;
import static igrad.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static igrad.logic.commands.module.ModuleCommandTestUtil.INVALID_MODULE_CODE_DESC;
import static igrad.logic.commands.module.ModuleCommandTestUtil.MODULE_MODULE_CODE_DESC_CS1101S;
import static igrad.logic.commands.module.ModuleCommandTestUtil.MODULE_MODULE_CODE_DESC_CS2100;
import static igrad.logic.commands.module.ModuleCommandTestUtil.VALID_MODULE_CODE_CS1101S;
import static igrad.logic.commands.module.ModuleCommandTestUtil.VALID_MODULE_CODE_CS2100;
import static igrad.logic.commands.requirement.RequirementCommandTestUtil.INVALID_REQ_CODE_DECIMAL;
import static igrad.logic.commands.requirement.RequirementCommandTestUtil.INVALID_REQ_CODE_SYMBOL;
import static igrad.logic.commands.requirement.RequirementCommandTestUtil.VALID_REQ_CODE_UE;
import static igrad.logic.commands.requirement.RequirementUnassignCommand.MESSAGE_REQUIREMENT_NO_MODULES;
import static igrad.logic.commands.requirement.RequirementUnassignCommand.MESSAGE_REQUIREMENT_UNASSIGN_HELP;
import static igrad.logic.parser.CliSyntax.PREFIX_CREDITS;
import static igrad.logic.parser.CliSyntax.PREFIX_MODULE_CODE;
import static igrad.logic.parser.CommandParserTestUtil.assertParseFailure;
import static igrad.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import igrad.logic.commands.requirement.RequirementUnassignCommand;
import igrad.model.module.ModuleCode;
import igrad.model.requirement.RequirementCode;

public class RequirementUnassignCommandParserTest {
    private static final String INVALID_COMMAND_FORMAT =
        String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_REQUIREMENT_UNASSIGN_HELP);
    private static final String MISSING_SPECIFIER =
        String.format(MESSAGE_SPECIFIER_NOT_SPECIFIED, RequirementCode.MESSAGE_CONSTRAINTS);
    private static final String INVALID_SPECIFIER =
        String.format(MESSAGE_SPECIFIER_INVALID, RequirementCode.MESSAGE_CONSTRAINTS);
    private static final String INVALID_MODULE_CODE_FORMAT = ModuleCode.MESSAGE_CONSTRAINTS;
    private static final String ARGUMENTS_NOT_SPECIFIED = MESSAGE_REQUIREMENT_NO_MODULES;

    private RequirementUnassignCommandParser parser = new RequirementUnassignCommandParser();

    @Test
    public void parse_missingSpecifierOrMissingArguments_failure() {
        String input;

        // missing specifier and arguments:

        // 'requirement unassign'
        input = "";
        assertParseFailure(parser, input, INVALID_COMMAND_FORMAT); // just "requirement unassign"

        // missing specifier only:

        // 'requirement unassign n/CS1101S'
        input = MODULE_MODULE_CODE_DESC_CS1101S;
        assertParseFailure(parser, input, MISSING_SPECIFIER); // no specifier

        // missing arguments only (i.e; module codes, there must be at least one module code):

        // 'requirement unassign UE0'
        input = VALID_REQ_CODE_UE;
        assertParseFailure(parser, input, ARGUMENTS_NOT_SPECIFIED); // no arguments (module codes)

        // 'requirement unassign UE0 n/'
        input = VALID_REQ_CODE_UE + " " + PREFIX_MODULE_CODE;
        assertParseFailure(parser, input, ARGUMENTS_NOT_SPECIFIED); // one empty string module code
    }

    @Test
    public void parse_invalidSpecifierOrInvalidArguments_failure() {
        String input;

        // invalid specifier:

        // 'requirement unassign RE1.0'
        input = INVALID_REQ_CODE_DECIMAL;
        assertParseFailure(parser, input, INVALID_SPECIFIER);

        // 'requirement unassign RE<'
        input = INVALID_REQ_CODE_SYMBOL;
        assertParseFailure(parser, input, INVALID_SPECIFIER);

        // wrong start prefix, causing it to be interpreted as (invalid) specifier:

        // 'requirement unassign UE0 u/
        input = VALID_REQ_CODE_UE + PREFIX_CREDITS;
        assertParseFailure(parser, input, INVALID_SPECIFIER);

        // wrong start prefix, but subsequent correct prefix, but nonetheless same as case above:

        // 'requirement unassign UE0 u/ n/CS1101S'
        input = VALID_REQ_CODE_UE + PREFIX_CREDITS + MODULE_MODULE_CODE_DESC_CS1101S;
        assertParseFailure(parser, input, INVALID_SPECIFIER);

        // invalid arguments (only one module code specified), i.e, invalid module codes:

        // 'requirement unassign UE0 n/CS2040S&'
        input = VALID_REQ_CODE_UE + INVALID_MODULE_CODE_DESC;
        assertParseFailure(parser, input, INVALID_MODULE_CODE_FORMAT);

        // invalid arguments, (two module codes specified, first one valid, second one invalid):

        // 'requirement unassign UE0 n/CS1101S n/CS2040S&'
        input = VALID_REQ_CODE_UE + MODULE_MODULE_CODE_DESC_CS1101S + INVALID_MODULE_CODE_DESC;
        assertParseFailure(parser, input, INVALID_MODULE_CODE_FORMAT);
    }

    @Test
    public void parse_validAndPresentSpecifierAndArguments_success() {
        String input;
        RequirementCode requirementCode = new RequirementCode(VALID_REQ_CODE_UE);

        List<ModuleCode> moduleCodes = new ArrayList<ModuleCode>();

        moduleCodes.add(new ModuleCode(VALID_MODULE_CODE_CS1101S));

        // 1 module only:

        // 'requirement unassign UE0 n/CS1101S'
        input = VALID_REQ_CODE_UE + MODULE_MODULE_CODE_DESC_CS1101S;
        assertParseSuccess(parser, input, new RequirementUnassignCommand(requirementCode, moduleCodes));

        // 1 module only, with white space preamble:

        // 'requirement unassign       UE0 n/CS1101S'
        input = PREAMBLE_WHITESPACE + VALID_REQ_CODE_UE + MODULE_MODULE_CODE_DESC_CS1101S;
        assertParseSuccess(parser, input, new RequirementUnassignCommand(requirementCode, moduleCodes));

        // 2 modules (or more)

        // 'requirement unassign UE0 n/CS1101S n/CS2100'
        moduleCodes.add(new ModuleCode(VALID_MODULE_CODE_CS2100));
        input = VALID_REQ_CODE_UE + MODULE_MODULE_CODE_DESC_CS1101S + MODULE_MODULE_CODE_DESC_CS2100;
        assertParseSuccess(parser, input, new RequirementUnassignCommand(requirementCode, moduleCodes));

        // 2 modules (or more), with white space preamble

        // 'requirement unassign     UE0 n/CS1101S n/CS2100'
        input = PREAMBLE_WHITESPACE + VALID_REQ_CODE_UE + MODULE_MODULE_CODE_DESC_CS1101S
            + MODULE_MODULE_CODE_DESC_CS2100;
        assertParseSuccess(parser, input, new RequirementUnassignCommand(requirementCode, moduleCodes));
    }
}
