package igrad.model.module;

import static igrad.logic.commands.CommandTestUtil.VALID_CREDITS_4;
import static igrad.logic.commands.CommandTestUtil.VALID_CREDITS_6;
import static igrad.logic.commands.CommandTestUtil.VALID_GRADE_COMPUTER_ORGANISATION;
import static igrad.logic.commands.CommandTestUtil.VALID_GRADE_PROGRAMMING_METHODOLOGY;
import static igrad.logic.commands.CommandTestUtil.VALID_MODULE_CODE_CS_1101S;
import static igrad.logic.commands.CommandTestUtil.VALID_MODULE_CODE_CS_2100;
import static igrad.logic.commands.CommandTestUtil.VALID_SEMESTER_Y1S1;
import static igrad.logic.commands.CommandTestUtil.VALID_SEMESTER_Y1S2;
import static igrad.logic.commands.CommandTestUtil.VALID_TITLE_CS_1101S;
import static igrad.logic.commands.CommandTestUtil.VALID_TITLE_CS_2100;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

import org.junit.jupiter.api.Test;

import igrad.model.requirement.Requirement;
import igrad.testutil.ModuleBuilder;
import igrad.testutil.RequirementBuilder;
import igrad.testutil.TypicalModules;

public class ModuleTest {

    private final Module cs1101s = TypicalModules.CS1101S;
    private final Module cs2100 = TypicalModules.CS2100;

    @Test
    public void hasModuleCodeOf_sameModuleCode_returnsTrue() {
        ModuleCode moduleCode = cs1101s.getModuleCode();
        assertTrue(cs1101s.hasModuleCodeOf(moduleCode));
    }

    @Test
    public void hasModuleCodeOf_differentModuleCode_returnsTrue() {
        ModuleCode moduleCode = cs2100.getModuleCode();
        assertFalse(cs1101s.hasModuleCodeOf(moduleCode));
    }

    @Test
    public void getTitle_success() {
        Title newTitle = new Title(VALID_GRADE_PROGRAMMING_METHODOLOGY);
        Module newModule = new ModuleBuilder()
            .withTitle(VALID_GRADE_PROGRAMMING_METHODOLOGY)
            .build();
        assertEquals(newTitle, newModule.getTitle());
    }

    @Test
    public void getModuleCode_success() {
        ModuleCode newModuleCode = new ModuleCode(VALID_MODULE_CODE_CS_1101S);
        Module newModule = new ModuleBuilder()
            .withModuleCode(VALID_MODULE_CODE_CS_1101S)
            .build();
        assertEquals(newModuleCode, newModule.getModuleCode());
    }

    @Test
    public void getCredits_success() {
        Credits newCredits = new Credits(VALID_CREDITS_4);
        Module newModule = new ModuleBuilder()
            .withCredits(VALID_CREDITS_4)
            .build();
        assertEquals(newCredits, newModule.getCredits());
    }

    @Test
    public void getSemester_success() {
        Semester newSemester = new Semester(VALID_SEMESTER_Y1S1);
        Module newModule = new ModuleBuilder()
            .withSemester(VALID_SEMESTER_Y1S1)
            .build();
        assertEquals(Optional.of(newSemester), newModule.getSemester());
    }

    @Test
    public void getGrade_success() {
        Grade newGrade = new Grade(VALID_GRADE_PROGRAMMING_METHODOLOGY);
        Module newModule = new ModuleBuilder()
            .withGrade(VALID_GRADE_PROGRAMMING_METHODOLOGY)
            .build();
        assertEquals(Optional.of(newGrade), newModule.getGrade());
    }

    @Test
    public void isDone_gradePresent_returnsTrue() {
        Module newModule = new ModuleBuilder()
            .withGrade(VALID_GRADE_PROGRAMMING_METHODOLOGY)
            .build();
        assertTrue(newModule.isDone());
    }

    @Test
    public void isDone_gradeNotPresent_returnsFalse() {
        Optional<Grade> grade = Optional.empty();
        Module newModule = new Module(new Title(VALID_TITLE_CS_1101S),
            new ModuleCode(VALID_MODULE_CODE_CS_1101S),
            new Credits(VALID_CREDITS_4),
            Optional.of(new Semester(VALID_SEMESTER_Y1S1)),
            grade);
        assertFalse(newModule.isDone());
    }

    @Test
    public void isSameModule() {
        // null
        assertFalse(cs1101s.isSameModule(null));

        // same module
        assertTrue(cs1101s.isSameModule(cs1101s));

        // different module
        assertFalse(cs1101s.isSameModule(cs2100));

        // different title
        Module editedModule = new ModuleBuilder(cs1101s)
                .withTitle(VALID_TITLE_CS_2100)
                .build();
        assertTrue(cs1101s.isSameModule(editedModule));

        // different module code
        editedModule = new ModuleBuilder(cs1101s)
                .withModuleCode(VALID_MODULE_CODE_CS_2100)
                .build();
        assertFalse(cs1101s.isSameModule(editedModule));

        // different credits
        editedModule = new ModuleBuilder(cs1101s)
                .withCredits(VALID_CREDITS_6)
                .build();
        assertTrue(cs1101s.isSameModule(editedModule));

        // different semester
        editedModule = new ModuleBuilder(cs1101s)
                .withSemester(VALID_SEMESTER_Y1S2)
                .build();
        assertTrue(cs1101s.isSameModule(editedModule));

        // different grade
        editedModule = new ModuleBuilder(cs1101s)
                .withGrade(VALID_GRADE_COMPUTER_ORGANISATION)
                .build();
        assertTrue(cs1101s.isSameModule(editedModule));
    }

    @Test
    public void equals() {

        // null
        assertFalse(cs1101s.equals(null));

        // same module
        assertTrue(cs1101s.equals(cs1101s));

        // copied module
        Module moduleCopy = new ModuleBuilder().build();
        assertTrue(cs1101s.equals(moduleCopy));

        // different type
        Requirement requirement = new RequirementBuilder().build();
        assertFalse(cs1101s.equals(requirement));

        // different module code
        Module editedModule = new ModuleBuilder()
                .withModuleCode(VALID_MODULE_CODE_CS_2100)
                .build();
        assertFalse(cs1101s.equals(editedModule));

        // different title
        editedModule = new ModuleBuilder()
                .withTitle(VALID_TITLE_CS_2100)
                .build();
        assertFalse(cs1101s.equals(editedModule));

        // different credits
        editedModule = new ModuleBuilder()
                .withCredits(VALID_CREDITS_6)
                .build();
        assertFalse(cs1101s.equals(editedModule));

        // different semester
        editedModule = new ModuleBuilder()
                .withSemester(VALID_SEMESTER_Y1S2)
                .build();
        assertFalse(cs1101s.equals(editedModule));

        // different grade
        editedModule = new ModuleBuilder()
                .withGrade(VALID_GRADE_COMPUTER_ORGANISATION)
                .build();
        assertFalse(cs1101s.equals(editedModule));
    }
}
