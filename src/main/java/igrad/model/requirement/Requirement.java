package igrad.model.requirement;

import static igrad.commons.util.CollectionUtil.requireAllNonNull;
import static java.util.Objects.requireNonNull;

import java.util.List;

import igrad.model.module.Module;
import igrad.model.module.UniqueModuleList;
import javafx.collections.ObservableList;

//@@author yjskrs

/**
 * The Requirement class contains the data required at the requirement level.
 * A Requirement has a RequirementCode attribute, a Title attribute, a Credits attribute
 * and a list of modules.
 * Guarantees: immutable, field values are validated, non-null.
 */
public class Requirement implements ReadOnlyRequirement {

    private final RequirementCode requirementCode; // unique requirement code of the requirement
    private final Title title; // title of the requirement
    private final Credits credits; // credit information for the requirement
    private final UniqueModuleList modules = new UniqueModuleList(); // list of modules associated with requirement

    /**
     * Creates a {@code Requirement} object with given {@code requirementCode}, {@code title}
     * and {@code credits} and a default empty modules list.
     *
     * @param requirementCode RequirementCode of the requirement.
     * @param title           Title of the requirement.
     * @param credits         Credits of the requirement.
     */
    public Requirement(RequirementCode requirementCode, Title title, Credits credits) {
        requireAllNonNull(requirementCode, title, credits);

        this.requirementCode = requirementCode;
        this.title = title;
        this.credits = credits;
    }

    /**
     * Creates a {@code Requirement} object with given {@code requirementCode}, {@code title}, {@code credits} and
     * a list of {@code modules}.
     *
     * @param requirementCode RequirementCode of the requirement.
     * @param title           Title of the requirement.
     * @param credits         Credits of the requirement.
     * @param modules         List of modules belonging in the requirement.
     */
    public Requirement(RequirementCode requirementCode, Title title, Credits credits, List<Module> modules) {
        requireAllNonNull(requirementCode, title, credits, modules);

        this.requirementCode = requirementCode;
        this.title = title;
        this.credits = computeCredits(credits, modules);
        resetModules(modules);
    }

    /**
     * Creates a requirement by making a copy from an existing requirement {@code toBeCopied}.
     *
     * @param toBeCopied Requirement to copy from.
     */
    public Requirement(ReadOnlyRequirement toBeCopied) {
        requireNonNull(toBeCopied);

        this.requirementCode = toBeCopied.getRequirementCode();
        this.title = toBeCopied.getTitle();
        this.credits = toBeCopied.getCredits();
        resetModules(toBeCopied.getModuleList());
    }

    // requirement-level operations

    /**
     * Replaces the contents of the module list with {@code modules}.
     * The list must not contain duplicate modules.
     */
    public void resetModules(List<Module> modules) {
        this.modules.setModules(modules);
    }

    // module-level operations

    /**
     * Returns true if a module with the same identity as {@code module} exists in the list.
     */
    public boolean hasModule(Module module) {
        return modules.contains(module);
    }

    //@@author nathanaelseen

    /**
     * Returns true if all modules in {@code modules} with the same identity as {@code module} exists in the list.
     */
    public boolean hasModules(List<Module> moduleList) {
        return modules.contains(moduleList);
    }

    //@@author yjskrs

    /**
     * Adds a {@code module} to the list.
     * The module must not already exist in the list.
     */
    public void addModule(Module module) {
        this.modules.add(module);
    }

    //@@author nathanaelseen

    /**
     * Adds a list of {@code Module}s; {@code modules} to the list.
     * The modules must not already exist in the list.
     */
    public void addModules(List<Module> modules) {
        this.modules.add(modules);
    }

    //@@author yjskrs

    /**
     * Replaces the given module {@code target} in the list with {@code editedModule}.
     * {@code target} must exist in the list.
     * The module identity of {@code editedModule} must not be the same as another existing module
     * in the list.
     */
    public void setModule(Module target, Module editedModule) {
        this.modules.setModule(target, editedModule);
    }

    /**
     * Removes {@code module} from this {@code Requirement}.
     * The {@code module} must exist in the list.
     */
    public void removeModule(Module module) {
        this.modules.remove(module);
    }

    /**
     * Removes a list of {@code Module}s; {@code modules} from the list.
     * The modules must already exist in the list.
     */
    public void removeModules(List<Module> modules) {
        this.modules.remove(modules);
    }

    // util methods

    @Override
    public Title getTitle() {
        return title;
    }

    @Override
    public Credits getCredits() {
        return credits;
    }

    @Override
    public RequirementCode getRequirementCode() {
        return requirementCode;
    }

    @Override
    public int getCreditsRequired() {
        return credits.getCreditsRequired();
    }

    @Override
    public int getCreditsAssigned() {
        return credits.getCreditsAssigned();
    }

    @Override
    public int getCreditsFulfilled() {
        return credits.getCreditsFulfilled();
    }

    @Override
    public ObservableList<Module> getModuleList() {
        return modules.asUnmodifiableObservableList();
    }

    @Override
    public boolean isSameRequirement(Requirement otherRequirement) {
        if (otherRequirement == null) {
            return false;
        }

        return this == otherRequirement
            || this.requirementCode.equals(otherRequirement.requirementCode);
    }

    @Override
    public boolean isFulfilled() {
        return credits.isFulfilled();
    }

    /**
     * Computes the number of credits fulfilled by the list of modules. Returns an integer.
     */
    private Credits computeCredits(Credits credits, List<Module> moduleList) {
        requireAllNonNull(credits, moduleList);

        int creditsAssigned = 0;
        int creditsFulfilled = 0;

        for (Module module : moduleList) {
            creditsAssigned += module.getCredits().toInteger();
            if (module.isDone()) {
                creditsFulfilled += module.getCredits().toInteger();
            }
        }

        return new Credits(credits.getCreditsRequired(), creditsAssigned, creditsFulfilled);
    }

    @Override
    public String toString() {
        RequirementCode code = getRequirementCode();
        Title title = getTitle();
        Credits credits = getCredits();

        final StringBuilder builder = new StringBuilder();
        builder
            .append("Requirement Code: ")
            .append(code)
            .append(", Title: ")
            .append(title)
            .append(", Credits: ")
            .append(credits);

        return builder.toString();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof Requirement // check properties, takes care of null other
            && requirementCode.equals(((Requirement) other).requirementCode)
            && title.equals(((Requirement) other).title)
            && credits.equals(((Requirement) other).credits)
            && modules.equals(((Requirement) other).modules));
    }

    @Override
    public int hashCode() {
        return modules.hashCode();
    }
}
