package igrad.logic.commands.module;

//@@author waynewee

import static igrad.logic.commands.module.ModuleCommandTestUtil.VALID_MODULE_CODE_CS2040;
import static igrad.logic.commands.module.ModuleCommandTestUtil.VALID_MODULE_CODE_CS2103T;
import static igrad.logic.commands.module.ModuleCommandTestUtil.VALID_MODULE_CREDITS_4;
import static igrad.logic.commands.module.ModuleCommandTestUtil.VALID_MODULE_CREDITS_6;
import static igrad.logic.commands.module.ModuleCommandTestUtil.VALID_MODULE_GRADE_A;
import static igrad.logic.commands.module.ModuleCommandTestUtil.VALID_MODULE_SEMESTER_Y1S1;
import static igrad.logic.commands.module.ModuleCommandTestUtil.VALID_MODULE_SEMESTER_Y2S2;
import static igrad.logic.commands.module.ModuleCommandTestUtil.VALID_MODULE_TITLE_CS2040;
import static igrad.logic.commands.module.ModuleCommandTestUtil.VALID_MODULE_TITLE_CS2100;
import static igrad.logic.commands.module.ModuleEditCommand.MESSAGE_MODULE_EDIT_SUCCESS;
import static igrad.logic.commands.module.ModuleEditCommand.MESSAGE_MODULE_NON_EXISTENT;
import static igrad.testutil.TypicalModules.getEmptyCourseBook;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Test;

import igrad.logic.commands.CommandResult;
import igrad.logic.commands.exceptions.CommandException;
import igrad.logic.commands.module.ModuleEditCommand.EditModuleDescriptor;
import igrad.model.Model;
import igrad.model.ModelManager;
import igrad.model.UserPrefs;
import igrad.model.module.Module;
import igrad.model.module.ModuleCode;
import igrad.testutil.EditModuleDescriptorBuilder;
import igrad.testutil.ModuleBuilder;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests
 * for ModuleEditCommand.
 */
public class ModuleEditCommandTest {

    private Model model = new ModelManager(getEmptyCourseBook(), new UserPrefs());

    private Module cs2040 = new ModuleBuilder()
        .withModuleCode(VALID_MODULE_CODE_CS2040)
        .withTitle(VALID_MODULE_TITLE_CS2040)
        .withCredits(VALID_MODULE_CREDITS_6)
        .withoutOptionals()
        .withSemester(VALID_MODULE_SEMESTER_Y1S1)
        .withGrade(VALID_MODULE_GRADE_A)
        .build();

    @Test
    public void execute_allFieldsSpecified_success() throws CommandException {

        Module toBeEdited = cs2040;

        EditModuleDescriptor descriptor = new EditModuleDescriptorBuilder()
            .withModuleCode(toBeEdited.getModuleCode().value)
            .withTitle(VALID_MODULE_TITLE_CS2100)
            .withCredits(VALID_MODULE_CREDITS_4)
            .withSemester(VALID_MODULE_SEMESTER_Y2S2)
            .build();


        /**
         * The edited module should have the fields specified in the descriptor
         * overwritten
         */
        Module editedModule = new ModuleBuilder(toBeEdited)
            .withTitle(VALID_MODULE_TITLE_CS2100)
            .withCredits(VALID_MODULE_CREDITS_4)
            .withSemester(VALID_MODULE_SEMESTER_Y2S2)
            .build();

        model.addModule(toBeEdited);

        ModuleEditCommand editCommand = new ModuleEditCommand(toBeEdited.getModuleCode(), descriptor);

        CommandResult expectedMessage = new CommandResult(String.format(MESSAGE_MODULE_EDIT_SUCCESS, editedModule));

        Model expectedModel = new ModelManager(getEmptyCourseBook(), new UserPrefs());

        expectedModel.addModule(editedModule);

        CommandResult actualMessage = editCommand.execute(model);

        assertEquals(expectedMessage, actualMessage);
        assertEquals(model.getFilteredModuleList(), expectedModel.getFilteredModuleList());
    }

