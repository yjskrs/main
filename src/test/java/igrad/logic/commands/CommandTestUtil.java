package igrad.logic.commands;

import static igrad.logic.parser.CliSyntax.PREFIX_CREDITS;
import static igrad.logic.parser.CliSyntax.PREFIX_MODULE_CODE;
import static igrad.logic.parser.CliSyntax.PREFIX_SEMESTER;
import static igrad.logic.parser.CliSyntax.PREFIX_TITLE;
import static igrad.testutil.Assert.assertThrows;
import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

import igrad.commons.core.GuiSettings;
import igrad.commons.exceptions.DataConversionException;
import igrad.logic.commands.exceptions.CommandException;
import igrad.logic.commands.module.ModuleEditCommand;
import igrad.model.CourseBook;
import igrad.model.Model;
import igrad.model.ModelManager;
import igrad.model.ReadOnlyCourseBook;
import igrad.model.ReadOnlyUserPrefs;
import igrad.model.avatar.Avatar;
import igrad.model.course.CourseInfo;
import igrad.model.module.Module;
import igrad.model.module.ModuleCode;
import igrad.model.requirement.Requirement;
import igrad.model.requirement.RequirementCode;
import igrad.testutil.EditModuleDescriptorBuilder;
import javafx.collections.ObservableList;

/**
 * Contains helper methods for testing commands.
 */
public class CommandTestUtil {
    public static final String VALID_TITLE_CS_1101S = "Programming Methodology";
    public static final String VALID_TITLE_CS_2100 = "Computer Organisation";
    public static final String VALID_TITLE_CS_2103T = "Software Engineering";
    public static final String VALID_TITLE_CS_2101 = "Effective Communication for Computing Professionals";
    public static final String VALID_TITLE_CS_2040 = "Data Structures and Algorithms";
    public static final String VALID_MODULE_CODE_CS_1101S = "CS1101S";
    public static final String VALID_MODULE_CODE_CS_2100 = "CS2100";
    public static final String VALID_MODULE_CODE_CS_2103T = "CS2103T";
    public static final String VALID_MODULE_CODE_CS_2101 = "CS2101";
    public static final String VALID_MODULE_CODE_CS_2040 = "CS2040";
    public static final String VALID_CREDITS_4 = "4";
    public static final String VALID_CREDITS_6 = "6";
    public static final String VALID_GRADE_PROGRAMMING_METHODOLOGY = "A+";
    public static final String VALID_GRADE_COMPUTER_ORGANISATION = "B-";
    public static final String VALID_MEMO_PROGRAMMING_METHODOLOGY = "v easy module leh";
    public static final String VALID_MEMO_COMPUTER_ORGANISATION = "a lot of calculation ah";
    public static final String VALID_SEMESTER_Y1S1 = "Y1S1";
    public static final String VALID_SEMESTER_Y1S2 = "Y1S2";
    public static final String VALID_SEMESTER_Y2S2 = "Y1S1";

    public static final String VALID_TAG_EASY = "easy";
    public static final String VALID_TAG_HARD = "hard";

    public static final String VALID_GRADE_A = "A";
    public static final String VALID_GRADE_B = "B";

    public static final String TITLE_DESC_PROGRAMMING_METHODOLOGY = " " + PREFIX_TITLE
        + VALID_TITLE_CS_1101S;

    public static final String TITLE_DESC_COMPUTER_ORGANISATION = " " + PREFIX_TITLE
        + VALID_TITLE_CS_2100;

    public static final String MODULE_CODE_DESC_PROGRAMMING_METHODOLOGY = " " + PREFIX_MODULE_CODE
        + VALID_MODULE_CODE_CS_1101S;

    public static final String MODULE_CODE_DESC_COMPUTER_ORGANISATION = " " + PREFIX_MODULE_CODE
        + VALID_MODULE_CODE_CS_2100;

    public static final String CREDITS_DESC_PROGRAMMING_METHODOLOGY = " " + PREFIX_CREDITS
        + VALID_CREDITS_4;

