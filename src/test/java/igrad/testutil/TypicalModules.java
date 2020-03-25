package igrad.testutil;

import static igrad.logic.commands.CommandTestUtil.VALID_CREDITS_COMPUTER_ORGANISATION;
import static igrad.logic.commands.CommandTestUtil.VALID_CREDITS_PROGRAMMING_METHODOLOGY;
import static igrad.logic.commands.CommandTestUtil.VALID_MEMO_COMPUTER_ORGANISATION;
import static igrad.logic.commands.CommandTestUtil.VALID_MEMO_PROGRAMMING_METHODOLOGY;
import static igrad.logic.commands.CommandTestUtil.VALID_MODULE_CODE_COMPUTER_ORGANISATION;
import static igrad.logic.commands.CommandTestUtil.VALID_MODULE_CODE_PROGRAMMING_METHODOLOGY;
import static igrad.logic.commands.CommandTestUtil.VALID_SEMESTER_COMPUTER_ORGANISATION;
import static igrad.logic.commands.CommandTestUtil.VALID_SEMESTER_PROGRAMMING_METHODOLOGY;
import static igrad.logic.commands.CommandTestUtil.VALID_TAG_EASY;
import static igrad.logic.commands.CommandTestUtil.VALID_TAG_HARD;
import static igrad.logic.commands.CommandTestUtil.VALID_TITLE_COMPUTER_ORGANISATION;
import static igrad.logic.commands.CommandTestUtil.VALID_TITLE_PROGRAMMING_METHODOLOGY;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import igrad.model.CourseBook;
import igrad.model.module.Module;

/**
 * A utility class containing a list of {@code Module} objects to be used in tests.
 */
public class TypicalModules {

    /*
     *public static final Module ALICE = new ModuleBuilder().withName("Alice Pauline")
     *       .withEmail("alice@example.com")
     *       .withPhone("94351253")
     *       .withTags("friends").build();
     */

    // Manually added
    /*
     *public static final Module HOON = new ModuleBuilder().withName("Hoon Meier").withPhone("8482424")
     * .withEmail("stefan@example.com").build();
     */

    // Manually added - Module's details found in {@code CommandTestUtil}
    public static final Module PROGRAMMING_METHODOLOGY = new ModuleBuilder()
        .withTitle(VALID_TITLE_PROGRAMMING_METHODOLOGY)
        .withModuleCode(VALID_MODULE_CODE_PROGRAMMING_METHODOLOGY)
        .withCredits(VALID_CREDITS_PROGRAMMING_METHODOLOGY)
        .withMemo(VALID_MEMO_PROGRAMMING_METHODOLOGY)
        .withSemester(VALID_SEMESTER_PROGRAMMING_METHODOLOGY)
        .withTags(VALID_TAG_EASY).build();

    public static final Module COMPUTER_ORGANISATION = new ModuleBuilder()
        .withTitle(VALID_TITLE_COMPUTER_ORGANISATION)
        .withModuleCode(VALID_MODULE_CODE_COMPUTER_ORGANISATION)
        .withCredits(VALID_CREDITS_COMPUTER_ORGANISATION)
        .withMemo(VALID_MEMO_COMPUTER_ORGANISATION)
        .withSemester(VALID_SEMESTER_COMPUTER_ORGANISATION)
        .withTags(VALID_TAG_HARD).build();

    public static final String KEYWORD_MATCHING_MEIER = "Meier"; // A keyword that matches MEIER

    private TypicalModules() {
    } // prevents instantiation

    /**
     * Returns an {@code CourseBook} with all the typical modules.
     */
    public static CourseBook getTypicalCourseBook() {
        CourseBook courseBook = new CourseBook();
        for (Module module : getTypicalModules()) {
            courseBook.addModule(module);
        }
        return courseBook;
    }

    public static List<Module> getTypicalModules() {
        return new ArrayList<>(Arrays.asList(PROGRAMMING_METHODOLOGY, COMPUTER_ORGANISATION));
    }
}
