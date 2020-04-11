package igrad.logic.commands.module;

//@@author waynewee

import static igrad.testutil.TypicalModules.getEmptyCourseBook;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;

import igrad.logic.commands.exceptions.CommandException;
import igrad.model.Model;
import igrad.model.ModelManager;
import igrad.model.UserPrefs;
import igrad.model.module.Credits;
import igrad.model.module.Grade;
import igrad.model.module.Module;
import igrad.model.module.Semester;
import igrad.testutil.ModuleBuilder;
import igrad.testutil.TypicalModules;

public class ModuleFilterCommandTest {

    private Model model = new ModelManager(getEmptyCourseBook(), new UserPrefs());

    @Test
    public void execute_filterModule_noParameters() throws CommandException {

        Module cs1101s = TypicalModules.CS1101S;
        Module cs2103t = TypicalModules.CS2103T;
        Module cs2101 = TypicalModules.CS2101;

        List<Module> validModules = new ArrayList<>();

        validModules.add(cs1101s);
        validModules.add(cs2103t);
        validModules.add(cs2101);

        model.addModule(cs1101s);
        model.addModule(cs2103t);
        model.addModule(cs2101);

        ModuleFilterCommand moduleFilterCommand = new ModuleFilterCommand(
            Optional.empty(),
            Optional.empty(),
            Optional.empty(),
            ModuleFilterCommand.AND
        );

        moduleFilterCommand.execute(model);

        assertEquals(validModules, model.getFilteredModuleList());
    }

    @Test
    public void execute_filterModule_orOperator() throws CommandException {

        Module moduleWithGrade = new ModuleBuilder()
            .withModuleCode(ModuleCommandTestUtil.VALID_MODULE_CODE_CS2103T)
            .withCredits(ModuleCommandTestUtil.VALID_MODULE_CREDITS_4)
            .withTitle(ModuleCommandTestUtil.VALID_MODULE_TITLE_CS2103T)
            .withoutOptionals()
            .withGrade(ModuleCommandTestUtil.VALID_MODULE_GRADE_A)
            .build();

        Module moduleWithSixCredits = new ModuleBuilder()
            .withModuleCode(ModuleCommandTestUtil.VALID_MODULE_CODE_CS2101)
            .withCredits(ModuleCommandTestUtil.VALID_MODULE_CREDITS_6)
            .withTitle(ModuleCommandTestUtil.VALID_MODULE_TITLE_CS2101)
            .withoutOptionals()
            .build();

        Module moduleWithSemester = new ModuleBuilder()
            .withModuleCode(ModuleCommandTestUtil.VALID_MODULE_CODE_CS2040)
            .withCredits(ModuleCommandTestUtil.VALID_MODULE_CREDITS_4)
            .withTitle(ModuleCommandTestUtil.VALID_MODULE_TITLE_CS2040)
            .withoutOptionals()
            .withSemester(ModuleCommandTestUtil.VALID_MODULE_SEMESTER_Y1S1)
            .build();

        Module moduleWithoutOptionals = new ModuleBuilder()
            .withModuleCode(ModuleCommandTestUtil.VALID_MODULE_CODE_CS2100)
            .withCredits(ModuleCommandTestUtil.VALID_MODULE_CREDITS_4)
            .withTitle(ModuleCommandTestUtil.VALID_MODULE_TITLE_CS2100)
            .withoutOptionals()
            .build();

        model.addModule(moduleWithGrade);
        model.addModule(moduleWithSemester);
        model.addModule(moduleWithSixCredits);
        model.addModule(moduleWithoutOptionals);

        List<Module> validModules = new ArrayList<>();
        validModules.add(moduleWithGrade);
        validModules.add(moduleWithSemester);
        validModules.add(moduleWithSixCredits);

        ModuleFilterCommand moduleFilterCommand = new ModuleFilterCommand(
            Optional.of(new Semester(ModuleCommandTestUtil.VALID_MODULE_SEMESTER_Y1S1)),
            Optional.of(new Credits(ModuleCommandTestUtil.VALID_MODULE_CREDITS_6)),
            Optional.of(new Grade(ModuleCommandTestUtil.VALID_MODULE_GRADE_A)),
            ModuleFilterCommand.OR
        );

        moduleFilterCommand.execute(model);

        assertEquals(validModules, model.getFilteredModuleList());
    }

    @Test
    public void execute_filterModule_andOperator() throws CommandException {

        Module moduleWithGradeA = new ModuleBuilder()
            .withModuleCode(ModuleCommandTestUtil.VALID_MODULE_CODE_CS2103T)
            .withCredits(ModuleCommandTestUtil.VALID_MODULE_CREDITS_4)
            .withTitle(ModuleCommandTestUtil.VALID_MODULE_TITLE_CS2103T)
            .withoutOptionals()
            .withGrade(ModuleCommandTestUtil.VALID_MODULE_GRADE_A)
            .build();

        Module moduleWithGradeB = new ModuleBuilder()
            .withModuleCode(ModuleCommandTestUtil.VALID_MODULE_CODE_CS2101)
            .withCredits(ModuleCommandTestUtil.VALID_MODULE_CREDITS_6)
            .withTitle(ModuleCommandTestUtil.VALID_MODULE_TITLE_CS2101)
            .withoutOptionals()
            .withGrade(ModuleCommandTestUtil.VALID_MODULE_GRADE_A)
            .build();

        Module moduleWithoutGrade = new ModuleBuilder()
            .withModuleCode(ModuleCommandTestUtil.VALID_MODULE_CODE_CS2040)
            .withCredits(ModuleCommandTestUtil.VALID_MODULE_CREDITS_4)
            .withTitle(ModuleCommandTestUtil.VALID_MODULE_TITLE_CS2040)
            .withoutOptionals()
            .build();

        model.addModule(moduleWithGradeA);
        model.addModule(moduleWithGradeB);
        model.addModule(moduleWithoutGrade);

        List<Module> validModules = new ArrayList<>();
        validModules.add(moduleWithGradeA);
        validModules.add(moduleWithGradeB);

        ModuleFilterCommand moduleFilterCommand = new ModuleFilterCommand(
            Optional.empty(),
            Optional.empty(),
            Optional.of(new Grade(ModuleCommandTestUtil.VALID_MODULE_GRADE_A)),
            ModuleFilterCommand.AND
        );

        moduleFilterCommand.execute(model);

        assertEquals(validModules, model.getFilteredModuleList());
    }

}
