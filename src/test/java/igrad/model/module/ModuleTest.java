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

    private final Module CS1101S = TypicalModules.CS1101S;
    private final Module CS2100 = TypicalModules.CS2100;

    @Test
    public void hasModuleCodeOf_sameModuleCode_returnsTrue() {
        ModuleCode moduleCode = CS1101S.getModuleCode();
        assertTrue(CS1101S.hasModuleCodeOf(moduleCode));
    }

    @Test
    public void hasModuleCodeOf_differentModuleCode_returnsTrue() {
        ModuleCode moduleCode = CS2100.getModuleCode();
        assertFalse(CS1101S.hasModuleCodeOf(moduleCode));
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
            Optional.of(new Description("blah")),
            grade);
        assertFalse(newModule.isDone());
    }

    @Test
    public void isSameModule() {
        // null
        assertFalse(CS1101S.isSameModule(null));

        // same module
        assertTrue(CS1101S.isSameModule(CS1101S));

        // different module
        assertFalse(CS1101S.isSameModule(CS2100));

        // different title
        Module editedModule = new ModuleBuilder(CS1101S)
            .withTitle(VALID_TITLE_CS_2100)
            .build();
        assertTrue(CS1101S.isSameModule(editedModule));

        // different module code
        editedModule = new ModuleBuilder(CS1101S)
            .withModuleCode(VALID_MODULE_CODE_CS_2100)
            .build();
        assertFalse(CS1101S.isSameModule(editedModule));

        // different credits
        editedModule = new ModuleBuilder(CS1101S)
            .withCredits(VALID_CREDITS_6)
            .build();
        assertTrue(CS1101S.isSameModule(editedModule));

        // different semester
        editedModule = new ModuleBuilder(CS1101S)
            .withSemester(VALID_SEMESTER_Y1S2)
            .build();
        assertTrue(CS1101S.isSameModule(editedModule));

        // different grade
        editedModule = new ModuleBuilder(CS1101S)
            .withGrade(VALID_GRADE_COMPUTER_ORGANISATION)
            .build();
        assertTrue(CS1101S.isSameModule(editedModule));
    }

    @Test
    public void equals() {

        // null
        assertFalse(CS1101S.equals(null));

        // same module
        assertTrue(CS1101S.equals(CS1101S));

        // copied module
        Module moduleCopy = new ModuleBuilder().build();
        assertTrue(CS1101S.equals(moduleCopy));

        // different type
        Requirement requirement = new RequirementBuilder().build();
        assertFalse(CS1101S.equals(requirement));

        // different module code
        Module editedModule = new ModuleBuilder()
            .withModuleCode(VALID_MODULE_CODE_CS_2100)
            .build();
        assertFalse(CS1101S.equals(editedModule));

        // different title
        editedModule = new ModuleBuilder()
            .withTitle(VALID_TITLE_CS_2100)
            .build();
        assertFalse(CS1101S.equals(editedModule));

        // different credits
        editedModule = new ModuleBuilder()
            .withCredits(VALID_CREDITS_6)
            .build();
        assertFalse(CS1101S.equals(editedModule));

        // different semester
        editedModule = new ModuleBuilder()
            .withSemester(VALID_SEMESTER_Y1S2)
            .build();
        assertFalse(CS1101S.equals(editedModule));

        // different grade
        editedModule = new ModuleBuilder()
            .withGrade(VALID_GRADE_COMPUTER_ORGANISATION)
            .build();
        assertFalse(CS1101S.equals(editedModule));
    }
}