    public static final String CREDITS_DESC_COMPUTER_ORGANISATION = " " + PREFIX_CREDITS
        + VALID_CREDITS_6;

    public static final String SEMESTER_DESC_PROGRAMMING_METHODOLOGY = " " + PREFIX_SEMESTER
        + VALID_SEMESTER_Y1S1;

    public static final String SEMESTER_DESC_COMPUTER_ORGANISATION = " " + PREFIX_SEMESTER
        + VALID_SEMESTER_Y2S2;

    // '!' not allowed in module codes
    public static final String INVALID_TITLE_DESC = " " + PREFIX_TITLE + "Programming Methodology!";

    // '&' not allowed in module codes
    public static final String INVALID_MODULE_CODE_DESC = " " + PREFIX_MODULE_CODE + "CS2040S&";

    // '&' not allowed in credits
    public static final String INVALID_CREDITS_DESC = " " + PREFIX_CREDITS + "4%";

    // '&' not allowed in semester
    public static final String INVALID_SEMESTER_DESC = " " + PREFIX_SEMESTER + "4%";

    public static final String PREAMBLE_WHITESPACE = "\t  \r  \n";
    public static final String PREAMBLE_NON_EMPTY = "NonEmptyPreamble";

    public static final ModuleEditCommand.EditModuleDescriptor DESC_PROGRAMMING_METHODOLOGY;
    public static final ModuleEditCommand.EditModuleDescriptor DESC_COMPUTER_ORGANISATION;

