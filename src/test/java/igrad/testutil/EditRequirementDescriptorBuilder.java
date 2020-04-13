package igrad.testutil;

import static igrad.logic.commands.requirement.RequirementEditCommand.EditRequirementDescriptor;
import static java.util.Objects.requireNonNull;

import igrad.model.requirement.Credits;
import igrad.model.requirement.Requirement;
import igrad.model.requirement.Title;

//@@author yjskrs

/**
 * A utility class to help with building EditRequirementDescriptor objects.
 */
public class EditRequirementDescriptorBuilder {

    private EditRequirementDescriptor descriptor;

    public EditRequirementDescriptorBuilder() {
        descriptor = new EditRequirementDescriptor();
    }

    public EditRequirementDescriptorBuilder(EditRequirementDescriptor descriptor) {
        requireNonNull(descriptor);

        this.descriptor = new EditRequirementDescriptor(descriptor);
    }

    /**
     * Returns an {@code EditRequirementDescriptor} with fields containing {@code requirement}'s details
     */
    public EditRequirementDescriptorBuilder(Requirement requirement) {
        requireNonNull(requirement);

        descriptor = new EditRequirementDescriptor();
        descriptor.setTitle(requirement.getTitle());
        descriptor.setCredits(requirement.getCredits());
    }

    /**
     * Sets the {@code Title} of the {@code EditRequirementDescriptor} that we are building.
     */
    public EditRequirementDescriptorBuilder withTitle(String title) {
        descriptor.setTitle(new Title(title));
        return this;
    }

    /**
     * Sets the {@code Credits} of the {@code EditRequirementDescriptor} that we are building.
     */
    public EditRequirementDescriptorBuilder withCredits(String credits) {
        descriptor.setCredits(new Credits(credits));
        return this;
    }

    /**
     * Returns an EditRequirementDescriptor object.
     */
    public EditRequirementDescriptor build() {
        return descriptor;
    }
}
