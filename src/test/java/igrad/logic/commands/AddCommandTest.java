package igrad.logic.commands;

import static igrad.testutil.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import igrad.logic.commands.exceptions.CommandException;
import igrad.logic.commands.module.ModuleAddCommand;
import igrad.model.module.Module;
import igrad.testutil.ModuleBuilder;

public class AddCommandTest {

    @Test
    public void constructor_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new ModuleAddCommand(null));
    }

    @Test
    public void execute_personAcceptedByModel_addSuccessful() throws Exception {
        CommandTestUtil.ModelStubAcceptingModuleAdded modelStub = new CommandTestUtil.ModelStubAcceptingModuleAdded();
        Module validModule = new ModuleBuilder().build();

        CommandResult commandResult = new ModuleAddCommand(validModule).execute(modelStub);

        assertEquals(String.format(ModuleAddCommand.MESSAGE_MODULE_ADD_SUCCESS, validModule),
            commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(validModule), modelStub.personsAdded);
    }

    @Test
    public void execute_duplicatePerson_throwsCommandException() {
        Module validModule = new ModuleBuilder().build();
        ModuleAddCommand moduleAddCommand = new ModuleAddCommand(validModule);
        CommandTestUtil.ModelStub modelStub = new CommandTestUtil.ModelStubWithModule(validModule);

        assertThrows(CommandException.class, ModuleAddCommand.MESSAGE_DUPLICATE_MODULE, (
        ) -> moduleAddCommand.execute(modelStub));
    }

}