package igrad.model.requirement;

//@@author yjskrs

import static igrad.logic.commands.requirement.RequirementCommandTestUtil.VALID_REQ_CODE_MS;
import static igrad.logic.commands.requirement.RequirementCommandTestUtil.VALID_REQ_CREDITS_GE;
import static igrad.logic.commands.requirement.RequirementCommandTestUtil.VALID_REQ_CREDITS_MS;
import static igrad.logic.commands.requirement.RequirementCommandTestUtil.VALID_REQ_TITLE_MS;
import static igrad.testutil.Assert.assertThrows;
import static igrad.testutil.TypicalModules.CS1101S;
import static igrad.testutil.TypicalModules.CS2100;
import static igrad.testutil.TypicalModules.getTypicalModules;
import static igrad.testutil.TypicalModules.getTypicalRequirement;
import static igrad.testutil.TypicalRequirements.GENERAL_ELECTIVES;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import igrad.model.module.Module;
import igrad.model.module.exceptions.DuplicateModuleException;
import igrad.model.module.exceptions.ModuleNotFoundException;
import igrad.testutil.ModuleBuilder;
import igrad.testutil.RequirementBuilder;

public class RequirementTest {
    private final Requirement requirement = new RequirementBuilder().build();
    private final Module module = new ModuleBuilder().build();

    @Test
    public void constructor_withEmptyRequirement_createsRequirementWithEmptyModuleList() {
        assertEquals(Collections.emptyList(), requirement.getModuleList());
    }

