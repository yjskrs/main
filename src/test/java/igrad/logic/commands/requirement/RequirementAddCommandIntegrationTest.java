package igrad.logic.commands.requirement;

import static igrad.logic.commands.CommandTestUtil.assertCommandFailure;
import static igrad.logic.commands.CommandTestUtil.assertCommandSuccess;
import static igrad.logic.commands.requirement.RequirementAddCommand.MESSAGE_REQUIREMENT_ADD_SUCCESS;
import static igrad.logic.commands.requirement.RequirementAddCommand.MESSAGE_REQUIREMENT_DUPLICATE;
import static igrad.logic.commands.requirement.RequirementCommandTestUtil.VALID_REQ_CODE_CSBD;
import static igrad.logic.commands.requirement.RequirementCommandTestUtil.VALID_REQ_CREDITS_CSBD;
import static igrad.logic.commands.requirement.RequirementCommandTestUtil.VALID_REQ_TITLE_CSBD;
import static igrad.logic.commands.requirement.RequirementCommandTestUtil.setupCourseInfo;
import static igrad.testutil.TypicalRequirements.CS_FOUNDATION;
import static igrad.testutil.TypicalRequirements.getTypicalCourseBook;

import org.junit.jupiter.api.Test;

import igrad.model.Model;
import igrad.model.ModelManager;
import igrad.model.UserPrefs;
import igrad.model.requirement.Requirement;
import igrad.testutil.RequirementBuilder;

/**
 * Contains integration tests with the model for {@code RequirementAddCommand}.
 */
public class RequirementAddCommandIntegrationTest {
    private Model model = new ModelManager(getTypicalCourseBook(), new UserPrefs());

    @Test
    public void execute_addNewRequirement_success() {
        Requirement requirement = new RequirementBuilder()
                                      .withRequirementCode(VALID_REQ_CODE_CSBD)
                                      .withTitle(VALID_REQ_TITLE_CSBD)
                                      .withCreditsOneParameter(VALID_REQ_CREDITS_CSBD)
                                      .build();
        Model expectedModel = new ModelManager(model.getCourseBook(), new UserPrefs());
        expectedModel.addRequirement(requirement);
        RequirementAddCommand command = new RequirementAddCommand(requirement);
        String message = String.format(MESSAGE_REQUIREMENT_ADD_SUCCESS, requirement);
        setupCourseInfo(expectedModel);
        assertCommandSuccess(command, model, message, expectedModel);
    }

    @Test
    public void execute_duplicateRequirement_throwsCommandException() {
        Requirement requirement = CS_FOUNDATION;
        RequirementAddCommand command = new RequirementAddCommand(requirement);
        String message = MESSAGE_REQUIREMENT_DUPLICATE;
        assertCommandFailure(command, model, message);
    }
}
