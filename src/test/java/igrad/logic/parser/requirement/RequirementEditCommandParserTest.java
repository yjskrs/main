package igrad.logic.parser.requirement;

//@@author yjskrs

import static igrad.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static igrad.commons.core.Messages.MESSAGE_SPECIFIER_INVALID;
import static igrad.commons.core.Messages.MESSAGE_SPECIFIER_NOT_SPECIFIED;
import static igrad.logic.commands.requirement.RequirementCommandTestUtil.REQ_CREDITS_DESC_CSF;
import static igrad.logic.commands.requirement.RequirementCommandTestUtil.REQ_TITLE_DESC_CSF;
import static igrad.logic.commands.requirement.RequirementCommandTestUtil.VALID_REQ_CODE_UE;
import static igrad.logic.commands.requirement.RequirementCommandTestUtil.VALID_REQ_CREDITS_CSF;
import static igrad.logic.commands.requirement.RequirementCommandTestUtil.VALID_REQ_TITLE_CSF;
import static igrad.logic.commands.requirement.RequirementEditCommand.EditRequirementDescriptor;
import static igrad.logic.commands.requirement.RequirementEditCommand.MESSAGE_REQUIREMENT_EDIT_HELP;
import static igrad.logic.commands.requirement.RequirementEditCommand.MESSAGE_REQUIREMENT_NOT_EDITED;
import static igrad.logic.parser.CommandParserTestUtil.assertParseFailure;
import static igrad.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import igrad.logic.commands.requirement.RequirementEditCommand;
import igrad.model.requirement.Credits;
import igrad.model.requirement.RequirementCode;
import igrad.model.requirement.Title;
import igrad.testutil.EditRequirementDescriptorBuilder;

