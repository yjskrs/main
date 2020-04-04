package igrad.model.requirement;

import static igrad.testutil.Assert.assertThrows;
import static igrad.testutil.TypicalModules.COMPUTER_ORGANISATION;
import static igrad.testutil.TypicalModules.PROGRAMMING_METHODOLOGY;
import static igrad.testutil.TypicalModules.getTypicalModules;
import static igrad.testutil.TypicalModules.getTypicalRequirement;
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
        assertThrows(NullPointerException.class, () -> requirement.hasModule((Module) null));
        assertThrows(NullPointerException.class, () -> requirement.hasModule((List<Module>) null));
    }

    @Test
    public void hasModule_moduleNotInRequirement_returnsFalse() {
        assertFalse(requirement.hasModule(PROGRAMMING_METHODOLOGY));
    }

    @Test
    public void hasModule_moduleInRequirement_returnsTrue() {
        requirement.addModule(PROGRAMMING_METHODOLOGY);
        assertTrue(requirement.hasModule(PROGRAMMING_METHODOLOGY));
    }

    @Test
    public void hasModule_withSameIdentityInRequirement_returnsTrue() {
        requirement.addModule(PROGRAMMING_METHODOLOGY);
        Module editedModule = new ModuleBuilder(PROGRAMMING_METHODOLOGY)
                                  .withTitle("Some Other Title")
                                  .build();
        assertTrue(requirement.hasModule(editedModule));
    }

    @Test
    public void addModule_nullModule_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> requirement.addModule((Module) null));
    }

    @Test
    public void addModule_moduleNotInRequirement_success() {
        requirement.addModule(PROGRAMMING_METHODOLOGY);
        requirement.addModule(COMPUTER_ORGANISATION);
        Requirement other = getTypicalRequirement();
        assertEquals(requirement, other);
    }

    @Test
    public void addModule_moduleInRequirement_throwsDuplicateModuleException() {
        requirement.addModule(PROGRAMMING_METHODOLOGY);
        assertThrows(DuplicateModuleException.class, () -> requirement.addModule(PROGRAMMING_METHODOLOGY));
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
    public void setModule_moduleInRequirement_success() {
        requirement.addModule(module);
        assertEquals(Collections.singletonList(module), requirement.getModuleList());
        Module modifiedModule = new ModuleBuilder(module)
                                    .withTitle("New Title")
                                    .build();
        requirement.setModule(module, modifiedModule);
        assertEquals(Collections.singletonList(modifiedModule), requirement.getModuleList());
    }

    @Test
    public void setModule_withSameIdentityInRequirement_success() {

    }

    @Test
    public void setModule_withDifferentIdentityInRequirement_success() {

    }

    @Test
    public void setModule_targetDoesNotExistInRequirement_throwsModuleNotFoundException() {

    }

    @Test
    public void setModule_duplicateModule_throwsDuplicateModuleException() {

    }

    //
    // /**
    //  * Removes {@code module} from this {@code Requirement}.
    //  * The {@code module} must exist in the list.
    //  */
    // public void removeModule(Module module) {
    //     modules.remove(module);
    // }

    @Test
    public void getTitle_withEmptyRequirement_success() {
        Title newTitle = new Title("Maths and Sciences");
        Requirement newReq = new RequirementBuilder(requirement)
                                 .withTitle("Maths and Sciences")
                                 .build();
        assertEquals(newTitle, newReq.getTitle());
    }

    @Test
    public void getCredits_withEmptyRequirement_success() {
        Credits newCreds = new Credits("60");
        Requirement newReq = new RequirementBuilder(requirement)
                                 .withCreditsOneParameter("60")
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

    //
    // @Override
    // public String toString() {
    //     return "Requirement: " + name + ", " + credits + " creditsRequired and "
    //                + getCreditsFulfilled() + " creditsFulfilled has "
    //                + modules.asUnmodifiableObservableList().size() + " modules";
    // }
    //
    // @Override
    // public boolean equals(Object other) {
    //     return other == this // short circuit if same object
    //                || (other instanceof Requirement // check properties
    //                        && name.equals(((Requirement) other).name)
    //                        && credits.equals(((Requirement) other).credits)
    //                        && modules.equals(((Requirement) other).modules));
    //
    // }
}
