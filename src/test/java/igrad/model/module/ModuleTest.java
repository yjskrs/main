package igrad.model.module;

import java.util.Optional;

import igrad.model.requirement.Requirement;
import igrad.testutil.ModuleBuilder;
import igrad.testutil.RequirementBuilder;
import igrad.testutil.TypicalModules;
import org.junit.jupiter.api.Test;

import static igrad.logic.commands.CommandTestUtil.*;
import static org.junit.jupiter.api.Assertions.*;

public class ModuleTest {

    private final Module PROGRAMMING_METHODOLOGY = TypicalModules.PROGRAMMING_METHODOLOGY;
    private final Module COMPUTER_ORGANISATION = TypicalModules.COMPUTER_ORGANISATION;

    @Test
    public void hasModuleCodeOf_sameModuleCode_returnsTrue() {
        ModuleCode moduleCode = PROGRAMMING_METHODOLOGY.getModuleCode();
        assertTrue(PROGRAMMING_METHODOLOGY.hasModuleCodeOf(moduleCode));
    }

    @Test
    public void hasModuleCodeOf_differentModuleCode_returnsTrue() {
        ModuleCode moduleCode = COMPUTER_ORGANISATION.getModuleCode();
        assertFalse(PROGRAMMING_METHODOLOGY.hasModuleCodeOf(moduleCode));
    }

    @Test
    public void getTitle_success() {
        Title newTitle = new Title(VALID_TITLE_PROGRAMMING_METHODOLOGY);
        Module newModule = new ModuleBuilder()
                .withTitle(VALID_TITLE_PROGRAMMING_METHODOLOGY)
                .build();
        assertEquals(newTitle, newModule.getTitle());
    }

    @Test
    public void getModuleCode_success() {
        ModuleCode newModuleCode = new ModuleCode(VALID_MODULE_CODE_PROGRAMMING_METHODOLOGY);
        Module newModule = new ModuleBuilder()
                .withModuleCode(VALID_MODULE_CODE_PROGRAMMING_METHODOLOGY)
                .build();
        assertEquals(newModuleCode, newModule.getModuleCode());
    }

    @Test
    public void getCredits_success() {
        Credits newCredits = new Credits(VALID_CREDITS_PROGRAMMING_METHODOLOGY);
        Module newModule = new ModuleBuilder()
                .withCredits(VALID_CREDITS_PROGRAMMING_METHODOLOGY)
                .build();
        assertEquals(newCredits, newModule.getCredits());
    }

    @Test
    public void getSemester_success() {
        Semester newSemester = new Semester(VALID_SEMESTER_PROGRAMMING_METHODOLOGY);
        Module newModule = new ModuleBuilder()
                .withSemester(VALID_SEMESTER_PROGRAMMING_METHODOLOGY)
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
        Module newModule = new Module(new Title(VALID_TITLE_PROGRAMMING_METHODOLOGY),
            new ModuleCode(VALID_MODULE_CODE_PROGRAMMING_METHODOLOGY),
            new Credits(VALID_CREDITS_PROGRAMMING_METHODOLOGY),
            Optional.of(new Semester(VALID_SEMESTER_PROGRAMMING_METHODOLOGY)),
            Optional.of(new Description("blah")),
            grade);
        assertFalse(newModule.isDone());
    }

    @Test
    public void isSameModule() {
        // null
        assertFalse(PROGRAMMING_METHODOLOGY.isSameModule(null));

        // same module
        assertTrue(PROGRAMMING_METHODOLOGY.isSameModule(PROGRAMMING_METHODOLOGY));

        // different module
        assertFalse(PROGRAMMING_METHODOLOGY.isSameModule(COMPUTER_ORGANISATION));

        // different title
        Module editedModule = new ModuleBuilder(PROGRAMMING_METHODOLOGY)
                .withTitle(VALID_TITLE_COMPUTER_ORGANISATION)
                .build();
        assertTrue(PROGRAMMING_METHODOLOGY.isSameModule(editedModule));

        // different module code
        editedModule = new ModuleBuilder(PROGRAMMING_METHODOLOGY)
                .withModuleCode(VALID_MODULE_CODE_COMPUTER_ORGANISATION)
                .build();
        assertFalse(PROGRAMMING_METHODOLOGY.isSameModule(editedModule));

        // different credits
        editedModule = new ModuleBuilder(PROGRAMMING_METHODOLOGY)
                .withCredits(VALID_CREDITS_OVER_FOUR)
                .build();
        assertTrue(PROGRAMMING_METHODOLOGY.isSameModule(editedModule));

        // different semester
        editedModule = new ModuleBuilder(PROGRAMMING_METHODOLOGY)
                .withSemester(VALID_SEMESTER_COMPUTER_ORGANISATION)
                .build();
        assertTrue(PROGRAMMING_METHODOLOGY.isSameModule(editedModule));

        // different grade
        editedModule = new ModuleBuilder(PROGRAMMING_METHODOLOGY)
                .withGrade(VALID_GRADE_COMPUTER_ORGANISATION)
                .build();
        assertTrue(PROGRAMMING_METHODOLOGY.isSameModule(editedModule));
    }

    @Test
    public void equals() {

        // null
        assertFalse(PROGRAMMING_METHODOLOGY.equals(null));

        // same module
        assertTrue(PROGRAMMING_METHODOLOGY.equals(PROGRAMMING_METHODOLOGY));

        // copied module
        Module moduleCopy = new ModuleBuilder().build();
        assertTrue(PROGRAMMING_METHODOLOGY.equals(moduleCopy));

        // different type
        Requirement requirement = new RequirementBuilder().build();
        assertFalse(PROGRAMMING_METHODOLOGY.equals(requirement));

        // different module code
        Module editedModule = new ModuleBuilder()
                .withModuleCode(VALID_MODULE_CODE_COMPUTER_ORGANISATION)
                .build();
        assertFalse(PROGRAMMING_METHODOLOGY.equals(editedModule));

        // different title
        editedModule = new ModuleBuilder()
                .withTitle(VALID_TITLE_COMPUTER_ORGANISATION)
                .build();
        assertFalse(PROGRAMMING_METHODOLOGY.equals(editedModule));

        // different credits
        editedModule = new ModuleBuilder()
                .withCredits(VALID_CREDITS_OVER_FOUR)
                .build();
        assertFalse(PROGRAMMING_METHODOLOGY.equals(editedModule));

        // different semester
        editedModule = new ModuleBuilder()
                .withSemester(VALID_SEMESTER_COMPUTER_ORGANISATION)
                .build();
        assertFalse(PROGRAMMING_METHODOLOGY.equals(editedModule));

        // different grade
        editedModule = new ModuleBuilder()
                .withGrade(VALID_GRADE_COMPUTER_ORGANISATION)
                .build();
        assertFalse(PROGRAMMING_METHODOLOGY.equals(editedModule));
    }
}
