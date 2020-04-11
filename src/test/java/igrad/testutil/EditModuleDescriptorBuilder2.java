package igrad.testutil;

import java.util.Optional;

import igrad.logic.commands.module.ModuleDoneCommand.EditModuleDescriptor;
import igrad.model.module.Grade;
import igrad.model.module.Module;
import igrad.model.module.Semester;

//@@author nathanaelseen

/**
 * A utility class to help with building EditModuleDescriptor objects (in module done command).
 */
public class EditModuleDescriptorBuilder2 {

    private EditModuleDescriptor descriptor;

    public EditModuleDescriptorBuilder2() {
        descriptor = new EditModuleDescriptor();
    }

    public EditModuleDescriptorBuilder2(EditModuleDescriptor descriptor) {
        this.descriptor = new EditModuleDescriptor(descriptor);
    }

    /**
     * Returns an {@code EditModuleDescriptor} with fields containing {@code module}'s details
     */
    public EditModuleDescriptorBuilder2(Module module) {
        descriptor = new EditModuleDescriptor();
        descriptor.setGrade(module.getGrade());
        descriptor.setSemester(module.getSemester());
    }

    /**
     * Sets the {@code Grade} of the {@code EditModuleDescriptor} that we are building.
     */
    public EditModuleDescriptorBuilder2 withGrade(String grade) {
        descriptor.setGrade(Optional.of(new Grade(grade)));
        return this;
    }

    /**
     * Sets the {@code Semester} of the {@code EditModuleDescriptor} that we are building.
     */
    public EditModuleDescriptorBuilder2 withSemester(String semester) {
        descriptor.setSemester(Optional.of(new Semester(semester)));
        return this;
    }

    public EditModuleDescriptor build() {
        return descriptor;
    }
}
