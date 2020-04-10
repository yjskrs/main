package igrad.logic.commands.module;

import static igrad.testutil.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import igrad.logic.commands.exceptions.CommandException;
import igrad.model.module.Module;
import igrad.testutil.ModuleBuilder;

//@@author waynewee
public class ModuleAddAutoCommandTest {


    @Test
    public void constructor_nullModule_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new ModuleAddAutoCommand(null));
    }

    @Test
    public void execute_multipleModulesAcceptedByModel_invalidModuleNotAdded() throws CommandException {

        ModuleCommandTestUtil.ModelStubAcceptingModuleAdded modelStub =
            new ModuleCommandTestUtil.ModelStubAcceptingModuleAdded();

        Module cs2103t = new ModuleBuilder()
            .withModuleCode(ModuleCommandTestUtil.VALID_MODULE_CODE_CS2103T)
            .withCredits(ModuleCommandTestUtil.VALID_MODULE_CREDITS_4)
            .withTitle(ModuleCommandTestUtil.VALID_MODULE_TITLE_CS2103T)
            .withoutOptionals()
            .build();

        Module cs2101 = new ModuleBuilder()
            .withModuleCode(ModuleCommandTestUtil.VALID_MODULE_CODE_CS2101)
            .withCredits(ModuleCommandTestUtil.VALID_MODULE_CREDITS_4)
            .withTitle(ModuleCommandTestUtil.VALID_MODULE_TITLE_CS2101)
            .withoutOptionals()
            .build();

        Module cs9999 = new ModuleBuilder()
            .withModuleCode("CS9999")
            .withCredits("4")
            .withTitle("This Module Is Not Real")
            .withoutOptionals()
            .build();

        List<Module> validModules = new ArrayList<>();
        List<String> moduleStrings = new ArrayList<>();

        validModules.add(cs2103t);
        validModules.add(cs2101);

        moduleStrings.add(cs2103t.getModuleCode().value);
        moduleStrings.add(cs2101.getModuleCode().value);
        moduleStrings.add(cs9999.getModuleCode().value);

        /*new ModuleAddAutoCommand(moduleStrings).execute(modelStub);

        assertEquals(validModules, modelStub.getModulesAdded());*/

    }

    @Test
    public void execute_multipleModulesAcceptedByModel_addSuccessful() throws CommandException {

        ModuleCommandTestUtil.ModelStubAcceptingModuleAdded modelStub =
            new ModuleCommandTestUtil.ModelStubAcceptingModuleAdded();

        Module cs2103t = new ModuleBuilder()
            .withModuleCode(ModuleCommandTestUtil.VALID_MODULE_CODE_CS2103T)
            .withCredits(ModuleCommandTestUtil.VALID_MODULE_CREDITS_4)
            .withTitle(ModuleCommandTestUtil.VALID_MODULE_TITLE_CS2103T)
            .withoutOptionals()
            .build();

        Module cs2101 = new ModuleBuilder()
            .withModuleCode(ModuleCommandTestUtil.VALID_MODULE_CODE_CS2101)
            .withCredits(ModuleCommandTestUtil.VALID_MODULE_CREDITS_4)
            .withTitle(ModuleCommandTestUtil.VALID_MODULE_TITLE_CS2101)
            .withoutOptionals()
            .build();

        Module cs2040 = new ModuleBuilder()
            .withModuleCode(ModuleCommandTestUtil.VALID_MODULE_CODE_CS2040)
            .withCredits(ModuleCommandTestUtil.VALID_MODULE_CREDITS_4)
            .withTitle(ModuleCommandTestUtil.VALID_MODULE_TITLE_CS2040)
            .withoutOptionals()
            .build();

        List<Module> validModules = new ArrayList<>();
        List<String> validModuleStrings = new ArrayList<>();

        validModules.add(cs2103t);
        validModules.add(cs2101);
        validModules.add(cs2040);

        validModuleStrings.add(cs2103t.getModuleCode().value);
        validModuleStrings.add(cs2101.getModuleCode().value);
        validModuleStrings.add(cs2040.getModuleCode().value);

        /*new ModuleAddAutoCommand(validModuleStrings).execute(modelStub);

        assertEquals(validModules, modelStub.getModulesAdded());*/

    }

    @Test
    public void execute_moduleAcceptedByModel_addSuccessful() throws CommandException {

        ModuleCommandTestUtil.ModelStubAcceptingModuleAdded modelStub =
            new ModuleCommandTestUtil.ModelStubAcceptingModuleAdded();

        Module validModule = new ModuleBuilder()
            .withModuleCode(ModuleCommandTestUtil.VALID_MODULE_CODE_CS2103T)
            .withCredits(ModuleCommandTestUtil.VALID_MODULE_CREDITS_4)
            .withTitle(ModuleCommandTestUtil.VALID_MODULE_TITLE_CS2103T)
            .withoutOptionals()
            .build();

        List<Module> validModules = new ArrayList<>();
        List<String> validModuleStrings = new ArrayList<>();

        validModules.add(validModule);
        validModuleStrings.add(validModule.getModuleCode().value);

        /*new ModuleAddAutoCommand(validModuleStrings).execute(modelStub);

        assertEquals(validModules, modelStub.getModulesAdded());*/
    }

    @Test
    public void execute_duplicateModuleNotAdded() throws CommandException {

        ModuleCommandTestUtil.ModelStubAcceptingModuleAdded modelStub =
            new ModuleCommandTestUtil.ModelStubAcceptingModuleAdded();

        Module validModule = new ModuleBuilder()
            .withModuleCode("CS2103T")
            .withCredits("4")
            .withTitle("Software Engineering")
            .withoutOptionals()
            .build();

        List<Module> validModules = new ArrayList<>();
        List<String> validModuleStrings = new ArrayList<>();

        validModules.add(validModule);
        validModuleStrings.add(validModule.getModuleCode().value);

        modelStub.addModule(validModule);
        new ModuleAddAutoCommand(validModuleStrings).execute(modelStub);

        assertEquals(validModules, modelStub.getModulesAdded());
    }

}