public class RequirementEditCommandParserTest {
    private static final String INVALID_COMMAND_FORMAT =
        String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_REQUIREMENT_EDIT_HELP);
    private static final String MISSING_SPECIFIER =
        String.format(MESSAGE_SPECIFIER_NOT_SPECIFIED, RequirementCode.MESSAGE_CONSTRAINTS);
    private static final String INVALID_SPECIFIER =
        String.format(MESSAGE_SPECIFIER_INVALID, RequirementCode.MESSAGE_CONSTRAINTS);
    private static final String FIELDS_NOT_SPECIFIED = MESSAGE_REQUIREMENT_NOT_EDITED;
    private static final String INVALID_TITLE_FORMAT = Title.MESSAGE_CONSTRAINTS;
    private static final String INVALID_CREDITS_FORMAT = Credits.MESSAGE_CONSTRAINTS;

    private RequirementEditCommandParser parser = new RequirementEditCommandParser();

    @Test
    public void parse_validSpecifierAndArguments_success() {
        RequirementCode requirementCode = new RequirementCode(VALID_REQ_CODE_UE);
        // Title title = new Title(VALID_REQ_TITLE_CSF);
        // Credits credits = new Credits(VALID_REQ_CREDITS_CSF);

        // both fields modified
        EditRequirementDescriptor descriptor = new EditRequirementDescriptorBuilder()
                                                   .withTitle(VALID_REQ_TITLE_CSF)
                                                   .withCredits(VALID_REQ_CREDITS_CSF)
                                                   .build();
        assertParseSuccess(parser, VALID_REQ_CODE_UE + REQ_TITLE_DESC_CSF + REQ_CREDITS_DESC_CSF,
               new RequirementEditCommand(requirementCode, descriptor));

        // only one field modified
        descriptor = new EditRequirementDescriptorBuilder().withTitle(VALID_REQ_TITLE_CSF).build();
        assertParseSuccess(parser, VALID_REQ_CODE_UE + REQ_TITLE_DESC_CSF,
            new RequirementEditCommand(requirementCode, descriptor));

        descriptor = new EditRequirementDescriptorBuilder().withCredits(VALID_REQ_CREDITS_CSF).build();
        assertParseSuccess(parser, VALID_REQ_CODE_UE + REQ_CREDITS_DESC_CSF,
            new RequirementEditCommand(requirementCode, descriptor));
    }

    @Test
    public void parse_missingSpecifierOrMissingArguments_failure() {
        // invalid command format
        assertParseFailure(parser, "", INVALID_COMMAND_FORMAT); // just "requirement edit"

        // missing specifier
        assertParseFailure(parser, " t/", MISSING_SPECIFIER); // no specifier
        assertParseFailure(parser, VALID_REQ_CODE_UE + " s/", INVALID_SPECIFIER); // wrong prefix
        assertParseFailure(parser, VALID_REQ_CODE_UE + " s/ t/", INVALID_SPECIFIER); // wrong with correct prefix

        // fields not given
        assertParseFailure(parser, VALID_REQ_CODE_UE, FIELDS_NOT_SPECIFIED); // no prefix/arguments
        assertParseFailure(parser, VALID_REQ_CODE_UE + " t/", FIELDS_NOT_SPECIFIED);
        assertParseFailure(parser, VALID_REQ_CODE_UE + " u/", FIELDS_NOT_SPECIFIED);
        assertParseFailure(parser, VALID_REQ_CODE_UE + " t/", FIELDS_NOT_SPECIFIED); // no title
        assertParseFailure(parser, VALID_REQ_CODE_UE + " u/", FIELDS_NOT_SPECIFIED); // no credits

        // invalid argument
        assertParseFailure(parser, VALID_REQ_CODE_UE + REQ_CREDITS_DESC_CSF + " t/", INVALID_TITLE_FORMAT); // no title
        assertParseFailure(parser, VALID_REQ_CODE_UE + " t/" + REQ_CREDITS_DESC_CSF, INVALID_TITLE_FORMAT); // no title
        assertParseFailure(parser, VALID_REQ_CODE_UE + REQ_TITLE_DESC_CSF + " u/", INVALID_CREDITS_FORMAT); // no credits
        assertParseFailure(parser, VALID_REQ_CODE_UE + " u/" + REQ_TITLE_DESC_CSF, INVALID_CREDITS_FORMAT); // no credits
    }

    @Test
    public void parse_invalidSpecifier_failure() {
        assertParseFailure(parser, "-1" + VALID_REQ_CODE_UE, INVALID_SPECIFIER);
        assertParseFailure(parser, "0" + VALID_REQ_CODE_UE, INVALID_SPECIFIER);
    }

    // @Test
    // public void parse_allFieldsSpecified_success() {
    //     Index targetIndex = INDEX_SECOND_PERSON;
    //     String userInput = targetIndex.getOneBased() + PHONE_DESC_BOB + TAG_DESC_HUSBAND
    //                            + EMAIL_DESC_AMY + ADDRESS_DESC_AMY + NAME_DESC_AMY + TAG_DESC_FRIEND;
    //
    //     EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder().withName(VALID_NAME_AMY)
    //                                           .withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_AMY).withAddress(VALID_ADDRESS_AMY)
    //                                           .withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND).build();
    //     EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);
    //
    //     assertParseSuccess(parser, userInput, expectedCommand);
    // }
    //
    // @Test
    // public void parse_someFieldsSpecified_success() {
    //     Index targetIndex = INDEX_FIRST_PERSON;
    //     String userInput = targetIndex.getOneBased() + PHONE_DESC_BOB + EMAIL_DESC_AMY;
    //
    //     EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder().withPhone(VALID_PHONE_BOB)
    //                                           .withEmail(VALID_EMAIL_AMY).build();
    //     EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);
    //
    //     assertParseSuccess(parser, userInput, expectedCommand);
    // }
    //
    // @Test
    // public void parse_oneFieldSpecified_success() {
    //     // name
    //     Index targetIndex = INDEX_THIRD_PERSON;
    //     String userInput = targetIndex.getOneBased() + NAME_DESC_AMY;
    //     EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder().withName(VALID_NAME_AMY).build();
    //     EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);
    //     assertParseSuccess(parser, userInput, expectedCommand);
    //
    //     // phone
    //     userInput = targetIndex.getOneBased() + PHONE_DESC_AMY;
    //     descriptor = new EditPersonDescriptorBuilder().withPhone(VALID_PHONE_AMY).build();
    //     expectedCommand = new EditCommand(targetIndex, descriptor);
    //     assertParseSuccess(parser, userInput, expectedCommand);
    //
    //     // email
    //     userInput = targetIndex.getOneBased() + EMAIL_DESC_AMY;
    //     descriptor = new EditPersonDescriptorBuilder().withEmail(VALID_EMAIL_AMY).build();
    //     expectedCommand = new EditCommand(targetIndex, descriptor);
    //     assertParseSuccess(parser, userInput, expectedCommand);
    //
    //     // address
    //     userInput = targetIndex.getOneBased() + ADDRESS_DESC_AMY;
    //     descriptor = new EditPersonDescriptorBuilder().withAddress(VALID_ADDRESS_AMY).build();
    //     expectedCommand = new EditCommand(targetIndex, descriptor);
    //     assertParseSuccess(parser, userInput, expectedCommand);
    //
    //     // tags
    //     userInput = targetIndex.getOneBased() + TAG_DESC_FRIEND;
    //     descriptor = new EditPersonDescriptorBuilder().withTags(VALID_TAG_FRIEND).build();
    //     expectedCommand = new EditCommand(targetIndex, descriptor);
    //     assertParseSuccess(parser, userInput, expectedCommand);
    // }
    //
    // @Test
    // public void parse_multipleRepeatedFields_acceptsLast() {
    //     Index targetIndex = INDEX_FIRST_PERSON;
    //     String userInput = targetIndex.getOneBased() + PHONE_DESC_AMY + ADDRESS_DESC_AMY + EMAIL_DESC_AMY
    //                            + TAG_DESC_FRIEND + PHONE_DESC_AMY + ADDRESS_DESC_AMY + EMAIL_DESC_AMY + TAG_DESC_FRIEND
    //                            + PHONE_DESC_BOB + ADDRESS_DESC_BOB + EMAIL_DESC_BOB + TAG_DESC_HUSBAND;
    //
    //     EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder().withPhone(VALID_PHONE_BOB)
    //                                           .withEmail(VALID_EMAIL_BOB).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_FRIEND, VALID_TAG_HUSBAND)
    //                                           .build();
    //     EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);
    //
    //     assertParseSuccess(parser, userInput, expectedCommand);
    // }
    //
    // @Test
    // public void parse_invalidValueFollowedByValidValue_success() {
    //     // no other valid values specified
    //     Index targetIndex = INDEX_FIRST_PERSON;
    //     String userInput = targetIndex.getOneBased() + INVALID_PHONE_DESC + PHONE_DESC_BOB;
    //     EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder().withPhone(VALID_PHONE_BOB).build();
    //     EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);
    //     assertParseSuccess(parser, userInput, expectedCommand);
    //
    //     // other valid values specified
    //     userInput = targetIndex.getOneBased() + EMAIL_DESC_BOB + INVALID_PHONE_DESC + ADDRESS_DESC_BOB
    //                     + PHONE_DESC_BOB;
    //     descriptor = new EditPersonDescriptorBuilder().withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_BOB)
    //                      .withAddress(VALID_ADDRESS_BOB).build();
    //     expectedCommand = new EditCommand(targetIndex, descriptor);
    //     assertParseSuccess(parser, userInput, expectedCommand);
    // }
}
