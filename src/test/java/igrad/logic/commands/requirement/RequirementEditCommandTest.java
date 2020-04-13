package igrad.logic.commands.requirement;

//@@author yjskrs

import static igrad.logic.commands.CommandTestUtil.assertCommandFailure;
import static igrad.logic.commands.CommandTestUtil.assertCommandSuccess;
import static igrad.logic.commands.requirement.RequirementCommand.MESSAGE_REQUIREMENT_NON_EXISTENT;
import static igrad.logic.commands.requirement.RequirementCommandTestUtil.ModelStubWithRequirement;
import static igrad.logic.commands.requirement.RequirementCommandTestUtil.VALID_REQ_CODE_CSF;
import static igrad.logic.commands.requirement.RequirementCommandTestUtil.VALID_REQ_CODE_GE;
import static igrad.logic.commands.requirement.RequirementCommandTestUtil.VALID_REQ_CREDITS_GE;
import static igrad.logic.commands.requirement.RequirementCommandTestUtil.VALID_REQ_TITLE_GE;
import static igrad.logic.commands.requirement.RequirementEditCommand.EditRequirementDescriptor;
import static igrad.logic.commands.requirement.RequirementEditCommand.MESSAGE_REQUIREMENT_EDIT_SUCCESS;
import static igrad.logic.commands.requirement.RequirementEditCommand.MESSAGE_REQUIREMENT_NOT_EDITED;
import static igrad.testutil.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import igrad.model.requirement.Requirement;
import igrad.model.requirement.RequirementCode;
import igrad.testutil.EditRequirementDescriptorBuilder;
import igrad.testutil.RequirementBuilder;

public class RequirementEditCommandTest {
    private RequirementCode code = new RequirementCode(VALID_REQ_CODE_CSF);
    private Requirement requirement = new RequirementBuilder().build();
    private EditRequirementDescriptor descriptor = new EditRequirementDescriptorBuilder().build();

    @Test
    public void constructor_nullRequirement_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new RequirementEditCommand(null, descriptor));
        assertThrows(NullPointerException.class, () -> new RequirementEditCommand(code, null));
    }

    @Test
    public void execute_editRequirementInCourseBook_success() {
        Requirement modifiedTitle = new RequirementBuilder().withTitle(VALID_REQ_TITLE_GE).build();
        Requirement modifiedCredits = new RequirementBuilder().withCreditsOneParameter(VALID_REQ_CREDITS_GE).build();
        Requirement modifiedBoth = new RequirementBuilder().withTitle(VALID_REQ_TITLE_GE)
                                       .withCreditsOneParameter(VALID_REQ_CREDITS_GE).build();

        EditRequirementDescriptor descriptorWithTitle = new EditRequirementDescriptorBuilder()
                                                            .withTitle(VALID_REQ_TITLE_GE)
                                                            .build();
        EditRequirementDescriptor descriptorWithCredits = new EditRequirementDescriptorBuilder()
                                                              .withCredits(VALID_REQ_CREDITS_GE)
                                                              .build();
        EditRequirementDescriptor descriptorWithBoth = new EditRequirementDescriptorBuilder()
                                                           .withTitle(VALID_REQ_TITLE_GE)
                                                           .withCredits(VALID_REQ_CREDITS_GE)
                                                           .build();

        ModelStubWithRequirement modelStub = new ModelStubWithRequirement(requirement);
        ModelStubWithRequirement modelStubExpected = new ModelStubWithRequirement(modifiedTitle);
        RequirementEditCommand command = new RequirementEditCommand(code, descriptorWithTitle);
        String message = String.format(MESSAGE_REQUIREMENT_EDIT_SUCCESS, modifiedTitle);
        assertCommandSuccess(command, modelStub, message, modelStubExpected);

        modelStub = new ModelStubWithRequirement(requirement);
        modelStubExpected = new ModelStubWithRequirement(modifiedCredits);
        command = new RequirementEditCommand(code, descriptorWithCredits);
        message = String.format(MESSAGE_REQUIREMENT_EDIT_SUCCESS, modifiedCredits);
        assertCommandSuccess(command, modelStub, message, modelStubExpected);

        modelStub = new ModelStubWithRequirement(requirement);
        modelStubExpected = new ModelStubWithRequirement(modifiedBoth);
        command = new RequirementEditCommand(code, descriptorWithBoth);
        message = String.format(MESSAGE_REQUIREMENT_EDIT_SUCCESS, modifiedBoth);
        assertCommandSuccess(command, modelStub, message, modelStubExpected);
    }

    @Test
    public void execute_editRequirementNonExisting_throwsCommandException() {
        RequirementCode differentCode = new RequirementCode(VALID_REQ_CODE_GE);
        ModelStubWithRequirement modelStub = new ModelStubWithRequirement(requirement);
        descriptor = new EditRequirementDescriptorBuilder(descriptor).withTitle(VALID_REQ_TITLE_GE).build();
        RequirementEditCommand command = new RequirementEditCommand(differentCode, descriptor);
        String message = MESSAGE_REQUIREMENT_NON_EXISTENT;

        assertCommandFailure(command, modelStub, message);
    }

    @Test
    public void execute_editRequirementDescriptorNotModified_throwsCommandException() {
        RequirementEditCommand command = new RequirementEditCommand(code, descriptor);
        ModelStubWithRequirement modelStub = new ModelStubWithRequirement(requirement);
        String message = MESSAGE_REQUIREMENT_NOT_EDITED;

        assertCommandFailure(command, modelStub, message);
    }

    @Test
    public void equals() {
        EditRequirementDescriptor descriptorWithDifferentTitle = new EditRequirementDescriptorBuilder()
                                                                     .withTitle(VALID_REQ_TITLE_GE)
                                                                     .build();
        EditRequirementDescriptor descriptorWithDifferentCredits = new EditRequirementDescriptorBuilder()
                                                                       .withCredits(VALID_REQ_CREDITS_GE)
                                                                       .build();

        RequirementCode differentCode = new RequirementCode(VALID_REQ_CODE_GE);

        RequirementEditCommand command = new RequirementEditCommand(code, descriptor);
        RequirementEditCommand commandNewInstance = new RequirementEditCommand(code, descriptor);
        RequirementEditCommand commandDifferentCode = new RequirementEditCommand(differentCode, descriptor);
        RequirementEditCommand commandEditTitle = new RequirementEditCommand(code, descriptorWithDifferentTitle);
        RequirementEditCommand commandEditCredits = new RequirementEditCommand(code, descriptorWithDifferentCredits);

        assertTrue(command.equals(commandNewInstance));
        assertFalse(command.equals(commandDifferentCode));
        assertFalse(command.equals(commandEditTitle));
        assertFalse(command.equals(commandEditCredits));
    }
}
