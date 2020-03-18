package igrad.model.requirement;

import static java.util.Objects.requireNonNull;

import java.util.List;

import igrad.model.module.Module;
import igrad.model.module.UniqueModuleList;
import javafx.collections.ObservableList;

/**
 * Contains data at the requirement level.
 */
public class Requirement implements ReadOnlyRequirement {
    private final UniqueModuleList modules = new UniqueModuleList();
    private final Name name;
    private final Credits credits;

    public Requirement(Name name, Credits credits) {
        requireNonNull(name);
        this.name = name;
        this.credits = credits;
    }

    public Requirement(Name name, Credits credits, List<Module> modules) {
        requireNonNull(name);
        this.name = name;
        this.credits = credits;
        setModules(modules);
    }

    public Requirement(ReadOnlyRequirement toBeCopied) {
        requireNonNull(toBeCopied);

        this.name = toBeCopied.getName();
        this.credits = toBeCopied.getCredits();
        resetModules(toBeCopied);
    }

    // requirement-level operations

    /**
     * Resets the existing modules of this {@code Requirement} with {@code newData}.
     */
    public void resetModules(ReadOnlyRequirement newData) {
        requireNonNull(newData);

        setModules(newData.getModuleList());
    }

    /**
     * Replaces the contents of the module list with {@code modules}.
     * {@code modules} must not contain duplicate modules.
     */
    public void setModules(List<Module> modules) {
        this.modules.setModules(modules);
    }

    // module-level operations

    /**
     * Returns true if a module with the same identity as {@code module} exists in the list.
     */
    public boolean hasModule(Module module) {
        requireNonNull(module);
        return modules.contains(module);
    }

    /**
     * Adds a module to the list.
     * The module must not already exist in the list.
     */
    public void addModule(Module module) {
        modules.add(module);
    }

    /**
     * Replaces the given module {@code target} in the list with {@code editedModule}.
     * {@code target} must exist in the list.
     * The module identity of {@code editedModule} must not be the same as another existing module
     * in the list.
     */
    public void setModule(Module target, Module editedModule) {
        requireNonNull(editedModule);

        modules.setModule(target, editedModule);
    }

    /**
     * Removes {@code module} from this {@code Requirement}.
     * {@code module} must exist in the list.
     */
    public void removeModule(Module module) {
        modules.remove(module);
    }

    // util methods

    @Override
    public Name getName() {
        return name;
    }

    /**
     * Checks if {@code otherRequirement} has the same name as this requirement.
     */
    public boolean hasSameName(Requirement otherRequirement) {
        return this.name.equals(otherRequirement.name);
    }

    /**
     * Checks if {@code otherRequirement} has the same credits as this requirement.
     */
    public boolean hasSameCredits(Requirement otherRequirement) {
        return this.credits.equals(otherRequirement.credits);
    }

    @Override
    public Credits getCredits() {
        return credits;
    }

    @Override
    public ObservableList<Module> getModuleList() {
        return modules.asUnmodifiableObservableList();
    }

    @Override
    public String toString() {
        return "Requirement: " + name + " " + credits + " has "
                   + modules.asUnmodifiableObservableList().size() + " modules";
        // TODO: refine later
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof Requirement // instanceof handles nulls
            && modules.equals(((Requirement) other).modules)
            && name.equals(((Requirement) other).name));
    }

    @Override
    public int hashCode() {
        return modules.hashCode();
    }
}
