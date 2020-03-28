package igrad.testutil;

import java.util.List;

import igrad.model.module.Module;
import igrad.model.module.UniqueModuleList;
import igrad.model.requirement.Credits;
import igrad.model.requirement.Title;
import igrad.model.requirement.Requirement;

/**
 * A utility class to help with building {@code Requirement} objects.
 */
public class RequirementBuilder {
    public static final String DEFAULT_NAME = "Computer Science Foundation";
    public static final String DEFAULT_CREDITS = "48";

    private Title title;
    private Credits credits;
    private UniqueModuleList modules;

    /**
     * Initializes the RequirementBuilder with the default data.
     */
    public RequirementBuilder() {
        title = new Title(DEFAULT_NAME);
        credits = new Credits(DEFAULT_CREDITS);
        modules = new UniqueModuleList();
    }

    /**
     * Initializes the RequirementBuilder with the {@code Requirement requirementToCopy}.
     */
    public RequirementBuilder(Requirement requirementToCopy) {
        title = requirementToCopy.getTitle();
        credits = requirementToCopy.getCredits();
        modules = new UniqueModuleList();
        modules.setModules(requirementToCopy.getModuleList());
    }

    /**
     * Sets the {@code Name} of the {@code Requirement} that we are building.
     */
    public RequirementBuilder withName(String name) {
        this.title = new Title(name);
        return this;
    }

    /**
     * Sets the {@code Credits} of the {@code Requirement} that we are building.
     */
    public RequirementBuilder withCreditsOneParameter(String creditsRequired) {
        this.credits = new Credits(creditsRequired);
        return this;
    }

    /**
     * Sets the {@code Credits} of the {@code Requirement} that we are building.
     */
    public RequirementBuilder withCreditsTwoParameters(String creditsRequired, String creditsFulfilled) {
        this.credits = new Credits(creditsRequired, creditsFulfilled);
        return this;
    }

    /**
     * Sets the {@code UniqueModuleList} of the {@code Requirement} that we are building.
     */
    public RequirementBuilder withModules(List<Module> moduleList) {
        modules.setModules(moduleList);
        return this;
    }

    /**
     * Returns a requirement object.
     */
    public Requirement build() {
        List<Module> moduleList = modules.asUnmodifiableObservableList();
        return new Requirement(title, credits, moduleList);
    }

}
