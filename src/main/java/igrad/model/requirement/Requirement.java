package igrad.model.requirement;

import static java.util.Objects.requireNonNull;

import java.util.List;

import igrad.model.module.Module;
import igrad.model.module.UniqueModuleList;
import javafx.collections.ObservableList;

/**
 * The Requirement class contains the data required at the requirement level.
 * A Requirement has a Name attribute, a Credits attribute and a list of modules.
 * Guarantees: immutable, field values are validated, non-null.
 */
public class Requirement implements ReadOnlyRequirement {

    private final Name name; // name of the requirement
    private final Credits credits; // credit information for the requirement
    private final UniqueModuleList modules = new UniqueModuleList(); // list of modules associated with requirement

    /**
     * Creates a {@code Requirement} object with given {@code name} and {@code credits}
     * and a default empty modules list.
     *
     * @param name Name of the requirement.
     * @param credits Credits of the requirement.
     */
    public Requirement(Name name, Credits credits) {
        requireNonNull(name);

        this.name = name;
        this.credits = credits;
    }

    /**
     * Creates a {@code Requirement} object with given {@code name}, {@code credits} and
     * a list of {@code modules}.
     *
     * @param name Name of the requirement.
     * @param credits Credits of the requirement.
     * @param modules List of modules belonging in the requirement.
     */
    public Requirement(Name name, Credits credits, List<Module> modules) {
        requireNonNull(name);

        this.name = name;
        this.credits = credits;
        setModules(modules);
    }

    /**
     * Creates a requirement by making a copy from an existing requirement {@code toBeCopied}.
     *
     * @param toBeCopied Requirement to copy from.
     */
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
     * The list must not contain duplicate modules.
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
     * Adds a {@code module} to the list.
     * The module must not already exist in the list.
     */
    public void addModule(Module module) {
        requireNonNull(module);

        modules.add(module);
    }

    /**
     * Replaces the given module {@code target} in the list with {@code editedModule}.
     * The {@code target} module must exist in the list.
     * The module identity of {@code editedModule} must not be the same as another
     * existing module in the list.
     */
    public void setModule(Module target, Module editedModule) {
        requireNonNull(editedModule);

        modules.setModule(target, editedModule);
    }

    /**
     * Removes {@code module} from this {@code Requirement}.
     * The {@code module} must exist in the list.
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
    public String getCreditsRequired() {
        return credits.getCreditsRequired();
    }

    @Override
    public String getCreditsFulfilled() {
        return credits.getCreditsFulfilled();
    }

    @Override
    public ObservableList<Module> getModuleList() {
        return modules.asUnmodifiableObservableList();
    }

    @Override
    public boolean isFulfilled() {
        return credits.isFulfilled();
    }

    @Override
    public String toString() {
        return "Requirement: " + name + ", " + credits + " creditsRequired and "
                   + getCreditsFulfilled() + " creditsFulfilled has "
                   + modules.asUnmodifiableObservableList().size() + " modules";
        // TODO: refine later
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                   || (other instanceof Requirement // check properties
                           && name.equals(((Requirement) other).name)
                           && credits.equals(((Requirement) other).credits)
                           && modules.equals(((Requirement) other).modules));

    }

    @Override
    public int hashCode() {
        return modules.hashCode();
    }
}
