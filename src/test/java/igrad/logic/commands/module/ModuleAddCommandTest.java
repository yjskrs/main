package igrad.logic.commands.module;

import static igrad.testutil.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import igrad.logic.commands.CommandResult;
import igrad.logic.commands.exceptions.CommandException;
import igrad.model.module.Module;
import igrad.testutil.ModuleBuilder;

public class ModuleAddCommandTest {

    @Test
    public void constructor_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new ModuleAddCommand(null));
    }

    @Test
    public void execute_personAcceptedByModel_addSuccessful() throws Exception {
        ModuleCommandTestUtil.ModelStubAcceptingModuleAdded modelStub =
            new ModuleCommandTestUtil.ModelStubAcceptingModuleAdded();
        Module validModule = new ModuleBuilder().build();

        CommandResult commandResult = new ModuleAddCommand(validModule).execute(modelStub);

        assertEquals(String.format(ModuleAddCommand.MESSAGE_MODULE_ADD_SUCCESS, validModule),
            commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(validModule), modelStub.modulesAdded);
    }

    @Test
    public void execute_duplicatePerson_throwsCommandException() {
        Module validModule = new ModuleBuilder().build();
        ModuleAddCommand moduleAddCommand = new ModuleAddCommand(validModule);
        ModuleCommandTestUtil.ModelStub modelStub =
            new ModuleCommandTestUtil.ModelStubWithModule(validModule);

        assertThrows(CommandException.class, ModuleAddCommand.MESSAGE_DUPLICATE_MODULE, (
        ) -> moduleAddCommand.execute(modelStub));
    }

}
