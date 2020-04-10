package igrad.logic.parser.requirement;

/*import static igrad.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static igrad.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static igrad.logic.commands.requirement.RequirementAddCommand.MESSAGE_REQUIREMENT_NOT_ADDED;
import static igrad.logic.commands.requirement.RequirementCommandTestUtil.INVALID_REQ_CREDITS_ALPHABET;
import static igrad.logic.commands.requirement.RequirementCommandTestUtil.INVALID_REQ_CREDITS_DECIMAL;
import static igrad.logic.commands.requirement.RequirementCommandTestUtil.INVALID_REQ_CREDITS_SYMBOL;
import static igrad.logic.commands.requirement.RequirementCommandTestUtil.REQ_CREDITS_DESC_CSF;
import static igrad.logic.commands.requirement.RequirementCommandTestUtil.REQ_CREDITS_DESC_UE;
import static igrad.logic.commands.requirement.RequirementCommandTestUtil.REQ_TITLE_DESC_CSBD;
import static igrad.logic.commands.requirement.RequirementCommandTestUtil.REQ_TITLE_DESC_CSF;
import static igrad.logic.commands.requirement.RequirementCommandTestUtil.REQ_TITLE_DESC_UE;
import static igrad.logic.commands.requirement.RequirementCommandTestUtil.VALID_REQ_CODE_UE;
import static igrad.logic.commands.requirement.RequirementCommandTestUtil.VALID_REQ_CREDITS_UE;
import static igrad.logic.commands.requirement.RequirementCommandTestUtil.VALID_REQ_TITLE_UE;
import static igrad.logic.parser.CliSyntax.PREFIX_CREDITS;
import static igrad.logic.parser.CliSyntax.PREFIX_TITLE;
import static igrad.logic.parser.CommandParserTestUtil.assertParseFailure;
import static igrad.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import igrad.logic.commands.requirement.RequirementAddCommand;
import igrad.model.requirement.Credits;
import igrad.model.requirement.Requirement;
import igrad.testutil.RequirementBuilder;*/

import org.junit.jupiter.api.Test;

//@@author nathanaelseen

public class RequirementAssignCommandParserTest {
    private RequirementAssignCommandParser parser = new RequirementAssignCommandParser();
    @Test
    public void parse_allFieldsPresentWithOneModule_success() {
        /*Requirement expectedRequirement = new RequirementBuilder()
                                              .withRequirementCode(VALID_REQ_CODE_UE)
                                              .build();*/

        // whitespace preamble
        // assertParseSuccess(parser, VALID_REQ_CODE_UE + REQ_CREDITS_DESC_UE,
        //     new RequirementAddCommand(expectedRequirement));

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
