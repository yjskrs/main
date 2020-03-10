package igrad.logic.commands;

import static igrad.logic.commands.CommandTestUtil.*;
import static igrad.testutil.TypicalIndexes.INDEX_FIRST_MODULE;
import static igrad.testutil.TypicalIndexes.INDEX_SECOND_MODULE;
import static igrad.testutil.TypicalModules.getTypicalCourseBook;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import igrad.commons.core.Messages;
import igrad.commons.core.index.Index;
import igrad.logic.commands.EditCommand.EditModuleDescriptor;
import igrad.model.CourseBook;
import igrad.model.Model;
import igrad.model.ModelManager;
import igrad.model.UserPrefs;
import igrad.model.module.Module;
import igrad.testutil.EditModuleDescriptorBuilder;
import igrad.testutil.ModuleBuilder;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for EditCommand.
 */
public class EditCommandTest {

    private Model model = new ModelManager(getTypicalCourseBook(), new UserPrefs());

    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() {
        Module editedModule = new ModuleBuilder().build();
        EditCommand.EditModuleDescriptor descriptor = new EditModuleDescriptorBuilder(editedModule).build();
        EditCommand editCommand = new EditCommand(INDEX_FIRST_MODULE, descriptor);

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_MODULE_SUCCESS, editedModule);

        Model expectedModel = new ModelManager(new CourseBook(model.getCourseBook()), new UserPrefs());
        expectedModel.setModule(model.getFilteredModuleList().get(0), editedModule);

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_someFieldsSpecifiedUnfilteredList_success() {
        Index indexLastPerson = Index.fromOneBased(model.getFilteredModuleList().size());
        Module lastModule = model.getFilteredModuleList().get(indexLastPerson.getZeroBased());

        ModuleBuilder personInList = new ModuleBuilder(lastModule);
        Module editedModule = personInList.withTitle(VALID_TITLE_COMPUTER_ORGANISATION)
                .withModuleCode(VALID_MODULE_CODE_COMPUTER_ORGANISATION)
                .withCredits(VALID_CREDITS_COMPUTER_ORGANISATION)
                .withMemo(VALID_MEMO_COMPUTER_ORGANISATION)
                .withSemester(VALID_SEMESTER_COMPUTER_ORGANISATION)
                .withTags(VALID_TAG_HARD)
                .build();

        EditModuleDescriptor descriptor = new EditModuleDescriptorBuilder()
                .withModuleCode(VALID_MODULE_CODE_COMPUTER_ORGANISATION)
                .withCredits(VALID_CREDITS_COMPUTER_ORGANISATION)
                .withMemo(VALID_MEMO_COMPUTER_ORGANISATION)
                .withSemester(VALID_SEMESTER_COMPUTER_ORGANISATION)
                .withTags(VALID_TAG_HARD)
                .build();
        EditCommand editCommand = new EditCommand(indexLastPerson, descriptor);

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_MODULE_SUCCESS, editedModule);

        Model expectedModel = new ModelManager(new CourseBook(model.getCourseBook()), new UserPrefs());
        expectedModel.setModule(lastModule, editedModule);

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_noFieldSpecifiedUnfilteredList_success() {
        EditCommand editCommand = new EditCommand(INDEX_FIRST_MODULE,
                new EditCommand.EditModuleDescriptor());
        Module editedModule = model.getFilteredModuleList().get(INDEX_FIRST_MODULE.getZeroBased());

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_MODULE_SUCCESS, editedModule);

        Model expectedModel = new ModelManager(new CourseBook(model.getCourseBook()), new UserPrefs());

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_filteredList_success() {
        showModuleAtIndex(model, INDEX_FIRST_MODULE);

        Module moduleInFilteredList = model.getFilteredModuleList()
                .get(INDEX_FIRST_MODULE.getZeroBased());
        Module editedModule = new ModuleBuilder(moduleInFilteredList).withTitle(VALID_TITLE_COMPUTER_ORGANISATION)
                .build();
        EditCommand editCommand = new EditCommand(INDEX_FIRST_MODULE,
                new EditModuleDescriptorBuilder().withTitle(VALID_TITLE_COMPUTER_ORGANISATION).build());

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_MODULE_SUCCESS, editedModule);

        Model expectedModel = new ModelManager(new CourseBook(model.getCourseBook()), new UserPrefs());
        expectedModel.setModule(model.getFilteredModuleList().get(0), editedModule);

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_duplicatePersonUnfilteredList_failure() {
        Module firstModule = model.getFilteredModuleList().get(INDEX_FIRST_MODULE.getZeroBased());
        EditModuleDescriptor descriptor = new EditModuleDescriptorBuilder(firstModule).build();
        EditCommand editCommand = new EditCommand(INDEX_SECOND_MODULE, descriptor);

        assertCommandFailure(editCommand, model, EditCommand.MESSAGE_DUPLICATE_MODULE);
    }

    @Test
    public void execute_duplicatePersonFilteredList_failure() {
        showModuleAtIndex(model, INDEX_FIRST_MODULE);

        // edit module in filtered list into a duplicate in course book
        Module moduleInList = model.getCourseBook().getModuleList()
                .get(INDEX_SECOND_MODULE.getZeroBased());
        EditCommand editCommand = new EditCommand(INDEX_FIRST_MODULE,
                new EditModuleDescriptorBuilder(moduleInList).build());

        assertCommandFailure(editCommand, model, EditCommand.MESSAGE_DUPLICATE_MODULE);
    }

    @Test
    public void execute_invalidPersonIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredModuleList().size() + 1);
        EditModuleDescriptor descriptor = new EditModuleDescriptorBuilder()
                .withTitle(VALID_TITLE_COMPUTER_ORGANISATION)
                .build();
        EditCommand editCommand = new EditCommand(outOfBoundIndex, descriptor);

        assertCommandFailure(editCommand, model, Messages.MESSAGE_INVALID_MODULE_DISPLAYED_INDEX);
    }

    /**
     * Edit filtered list where index is larger than size of filtered list,
     * but smaller than size of course book
     */
    @Test
    public void execute_invalidPersonIndexFilteredList_failure() {
        showModuleAtIndex(model, INDEX_FIRST_MODULE);
        Index outOfBoundIndex = INDEX_SECOND_MODULE;
        // ensures that outOfBoundIndex is still in bounds of course book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getCourseBook().getModuleList().size());

        EditCommand editCommand = new EditCommand(outOfBoundIndex,
                new EditModuleDescriptorBuilder().withTitle(VALID_TITLE_COMPUTER_ORGANISATION).build());

        assertCommandFailure(editCommand, model, Messages.MESSAGE_INVALID_MODULE_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        final EditCommand standardCommand = new EditCommand(INDEX_FIRST_MODULE, DESC_PROGRAMMING_METHODOLOGY);

        // same values -> returns true
        EditModuleDescriptor copyDescriptor = new EditModuleDescriptor(DESC_PROGRAMMING_METHODOLOGY);
        EditCommand commandWithSameValues = new EditCommand(INDEX_FIRST_MODULE, copyDescriptor);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new EditCommand(INDEX_SECOND_MODULE, DESC_PROGRAMMING_METHODOLOGY)));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new EditCommand(INDEX_FIRST_MODULE, DESC_COMPUTER_ORGANISATION)));
    }

}
