package igrad.logic.commands.requirement;

//@@author yjskrs

import static igrad.logic.commands.CommandTestUtil.assertCommandFailure;
import static igrad.logic.commands.requirement.RequirementCommand.MESSAGE_REQUIREMENT_NON_EXISTENT;
import static igrad.logic.commands.requirement.RequirementCommandTestUtil.VALID_REQ_CODE_CSF;
import static igrad.logic.commands.requirement.RequirementCommandTestUtil.VALID_REQ_CODE_IP;
import static igrad.logic.commands.requirement.RequirementCommandTestUtil.VALID_REQ_CREDITS_GE;
import static igrad.logic.commands.requirement.RequirementCommandTestUtil.VALID_REQ_CREDITS_MS;
import static igrad.logic.commands.requirement.RequirementCommandTestUtil.VALID_REQ_TITLE_GE;
import static igrad.logic.commands.requirement.RequirementCommandTestUtil.VALID_REQ_TITLE_MS;
import static igrad.logic.commands.requirement.RequirementCommandTestUtil.assertCommandSuccess;
import static igrad.logic.commands.requirement.RequirementCommandTestUtil.assertCommandThrows;
import static igrad.logic.commands.requirement.RequirementEditCommand.EditRequirementDescriptor;
import static igrad.logic.commands.requirement.RequirementEditCommand.MESSAGE_REQUIREMENT_EDIT_SUCCESS;
import static igrad.logic.commands.requirement.RequirementEditCommand.MESSAGE_REQUIREMENT_NOT_EDITED;
import static igrad.testutil.TypicalRequirements.CS_FOUNDATION;
import static igrad.testutil.TypicalRequirements.GENERAL_ELECTIVES;
import static igrad.testutil.TypicalRequirements.IT_PROFESSIONALISM;
import static igrad.testutil.TypicalRequirements.getTypicalCourseBook;

import org.junit.jupiter.api.Test;

import igrad.model.CourseBook;
import igrad.model.Model;
import igrad.model.ModelManager;
import igrad.model.UserPrefs;
import igrad.model.requirement.Requirement;
import igrad.model.requirement.RequirementCode;
import igrad.testutil.EditRequirementDescriptorBuilder;
import igrad.testutil.RequirementBuilder;

/**
 * Contains integration tests with the model for {@code RequirementEditCommand}.
 */
public class RequirementEditCommandIntegrationTest {
    private Model model = new ModelManager(getTypicalCourseBook(), new UserPrefs());
    private Requirement requirement = CS_FOUNDATION;
    private RequirementCode code = new RequirementCode(VALID_REQ_CODE_CSF);

    @Test
    public void execute_allFieldsSpecified_success() {
        Model expectedModel = new ModelManager(new CourseBook(model.getCourseBook()), new UserPrefs());
        Requirement editedRequirement = new RequirementBuilder(requirement).withTitle(VALID_REQ_TITLE_MS)
                                            .withCreditsOneParameter(VALID_REQ_CREDITS_MS).build();
        EditRequirementDescriptor descriptor = new EditRequirementDescriptorBuilder().withTitle(VALID_REQ_TITLE_MS)
                                                   .withCredits(VALID_REQ_CREDITS_MS).build();

        expectedModel.setRequirement(model.getRequirement(code).get(), editedRequirement);
        RequirementEditCommand command = new RequirementEditCommand(code, descriptor);
        String expectedMessage = String.format(MESSAGE_REQUIREMENT_EDIT_SUCCESS, editedRequirement);

        assertCommandSuccess(command, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_someFieldsSpecified_success() {
        Requirement editedTitle = new RequirementBuilder(requirement).withTitle(VALID_REQ_TITLE_MS).build();
        Requirement editedCredits = new RequirementBuilder(requirement)
                                        .withCreditsOneParameter(VALID_REQ_CREDITS_MS).build();
        EditRequirementDescriptor descriptorTitle = new EditRequirementDescriptorBuilder()
                                                        .withTitle(VALID_REQ_TITLE_MS).build();
        EditRequirementDescriptor descriptorCredits = new EditRequirementDescriptorBuilder()
                                                        .withCredits(VALID_REQ_CREDITS_MS).build();

        Model expectedModel = new ModelManager(new CourseBook(model.getCourseBook()), new UserPrefs());
        expectedModel.setRequirement(model.getRequirement(code).get(), editedTitle);
        RequirementEditCommand command = new RequirementEditCommand(code, descriptorTitle);
        String expectedMessage = String.format(MESSAGE_REQUIREMENT_EDIT_SUCCESS, editedTitle);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);

        model = new ModelManager(getTypicalCourseBook(), new UserPrefs());
        expectedModel = new ModelManager(new CourseBook(model.getCourseBook()), new UserPrefs());
        expectedModel.setRequirement(model.getRequirement(code).get(), editedCredits);
        command = new RequirementEditCommand(code, descriptorCredits);
        expectedMessage = String.format(MESSAGE_REQUIREMENT_EDIT_SUCCESS, editedCredits);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_noFieldSpecified_modelNotUpdated() {
        EditRequirementDescriptor descriptor = new EditRequirementDescriptor();
        Model expectedModel = new ModelManager(new CourseBook(model.getCourseBook()), new UserPrefs());
        RequirementEditCommand command = new RequirementEditCommand(code, descriptor);
        String expectedMessage = MESSAGE_REQUIREMENT_NOT_EDITED;

        assertCommandThrows(command, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_duplicateRequirementTitleAndCredits_success() {
        EditRequirementDescriptor descriptor = new EditRequirementDescriptorBuilder(GENERAL_ELECTIVES).build();
        RequirementEditCommand command = new RequirementEditCommand(code, descriptor);
        Requirement editedRequirement = new RequirementBuilder(CS_FOUNDATION)
                                            .withTitle(VALID_REQ_TITLE_GE)
                                            .withCreditsOneParameter(VALID_REQ_CREDITS_GE)
                                            .build();

        Model expectedModel = new ModelManager(new CourseBook(model.getCourseBook()), new UserPrefs());
        expectedModel.setRequirement(requirement, editedRequirement);

        String expectedMessage = String.format(MESSAGE_REQUIREMENT_EDIT_SUCCESS, editedRequirement);

        assertCommandSuccess(command, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_nonExistentRequirementCode_throwsCommandException() {
        EditRequirementDescriptor descriptor = new EditRequirementDescriptorBuilder(IT_PROFESSIONALISM).build();
        RequirementCode invalidCode = new RequirementCode(VALID_REQ_CODE_IP);
        RequirementEditCommand command = new RequirementEditCommand(invalidCode, descriptor);

        assertCommandFailure(command, model, MESSAGE_REQUIREMENT_NON_EXISTENT);
    }
}
