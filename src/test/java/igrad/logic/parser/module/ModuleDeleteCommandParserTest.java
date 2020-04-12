package igrad.logic.parser.module;

//@@author dargohzy

import static igrad.commons.core.Messages.MESSAGE_SPECIFIER_INVALID;
import static igrad.logic.commands.module.ModuleCommandTestUtil.INVALID_MODULE_CODE_DESC;
import static igrad.logic.commands.module.ModuleCommandTestUtil.VALID_MODULE_CODE_CS1101S;
import static igrad.logic.commands.module.ModuleDeleteCommand.MESSAGE_MODULE_DELETE_HELP;
import static igrad.logic.parser.CommandParserTestUtil.assertParseFailure;
import static igrad.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import igrad.commons.core.Messages;
import igrad.logic.commands.module.ModuleDeleteCommand;
import igrad.model.module.ModuleCode;

/**
 * As we are only doing white-box testing, our test cases do not cover path variations
 * outside of the ModuleDeleteCommand code. For example, inputs "1" and "1 abc" take the
 * same path through the ModuleDeleteCommand, and therefore we test only one of them.
 * The path variation for those two cases occur inside the ParserUtil, and
 * therefore should be covered by the ParserUtilTest.
 */
public class ModuleDeleteCommandParserTest {

    private ModuleDeleteCommandParser parser = new ModuleDeleteCommandParser();

    @Test
    public void parse_validArgs_returnsDeleteCommand() {
        ModuleCode moduleCode = new ModuleCode(VALID_MODULE_CODE_CS1101S);

        assertParseSuccess(parser,
            VALID_MODULE_CODE_CS1101S, new ModuleDeleteCommand(moduleCode));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        String errorMessageEmpty = String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_MODULE_DELETE_HELP);
        String errorMessageInvalid = String.format(MESSAGE_SPECIFIER_INVALID, ModuleCode.MESSAGE_CONSTRAINTS);

        // Empty arguments
        String empty = "";

        // Invalid module code
        String invalidModuleCode = INVALID_MODULE_CODE_DESC;

        assertParseFailure(parser, empty, errorMessageEmpty);
        assertParseFailure(parser, invalidModuleCode, errorMessageInvalid);
    }
}
