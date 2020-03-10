package igrad.testutil;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import igrad.logic.commands.ModuleEditCommand;
import igrad.logic.commands.ModuleEditCommand.EditModuleDescriptor;
import igrad.model.module.Credits;
import igrad.model.module.Memo;
import igrad.model.module.Module;
import igrad.model.module.ModuleCode;
import igrad.model.module.Semester;
import igrad.model.module.Title;
import igrad.model.tag.Tag;

/**
 * A utility class to help with building EditModuleDescriptor objects.
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
        descriptor.setMemo(module.getMemo());
        descriptor.setSemester(module.getSemester());
        descriptor.setTags(module.getTags());
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
     * Sets the {@code Memo} of the {@code EditModuleDescriptor} that we are building.
     */
    public EditModuleDescriptorBuilder withMemo(String memo) {
        descriptor.setMemo(new Memo(memo));
        return this;
    }

    /**
     * Sets the {@code Semester} of the {@code EditModuleDescriptor} that we are building.
     */
    public EditModuleDescriptorBuilder withSemester(String semester) {
        descriptor.setSemester(new Semester(semester));
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code EditPersonDescriptor}
     * that we are building.
     */
    public EditModuleDescriptorBuilder withTags(String... tags) {
        Set<Tag> tagsSet = Stream.of(tags).map(Tag::new).collect(Collectors.toSet());
        descriptor.setTags(tagsSet);
        return this;
    }

    public EditModuleDescriptor build() {
        return descriptor;
    }
}
