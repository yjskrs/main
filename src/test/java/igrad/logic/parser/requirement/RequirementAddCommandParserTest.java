package igrad.logic.parser.requirement;

//@@author yjskrs

import static igrad.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static igrad.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static igrad.logic.commands.requirement.RequirementAddCommand.MESSAGE_REQUIREMENT_NOT_ADDED;
import static igrad.logic.commands.requirement.RequirementCommandTestUtil.INVALID_REQ_CREDITS_ALPHABET;
import static igrad.logic.commands.requirement.RequirementCommandTestUtil.INVALID_REQ_CREDITS_DECIMAL;
import static igrad.logic.commands.requirement.RequirementCommandTestUtil.INVALID_REQ_CREDITS_SYMBOL;
import static igrad.logic.commands.requirement.RequirementCommandTestUtil.REQ_CREDITS_DESC_CSBD;
import static igrad.logic.commands.requirement.RequirementCommandTestUtil.REQ_CREDITS_DESC_CSF;
import static igrad.logic.commands.requirement.RequirementCommandTestUtil.REQ_CREDITS_DESC_UE;
import static igrad.logic.commands.requirement.RequirementCommandTestUtil.REQ_TITLE_DESC_CSBD;
import static igrad.logic.commands.requirement.RequirementCommandTestUtil.REQ_TITLE_DESC_CSF;
import static igrad.logic.commands.requirement.RequirementCommandTestUtil.REQ_TITLE_DESC_UE;
import static igrad.logic.commands.requirement.RequirementCommandTestUtil.VALID_REQ_CREDITS_UE;
import static igrad.logic.commands.requirement.RequirementCommandTestUtil.VALID_REQ_TITLE_UE;
import static igrad.logic.parser.CliSyntax.PREFIX_CREDITS;
import static igrad.logic.parser.CliSyntax.PREFIX_TITLE;
import static igrad.logic.parser.CommandParserTestUtil.assertParseFailure;
import static igrad.logic.parser.CommandParserTestUtil.assertParseNotEquals;
import static igrad.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import igrad.logic.commands.requirement.RequirementAddCommand;
import igrad.model.requirement.Credits;
import igrad.model.requirement.Requirement;
import igrad.testutil.RequirementBuilder;


public class RequirementAddCommandParserTest {
    private RequirementAddCommandParser parser = new RequirementAddCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Requirement expectedRequirement = new RequirementBuilder()
            .withRequirementCode("UE")
            .withTitle(VALID_REQ_TITLE_UE)
            .withCreditsOneParameter(VALID_REQ_CREDITS_UE)
            .build();

        // whitespace preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + REQ_TITLE_DESC_UE + REQ_CREDITS_DESC_UE,
            new RequirementAddCommand(expectedRequirement));

        // jumbled order
        assertParseSuccess(parser, REQ_CREDITS_DESC_UE + REQ_TITLE_DESC_UE,
            new RequirementAddCommand(expectedRequirement));

        // multiple titles, only last title parsed
        assertParseSuccess(parser,
            REQ_CREDITS_DESC_UE + REQ_TITLE_DESC_CSBD + REQ_TITLE_DESC_UE,
            new RequirementAddCommand(expectedRequirement));
        assertParseNotEquals(parser,
            REQ_CREDITS_DESC_UE + REQ_TITLE_DESC_CSBD + REQ_TITLE_DESC_UE,
            REQ_CREDITS_DESC_UE + REQ_TITLE_DESC_UE + REQ_TITLE_DESC_CSBD);

        // multiple credits, only last credits parsed
        assertParseSuccess(parser,
            REQ_CREDITS_DESC_CSBD + REQ_CREDITS_DESC_UE + REQ_TITLE_DESC_UE,
            new RequirementAddCommand(expectedRequirement));
        assertParseNotEquals(parser,
            REQ_CREDITS_DESC_CSBD + REQ_CREDITS_DESC_UE + REQ_TITLE_DESC_UE,
            REQ_TITLE_DESC_UE + REQ_CREDITS_DESC_UE + REQ_CREDITS_DESC_CSBD);
    }

    @Test
    public void parse_prefixesMissing_failure() {
        String prefixMissingMessage = String.format(
            MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_REQUIREMENT_NOT_ADDED);

        // missing title prefix (" t/")
        assertParseFailure(parser, REQ_CREDITS_DESC_CSF, prefixMissingMessage);

        // missing credits prefix (" c/")
        assertParseFailure(parser, REQ_TITLE_DESC_CSF, prefixMissingMessage);
    }

    @Test
    public void parse_argumentsMissing_failure() {
        String argumentMissingMessage = String.format(
            MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_REQUIREMENT_NOT_ADDED);

        // missing title
        String input = " " + PREFIX_TITLE.getPrefix() + REQ_CREDITS_DESC_CSF;
        assertParseFailure(parser, input, argumentMissingMessage);

        // missing credits
        input = REQ_TITLE_DESC_CSF + " " + PREFIX_CREDITS.getPrefix();
        assertParseFailure(parser, input, argumentMissingMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid credits
        String validInput = REQ_TITLE_DESC_CSBD + " " + PREFIX_CREDITS;
        assertParseFailure(parser, validInput + INVALID_REQ_CREDITS_ALPHABET, Credits.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, validInput + INVALID_REQ_CREDITS_DECIMAL, Credits.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, validInput + INVALID_REQ_CREDITS_SYMBOL, Credits.MESSAGE_CONSTRAINTS);
    }

}
