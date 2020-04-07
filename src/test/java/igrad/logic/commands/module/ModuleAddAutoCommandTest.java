package igrad.logic.commands.module;

import static igrad.testutil.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import igrad.model.module.Module;
import igrad.testutil.ModuleBuilder;

public class ModuleAddAutoCommandTest {


    @Test
    public void constructor_nullModule_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new ModuleAddAutoCommand(null));
    }

    @Test
    public void execute_multipleModulesAcceptedByModel_invalidModuleNotAdded() {

        ModuleCommandTestUtil.ModelStubAcceptingModuleAdded modelStub = new ModuleCommandTestUtil.ModelStubAcceptingModuleAdded();

        Module CS2103T = new ModuleBuilder()
            .withModuleCode(ModuleCommandTestUtil.VALID_MODULE_CODE_CS2103T)
            .withCredits(ModuleCommandTestUtil.VALID_CREDITS_4)
            .withTitle(ModuleCommandTestUtil.VALID_TITLE_CS2103T)
            .withoutOptionals()
            .build();

        Module CS2101 = new ModuleBuilder()
            .withModuleCode(ModuleCommandTestUtil.VALID_MODULE_CODE_CS2101)
            .withCredits(ModuleCommandTestUtil.VALID_CREDITS_4)
            .withTitle(ModuleCommandTestUtil.VALID_TITLE_CS2101)
            .withoutOptionals()
            .build();

        Module CS9999 = new ModuleBuilder( )
            .withModuleCode("CS9999")
            .withCredits("4")
            .withTitle("This Module Is Not Real")
            .withoutOptionals()
            .build();

        List<Module> validModules = new ArrayList<>();
        List<String> validModuleStrings = new ArrayList<>();

        validModules.add(CS2103T);
        validModules.add(CS2101);

        validModuleStrings.add(CS2103T.getModuleCode().value);
        validModuleStrings.add(CS2101.getModuleCode().value);
        validModuleStrings.add(CS9999.getModuleCode().value);

        new ModuleAddAutoCommand(validModuleStrings).execute(modelStub);

        assertEquals(validModules, modelStub.getModulesAdded());

    }

    @Test
    public void execute_multipleModulesAcceptedByModel_addSuccessful() {

        ModuleCommandTestUtil.ModelStubAcceptingModuleAdded modelStub = new ModuleCommandTestUtil.ModelStubAcceptingModuleAdded();

        Module CS2103T = new ModuleBuilder()
            .withModuleCode(ModuleCommandTestUtil.VALID_MODULE_CODE_CS2103T)
            .withCredits(ModuleCommandTestUtil.VALID_CREDITS_4)
            .withTitle(ModuleCommandTestUtil.VALID_TITLE_CS2103T)
            .withoutOptionals()
            .build();

        Module CS2101 = new ModuleBuilder()
            .withModuleCode(ModuleCommandTestUtil.VALID_MODULE_CODE_CS2101)
            .withCredits(ModuleCommandTestUtil.VALID_CREDITS_4)
            .withTitle(ModuleCommandTestUtil.VALID_TITLE_CS2101)
            .withoutOptionals()
            .build();

        Module CS2040 = new ModuleBuilder()
            .withModuleCode(ModuleCommandTestUtil.VALID_MODULE_CODE_CS2040)
            .withCredits(ModuleCommandTestUtil.VALID_CREDITS_4)
            .withTitle(ModuleCommandTestUtil.VALID_TITLE_CS2100)
            .withoutOptionals()
            .build();

        List<Module> validModules = new ArrayList<>();
        List<String> validModuleStrings = new ArrayList<>();

        validModules.add(CS2103T);
        validModules.add(CS2101);
        validModules.add(CS2040);

        validModuleStrings.add(CS2103T.getModuleCode().value);
        validModuleStrings.add(CS2101.getModuleCode().value);
        validModuleStrings.add(CS2040.getModuleCode().value);

        new ModuleAddAutoCommand(validModuleStrings).execute(modelStub);

        assertEquals(validModules, modelStub.getModulesAdded());

    }

    @Test
    public void execute_moduleAcceptedByModel_addSuccessful() {

        ModuleCommandTestUtil.ModelStubAcceptingModuleAdded modelStub = new ModuleCommandTestUtil.ModelStubAcceptingModuleAdded();

        Module validModule = new ModuleBuilder()
            .withModuleCode(ModuleCommandTestUtil.VALID_MODULE_CODE_CS2103T)
            .withCredits(ModuleCommandTestUtil.VALID_CREDITS_4)
            .withTitle(ModuleCommandTestUtil.VALID_TITLE_CS2103T)
            .withoutOptionals()
            .build();

        List<Module> validModules = new ArrayList<>();
        List<String> validModuleStrings = new ArrayList<>();

        validModules.add(validModule);
        validModuleStrings.add(validModule.getModuleCode().value);

        new ModuleAddAutoCommand(validModuleStrings).execute(modelStub);

        assertEquals(validModules, modelStub.getModulesAdded());
    }

    @Test
    public void execute_duplicateModuleNotAdded() {

        ModuleCommandTestUtil.ModelStubAcceptingModuleAdded modelStub = new ModuleCommandTestUtil.ModelStubAcceptingModuleAdded();

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
