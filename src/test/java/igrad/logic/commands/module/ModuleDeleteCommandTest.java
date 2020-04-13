package igrad.logic.commands.module;

//@@author waynewee

import static igrad.logic.commands.module.ModuleCommandTestUtil.VALID_MODULE_CODE_CS2040;
import static igrad.logic.commands.module.ModuleCommandTestUtil.VALID_MODULE_CODE_CS2103T;
import static igrad.logic.commands.module.ModuleCommandTestUtil.VALID_MODULE_CREDITS_4;
import static igrad.logic.commands.module.ModuleCommandTestUtil.VALID_MODULE_TITLE_CS2040;
import static igrad.logic.commands.module.ModuleEditCommand.MESSAGE_MODULE_NON_EXISTENT;
import static igrad.testutil.TypicalModules.getEmptyCourseBook;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Test;

import igrad.logic.commands.CommandResult;
import igrad.logic.commands.exceptions.CommandException;
import igrad.model.Model;
import igrad.model.ModelManager;
import igrad.model.UserPrefs;
import igrad.model.module.Module;
import igrad.model.module.ModuleCode;
import igrad.testutil.ModuleBuilder;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests
 * for ModuleEditCommand.
 */
public class ModuleDeleteCommandTest {

    private Model model = new ModelManager(getEmptyCourseBook(), new UserPrefs());

    private Module cs2040 = new ModuleBuilder()
            .withModuleCode(VALID_MODULE_CODE_CS2040)
            .withTitle(VALID_MODULE_TITLE_CS2040)
            .withCredits(VALID_MODULE_CREDITS_4)
            .withoutOptionals()
            .build();

    private Module cs2103t = new ModuleBuilder()
        .withModuleCode(ModuleCommandTestUtil.VALID_MODULE_CODE_CS2103T)
        .withCredits(ModuleCommandTestUtil.VALID_MODULE_CREDITS_4)
        .withTitle(ModuleCommandTestUtil.VALID_MODULE_TITLE_CS2103T)
        .withoutOptionals()
        .build();

    private Module cs2101 = new ModuleBuilder()
        .withModuleCode(ModuleCommandTestUtil.VALID_MODULE_CODE_CS2101)
        .withCredits(ModuleCommandTestUtil.VALID_MODULE_CREDITS_4)
        .withTitle(ModuleCommandTestUtil.VALID_MODULE_TITLE_CS2101)
        .withoutOptionals()
        .build();


    @Test
    public void execute_deleteOne_success() throws CommandException {

        Module moduleToDelete = cs2040;

        model.addModule(moduleToDelete);
        model.addModule(cs2103t);

        ModuleDeleteCommand moduleDeleteCommand = new ModuleDeleteCommand(moduleToDelete.getModuleCode());

        CommandResult result = moduleDeleteCommand.execute(model);
        CommandResult expectedResult = new CommandResult(
            String.format(
                ModuleDeleteCommand.MESSAGE_MODULE_DELETE_SUCCESS,
                moduleToDelete
            )
        );

        Model exepectedModel = new ModelManager(getEmptyCourseBook(), new UserPrefs());
        exepectedModel.addModule(cs2103t);

        assertEquals(result, expectedResult);
        assertEquals(model.getFilteredModuleList(), exepectedModel.getFilteredModuleList());

    }

    @Test
    public void execute_deleteMultiple_success() throws CommandException {

        Module moduleNotToDelete = cs2101;

        model.addModule(cs2040);
        model.addModule(cs2103t);
        model.addModule(moduleNotToDelete);

        ModuleDeleteCommand moduleDeleteCommand = new ModuleDeleteCommand(cs2040.getModuleCode());
        ModuleDeleteCommand moduleDeleteCommand1 = new ModuleDeleteCommand(cs2103t.getModuleCode());

        CommandResult result = moduleDeleteCommand.execute(model);
        CommandResult expectedResult = new CommandResult(
            String.format(
                ModuleDeleteCommand.MESSAGE_MODULE_DELETE_SUCCESS,
                cs2040
            )
        );

        CommandResult result1 = moduleDeleteCommand1.execute(model);
        CommandResult expectedResult1 = new CommandResult(
            String.format(
                ModuleDeleteCommand.MESSAGE_MODULE_DELETE_SUCCESS,
                cs2103t
            )
        );

        Model exepectedModel = new ModelManager(getEmptyCourseBook(), new UserPrefs());
        exepectedModel.addModule(moduleNotToDelete);

        assertEquals(result, expectedResult);
        assertEquals(result1, expectedResult1);
        assertEquals(model.getFilteredModuleList(), exepectedModel.getFilteredModuleList());

    }

    @Test
    public void execute_moduleNotFound_failure() {

        Module moduleToDelete = cs2040;

        model.addModule(cs2101);
        model.addModule(cs2103t);

        ModuleDeleteCommand moduleDeleteCommand = new ModuleDeleteCommand(moduleToDelete.getModuleCode());

        CommandResult expectedResult = new CommandResult(
            String.format(MESSAGE_MODULE_NON_EXISTENT, moduleToDelete.getModuleCode().value)
        );

        Model exepectedModel = new ModelManager(getEmptyCourseBook(), new UserPrefs());

        exepectedModel.addModule(cs2101);
        exepectedModel.addModule(cs2103t);

        try {

            moduleDeleteCommand.execute(model);

        } catch (CommandException ce) {
            assertEquals(new CommandResult(ce.getMessage()), expectedResult);
        } finally {
            assertEquals(model.getFilteredModuleList(), exepectedModel.getFilteredModuleList());
        }



    }

    @Test
    public void equals() {

        final ModuleDeleteCommand standardCommand = new ModuleDeleteCommand(
            new ModuleCode(VALID_MODULE_CODE_CS2040)
        );

        // same values -> returns true
        ModuleDeleteCommand commandWithSameValues = new ModuleDeleteCommand(
            new ModuleCode(VALID_MODULE_CODE_CS2040)
        );

        assertEquals(standardCommand, commandWithSameValues);

        // same object -> returns true
        assertEquals(standardCommand, standardCommand);

        // null -> returns false
        assertNotEquals(standardCommand, null);


        // different index -> returns false
        assertNotEquals(standardCommand, new ModuleDeleteCommand(new ModuleCode(VALID_MODULE_CODE_CS2103T)));

    }

}
