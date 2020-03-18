package igrad.logic;

import static igrad.testutil.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.nio.file.Path;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import igrad.logic.commands.CommandResult;
import igrad.logic.commands.exceptions.CommandException;
import igrad.logic.parser.exceptions.ParseException;
import igrad.model.Model;
import igrad.model.ModelManager;
import igrad.model.ReadOnlyCourseBook;
import igrad.model.UserPrefs;
import igrad.services.exceptions.ServiceException;
import igrad.storage.JsonCourseBookStorage;
import igrad.storage.JsonUserPrefsStorage;
import igrad.storage.StorageManager;

public class LogicManagerTest {
    private static final IOException DUMMY_IO_EXCEPTION = new IOException("dummy exception");

    @TempDir
    public Path temporaryFolder;

    private Model model = new ModelManager();
    private Logic logic;

    @BeforeEach
    public void setUp() {
        JsonCourseBookStorage courseBookStorage =
            new JsonCourseBookStorage(temporaryFolder.resolve("courseBook.json"));
        JsonUserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(temporaryFolder.resolve("userPrefs.json"));
        StorageManager storage = new StorageManager(courseBookStorage, userPrefsStorage);
        logic = new LogicManager(model, storage);
    }

    @Test
    public void execute_invalidCommandFormat_throwsParseException() {
        /*String invalidCommand = "uicfhmowqewca";
        assertParseException(invalidCommand, MESSAGE_UNKNOWN_COMMAND);
         */
    }

    @Test
    public void execute_commandExecutionError_throwsCommandException() {
        /*String deleteCommand = "delete 9";
        assertCommandException(deleteCommand, MESSAGE_INVALID_MODULE_DISPLAYED_INDEX);
         */
    }

    @Test
    public void execute_storageThrowsIoException_throwsCommandException() {
        /*
        // Setup LogicManager with JsonCourseBookIoExceptionThrowingStub
        JsonCourseBookStorage courseBookStorage =
            new JsonCourseBookIoExceptionThrowingStub(temporaryFolder.resolve("ioExceptionCourseBook.json"));
        JsonUserPrefsStorage userPrefsStorage =
            new JsonUserPrefsStorage(temporaryFolder.resolve("ioExceptionUserPrefs.json"));
        StorageManager storage = new StorageManager(courseBookStorage, userPrefsStorage);
        logic = new LogicManager(model, storage);

        // Execute add command
        String addCommand = ModuleAddCommand.COMMAND_WORD + TITLE_DESC_PROGRAMMING_METHODOLOGY
            + MODULE_CODE_DESC_PROGRAMMING_METHODOLOGY
            + CREDITS_DESC_PROGRAMMING_METHODOLOGY
            + MEMO_DESC_PROGRAMMING_METHODOLOGY
            + SEMESTER_DESC_PROGRAMMING_METHODOLOGY;
        Module expectedModule = new ModuleBuilder(TypicalModules.PROGRAMMING_METHODOLOGY).withTags().build();
        ModelManager expectedModel = new ModelManager();
        expectedModel.addModule(expectedModule);
        String expectedMessage = LogicManager.FILE_OPS_ERROR_MESSAGE + DUMMY_IO_EXCEPTION;
        assertCommandFailure(addCommand, CommandException.class, expectedMessage, expectedModel);
         */
    }

    @Test
    public void getFilteredPersonList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> logic.getFilteredModuleList().remove(0));
    }

    /**
     * Executes the command and confirms that
     * - no exceptions are thrown <br>
     * - the feedback message is equal to {@code expectedMessage} <br>
     * - the internal model manager state is the same as that in {@code expectedModel} <br>
     *
     * @see #assertCommandFailure(String, Class, String, Model)
     */
    private void assertCommandSuccess(
        String inputCommand,
        String expectedMessage,
        Model expectedModel
    ) throws CommandException, ParseException, IOException, ServiceException {
        CommandResult result = logic.execute(inputCommand);
        assertEquals(expectedMessage, result.getFeedbackToUser());
        assertEquals(expectedModel, model);
    }

    /**
     * Executes the command, confirms that a ParseException is thrown and that the result message is correct.
     *
     * @see #assertCommandFailure(String, Class, String, Model)
     */
    private void assertParseException(String inputCommand, String expectedMessage) {
        assertCommandFailure(inputCommand, ParseException.class, expectedMessage);
    }

    /**
     * Executes the command, confirms that a CommandException is thrown and that the result message is correct.
     *
     * @see #assertCommandFailure(String, Class, String, Model)
     */
    private void assertCommandException(String inputCommand, String expectedMessage) {
        assertCommandFailure(inputCommand, CommandException.class, expectedMessage);
    }

    /**
     * Executes the command, confirms that the exception is thrown and that the result message is correct.
     *
     * @see #assertCommandFailure(String, Class, String, Model)
     */
    private void assertCommandFailure(String inputCommand, Class<? extends Throwable> expectedException,
                                      String expectedMessage) {
        Model expectedModel = new ModelManager(model.getCourseBook(), new UserPrefs());
        assertCommandFailure(inputCommand, expectedException, expectedMessage, expectedModel);
    }

    /**
     * Executes the command and confirms that
     * - the {@code expectedException} is thrown <br>
     * - the resulting error message is equal to {@code expectedMessage} <br>
     * - the internal model manager state is the same as that in {@code expectedModel} <br>
     *
     * @see #assertCommandSuccess(String, String, Model)
     */
    private void assertCommandFailure(String inputCommand, Class<? extends Throwable> expectedException,
                                      String expectedMessage, Model expectedModel) {
        assertThrows(expectedException, expectedMessage, () -> logic.execute(inputCommand));
        assertEquals(expectedModel, model);
    }

    /**
     * A stub class to throw an {@code IOException} when the save method is called.
     */
    private static class JsonCourseBookIoExceptionThrowingStub extends JsonCourseBookStorage {
        private JsonCourseBookIoExceptionThrowingStub(Path filePath) {
            super(filePath);
        }

        @Override
        public void saveCourseBook(ReadOnlyCourseBook courseBook, Path filePath) throws IOException {
            throw DUMMY_IO_EXCEPTION;
        }
    }
}
