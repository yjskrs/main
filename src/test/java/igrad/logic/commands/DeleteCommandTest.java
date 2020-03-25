package igrad.logic.commands;

import static igrad.testutil.TypicalModules.getTypicalCourseBook;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import igrad.model.Model;
import igrad.model.ModelManager;
import igrad.model.UserPrefs;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for
 * {@code ModuleDeleteCommand}.
 */
public class DeleteCommandTest {

    private Model model = new ModelManager(getTypicalCourseBook(), new UserPrefs());

    @Test
    public void execute_validIndexUnfilteredList_success() {
        /*Module moduleToDelete = model.getFilteredModuleList().get(INDEX_FIRST_MODULE.getZeroBased());
        ModuleDeleteCommand deleteCommand = new ModuleDeleteCommand(INDEX_FIRST_MODULE);

        String expectedMessage = String.format(ModuleDeleteCommand.MESSAGE_DELETE_MODULE_SUCCESS, moduleToDelete);

        ModelManager expectedModel = new ModelManager(model.getCourseBook(), new UserPrefs());
        expectedModel.deleteModule(moduleToDelete);

        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
         */
    }

    /*
    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredModuleList().size() + 1);
        ModuleDeleteCommand deleteCommand = new ModuleDeleteCommand(outOfBoundIndex);

        assertCommandFailure(deleteCommand, model, Messages.MESSAGE_INVALID_MODULE_DISPLAYED_INDEX);
    }
    */

    @Test
    public void execute_validIndexFilteredList_success() {
        /*showModuleAtIndex(model, INDEX_FIRST_MODULE);

        Module moduleToDelete = model.getFilteredModuleList().get(INDEX_FIRST_MODULE.getZeroBased());
        ModuleDeleteCommand deleteCommand = new ModuleDeleteCommand(INDEX_FIRST_MODULE);

        String expectedMessage = String.format(ModuleDeleteCommand.MESSAGE_DELETE_MODULE_SUCCESS, moduleToDelete);

        Model expectedModel = new ModelManager(model.getCourseBook(), new UserPrefs());
        expectedModel.deleteModule(moduleToDelete);
        showNoPerson(expectedModel);

        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
         */
    }

    /*
    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showModuleAtIndex(model, INDEX_FIRST_MODULE);

        Index outOfBoundIndex = INDEX_SECOND_MODULE;
        // ensures that outOfBoundIndex is still in bounds of course book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getCourseBook().getModuleList().size());

        ModuleDeleteCommand deleteCommand = new ModuleDeleteCommand(outOfBoundIndex);

        assertCommandFailure(deleteCommand, model, Messages.MESSAGE_INVALID_MODULE_DISPLAYED_INDEX);
    }*/

    /*
    @Test
    public void equals() {
        ModuleDeleteCommand deleteFirstCommand = new ModuleDeleteCommand(INDEX_FIRST_MODULE);
        ModuleDeleteCommand deleteSecondCommand = new ModuleDeleteCommand(INDEX_SECOND_MODULE);

        // same object -> returns true
        assertTrue(deleteFirstCommand.equals(deleteFirstCommand));

        // same values -> returns true
        ModuleDeleteCommand deleteFirstCommandCopy = new ModuleDeleteCommand(INDEX_FIRST_MODULE);
        assertTrue(deleteFirstCommand.equals(deleteFirstCommandCopy));

        // different types -> returns false
        assertFalse(deleteFirstCommand.equals(1));

        // null -> returns false
        assertFalse(deleteFirstCommand.equals(null));

        // different module -> returns false
        assertFalse(deleteFirstCommand.equals(deleteSecondCommand));
    }*/

    /**
     * Updates {@code model}'s filtered list to show no one.
     */
    private void showNoPerson(Model model) {
        model.updateFilteredModuleList(p -> false);

        assertTrue(model.getFilteredModuleList().isEmpty());
    }
}
