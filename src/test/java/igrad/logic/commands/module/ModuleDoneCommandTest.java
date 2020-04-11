package igrad.logic.commands.module;

//@@author nathanaelseen

import static igrad.logic.commands.module.ModuleCommandTestUtil.VALID_MODULE_CODE_CS1101S;
import static igrad.testutil.Assert.assertThrows;
// import static org.junit.jupiter.api.Assertions.assertFalse;
// import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import igrad.logic.commands.module.ModuleDoneCommand.EditModuleDescriptor;
import igrad.model.Model;
import igrad.model.ModelManager;
// import igrad.model.module.Module;
import igrad.model.module.ModuleCode;
import igrad.testutil.EditModuleDescriptorBuilder2;
// import igrad.testutil.ModuleBuilder;


/**
 * Contains integration tests (interaction with the Model) and unit tests
 * for ModuleDoneCommand.
 */
public class ModuleDoneCommandTest {
    private Model model = new ModelManager();

    @Test
    public void constructor_null_throwsNullPointerException() {
        // ModuleCode null, but EditModuleDescriptor not null
        ModuleCode moduleCode1 = null;
        EditModuleDescriptor descriptor1 = new EditModuleDescriptorBuilder2()
            .build();
        assertThrows(NullPointerException.class, (
                    ) -> new ModuleDoneCommand(moduleCode1, descriptor1));

        // EditModuleDescriptor null, but ModuleCode not null
        ModuleCode moduleCode2 = new ModuleCode(VALID_MODULE_CODE_CS1101S);
        EditModuleDescriptor descriptor2 = null;
        assertThrows(NullPointerException.class, (
                    ) -> new ModuleDoneCommand(moduleCode2, descriptor2));
    }
}
