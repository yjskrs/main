package igrad.logic.commands.module;

import static igrad.testutil.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import igrad.logic.commands.CommandTestUtil;
import igrad.model.module.Module;
import igrad.testutil.ModuleBuilder;

public class ModuleAddAutoCommandTest {


    @Test
    public void constructor_nullModule_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new ModuleAddAutoCommand(null));
    }

    @Test
    public void execute_multipleModulesAcceptedByModel_invalidModuleNotAdded() {

        CommandTestUtil.ModelStubAcceptingModuleAdded modelStub = new CommandTestUtil.ModelStubAcceptingModuleAdded();

        Module CS2103T = new ModuleBuilder()
            .withModuleCode("CS2103T")
            .withCredits("4")
            .withTitle("Software Engineering")
            .withoutSemester()
            .withoutDescription()
            .build();

        Module CS2101 = new ModuleBuilder()
            .withModuleCode("CS2101")
            .withCredits("4")
            .withTitle("Effective Communication for Computing Professionals")
            .withoutSemester()
            .withoutDescription()
            .build();

        Module CS9999 = new ModuleBuilder()
            .withModuleCode("CS9999")
            .withCredits("4")
            .withTitle("This Module Is Not Real")
            .withoutSemester()
            .withoutDescription()
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

        CommandTestUtil.ModelStubAcceptingModuleAdded modelStub = new CommandTestUtil.ModelStubAcceptingModuleAdded();

        Module CS2103T = new ModuleBuilder()
            .withModuleCode("CS2103T")
            .withCredits("4")
            .withTitle("Software Engineering")
            .withoutSemester()
            .withoutDescription()
            .build();

        Module CS2101 = new ModuleBuilder()
            .withModuleCode("CS2101")
            .withCredits("4")
            .withTitle("Effective Communication for Computing Professionals")
            .withoutSemester()
            .withoutDescription()
            .build();

        Module CS2040 = new ModuleBuilder()
            .withModuleCode("CS2040")
            .withCredits("4")
            .withTitle("Data Structures and Algorithms")
            .withoutSemester()
            .withoutDescription()
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

        CommandTestUtil.ModelStubAcceptingModuleAdded modelStub = new CommandTestUtil.ModelStubAcceptingModuleAdded();

        Module validModule = new ModuleBuilder()
            .withModuleCode("CS2103T")
            .withCredits("4")
            .withTitle("Software Engineering")
            .withoutSemester()
            .withoutDescription()
            .build();

        List<Module> validModules = new ArrayList<>();
        List<String> validModuleStrings = new ArrayList<>();

        validModules.add(validModule);
        validModuleStrings.add(validModule.getModuleCode().value);

        new ModuleAddAutoCommand(validModuleStrings).execute(modelStub);

        assertEquals(validModules, modelStub);
    }

    @Test
    public void execute_duplicateModuleNotAdded() {

        CommandTestUtil.ModelStubAcceptingModuleAdded modelStub = new CommandTestUtil.ModelStubAcceptingModuleAdded();

        Module validModule = new ModuleBuilder()
            .withModuleCode("CS2103T")
            .withCredits("4")
            .withTitle("Software Engineering")
            .withoutSemester()
            .withoutDescription()
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
