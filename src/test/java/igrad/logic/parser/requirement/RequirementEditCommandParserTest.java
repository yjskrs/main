package igrad.logic.parser.requirement;

//@@author yjskrs

import static igrad.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static igrad.commons.core.Messages.MESSAGE_SPECIFIER_INVALID;
import static igrad.commons.core.Messages.MESSAGE_SPECIFIER_NOT_SPECIFIED;
import static igrad.logic.commands.requirement.RequirementCommandTestUtil.INVALID_REQ_CREDITS_DESC;
import static igrad.logic.commands.requirement.RequirementCommandTestUtil.REQ_CREDITS_DESC_CSF;
import static igrad.logic.commands.requirement.RequirementCommandTestUtil.REQ_CREDITS_DESC_IP;
import static igrad.logic.commands.requirement.RequirementCommandTestUtil.REQ_CREDITS_DESC_MS;
import static igrad.logic.commands.requirement.RequirementCommandTestUtil.REQ_CREDITS_DESC_UE;
import static igrad.logic.commands.requirement.RequirementCommandTestUtil.REQ_TITLE_DESC_CSF;
import static igrad.logic.commands.requirement.RequirementCommandTestUtil.REQ_TITLE_DESC_IP;
import static igrad.logic.commands.requirement.RequirementCommandTestUtil.REQ_TITLE_DESC_MS;
import static igrad.logic.commands.requirement.RequirementCommandTestUtil.REQ_TITLE_DESC_UE;
import static igrad.logic.commands.requirement.RequirementCommandTestUtil.VALID_REQ_CODE_CSBD;
import static igrad.logic.commands.requirement.RequirementCommandTestUtil.VALID_REQ_CODE_IP;
import static igrad.logic.commands.requirement.RequirementCommandTestUtil.VALID_REQ_CODE_UE;
import static igrad.logic.commands.requirement.RequirementCommandTestUtil.VALID_REQ_CREDITS_CSF;
import static igrad.logic.commands.requirement.RequirementCommandTestUtil.VALID_REQ_CREDITS_IP;
import static igrad.logic.commands.requirement.RequirementCommandTestUtil.VALID_REQ_CREDITS_MS;
import static igrad.logic.commands.requirement.RequirementCommandTestUtil.VALID_REQ_CREDITS_UE;
import static igrad.logic.commands.requirement.RequirementCommandTestUtil.VALID_REQ_TITLE_CSF;
import static igrad.logic.commands.requirement.RequirementCommandTestUtil.VALID_REQ_TITLE_IP;
import static igrad.logic.commands.requirement.RequirementCommandTestUtil.VALID_REQ_TITLE_MS;
import static igrad.logic.commands.requirement.RequirementCommandTestUtil.VALID_REQ_TITLE_UE;
import static igrad.logic.commands.requirement.RequirementEditCommand.EditRequirementDescriptor;
import static igrad.logic.commands.requirement.RequirementEditCommand.MESSAGE_REQUIREMENT_EDIT_HELP;
import static igrad.logic.commands.requirement.RequirementEditCommand.MESSAGE_REQUIREMENT_NOT_EDITED;
import static igrad.logic.parser.CommandParserTestUtil.assertParseEquals;
import static igrad.logic.parser.CommandParserTestUtil.assertParseFailure;
import static igrad.logic.parser.CommandParserTestUtil.assertParseNotEquals;
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
        assertParseFailure(parser, VALID_REQ_CODE_UE + " s/ t/", INVALID_SPECIFIER); // wrong w correct prefix

        // fields not given
        assertParseFailure(parser, VALID_REQ_CODE_UE, FIELDS_NOT_SPECIFIED); // no prefix/arguments
        assertParseFailure(parser, VALID_REQ_CODE_UE + " t/", FIELDS_NOT_SPECIFIED);
        assertParseFailure(parser, VALID_REQ_CODE_UE + " u/", FIELDS_NOT_SPECIFIED);
        assertParseFailure(parser, VALID_REQ_CODE_UE + " t/", FIELDS_NOT_SPECIFIED); // no title
        assertParseFailure(parser, VALID_REQ_CODE_UE + " u/", FIELDS_NOT_SPECIFIED); // no credits

        // invalid argument
        assertParseFailure(parser, VALID_REQ_CODE_UE + REQ_CREDITS_DESC_CSF + " t/", INVALID_TITLE_FORMAT);
        assertParseFailure(parser, VALID_REQ_CODE_UE + " t/" + REQ_CREDITS_DESC_CSF, INVALID_TITLE_FORMAT);
        assertParseFailure(parser, VALID_REQ_CODE_UE + REQ_TITLE_DESC_CSF + " u/", INVALID_CREDITS_FORMAT);
        assertParseFailure(parser, VALID_REQ_CODE_UE + " u/" + REQ_TITLE_DESC_CSF, INVALID_CREDITS_FORMAT);
    }

    @Test
    public void parse_invalidSpecifier_failure() {
        assertParseFailure(parser, "-1" + VALID_REQ_CODE_UE, INVALID_SPECIFIER);
        assertParseFailure(parser, ".", INVALID_SPECIFIER);
        assertParseFailure(parser, "  and some spaces", INVALID_SPECIFIER);
    }

    @Test
    public void parse_multipleRepeatedFields_acceptsLast() {
        RequirementCode requirementCode = new RequirementCode(VALID_REQ_CODE_CSBD);

        String input = VALID_REQ_CODE_CSBD + REQ_TITLE_DESC_UE + REQ_CREDITS_DESC_UE
                           + REQ_TITLE_DESC_MS + REQ_CREDITS_DESC_MS;

        String basicInput = VALID_REQ_CODE_CSBD + REQ_TITLE_DESC_MS + REQ_CREDITS_DESC_MS;

        String differentInput = VALID_REQ_CODE_CSBD + REQ_TITLE_DESC_MS + REQ_CREDITS_DESC_MS
                                    + REQ_TITLE_DESC_UE + REQ_CREDITS_DESC_UE;

        String differentBasicInput = VALID_REQ_CODE_CSBD + REQ_TITLE_DESC_UE + REQ_CREDITS_DESC_UE;

        EditRequirementDescriptor descriptor = new EditRequirementDescriptorBuilder()
                                                   .withTitle(VALID_REQ_TITLE_MS)
                                                   .withCredits(VALID_REQ_CREDITS_MS)
                                                   .build();

        EditRequirementDescriptor differentDescriptor = new EditRequirementDescriptorBuilder()
                                                   .withTitle(VALID_REQ_TITLE_UE)
                                                   .withCredits(VALID_REQ_CREDITS_UE)
                                                   .build();

        assertParseSuccess(parser, input,
            new RequirementEditCommand(requirementCode, descriptor));
        assertParseSuccess(parser, differentInput,
            new RequirementEditCommand(requirementCode, differentDescriptor));
        assertParseEquals(parser, input, basicInput);
        assertParseEquals(parser, differentInput, differentBasicInput);
        assertParseNotEquals(parser, input, differentBasicInput);
        assertParseNotEquals(parser, differentInput, basicInput);
    }

    @Test
    public void parse_invalidValueFollowedByValidValue_success() {
        RequirementCode code = new RequirementCode(VALID_REQ_CODE_IP);

        String input = VALID_REQ_CODE_IP + INVALID_REQ_CREDITS_DESC + REQ_CREDITS_DESC_IP;
        EditRequirementDescriptor descriptor = new EditRequirementDescriptorBuilder()
                                                  .withCredits(VALID_REQ_CREDITS_IP)
                                                  .build();
        assertParseSuccess(parser, input, new RequirementEditCommand(code, descriptor));

        input = VALID_REQ_CODE_IP + INVALID_REQ_CREDITS_DESC + REQ_TITLE_DESC_IP + REQ_CREDITS_DESC_IP;
        descriptor = new EditRequirementDescriptorBuilder(descriptor).withTitle(VALID_REQ_TITLE_IP).build();
        assertParseSuccess(parser, input, new RequirementEditCommand(code, descriptor));
    }
}
