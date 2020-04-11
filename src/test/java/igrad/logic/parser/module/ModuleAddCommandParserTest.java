package igrad.logic.parser.module;

//@@author dargohzy

import static igrad.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static igrad.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static igrad.logic.commands.CommandTestUtil.VALID_TITLE_CS_1101S;
import static igrad.logic.commands.module.ModuleAddCommand.MESSAGE_MODULE_NOT_ADDED;
import static igrad.logic.commands.module.ModuleCommandTestUtil.INVALID_MODULE_CODE_DESC;
import static igrad.logic.commands.module.ModuleCommandTestUtil.INVALID_MODULE_CREDITS_DESC;
import static igrad.logic.commands.module.ModuleCommandTestUtil.INVALID_MODULE_TITLE_DESC;
import static igrad.logic.commands.module.ModuleCommandTestUtil.INVALID_MODULE_TITLE_EMPTY_DESC;
import static igrad.logic.commands.module.ModuleCommandTestUtil.INVALID_MODULE_TITLE_SLASH_DESC;
import static igrad.logic.commands.module.ModuleCommandTestUtil.MODULE_CREDITS_DESC_CS1101S;
import static igrad.logic.commands.module.ModuleCommandTestUtil.MODULE_MODULE_CODE_DESC_CS1101S;
import static igrad.logic.commands.module.ModuleCommandTestUtil.MODULE_TITLE_DESC_CS1101S;
import static igrad.logic.commands.module.ModuleCommandTestUtil.VALID_MODULE_CODE_CS1101S;
import static igrad.logic.commands.module.ModuleCommandTestUtil.VALID_MODULE_CREDITS_4;
import static igrad.logic.commands.module.ModuleCommandTestUtil.VALID_MODULE_TITLE_CS1101S;
import static igrad.logic.parser.CommandParserTestUtil.assertParseFailure;
import static igrad.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import igrad.logic.commands.module.ModuleAddCommand;
import igrad.model.module.Credits;
import igrad.model.module.Module;
import igrad.model.module.ModuleCode;
import igrad.model.module.Title;
import igrad.testutil.ModuleBuilder;

public class ModuleAddCommandParserTest {
    private ModuleAddCommandParser parser = new ModuleAddCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {

        Module expectedModule = new ModuleBuilder()
            .withTitle(VALID_MODULE_TITLE_CS1101S)
            .withModuleCode(VALID_MODULE_CODE_CS1101S)
            .withCredits(VALID_MODULE_CREDITS_4)
            .withoutOptionals()
            .build();

        String whiteSpace = PREAMBLE_WHITESPACE
            + MODULE_MODULE_CODE_DESC_CS1101S
            + MODULE_TITLE_DESC_CS1101S
            + MODULE_CREDITS_DESC_CS1101S;

        String jumbledOrder = MODULE_TITLE_DESC_CS1101S
            + MODULE_CREDITS_DESC_CS1101S
            + MODULE_MODULE_CODE_DESC_CS1101S;

        assertParseSuccess(parser, whiteSpace, new ModuleAddCommand(expectedModule));
        assertParseSuccess(parser, jumbledOrder, new ModuleAddCommand(expectedModule));
    }

    @Test
    public void parse_optionalFieldsMissing_success() {

        Module expectedModule = new ModuleBuilder()
            .withTitle(VALID_MODULE_TITLE_CS1101S)
            .withModuleCode(VALID_MODULE_CODE_CS1101S)
            .withCredits(VALID_MODULE_CREDITS_4)
            .withoutOptionals()
            .build();

        String addModule = PREAMBLE_WHITESPACE
            + MODULE_MODULE_CODE_DESC_CS1101S
            + MODULE_TITLE_DESC_CS1101S
            + MODULE_CREDITS_DESC_CS1101S;

        assertParseSuccess(parser, addModule, new ModuleAddCommand(expectedModule));
    }

    @Test
    public void parse_compulsoryPrefixesMissing_failure() {
        String prefixMissingMessage = String.format(
            MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_MODULE_NOT_ADDED);

        // missing title prefix (" t/")
        String missingTitle = MODULE_MODULE_CODE_DESC_CS1101S
            + VALID_TITLE_CS_1101S
            + MODULE_CREDITS_DESC_CS1101S;

        // missing module code prefix (" n/")
        String missingModuleCode = VALID_MODULE_CODE_CS1101S
            + MODULE_TITLE_DESC_CS1101S
            + MODULE_CREDITS_DESC_CS1101S;

        // missing credits prefix (" u/")
        String missingCredits = MODULE_MODULE_CODE_DESC_CS1101S
            + MODULE_TITLE_DESC_CS1101S
            + VALID_MODULE_CREDITS_4;

        assertParseFailure(parser, missingTitle, prefixMissingMessage);
        assertParseFailure(parser, missingModuleCode, prefixMissingMessage);
        assertParseFailure(parser, missingCredits, prefixMissingMessage);
    }

    @Test
    public void parse_argumentsMissing_failure() {
        String errorMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_MODULE_NOT_ADDED);

        // missing title
        String missingTitle = MODULE_TITLE_DESC_CS1101S + MODULE_CREDITS_DESC_CS1101S;

        // missing module code
        String missingModuleCode = MODULE_MODULE_CODE_DESC_CS1101S + MODULE_CREDITS_DESC_CS1101S;

        // missing credits
        String missingCredits = MODULE_TITLE_DESC_CS1101S + MODULE_MODULE_CODE_DESC_CS1101S;

        assertParseFailure(parser, missingTitle, errorMessage);
        assertParseFailure(parser, missingModuleCode, errorMessage);
        assertParseFailure(parser, missingCredits, errorMessage);
    }

    @Test
    public void parse_argumentsInvalid_failure() {
        String invalidModuleCodeErrorMessage = ModuleCode.MESSAGE_CONSTRAINTS;
        String invalidTitleErrorMessage = Title.MESSAGE_CONSTRAINTS;
        String invalidCreditsErrorMessage = Credits.MESSAGE_CONSTRAINTS;

        // invalid module code
        String invalidModuleCode = INVALID_MODULE_CODE_DESC
            + MODULE_TITLE_DESC_CS1101S
            + MODULE_CREDITS_DESC_CS1101S;

        // invalid title : starting with blank
        String invalidTitle = MODULE_MODULE_CODE_DESC_CS1101S
            + INVALID_MODULE_TITLE_DESC
            + MODULE_CREDITS_DESC_CS1101S;

        // invalid title : starting with slash
        String invalidTitleSlash = MODULE_MODULE_CODE_DESC_CS1101S
            + INVALID_MODULE_TITLE_SLASH_DESC
            + MODULE_CREDITS_DESC_CS1101S;

        // invalid title : empty
        String invalidTitleEmpty = MODULE_MODULE_CODE_DESC_CS1101S
            + INVALID_MODULE_TITLE_EMPTY_DESC
            + MODULE_CREDITS_DESC_CS1101S;

        // invalid credits
        String invalidCredits = MODULE_MODULE_CODE_DESC_CS1101S
            + MODULE_TITLE_DESC_CS1101S
            + INVALID_MODULE_CREDITS_DESC;

        assertParseFailure(parser, invalidModuleCode, invalidModuleCodeErrorMessage);
        /* assertParseFailure(parser, invalidTitle, invalidTitleErrorMessage);
        assertParseFailure(parser, invalidTitleSlash, invalidTitleErrorMessage);
        assertParseFailure(parser, invalidTitleEmpty, invalidTitleErrorMessage);*/
        assertParseFailure(parser, invalidCredits, invalidCreditsErrorMessage);
    }
}
