package igrad.logic.parser.requirement;

//@@author yjskrs

import static igrad.commons.core.Messages.MESSAGE_SPECIFIER_INVALID;
import static igrad.logic.commands.requirement.RequirementCommandTestUtil.INVALID_REQ_CODE_DECIMAL;
import static igrad.logic.commands.requirement.RequirementCommandTestUtil.INVALID_REQ_CODE_SYMBOL;
import static igrad.logic.commands.requirement.RequirementCommandTestUtil.VALID_REQ_CODE_MS;
import static igrad.logic.parser.CommandParserTestUtil.assertParseFailure;
import static igrad.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import igrad.logic.commands.requirement.RequirementDeleteCommand;
import igrad.model.requirement.RequirementCode;

public class RequirementDeleteCommandParserTest {
    private RequirementDeleteCommandParser parser = new RequirementDeleteCommandParser();

    @Test
    public void parse_validSpecifier_success() {
        RequirementCode requirementCode = new RequirementCode(VALID_REQ_CODE_MS);

        assertParseSuccess(parser, VALID_REQ_CODE_MS, new RequirementDeleteCommand(requirementCode));
    }

    @Test
    public void parse_invalidSpecifier_failure() {
        String errorMessage = String.format(MESSAGE_SPECIFIER_INVALID, RequirementCode.MESSAGE_CONSTRAINTS);

        assertParseFailure(parser, INVALID_REQ_CODE_DECIMAL, errorMessage);
        assertParseFailure(parser, INVALID_REQ_CODE_SYMBOL, errorMessage);
    }
}
