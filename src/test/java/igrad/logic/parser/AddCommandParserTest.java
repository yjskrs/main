package igrad.logic.parser;

import static igrad.logic.commands.CommandTestUtil.VALID_TAG_HARD;
import static igrad.testutil.TypicalModules.COMPUTER_ORGANISATION;

import org.junit.jupiter.api.Test;

import igrad.model.module.Module;
import igrad.testutil.ModuleBuilder;

public class AddCommandParserTest {
    private AddCommandParser parser = new AddCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Module expectedModule = new ModuleBuilder(COMPUTER_ORGANISATION).withTags(VALID_TAG_HARD)
                .build();

        // whitespace only preamble
        /*assertParseSuccess(parser, PREAMBLE_WHITESPACE + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
         *       + TAG_DESC_FRIEND, new ModuleAddCommand(expectedModule));
         */
        // multiple names - last name accepted
        /*assertParseSuccess(parser, NAME_DESC_AMY + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
         *       + TAG_DESC_FRIEND, new ModuleAddCommand(expectedModule));
         */
        // multiple phones - last phone accepted
        /*assertParseSuccess(parser, NAME_DESC_BOB + PHONE_DESC_AMY + PHONE_DESC_BOB + EMAIL_DESC_BOB
         *       + TAG_DESC_FRIEND, new ModuleAddCommand(expectedModule));
         */
        // multiple emails - last email accepted
        /*assertParseSuccess(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_AMY + EMAIL_DESC_BOB
         *       + TAG_DESC_FRIEND, new ModuleAddCommand(expectedModule));
         */
        // multiple tags - all accepted
        //Module expectedModuleMultipleTags = new ModuleBuilder(TypicalPersons.BOB)
        //        .withTags(VALID_TAG_FRIEND, VALID_TAG_HUSBAND)
        //        .build();
        //assertParseSuccess(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
        //        + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, new ModuleAddCommand(expectedModuleMultipleTags));
    }

    @Test
    public void parse_optionalFieldsMissing_success() {
        // zero tags
        //Module expectedModule = new ModuleBuilder(AMY).withTags().build();
        //assertParseSuccess(parser, NAME_DESC_AMY + PHONE_DESC_AMY + EMAIL_DESC_AMY,
        //        new ModuleAddCommand(expectedModule));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        //String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, ModuleAddCommand.MESSAGE_USAGE);

        // missing name prefix
        //assertParseFailure(parser, VALID_NAME_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB, expectedMessage);

        // missing phone prefix
        //assertParseFailure(parser, NAME_DESC_BOB + VALID_PHONE_BOB + EMAIL_DESC_BOB, expectedMessage);

        // missing email prefix
        //assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + VALID_EMAIL_BOB, expectedMessage);

        // all prefixes missing
        //assertParseFailure(parser, VALID_NAME_BOB + VALID_PHONE_BOB + VALID_EMAIL_BOB, expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid name
        //assertParseFailure(parser, INVALID_NAME_DESC + PHONE_DESC_BOB + EMAIL_DESC_BOB
        //+ TAG_DESC_HUSBAND + TAG_DESC_FRIEND, Name.MESSAGE_CONSTRAINTS);

        // invalid phone
        //assertParseFailure(parser, NAME_DESC_BOB + INVALID_PHONE_DESC + EMAIL_DESC_BOB
        //+ TAG_DESC_HUSBAND + TAG_DESC_FRIEND, Phone.MESSAGE_CONSTRAINTS);

        // invalid email
        //assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + INVALID_EMAIL_DESC
        //        + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, Email.MESSAGE_CONSTRAINTS);

        // invalid tag
        //assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
        //        + INVALID_TAG_DESC + VALID_TAG_FRIEND, Tag.MESSAGE_CONSTRAINTS);

        // two invalid values, only first invalid value reported
        //assertParseFailure(parser, INVALID_NAME_DESC + PHONE_DESC_BOB + EMAIL_DESC_BOB,
        //        Name.MESSAGE_CONSTRAINTS);

        // non-empty preamble
        //assertParseFailure(parser, PREAMBLE_NON_EMPTY + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
        //        + TAG_DESC_HUSBAND + TAG_DESC_FRIEND,
        //        String.format(MESSAGE_INVALID_COMMAND_FORMAT, ModuleAddCommand.MESSAGE_USAGE));
    }
}
