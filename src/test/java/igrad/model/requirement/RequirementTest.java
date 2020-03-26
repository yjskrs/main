package igrad.model.requirement;

import static igrad.testutil.Assert.assertThrows;
import static igrad.testutil.TypicalModules.getTypicalRequirement;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Collections;

import org.junit.jupiter.api.Test;

import igrad.testutil.RequirementBuilder;

public class RequirementTest {

    @Test
    public void constructor() {
        Requirement requirement = new RequirementBuilder().build();
        assertEquals(Collections.emptyList(), requirement.getModuleList());
    }

    @Test
    public void resetModules_null_throwsNullPointerException() {
        Requirement requirement = new RequirementBuilder().build();
        assertThrows(NullPointerException.class, () -> requirement.resetModules(null));
    }

    @Test
    public void resetModules_withValidReadOnlyRequirement_replacesModules() {
        Requirement requirement = new RequirementBuilder().build();
        assertThrows(NullPointerException.class, () -> requirement.resetModules(getTypicalRequirement()));
    }

    // @Test
    // public void setModules()
    //
    // /**
    //  * Replaces the contents of the module list with {@code modules}.
    //  * The list must not contain duplicate modules.
    //  */
    // public void setModules(List<Module> modules) {
    //     this.modules.setModules(modules);
    // }
    //
    // // module-level operations
    //
    // /**
    //  * Returns true if a module with the same identity as {@code module} exists in the list.
    //  */
    // public boolean hasModule(Module module) {
    //     requireNonNull(module);
    //
    //     return modules.contains(module);
    // }
    //
    // /**
    //  * Adds a {@code module} to the list.
    //  * The module must not already exist in the list.
    //  */
    // public void addModule(Module module) {
    //     requireNonNull(module);
    //
    //     modules.add(module);
    // }
    //
    // /**
    //  * Replaces the given module {@code target} in the list with {@code editedModule}.
    //  * The {@code target} module must exist in the list.
    //  * The module identity of {@code editedModule} must not be the same as another
    //  * existing module in the list.
    //  */
    // public void setModule(Module target, Module editedModule) {
    //     requireNonNull(editedModule);
    //
    //     modules.setModule(target, editedModule);
    // }
    //
    // /**
    //  * Removes {@code module} from this {@code Requirement}.
    //  * The {@code module} must exist in the list.
    //  */
    // public void removeModule(Module module) {
    //     modules.remove(module);
    // }
    //
    // // util methods
    //
    // @Override
    // public Name getName() {
    //     return name;
    // }
    //
    // /**
    //  * Checks if {@code otherRequirement} has the same name as this requirement.
    //  */
    // public boolean hasSameName(Requirement otherRequirement) {
    //     return this.name.equals(otherRequirement.name);
    // }
    //
    // /**
    //  * Checks if {@code otherRequirement} has the same credits as this requirement.
    //  */
    // public boolean hasSameCredits(Requirement otherRequirement) {
    //     return this.credits.equals(otherRequirement.credits);
    // }
    //
    // @Override
    // public Credits getCredits() {
    //     return credits;
    // }
    //
    // @Override
    // public String getCreditsRequired() {
    //     return credits.getCreditsRequired();
    // }
    //
    // @Override
    // public String getCreditsFulfilled() {
    //     return credits.getCreditsFulfilled();
    // }
    //
    // @Override
    // public ObservableList<Module> getModuleList() {
    //     return modules.asUnmodifiableObservableList();
    // }
    //
    // @Override
    // public boolean isFulfilled() {
    //     return credits.isFulfilled();
    // }
    //
    // @Override
    // public String toString() {
    //     return "Requirement: " + name + ", " + credits + " creditsRequired and "
    //                + getCreditsFulfilled() + " creditsFulfilled has "
    //                + modules.asUnmodifiableObservableList().size() + " modules";
    //     // TODO: refine later
    // }x
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
