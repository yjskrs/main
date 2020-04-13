package igrad.logic.commands.requirement;

//@@author yjskrs

import static igrad.logic.commands.CommandTestUtil.assertCommandSuccess;
import static igrad.logic.commands.requirement.RequirementAddCommand.MESSAGE_REQUIREMENT_ADD_SUCCESS;
import static igrad.logic.commands.requirement.RequirementCommandTestUtil.ModelStub;
import static igrad.logic.commands.requirement.RequirementCommandTestUtil.ModelStubAcceptingRequirementAdded;
import static igrad.logic.commands.requirement.RequirementCommandTestUtil.ModelStubWithRequirement;
import static igrad.logic.commands.requirement.RequirementCommandTestUtil.VALID_REQ_CODE_IP;
import static igrad.logic.commands.requirement.RequirementCommandTestUtil.VALID_REQ_CREDITS_IP;
import static igrad.logic.commands.requirement.RequirementCommandTestUtil.VALID_REQ_TITLE_IP;
import static igrad.testutil.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Collections;

import org.junit.jupiter.api.Test;

import igrad.logic.commands.Command;
import igrad.logic.commands.CommandResult;
import igrad.logic.commands.exceptions.CommandException;
import igrad.model.requirement.Requirement;
import igrad.testutil.RequirementBuilder;

public class RequirementAddCommandTest {

    @Test
    public void constructor_nullRequirement_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new RequirementAddCommand(null));
    }

    @Test
    public void execute_requirementAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingRequirementAdded modelStub = new ModelStubAcceptingRequirementAdded();
        Requirement validRequirement = new RequirementBuilder().build();

        CommandResult commandResult = new RequirementAddCommand(validRequirement).execute(modelStub);

        assertEquals(String.format(MESSAGE_REQUIREMENT_ADD_SUCCESS, validRequirement),
            commandResult.getFeedbackToUser());

        assertEquals(Collections.singletonList(validRequirement), modelStub.requirements);
    }

    @Test
    public void execute_addRequirement_success() throws Exception {
        ModelStubAcceptingRequirementAdded modelStub = new ModelStubAcceptingRequirementAdded();
        ModelStubAcceptingRequirementAdded modelStubSuccess = new ModelStubAcceptingRequirementAdded();
        Requirement validRequirement = new RequirementBuilder()
                                           .withRequirementCode(VALID_REQ_CODE_IP)
                                           .withTitle(VALID_REQ_TITLE_IP)
                                           .withCreditsOneParameter(VALID_REQ_CREDITS_IP)
                                           .build();
        Command command = new RequirementAddCommand(validRequirement);
        String successMsg = String.format(MESSAGE_REQUIREMENT_ADD_SUCCESS, validRequirement);
        command.execute(modelStubSuccess);

        assertCommandSuccess(command, modelStub, successMsg, modelStubSuccess);
    }

    @Test
    public void execute_duplicateRequirement_throwsCommandException() {
        Requirement requirement = new RequirementBuilder().build();
        RequirementAddCommand command = new RequirementAddCommand(requirement);
        ModelStub modelStub = new ModelStubWithRequirement(requirement);

        assertThrows(CommandException.class,
            RequirementAddCommand.MESSAGE_REQUIREMENT_DUPLICATE, () -> command.execute(modelStub));
    }

    @Test
    public void equals() {
        Requirement requirement = new RequirementBuilder().build();
        Requirement requirementWithDifferentCode = new RequirementBuilder()
                                                       .withRequirementCode(VALID_REQ_CODE_IP)
                                                       .build();
        Requirement requirementWithDifferentTitle = new RequirementBuilder()
                                                        .withTitle(VALID_REQ_TITLE_IP)
                                                        .build();
        Requirement requirementWithDifferentCredits = new RequirementBuilder()
                                                          .withCreditsOneParameter(VALID_REQ_CREDITS_IP)
                                                          .build();
        RequirementAddCommand command = new RequirementAddCommand(requirement);
        RequirementAddCommand commandWithSameRequirement = new RequirementAddCommand(requirement);
        RequirementAddCommand commandWithDifferentCode = new RequirementAddCommand(requirementWithDifferentCode);
        RequirementAddCommand commandWithDifferentTitle = new RequirementAddCommand(requirementWithDifferentTitle);
        RequirementAddCommand commandWithDifferentCredits = new RequirementAddCommand(requirementWithDifferentCredits);

        assertTrue(command.equals(command));
        assertTrue(command.equals(commandWithSameRequirement));
        assertFalse(command.equals(commandWithDifferentCode));
        assertFalse(command.equals(commandWithDifferentTitle));
        assertFalse(command.equals(commandWithDifferentCredits));
    }
}
