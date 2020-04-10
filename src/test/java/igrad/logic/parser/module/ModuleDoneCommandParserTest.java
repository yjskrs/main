package igrad.logic.parser.module;

//@@author nathanaelseen

public class ModuleDoneCommandParserTest {
    private ModuleDoneCommandParser parser = new ModuleDoneCommandParser();

    /*@Test
    public void parse_missingSpecifierOrMissingArguments_failure() {
        // missing specifier and arguments
        assertParseFailure(parser, "", INVALID_COMMAND_FORMAT); // just "requirement assign"

        // missing specifier only
        assertParseFailure(parser, " n/", MISSING_SPECIFIER); // no specifier

        // missing arguments only (i.e; module codes, there must be at least one module code)
        assertParseFailure(parser, VALID_REQ_CODE_UE, ARGUMENTS_NOT_SPECIFIED); // no arguments (module codes)
        assertParseFailure(parser, VALID_REQ_CODE_UE + " n/", ARGUMENTS_NOT_SPECIFIED); // one empty string module code
    }*/
}
