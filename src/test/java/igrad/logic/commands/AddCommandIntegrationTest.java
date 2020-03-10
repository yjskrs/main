package igrad.logic.commands;

import static igrad.logic.commands.CommandTestUtil.assertCommandSuccess;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import igrad.model.Model;
import igrad.model.ModelManager;
import igrad.model.UserPrefs;
import igrad.model.module.Module;
import igrad.testutil.ModuleBuilder;
import igrad.testutil.TypicalModules;

/**
 * Contains integration tests (interaction with the Model) for {@code ModuleAddCommand}.
 */
public class AddCommandIntegrationTest {

    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(TypicalModules.getTypicalCourseBook(), new UserPrefs());
    }

    @Test
    public void execute_newPerson_success() {
        Module validModule = new ModuleBuilder().build();

        Model expectedModel = new ModelManager(model.getCourseBook(), new UserPrefs());
        expectedModel.addModule(validModule);

        assertCommandSuccess(new ModuleAddCommand(validModule), model,
                String.format(ModuleAddCommand.MESSAGE_SUCCESS, validModule), expectedModel);
    }

    @Test
    public void execute_duplicatePerson_throwsCommandException() {
        Module moduleInList = model.getCourseBook().getModuleList().get(0);
        CommandTestUtil.assertCommandFailure(new ModuleAddCommand(moduleInList), model, ModuleAddCommand.MESSAGE_DUPLICATE_MODULE);
    }

}
