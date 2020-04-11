package igrad.logic.commands.requirement;

import static igrad.logic.commands.module.ModuleCommandTestUtil.VALID_MODULE_CODE_CS1101S;
import static igrad.logic.commands.module.ModuleCommandTestUtil.VALID_MODULE_CODE_CS2040;
import static igrad.logic.commands.module.ModuleCommandTestUtil.VALID_MODULE_CODE_CS2100;
import static igrad.logic.commands.requirement.RequirementCommandTestUtil.VALID_REQ_CODE_UE;
import static igrad.testutil.Assert.assertThrows;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import igrad.model.module.ModuleCode;
import igrad.model.requirement.RequirementCode;
// import igrad.model.requirement.Requirement;

public class RequirementAssignCommandTest {
    @Test
    public void constructor_null_throwsNullPointerException() {
        // RequirementCode null, but moduleCodes not null
        RequirementCode requirementCodeA = null;
        List<ModuleCode> moduleCodesA = new ArrayList<ModuleCode>() {
            {
                add(new ModuleCode(VALID_MODULE_CODE_CS1101S));
                add(new ModuleCode(VALID_MODULE_CODE_CS2040));
                add(new ModuleCode(VALID_MODULE_CODE_CS2100));
            }
        };

        assertThrows(NullPointerException.class, (
        ) -> new RequirementAssignCommand(requirementCodeA, moduleCodesA));

        // moduleCodes null, but RequirementCode not null
        RequirementCode requirementCodeB = new RequirementCode(VALID_REQ_CODE_UE);
        List<ModuleCode> moduleCodesB = null;

        assertThrows(NullPointerException.class, (
        ) -> new RequirementAssignCommand(requirementCodeB, moduleCodesB));
    }
}
