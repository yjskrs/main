package igrad.logic.parser.module;

/**
 * As we are only doing white-box testing, our test cases do not cover path variations
 * outside of the ModuleDeleteCommand code. For example, inputs "1" and "1 abc" take the
 * same path through the ModuleDeleteCommand, and therefore we test only one of them.
 * The path variation for those two cases occur inside the ParserUtil, and
 * therefore should be covered by the ParserUtilTest.
 */
public class ModuleDeleteCommandParserTest {

    private ModuleDeleteCommandParser parser = new ModuleDeleteCommandParser();

    /*
    @Test
    public void parse_validArgs_returnsDeleteCommand() {
        assertParseSuccess(parser,
            "1", new ModuleDeleteCommand(INDEX_FIRST_MODULE));
    }*/

    /*
    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser,
            "a", String.format(MESSAGE_INVALID_COMMAND_FORMAT, ModuleDeleteCommand.MESSAGE_USAGE));
    }*/
}
