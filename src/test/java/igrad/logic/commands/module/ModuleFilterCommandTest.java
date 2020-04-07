package igrad.logic.commands.module;

import static igrad.testutil.Assert.assertThrows;
import org.junit.jupiter.api.Test;

public class ModuleFilterCommandTest {

    @Test
    public void constructor_nullModule_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new ModuleFilterCommand(null, null, null, null));
    }

    @Test
    public void execute_filterModuleByGrade() {

    }

}
