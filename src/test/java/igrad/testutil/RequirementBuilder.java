package igrad.testutil;

import static igrad.logic.commands.requirement.RequirementCommandTestUtil.VALID_REQ_CODE_CSF;
import static igrad.logic.commands.requirement.RequirementCommandTestUtil.VALID_REQ_CREDITS_CSF;
import static igrad.logic.commands.requirement.RequirementCommandTestUtil.VALID_REQ_TITLE_CSF;

import java.util.List;

import igrad.model.module.Module;
import igrad.model.module.UniqueModuleList;
import igrad.model.requirement.Credits;
import igrad.model.requirement.Requirement;
import igrad.model.requirement.RequirementCode;
import igrad.model.requirement.Title;

//@@author yjskrs

/**
 * A utility class to help with building {@code Requirement} objects.
 */
public class RequirementBuilder {
    public static final String DEFAULT_REQUIREMENT_CODE = VALID_REQ_CODE_CSF;
    public static final String DEFAULT_TITLE = VALID_REQ_TITLE_CSF;
    public static final String DEFAULT_CREDITS = VALID_REQ_CREDITS_CSF;

    private RequirementCode requirementCode;
    private Title title;
    private Credits credits;
    private UniqueModuleList modules;

    /**
     * Initializes the RequirementBuilder with the default data.
     */
    public RequirementBuilder() {
        requirementCode = new RequirementCode(DEFAULT_REQUIREMENT_CODE);
        title = new Title(DEFAULT_TITLE);
        credits = new Credits(DEFAULT_CREDITS);
        modules = new UniqueModuleList();
    }

    /**
     * Initializes the RequirementBuilder with the {@code Requirement requirementToCopy}.
     */
    public RequirementBuilder(Requirement requirementToCopy) {
        requirementCode = requirementToCopy.getRequirementCode();
        title = requirementToCopy.getTitle();
        credits = requirementToCopy.getCredits();
        modules = new UniqueModuleList();
        modules.setModules(requirementToCopy.getModuleList());
    }

    /**
     * Sets the {@code RequirementCode} of the {@code Requirement} that we are building.
     */
    public RequirementBuilder withRequirementCode(String requirementCode) {
        this.requirementCode = new RequirementCode(requirementCode);
        return this;
    }

    /**
     * Sets the {@code Title} of the {@code Requirement} that we are building.
     */
    public RequirementBuilder withTitle(String title) {
        this.title = new Title(title);
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
    public RequirementBuilder withCreditsThreeParameters(int creditsRequired,
                                                         int creditsAssigned,
                                                         int creditsFulfilled) {
        this.credits = new Credits(creditsRequired, creditsAssigned, creditsFulfilled);
        return this;
    }

    /**
     * Update credits value with addition of modules.
     */
    public RequirementBuilder updateCredits() {
        int creditsAssigned = 0;
        int creditsFulfilled = 0;

        for (Module module : modules) {
            creditsAssigned += module.getCredits().toInteger();
            if (module.isDone()) {
                creditsFulfilled += module.getCredits().toInteger();
            }
        }

        this.credits = new Credits(credits.getCreditsRequired(), creditsAssigned, creditsFulfilled);
        return this;
    }

    /**
     * Sets the {@code UniqueModuleList} of the {@code Requirement} that we are building.
     */
    public RequirementBuilder withModules(List<Module> moduleList) {
        modules.setModules(moduleList);
        return updateCredits();
    }

    /**
     * Returns a requirement object.
     */
    public Requirement build() {
        List<Module> moduleList = modules.asUnmodifiableObservableList();
        return new Requirement(requirementCode, title, credits, moduleList);
    }

}
