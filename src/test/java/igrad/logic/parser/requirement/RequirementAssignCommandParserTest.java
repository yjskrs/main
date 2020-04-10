package igrad.logic.parser.requirement;

//@@author nathanaelseen

import static igrad.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static igrad.commons.core.Messages.MESSAGE_SPECIFIER_INVALID;
import static igrad.commons.core.Messages.MESSAGE_SPECIFIER_NOT_SPECIFIED;
import static igrad.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static igrad.logic.commands.module.ModuleCommandTestUtil.MODULE_MODULE_CODE_DESC_CS1101S;
import static igrad.logic.commands.module.ModuleCommandTestUtil.MODULE_MODULE_CODE_DESC_CS2100;
import static igrad.logic.commands.module.ModuleCommandTestUtil.VALID_MODULE_CODE_CS1101S;
import static igrad.logic.commands.module.ModuleCommandTestUtil.VALID_MODULE_CODE_CS2100;
import static igrad.logic.commands.requirement.RequirementAssignCommand.MESSAGE_REQUIREMENT_ASSIGN_HELP;
import static igrad.logic.commands.requirement.RequirementCommandTestUtil.VALID_REQ_CODE_UE;
import static igrad.logic.parser.CommandParserTestUtil.assertParseFailure;
import static igrad.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import igrad.logic.commands.requirement.RequirementAssignCommand;
import igrad.model.module.ModuleCode;
import igrad.model.requirement.RequirementCode;

public class RequirementAssignCommandParserTest {
    private static final String INVALID_COMMAND_FORMAT =
        String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_REQUIREMENT_ASSIGN_HELP);
    private static final String MISSING_SPECIFIER =
        String.format(MESSAGE_SPECIFIER_NOT_SPECIFIED, RequirementCode.MESSAGE_CONSTRAINTS);
    private static final String INVALID_SPECIFIER =
        String.format(MESSAGE_SPECIFIER_INVALID, RequirementCode.MESSAGE_CONSTRAINTS);
    private RequirementAssignCommandParser parser = new RequirementAssignCommandParser();

    @Test
    public void parse_validSpecifierAndArgumentsSuccess() {
        RequirementCode requirementCode = new RequirementCode(VALID_REQ_CODE_UE);

        List<ModuleCode> moduleCodes = new ArrayList<ModuleCode>();

        moduleCodes.add(new ModuleCode(VALID_MODULE_CODE_CS1101S));

        // 1 module only
        assertParseSuccess(parser, VALID_REQ_CODE_UE
            + MODULE_MODULE_CODE_DESC_CS1101S,
            new RequirementAssignCommand(requirementCode, moduleCodes));

        // 1 module only, with white space preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + VALID_REQ_CODE_UE
            + MODULE_MODULE_CODE_DESC_CS1101S,
            new RequirementAssignCommand(requirementCode, moduleCodes));

        // 2 modules (or more)
        moduleCodes.add(new ModuleCode(VALID_MODULE_CODE_CS2100));

        assertParseSuccess(parser, VALID_REQ_CODE_UE
            + MODULE_MODULE_CODE_DESC_CS1101S
            + MODULE_MODULE_CODE_DESC_CS2100,
            new RequirementAssignCommand(requirementCode, moduleCodes));

        // 2 modules (or more), with white space preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + VALID_REQ_CODE_UE
            + MODULE_MODULE_CODE_DESC_CS1101S
            + MODULE_MODULE_CODE_DESC_CS2100,
            new RequirementAssignCommand(requirementCode, moduleCodes));
    }

    @Test
    public void parse_missingSpecifierOrMissingArguments_failure() {
        // missing specifier
        assertParseFailure(parser, "", INVALID_COMMAND_FORMAT); // just "requirement assign"
    }
}
