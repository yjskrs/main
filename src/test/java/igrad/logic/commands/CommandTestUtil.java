package igrad.logic.commands;

import static igrad.logic.parser.CliSyntax.PREFIX_CREDITS;
import static igrad.logic.parser.CliSyntax.PREFIX_MODULE_CODE;
import static igrad.logic.parser.CliSyntax.PREFIX_SEMESTER;
import static igrad.logic.parser.CliSyntax.PREFIX_TAG;
import static igrad.logic.parser.CliSyntax.PREFIX_TITLE;
import static igrad.testutil.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import igrad.logic.commands.exceptions.CommandException;
import igrad.logic.commands.module.ModuleEditCommand;
import igrad.model.CourseBook;
import igrad.model.Model;
import igrad.model.module.Module;
import igrad.testutil.EditModuleDescriptorBuilder;

/**
 * Contains helper methods for testing commands.
 */
public class CommandTestUtil {

    public static final String VALID_NAME_B_COMP_SCI = "Bachelor of Computing (Honours) in Computer Science";
    public static final String VALID_NAME_B_ARTS_PHILO = "Bachelor of Arts (Honours) in Philosophy";
    public static final String VALID_TITLE_PROGRAMMING_METHODOLOGY = "Programming Methodology";
    public static final String VALID_TITLE_COMPUTER_ORGANISATION = "Computer Organisation";
    public static final String VALID_MODULE_CODE_PROGRAMMING_METHODOLOGY = "CS1101S";
    public static final String VALID_MODULE_CODE_COMPUTER_ORGANISATION = "CS2100";
    public static final String VALID_CREDITS_PROGRAMMING_METHODOLOGY = "4";
    public static final String VALID_CREDITS_COMPUTER_ORGANISATION = "4";
    public static final String VALID_MEMO_PROGRAMMING_METHODOLOGY = "v easy module leh";
    public static final String VALID_MEMO_COMPUTER_ORGANISATION = "a lot of calculation ah";
    public static final String VALID_SEMESTER_PROGRAMMING_METHODOLOGY = "Y1S1";
    public static final String VALID_SEMESTER_COMPUTER_ORGANISATION = "Y1S2";
    public static final String VALID_TAG_EASY = "easy";
    public static final String VALID_TAG_HARD = "hard";

    public static final String TITLE_DESC_PROGRAMMING_METHODOLOGY = " " + PREFIX_TITLE
        + VALID_TITLE_PROGRAMMING_METHODOLOGY;

    public static final String TITLE_DESC_COMPUTER_ORGANISATION = " " + PREFIX_TITLE
        + VALID_TITLE_COMPUTER_ORGANISATION;

    public static final String MODULE_CODE_DESC_PROGRAMMING_METHODOLOGY = " " + PREFIX_MODULE_CODE
        + VALID_MODULE_CODE_PROGRAMMING_METHODOLOGY;

    public static final String MODULE_CODE_DESC_COMPUTER_ORGANISATION = " " + PREFIX_MODULE_CODE
        + VALID_MODULE_CODE_COMPUTER_ORGANISATION;

    public static final String CREDITS_DESC_PROGRAMMING_METHODOLOGY = " " + PREFIX_CREDITS
        + VALID_CREDITS_PROGRAMMING_METHODOLOGY;

    public static final String CREDITS_DESC_COMPUTER_ORGANISATION = " " + PREFIX_CREDITS
        + VALID_CREDITS_COMPUTER_ORGANISATION;

    public static final String SEMESTER_DESC_PROGRAMMING_METHODOLOGY = " " + PREFIX_SEMESTER
        + VALID_SEMESTER_PROGRAMMING_METHODOLOGY;

    public static final String SEMESTER_DESC_COMPUTER_ORGANISATION = " " + PREFIX_SEMESTER
        + VALID_SEMESTER_COMPUTER_ORGANISATION;

    public static final String TAG_DESC_EASY = " " + PREFIX_TAG + VALID_TAG_EASY;
    public static final String TAG_DESC_HARD = " " + PREFIX_TAG + VALID_TAG_HARD;

    // '!' not allowed in module codes
    public static final String INVALID_TITLE_DESC = " " + PREFIX_TITLE + "Programming Methodology!";

    // '&' not allowed in module codes
    public static final String INVALID_MODULE_CODE_DESC = " " + PREFIX_MODULE_CODE + "CS2040S&";

    // '&' not allowed in credits
    public static final String INVALID_CREDITS_DESC = " " + PREFIX_CREDITS + "4%";

    // '&' not allowed in semester
    public static final String INVALID_SEMESTER_DESC = " " + PREFIX_SEMESTER + "4%";

    // '*' not allowed in tags
    public static final String INVALID_TAG_DESC = " " + PREFIX_TAG + "easy*";

    public static final String PREAMBLE_WHITESPACE = "\t  \r  \n";
    public static final String PREAMBLE_NON_EMPTY = "NonEmptyPreamble";

    public static final ModuleEditCommand.EditModuleDescriptor DESC_PROGRAMMING_METHODOLOGY;
    public static final ModuleEditCommand.EditModuleDescriptor DESC_COMPUTER_ORGANISATION;

    static {
        DESC_PROGRAMMING_METHODOLOGY = new EditModuleDescriptorBuilder()
            .withTitle(VALID_TITLE_PROGRAMMING_METHODOLOGY)
            .withModuleCode(VALID_MODULE_CODE_PROGRAMMING_METHODOLOGY)
            .withCredits(VALID_CREDITS_PROGRAMMING_METHODOLOGY)
            .withSemester(VALID_SEMESTER_PROGRAMMING_METHODOLOGY).build();

        DESC_COMPUTER_ORGANISATION = new EditModuleDescriptorBuilder()
            .withTitle(VALID_TITLE_COMPUTER_ORGANISATION)
            .withModuleCode(VALID_MODULE_CODE_COMPUTER_ORGANISATION)
            .withCredits(VALID_CREDITS_COMPUTER_ORGANISATION)
            .withSemester(VALID_SEMESTER_COMPUTER_ORGANISATION).build();
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

}
