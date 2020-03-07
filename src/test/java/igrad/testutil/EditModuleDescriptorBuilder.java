package igrad.testutil;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import igrad.logic.commands.EditCommand;
import igrad.logic.commands.EditCommand.EditModuleDescriptor;
import igrad.model.module.Email;
import igrad.model.module.Module;
import igrad.model.module.Name;
import igrad.model.module.Phone;
import igrad.model.tag.Tag;

/**
 * A utility class to help with building EditModuleDescriptor objects.
 */
public class EditModuleDescriptorBuilder {

    private EditCommand.EditModuleDescriptor descriptor;

    public EditModuleDescriptorBuilder() {
        descriptor = new EditCommand.EditModuleDescriptor();
    }

    public EditModuleDescriptorBuilder(EditCommand.EditModuleDescriptor descriptor) {
        this.descriptor = new EditCommand.EditModuleDescriptor(descriptor);
    }

    /**
     * Returns an {@code EditModuleDescriptor} with fields containing {@code module}'s details
     */
    public EditModuleDescriptorBuilder(Module module) {
        descriptor = new EditCommand.EditModuleDescriptor();
        descriptor.setName(module.getName());
        descriptor.setPhone(module.getPhone());
        descriptor.setEmail(module.getEmail());
        descriptor.setTags(module.getTags());
    }

    /**
     * Sets the {@code Name} of the {@code EditModuleDescriptor} that we are building.
     */
    public EditModuleDescriptorBuilder withName(String name) {
        descriptor.setName(new Name(name));
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code EditModuleDescriptor} that we are building.
     */
    public EditModuleDescriptorBuilder withPhone(String phone) {
        descriptor.setPhone(new Phone(phone));
        return this;
    }

    /**
     * Sets the {@code Email} of the {@code EditModuleDescriptor} that we are building.
     */
    public EditModuleDescriptorBuilder withEmail(String email) {
        descriptor.setEmail(new Email(email));
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code EditModuleDescriptor}
     * that we are building.
     */
    public EditModuleDescriptorBuilder withTags(String... tags) {
        Set<Tag> tagSet = Stream.of(tags).map(Tag::new).collect(Collectors.toSet());
        descriptor.setTags(tagSet);
        return this;
    }

    public EditModuleDescriptor build() {
        return descriptor;
    }
}