    static {
        DESC_PROGRAMMING_METHODOLOGY = new EditModuleDescriptorBuilder()
            .withTitle(VALID_TITLE_CS_1101S)
            .withModuleCode(VALID_MODULE_CODE_CS_1101S)
            .withCredits(VALID_CREDITS_4)
            .withSemester(VALID_SEMESTER_Y1S1).build();

        DESC_COMPUTER_ORGANISATION = new EditModuleDescriptorBuilder()
            .withTitle(VALID_TITLE_CS_2100)
            .withModuleCode(VALID_MODULE_CODE_CS_2100)
            .withCredits(VALID_CREDITS_6)
            .withSemester(VALID_SEMESTER_Y2S2).build();
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - the returned {@link CommandResult} matches {@code expectedCommandResult} <br>
     * - the {@code actualModel} matches {@code expectedModel}
     */
    public static void assertCommandSuccess(Command command, Model actualModel, CommandResult expectedCommandResult,
                                            Model expectedModel) {
        try {
            CommandResult result = command.execute(actualModel);
            assertEquals(expectedCommandResult, result);
            assertEquals(expectedModel, actualModel);
        } catch (CommandException ce) {
            throw new AssertionError("Execution of command should not fail.", ce);
        }
    }


    /**
     * Convenience wrapper to {@link #assertCommandSuccess(Command, Model, CommandResult, Model)}
     * that takes a string {@code expectedMessage}.
     */
    public static void assertCommandSuccess(Command command, Model actualModel, String expectedMessage,
                                            Model expectedModel) {
        CommandResult expectedCommandResult = new CommandResult(expectedMessage);
        assertCommandSuccess(command, actualModel, expectedCommandResult, expectedModel);
    }


    /**
     * Executes the given {@code command}, confirms that <br>
     * - a {@code CommandException} is thrown <br>
     * - the CommandException message matches {@code expectedMessage} <br>
     * - the course book, filtered module list and selected module in {@code actualModel} remain unchanged
     */
    public static void assertCommandFailure(Command command, Model actualModel, String expectedMessage) {
        // we are unable to defensively copy the model for comparison later, so we can
        // only do so by copying its components.
        CourseBook expectedCourseBook = new CourseBook(actualModel.getCourseBook());
        List<Module> expectedFilteredList = new ArrayList<>(actualModel.getFilteredModuleList());

        assertThrows(CommandException.class, expectedMessage, () -> command.execute(actualModel));
        assertEquals(expectedCourseBook, actualModel.getCourseBook());
        assertEquals(expectedFilteredList, actualModel.getFilteredModuleList());
    }

    //@@author nathanaelseen
    /**
     * Asserts that the execution of {@code command} given a {@code model} is successful and the
     * model manipulated is equals to {@code expectedModel}, and the success message is equals to
     * {@code expectedMessage}.
     *
     * @param command         Command object.
     * @param model           User input string.
     * @param expectedModel   Expected model.
     * @param expectedMessage Expected message.
     */
    public static void assertExecuteSuccess(Command command, Model model, Model expectedModel, String expectedMessage) {
        try {
            CommandResult commandResult = command.execute(model);

            // First check the message equals to expected message
            assertEquals(expectedMessage, commandResult.getFeedbackToUser());

            // Next check if the model equals to the expected model
            assertEquals(model, expectedModel);
        } catch (CommandException ce) {
            throw new AssertionError("The CommandException was unexpectedly thrown.");
        }
    }

    /**
     * Asserts that the execution of {@code command} given a {@code model} is unsuccessful and the error message
     * is equals to {@code expectedMessage}.
     *
     * @param command         Command object.
     * @param model           User input string.
     * @param expectedMessage Expected command.
     */
    public static void assertExecuteFailure(Command command, Model model, String expectedMessage) {
        // Create a backup of this model first
        Model oldModel = new ModelManager(model.getCourseBook(), model.getUserPrefs());

        try {
            command.execute(model);
            throw new AssertionError("The expected CommandException was not thrown.");
        } catch (CommandException ce) {
            assertEquals(expectedMessage, ce.getMessage());
        } finally {
            // in the event a command changes, the model should not be mutated
            assertEquals(oldModel, model);
        }
    }
    //@@author

    /**
     * A default model stub that have all of the methods failing.
     */
    public static class ModelStub implements Model {
        @Override
        public ReadOnlyUserPrefs getUserPrefs() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public GuiSettings getGuiSettings() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setGuiSettings(GuiSettings guiSettings) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Path getCourseBookFilePath() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setCourseBookFilePath(Path courseBookFilePath) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Path getBackupCourseBookFilePath() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void resetCourseBook(ReadOnlyCourseBook courseBook) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean undoCourseBook() throws IOException, DataConversionException {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public List<Module> exportModuleList() throws IOException {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Avatar getAvatar() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setAvatar(Avatar avatar) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean isSampleAvatar() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyCourseBook getCourseBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setCourseBook(ReadOnlyCourseBook newData) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasModule(Module module) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteModule(Module target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addCourseInfo(CourseInfo courseInfo) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public CourseInfo getCourseInfo() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setCourseInfo(CourseInfo editedCourseInfo) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean isCourseNameSet() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addModule(Module module) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setModule(Module target, Module editedModule) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasRequirement(Requirement requirement) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Optional<Requirement> getRequirement(RequirementCode requirementCode) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addRequirement(Requirement requirement) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setRequirement(Requirement target, Requirement editedRequirement) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteRequirement(Requirement requirement) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Module> getFilteredModuleList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public List<Module> getSortedModuleList(Comparator<Module> comparator) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Requirement> getRequirementList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredModuleList(Predicate<Module> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateRequirementList(Predicate<Requirement> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public List<Requirement> getRequirementsWithModule(Module module) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Optional<Module> getModule(ModuleCode moduleCode) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public List<Module> getModules(List<ModuleCode> moduleCodes) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasModulePreclusions(Module module) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasModulePrerequisites(Module module) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public String getRandomQuoteString() {
            throw new AssertionError("This method should not be called.");
        }
    }

    /**
     * A Model stub that contains a single module.
     */
    public static class ModelStubWithModule extends ModelStub {
        private final Module module;

        ModelStubWithModule(Module module) {
            requireNonNull(module);
            this.module = module;
        }

        @Override
        public boolean hasModule(Module module) {
            requireNonNull(module);
            return this.module.isSameModule(module);
        }
    }
}

