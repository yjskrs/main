package igrad.logic.parser.requirement;

//@@author nathanaelseen

import static igrad.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static igrad.logic.commands.module.ModuleCommandTestUtil.MODULE_MODULE_CODE_DESC_CS1101S;
import static igrad.logic.commands.module.ModuleCommandTestUtil.MODULE_MODULE_CODE_DESC_CS2100;
import static igrad.logic.commands.module.ModuleCommandTestUtil.VALID_MODULE_CODE_CS1101S;
import static igrad.logic.commands.module.ModuleCommandTestUtil.VALID_MODULE_CODE_CS2100;
import static igrad.logic.commands.requirement.RequirementCommandTestUtil.VALID_REQ_CODE_UE;
import static igrad.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import igrad.logic.commands.requirement.RequirementAssignCommand;
import igrad.model.module.ModuleCode;
import igrad.model.requirement.RequirementCode;

public class RequirementAssignCommandParserTest {
    private RequirementAssignCommandParser parser = new RequirementAssignCommandParser();
    @Test
    public void parse_validRequirementSpecifierWithOneModule_success() {
        RequirementCode requirementCode = new RequirementCode(VALID_REQ_CODE_UE);

        List<ModuleCode> moduleCodes = new ArrayList<ModuleCode>();

        moduleCodes.add(new ModuleCode(VALID_MODULE_CODE_CS1101S));
        moduleCodes.add(new ModuleCode(VALID_MODULE_CODE_CS2100));

        // whitespace preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + VALID_REQ_CODE_UE
            + MODULE_MODULE_CODE_DESC_CS1101S
            + MODULE_MODULE_CODE_DESC_CS2100,
            new RequirementAssignCommand(requirementCode, moduleCodes));

        // jumbled order
        // assertParseSuccess(parser, REQ_CREDITS_DESC_UE + REQ_TITLE_DESC_UE,
        //     new RequirementAddCommand(expectedRequirement));

        // multiple titles, only last title parsed
        // assertParseFailure(parser, , new RequirementAddCommand(expectedRequirement));
        // assertParseSuccess(parser, , new RequirementAddCommand(expectedRequirement));

        // multiple credits, only last credits parsed
        // assertParseFailure(parser, , new RequirementAddCommand(expectedRequirement));
        // assertParseSuccess(parser, , new RequirementAddCommand(expectedRequirement));

    }
}