    @Test
    public void resetModules_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> requirement.resetModules(null));
    }

    @Test
    public void resetModules_withValidModuleList_success() {
        List<Module> modules = getTypicalModules();
        requirement.resetModules(modules);
        assertEquals(getTypicalRequirement(), requirement);
    }

    @Test
    public void resetModules_withDuplicateModules_throwsDuplicateModuleException() {
        Module other = new ModuleBuilder()
            .withTitle("Pragramming Methadolajy")
            .withCredits("8")
            .build();
        List<Module> modules = Arrays.asList(module, other);
        assertThrows(DuplicateModuleException.class, () -> requirement.resetModules(modules));
    }

    @Test
    public void hasModule_nullModule_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> requirement.hasModule(null));
        assertThrows(NullPointerException.class, () -> requirement.hasModules(null));
    }

    @Test
    public void hasModule_moduleNotInRequirement_returnsFalse() {
        assertFalse(requirement.hasModule(CS1101S));
    }

    @Test
    public void hasModule_moduleInRequirement_returnsTrue() {
        requirement.addModule(CS1101S);
        assertTrue(requirement.hasModule(CS1101S));
    }

    @Test
    public void hasModule_withSameIdentityInRequirement_returnsTrue() {
        requirement.addModule(CS1101S);
        Module editedModule = new ModuleBuilder(CS1101S)
            .withTitle("Some Other Title")
            .build();
        assertTrue(requirement.hasModule(editedModule));
    }

    @Test
    public void addModule_nullModule_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> requirement.addModule(null));
    }

    @Test
    public void addModule_moduleNotInRequirement_success() {
        requirement.addModule(CS1101S);
        requirement.addModule(CS2100);
        Requirement other = getTypicalRequirement();
        assertEquals(requirement, other);
    }

    @Test
    public void addModule_moduleInRequirement_throwsDuplicateModuleException() {
        requirement.addModule(CS1101S);
        assertThrows(DuplicateModuleException.class, () -> requirement.addModule(CS1101S));
    }

    @Test
    public void addModule_withSameIdentityInRequirement_throwsDuplicateModuleException() {
        requirement.addModule(module);
        Module sameModule = new ModuleBuilder(module)
            .withTitle("Another Title")
            .build();
        assertThrows(DuplicateModuleException.class, () -> requirement.addModule(sameModule));
    }

    @Test
    public void setModule_targetNull_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> requirement.setModule(null, module));
    }

    @Test
    public void setModule_editedModuleNull_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> requirement.setModule(module, null));
    }

    @Test
    public void setModule_withSameIdentityInRequirement_success() {
        requirement.addModule(module);
        assertEquals(Collections.singletonList(module), requirement.getModuleList());
        Module modifiedModule = new ModuleBuilder(module)
            .withTitle("New Title")
            .build();
        requirement.setModule(module, modifiedModule);
        assertEquals(Collections.singletonList(modifiedModule), requirement.getModuleList());
    }

    @Test
    public void setModule_withDifferentIdentityInRequirement_success() {
        requirement.addModule(module);
        assertEquals(Collections.singletonList(module), requirement.getModuleList());
        Module modifiedModule = new ModuleBuilder(module)
            .withModuleCode("RN1111G")
            .withTitle("New Title")
            .build();
        requirement.setModule(module, modifiedModule);
        assertEquals(Collections.singletonList(modifiedModule), requirement.getModuleList());
    }

    @Test
    public void setModule_targetDoesNotExistInRequirement_throwsModuleNotFoundException() {
        assertThrows(ModuleNotFoundException.class, () -> requirement.setModule(module, module));
    }

    @Test
    public void setModule_duplicateModule_throwsDuplicateModuleException() {
        Module otherModule = new ModuleBuilder(module)
            .withModuleCode("RNG1111")
            .withTitle("New Title")
            .build();
        requirement.addModule(module);
        requirement.addModule(otherModule);
        Module modifiedOtherModule = new ModuleBuilder(otherModule).build();
        assertThrows(DuplicateModuleException.class, () -> requirement.setModule(module, modifiedOtherModule));
    }

    @Test
    public void removeModule_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> requirement.removeModule(null));
    }

    @Test
    public void removeModule_moduleInRequirement_success() {
        requirement.addModule(module);
        requirement.removeModule(module);
        assertEquals(Collections.emptyList(), requirement.getModuleList());
    }

    @Test
    public void removeModule_moduleNotInRequirement_throwsModuleNotFoundException() {
        assertThrows(ModuleNotFoundException.class, () -> requirement.removeModule(module));
        requirement.addModule(module);
        Module modifiedModule = new ModuleBuilder(module)
            .withTitle("This is a title")
            .build();
        assertThrows(ModuleNotFoundException.class, () -> requirement.removeModule(modifiedModule));
    }

    @Test
    public void getTitle_withEmptyRequirement_success() {
        Title newTitle = new Title(VALID_REQ_TITLE_MS);
        Requirement newReq = new RequirementBuilder(requirement)
            .withTitle(VALID_REQ_TITLE_MS)
            .build();
        assertEquals(newTitle, newReq.getTitle());
    }

    @Test
    public void getCredits_withEmptyRequirement_success() {
        Credits newCreds = new Credits(VALID_REQ_CREDITS_GE);
        Requirement newReq = new RequirementBuilder(requirement)
            .withCreditsOneParameter(VALID_REQ_CREDITS_GE)
            .build();
        assertEquals(newCreds, newReq.getCredits());
    }

    @Test
    public void getCreditsRequired_withCreditsRequiredValue_nonZero() {
        assertNotEquals(0, requirement.getCreditsRequired());
    }

    @Test
    public void getCreditsFulfilled_withEmptyRequirement_returnsZero() {
        assertEquals(0, requirement.getCreditsFulfilled());
    }

    @Test
    public void getModuleList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> requirement.getModuleList().remove(0));
    }

    @Test
    public void isSameRequirement() {
        // null
        assertFalse(GENERAL_ELECTIVES.isSameRequirement(null));

        // same requirement
        assertTrue(GENERAL_ELECTIVES.isSameRequirement(GENERAL_ELECTIVES));

        // different title
        Requirement editedRequirement = new RequirementBuilder(GENERAL_ELECTIVES)
            .withTitle(VALID_REQ_TITLE_MS)
            .build();
        assertTrue(GENERAL_ELECTIVES.isSameRequirement(editedRequirement));

        // different credits
        editedRequirement = new RequirementBuilder(GENERAL_ELECTIVES)
            .withCreditsOneParameter(VALID_REQ_CREDITS_MS)
            .build();
        assertTrue(GENERAL_ELECTIVES.isSameRequirement(editedRequirement));

        // different requirement code
        editedRequirement = new RequirementBuilder(GENERAL_ELECTIVES)
            .withRequirementCode(VALID_REQ_CODE_MS)
            .build();
        assertFalse(GENERAL_ELECTIVES.isSameRequirement(editedRequirement));
    }

    @Test
    public void equals() {
        // same requirement
        assertTrue(GENERAL_ELECTIVES.equals(GENERAL_ELECTIVES));

        // copied requirement
        Requirement requirementCopy = new RequirementBuilder(GENERAL_ELECTIVES).build();
        assertTrue(GENERAL_ELECTIVES.equals(requirementCopy));

        // different type
        assertFalse(GENERAL_ELECTIVES.equals(module));

        // different requirement title and credits
        Requirement other = new RequirementBuilder(GENERAL_ELECTIVES)
            .withTitle(VALID_REQ_TITLE_MS)
            .withCreditsOneParameter(VALID_REQ_CREDITS_MS)
            .build();
        assertFalse(GENERAL_ELECTIVES.equals(other));
    }

}
