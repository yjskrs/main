package igrad.testutil;

import java.util.Optional;

import igrad.logic.commands.module.ModuleEditCommand;
import igrad.logic.commands.module.ModuleEditCommand.EditModuleDescriptor;
import igrad.model.module.Credits;
import igrad.model.module.Module;
import igrad.model.module.ModuleCode;
import igrad.model.module.Semester;
import igrad.model.module.Title;

/**
 * A utility class to help with building EditModuleDescriptor objects (in module edit command).
 */
public class EditModuleDescriptorBuilder {

    private ModuleEditCommand.EditModuleDescriptor descriptor;

    public EditModuleDescriptorBuilder() {
        descriptor = new ModuleEditCommand.EditModuleDescriptor();
    }

    public EditModuleDescriptorBuilder(ModuleEditCommand.EditModuleDescriptor descriptor) {
        this.descriptor = new ModuleEditCommand.EditModuleDescriptor(descriptor);
    }

    /**
     * Returns an {@code EditModuleDescriptor} with fields containing {@code module}'s details
     */
    public EditModuleDescriptorBuilder(Module module) {
        descriptor = new EditModuleDescriptor();
        descriptor.setTitle(module.getTitle());
        descriptor.setModuleCode(module.getModuleCode());
        descriptor.setCredits(module.getCredits());
        descriptor.setSemester(module.getSemester());
    }

    /**
     * Sets the {@code Title} of the {@code EditModuleDescriptor} that we are building.
     */
    public EditModuleDescriptorBuilder withTitle(String title) {
        descriptor.setTitle(new Title(title));
        return this;
    }

    /**
     * Sets the {@code ModuleCode} of the {@code EditModuleDescriptor} that we are building.
     */
    public EditModuleDescriptorBuilder withModuleCode(String moduleCode) {
        descriptor.setModuleCode(new ModuleCode(moduleCode));
        return this;
    }

    /**
     * Sets the {@code Credits} of the {@code EditModuleDescriptor} that we are building.
     */
    public EditModuleDescriptorBuilder withCredits(String credits) {
        descriptor.setCredits(new Credits(credits));
        return this;
    }

    /**
     * Sets the {@code Semester} of the {@code EditModuleDescriptor} that we are building.
     */
    public EditModuleDescriptorBuilder withSemester(String semester) {
        descriptor.setSemester(Optional.of(new Semester(semester)));
        return this;
    }

    public EditModuleDescriptor build() {
        return descriptor;
    }
}
