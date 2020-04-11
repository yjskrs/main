package igrad.logic.commands.module;

//@@author nathanaelseen
import static igrad.logic.commands.CommandTestUtil.assertExecuteFailure;
import static igrad.logic.commands.module.ModuleCommand.MESSAGE_MODULE_NON_EXISTENT;
import static igrad.logic.commands.module.ModuleCommandTestUtil.VALID_MODULE_CODE_CS1101S;
import static igrad.logic.commands.module.ModuleCommandTestUtil.VALID_MODULE_GRADE_CS1101S;
import static igrad.testutil.Assert.assertThrows;
import static igrad.testutil.TypicalModules.CS2040;
// import static org.junit.jupiter.api.Assertions.assertFalse;
// import static org.junit.jupiter.api.Assertions.assertTrue;
// import static igrad.testutil.TypicalModules.getTypicalModules;

import org.junit.jupiter.api.Test;

import igrad.logic.commands.module.ModuleDoneCommand.EditModuleDescriptor;
import igrad.model.Model;
import igrad.model.ModelManager;
import igrad.model.module.ModuleCode;
import igrad.testutil.EditModuleDescriptorBuilder2;
// import igrad.testutil.ModuleBuilder;
// import igrad.logic.commands.module.ModuleDoneCommand;


/**
 * Contains integration tests (interaction with the Model) and unit tests
 * for ModuleDoneCommand.
 */
public class ModuleDoneCommandTest {
    @Test
    public void constructor_null_throwsNullPointerException() {
        ModuleCode moduleCode1;
        EditModuleDescriptor descriptor1;
        ModuleCode moduleCode2;
        EditModuleDescriptor descriptor2;

        // ModuleCode null, but EditModuleDescriptor not null
        moduleCode1 = null;
        descriptor1 = new EditModuleDescriptorBuilder2()
            .build();
        assertThrows(NullPointerException.class, (
                    ) -> new ModuleDoneCommand(moduleCode1, descriptor1));

        // EditModuleDescriptor null, but ModuleCode not null
        moduleCode2 = new ModuleCode(VALID_MODULE_CODE_CS1101S);
        descriptor2 = null;
        assertThrows(NullPointerException.class, (
                    ) -> new ModuleDoneCommand(moduleCode2, descriptor2));
    }

    @Test
    public void execute_nullModel_throwsNullPointerException() {
        Model model = null;
        ModuleCode moduleCode = new ModuleCode(VALID_MODULE_CODE_CS1101S);
        EditModuleDescriptor descriptor = new EditModuleDescriptorBuilder2()
                                               .withGrade(VALID_MODULE_GRADE_CS1101S)
                                               .build();
        ModuleDoneCommand cmd = new ModuleDoneCommand(moduleCode, descriptor);
        assertThrows(NullPointerException.class, () -> cmd.execute(model));
    }

    @Test
    public void execute_moduleNonExistentOnEmptyModel_throwCommandException() {
        Model model = new ModelManager(); // set-up an empty Model
        ModuleCode moduleCode = new ModuleCode(VALID_MODULE_CODE_CS1101S);
        EditModuleDescriptor descriptor = new EditModuleDescriptorBuilder2()
                                                   .withGrade(VALID_MODULE_GRADE_CS1101S)
                                                   .build();
        ModuleDoneCommand cmd = new ModuleDoneCommand(moduleCode, descriptor);

        assertExecuteFailure(cmd, model, MESSAGE_MODULE_NON_EXISTENT);
    }

    @Test
    public void execute_moduleNonExistentNonEmptyModel_throwCommandException() {
        // set-up a non-empty Model
        Model model = new ModelManager();
        model.addModule(CS2040);

        ModuleCode moduleCode = new ModuleCode(VALID_MODULE_CODE_CS1101S);
        EditModuleDescriptor descriptor = new EditModuleDescriptorBuilder2()
                                                   .withGrade(VALID_MODULE_GRADE_CS1101S)
                                                   .build();
        ModuleDoneCommand cmd = new ModuleDoneCommand(moduleCode, descriptor);

        assertExecuteFailure(cmd, model, MESSAGE_MODULE_NON_EXISTENT);
    }
}
