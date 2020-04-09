package igrad.logic.parser.requirement;

//@@author yjskrs

import static igrad.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static igrad.logic.commands.requirement.RequirementCommandTestUtil.REQ_CREDITS_DESC_CSF;
import static igrad.logic.commands.requirement.RequirementCommandTestUtil.REQ_TITLE_DESC_CSF;
import static igrad.logic.commands.requirement.RequirementCommandTestUtil.VALID_REQ_CODE_UE;
import static igrad.logic.commands.requirement.RequirementCommandTestUtil.VALID_REQ_CREDITS_CSF;
import static igrad.logic.commands.requirement.RequirementCommandTestUtil.VALID_REQ_TITLE_CSF;
import static igrad.logic.commands.requirement.RequirementEditCommand.EditRequirementDescriptor;
import static igrad.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import igrad.logic.commands.requirement.RequirementEditCommand;
import igrad.model.requirement.RequirementCode;
import igrad.testutil.EditRequirementDescriptorBuilder;

public class RequirementEditCommandParserTest {
    private static final String MESSAGE_INVALID_FORMAT =
        String.format(MESSAGE_INVALID_COMMAND_FORMAT, RequirementEditCommand.MESSAGE_REQUIREMENT_EDIT_HELP);

    private RequirementEditCommandParser parser = new RequirementEditCommandParser();

    private EditRequirementDescriptorBuilder descriptorBuilder = new EditRequirementDescriptorBuilder();

    @Test
    public void parse_validSpecifierAndArguments_success() {
        RequirementCode requirementCode = new RequirementCode(VALID_REQ_CODE_UE);
        // Title title = new Title(VALID_REQ_TITLE_CSF);
        // Credits credits = new Credits(VALID_REQ_CREDITS_CSF);
        EditRequirementDescriptor descriptor = descriptorBuilder
                                                   .withTitle(VALID_REQ_TITLE_CSF)
                                                   .withCredits(VALID_REQ_CREDITS_CSF)
                                                   .build();

        assertParseSuccess(parser, VALID_REQ_CODE_UE + REQ_TITLE_DESC_CSF + REQ_CREDITS_DESC_CSF,
               new RequirementEditCommand(requirementCode, descriptor));
    }

}
