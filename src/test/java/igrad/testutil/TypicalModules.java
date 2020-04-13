package igrad.testutil;

import static igrad.logic.commands.CommandTestUtil.VALID_CREDITS_4;
import static igrad.logic.commands.CommandTestUtil.VALID_CREDITS_6;
import static igrad.logic.commands.CommandTestUtil.VALID_GRADE_A;
import static igrad.logic.commands.CommandTestUtil.VALID_GRADE_COMPUTER_ORGANISATION;
import static igrad.logic.commands.CommandTestUtil.VALID_GRADE_PROGRAMMING_METHODOLOGY;
import static igrad.logic.commands.CommandTestUtil.VALID_MODULE_CODE_CS_1101S;
import static igrad.logic.commands.CommandTestUtil.VALID_MODULE_CODE_CS_2040;
import static igrad.logic.commands.CommandTestUtil.VALID_MODULE_CODE_CS_2100;
import static igrad.logic.commands.CommandTestUtil.VALID_MODULE_CODE_CS_2101;
import static igrad.logic.commands.CommandTestUtil.VALID_MODULE_CODE_CS_2103T;
import static igrad.logic.commands.CommandTestUtil.VALID_SEMESTER_Y1S1;
import static igrad.logic.commands.CommandTestUtil.VALID_SEMESTER_Y1S2;
import static igrad.logic.commands.CommandTestUtil.VALID_TITLE_CS_1101S;
import static igrad.logic.commands.CommandTestUtil.VALID_TITLE_CS_2040;
import static igrad.logic.commands.CommandTestUtil.VALID_TITLE_CS_2100;
import static igrad.logic.commands.CommandTestUtil.VALID_TITLE_CS_2101;
import static igrad.logic.commands.CommandTestUtil.VALID_TITLE_CS_2103T;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import igrad.model.CourseBook;
import igrad.model.module.Module;
import igrad.model.requirement.Requirement;

/**
 * A utility class containing a list of {@code Module} objects to be used in tests.
 */
public class TypicalModules {

    public static final Module CS2040 = new ModuleBuilder()
        .withTitle(VALID_TITLE_CS_2040)
        .withModuleCode(VALID_MODULE_CODE_CS_2040)
        .withCredits(VALID_CREDITS_4)
        .withSemester(VALID_SEMESTER_Y1S1)
        .withGrade(VALID_GRADE_A)
        .build();

    public static final Module CS2101 = new ModuleBuilder()
        .withTitle(VALID_TITLE_CS_2101)
        .withModuleCode(VALID_MODULE_CODE_CS_2101)
        .withCredits(VALID_CREDITS_4)
        .withSemester(VALID_SEMESTER_Y1S1)
        .withGrade(VALID_GRADE_A)
        .build();

    public static final Module CS2103T = new ModuleBuilder()
        .withTitle(VALID_TITLE_CS_2103T)
        .withModuleCode(VALID_MODULE_CODE_CS_2103T)
        .withCredits(VALID_CREDITS_4)
        .withSemester(VALID_SEMESTER_Y1S1)
        .withGrade(VALID_GRADE_A)
        .build();

    // Manually added - Module's details found in {@code CommandTestUtil}
    public static final Module CS1101S = new ModuleBuilder()
        .withTitle(VALID_TITLE_CS_1101S)
        .withModuleCode(VALID_MODULE_CODE_CS_1101S)
        .withCredits(VALID_CREDITS_4)
        .withSemester(VALID_SEMESTER_Y1S1)
        .withGrade(VALID_GRADE_PROGRAMMING_METHODOLOGY).build();

    public static final Module CS2100 = new ModuleBuilder()
        .withTitle(VALID_TITLE_CS_2100)
        .withModuleCode(VALID_MODULE_CODE_CS_2100)
        .withCredits(VALID_CREDITS_6)
        .withSemester(VALID_SEMESTER_Y1S2)
        .withGrade(VALID_GRADE_COMPUTER_ORGANISATION).build();

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

    /**
     * Returns an empty {@code CourseBook}
     */
    public static CourseBook getEmptyCourseBook() {
        return new CourseBook();
    }

    /**
     * Returns a {@code Requirement} with all the typical modules.
     */
    public static Requirement getTypicalRequirement() {
        Requirement requirement = new RequirementBuilder().build();
        for (Module module : getTypicalModules()) {
            requirement.addModule(module);
        }
        return requirement;
    }

    public static List<Module> getTypicalModules() {
        return new ArrayList<>(Arrays.asList(CS1101S, CS2100));
    }

}