    @Test
    public void execute_someFieldsSpecifiedUnfilteredList_success() throws CommandException {

        Module toBeEdited = cs2040;

        EditModuleDescriptor descriptor = new EditModuleDescriptorBuilder()
            .withModuleCode(toBeEdited.getModuleCode().value)
            .withTitle(VALID_MODULE_TITLE_CS2100)
            .build();


        /**
         * The edited module should have the fields specified in the descriptor
         * overwritten
         */
        Module editedModule = new ModuleBuilder(toBeEdited)
            .withTitle(VALID_MODULE_TITLE_CS2100)
            .build();

        model.addModule(toBeEdited);

        ModuleEditCommand editCommand = new ModuleEditCommand(toBeEdited.getModuleCode(), descriptor);

        CommandResult expectedMessage = new CommandResult(String.format(MESSAGE_MODULE_EDIT_SUCCESS, editedModule));

        Model expectedModel = new ModelManager(getEmptyCourseBook(), new UserPrefs());

        expectedModel.addModule(editedModule);

        CommandResult actualMessage = editCommand.execute(model);

        assertEquals(expectedMessage, actualMessage);
        assertEquals(model.getFilteredModuleList(), expectedModel.getFilteredModuleList());
    }

    @Test
    public void execute_noFieldSpecifiedUnfilteredList_success() throws CommandException {

        Module toBeEdited = cs2040;

        EditModuleDescriptor descriptor = new EditModuleDescriptorBuilder()
            .withModuleCode(toBeEdited.getModuleCode().value)
            .build();


        /**
         * The edited module should have the fields specified in the descriptor
         * overwritten
         */
        Module editedModule = new ModuleBuilder(toBeEdited)
            .build();

        model.addModule(toBeEdited);

        ModuleEditCommand editCommand = new ModuleEditCommand(toBeEdited.getModuleCode(), descriptor);

        CommandResult expectedMessage = new CommandResult(String.format(MESSAGE_MODULE_EDIT_SUCCESS, editedModule));

        Model expectedModel = new ModelManager(getEmptyCourseBook(), new UserPrefs());

        expectedModel.addModule(editedModule);

        CommandResult actualMessage = editCommand.execute(model);

        assertEquals(expectedMessage, actualMessage);
        assertEquals(model.getFilteredModuleList(), expectedModel.getFilteredModuleList());
    }

    @Test
    public void execute_moduleCodeNotFound_failure() {
        Module toBeEdited = cs2040;

        EditModuleDescriptor descriptor = new EditModuleDescriptorBuilder()
            .withModuleCode(toBeEdited.getModuleCode().value)
            .withTitle(VALID_MODULE_TITLE_CS2100)
            .build();


        ModuleEditCommand editCommand = new ModuleEditCommand(toBeEdited.getModuleCode(), descriptor);

        CommandResult expectedMessage = new CommandResult(
            String.format(
                MESSAGE_MODULE_NON_EXISTENT, toBeEdited.getModuleCode().value
            )
        );

        /**
         * note that the module is not added to the model
         */

        try {

            editCommand.execute(model);

        } catch (CommandException ce) {
            assertEquals(expectedMessage, new CommandResult(ce.getMessage()));
        }


    }


    @Test
    public void equals() {

        EditModuleDescriptor descriptor = new EditModuleDescriptorBuilder()
            .build();


        final ModuleEditCommand standardCommand = new ModuleEditCommand(
            new ModuleCode(VALID_MODULE_CODE_CS2040),
            descriptor
        );

        // same values -> returns true
        EditModuleDescriptor copyDescriptor = new EditModuleDescriptor();
        ModuleEditCommand commandWithSameValues = new ModuleEditCommand(
            new ModuleCode(VALID_MODULE_CODE_CS2040),
            copyDescriptor
        );

        assertEquals(standardCommand, commandWithSameValues);

        // same object -> returns true
        assertEquals(standardCommand, standardCommand);

        // null -> returns false
        assertNotEquals(standardCommand, null);


        // different index -> returns false
        assertNotEquals(standardCommand, new ModuleEditCommand(new ModuleCode(VALID_MODULE_CODE_CS2103T), descriptor));

    }

}
