package igrad.logic.commands.requirement;

//@@author yjskrs

import static igrad.logic.commands.CommandTestUtil.assertCommandFailure;
import static igrad.logic.commands.requirement.RequirementCommand.MESSAGE_REQUIREMENT_NON_EXISTENT;
import static igrad.logic.commands.requirement.RequirementCommandTestUtil.VALID_REQ_CODE_CSBD;
import static igrad.logic.commands.requirement.RequirementCommandTestUtil.VALID_REQ_CODE_CSF;
import static igrad.logic.commands.requirement.RequirementCommandTestUtil.VALID_REQ_CODE_IP;
import static igrad.logic.commands.requirement.RequirementCommandTestUtil.assertCommandSuccess;
import static igrad.logic.commands.requirement.RequirementCommandTestUtil.setupCourseInfo;
import static igrad.logic.commands.requirement.RequirementDeleteCommand.MESSAGE_REQUIREMENT_DELETE_SUCCESS;
import static igrad.testutil.TypicalRequirements.CS_FOUNDATION;
import static igrad.testutil.TypicalRequirements.getTypicalCourseBook;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import igrad.model.Model;
import igrad.model.ModelManager;
import igrad.model.UserPrefs;
import igrad.model.requirement.Requirement;
import igrad.model.requirement.RequirementCode;

/**
 * Contains integration tests with the model and unit tests for {@code RequirementDeleteCommand}.
 */

public class RequirementDeleteCommandTest {
    private Model model = new ModelManager(getTypicalCourseBook(), new UserPrefs());
    private final Requirement requirement = CS_FOUNDATION;
    private final RequirementCode code = new RequirementCode(VALID_REQ_CODE_CSF);

    @Test
    public void execute_deleteRequirementInCourseBook_success() {
        RequirementDeleteCommand command = new RequirementDeleteCommand(code);
        String message = String.format(MESSAGE_REQUIREMENT_DELETE_SUCCESS, requirement);

        Model expectedModel = new ModelManager(model.getCourseBook(), new UserPrefs());
        expectedModel.deleteRequirement(requirement);
        setupCourseInfo(expectedModel);

        assertCommandSuccess(command, model, message, expectedModel);
    }

    @Test
    public void execute_nonExistentRequirementCode_throwsCommandException() {
        RequirementCode invalidCode = new RequirementCode(VALID_REQ_CODE_IP);
        RequirementDeleteCommand command = new RequirementDeleteCommand(invalidCode);

        assertCommandFailure(command, model, MESSAGE_REQUIREMENT_NON_EXISTENT);
    }

    @Test
    public void equals() {
        RequirementDeleteCommand command = new RequirementDeleteCommand(code);
        RequirementDeleteCommand commandSame = new RequirementDeleteCommand(code);
        RequirementDeleteCommand commandDifferent =
            new RequirementDeleteCommand(new RequirementCode(VALID_REQ_CODE_CSBD));

        assertTrue(command.equals(command));
        assertTrue(command.equals(commandSame));
        assertFalse(command.equals(commandDifferent));
    }
}
