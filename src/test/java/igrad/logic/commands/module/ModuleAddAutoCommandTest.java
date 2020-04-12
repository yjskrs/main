package igrad.logic.commands.module;

import static igrad.testutil.Assert.assertThrows;
import static igrad.testutil.TypicalModules.getEmptyCourseBook;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import igrad.logic.commands.exceptions.CommandException;
import igrad.model.Model;
import igrad.model.ModelManager;
import igrad.model.UserPrefs;
import igrad.model.module.Module;
import igrad.testutil.ModuleBuilder;

//@@author waynewee
public class ModuleAddAutoCommandTest {

    private Model model = new ModelManager(getEmptyCourseBook(), new UserPrefs());

    private Module cs2101 = new ModuleBuilder()
        .withModuleCode(ModuleCommandTestUtil.VALID_MODULE_CODE_CS2101)
        .withCredits(ModuleCommandTestUtil.VALID_MODULE_CREDITS_4)
        .withTitle(ModuleCommandTestUtil.VALID_MODULE_TITLE_CS2101)
        .withoutOptionals()
        .build();

    private Module cs2103t = new ModuleBuilder()
        .withModuleCode(ModuleCommandTestUtil.VALID_MODULE_CODE_CS2103T)
        .withCredits(ModuleCommandTestUtil.VALID_MODULE_CREDITS_4)
        .withTitle(ModuleCommandTestUtil.VALID_MODULE_TITLE_CS2103T)
        .withoutOptionals()
        .build();

    private Module cs9999 = new ModuleBuilder()
        .withModuleCode("CS9999")
        .withCredits("4")
        .withTitle("This Module Is Not Real")
        .withoutOptionals()
        .build();

    private Module cs2040 = new ModuleBuilder()
        .withModuleCode(ModuleCommandTestUtil.VALID_MODULE_CODE_CS2040)
        .withCredits(ModuleCommandTestUtil.VALID_MODULE_CREDITS_4)
        .withTitle(ModuleCommandTestUtil.VALID_MODULE_TITLE_CS2040)
        .withoutOptionals()
        .build();

    @Test
    public void constructor_nullModule_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new ModuleAddAutoCommand(null));
    }

    @Test
    public void execute_multipleModulesAcceptedByModel_invalidModuleNotAdded() throws CommandException {
        List<Module> validModules = new ArrayList<>();
        List<String> moduleStrings = new ArrayList<>();

        validModules.add(cs2103t);
        validModules.add(cs2101);

        moduleStrings.add(cs2103t.getModuleCode().value);
        moduleStrings.add(cs2101.getModuleCode().value);
        moduleStrings.add(cs9999.getModuleCode().value);

        new ModuleAddAutoCommand(moduleStrings).execute(model);

        assertEquals(validModules, model.getFilteredModuleList());
    }

    @Test
    public void execute_multipleModulesAcceptedByModel_addSuccessful() throws CommandException {
        List<Module> validModules = new ArrayList<>();
        List<String> validModuleStrings = new ArrayList<>();

        validModules.add(cs2103t);
        validModules.add(cs2101);
        validModules.add(cs2040);

        validModuleStrings.add(cs2103t.getModuleCode().value);
        validModuleStrings.add(cs2101.getModuleCode().value);
        validModuleStrings.add(cs2040.getModuleCode().value);

        new ModuleAddAutoCommand(validModuleStrings).execute(model);

        assertEquals(validModules, model.getFilteredModuleList());

    }

    @Test
    public void execute_moduleAcceptedByModel_addSuccessful() throws CommandException {
        List<Module> validModules = new ArrayList<>();
        List<String> validModuleStrings = new ArrayList<>();

        validModules.add(cs2103t);
        validModuleStrings.add(cs2103t.getModuleCode().value);

        new ModuleAddAutoCommand(validModuleStrings).execute(model);

        assertEquals(validModules, model.getFilteredModuleList());
    }

    @Test
    public void execute_duplicateModuleNotAdded() throws CommandException {
        List<Module> validModules = new ArrayList<>();
        List<String> validModuleStrings = new ArrayList<>();

        validModules.add(cs2103t);
        validModuleStrings.add(cs2103t.getModuleCode().value);

        model.addModule(cs2103t);
        new ModuleAddAutoCommand(validModuleStrings).execute(model);

        assertEquals(validModules, model.getFilteredModuleList());
    }

}
