package igrad.testutil;

import java.util.Optional;

import igrad.logic.commands.course.CourseEditCommand.EditCourseDescriptor;
import igrad.model.course.CourseInfo;
import igrad.model.course.Name;
import igrad.model.course.Semesters;

//@@author nathanaelseen

/**
 * A utility class to help with building EditCourseDescriptor objects (in course edit command).
 */
public class EditCourseDescriptorBuilder {

    private EditCourseDescriptor descriptor;

    public EditCourseDescriptorBuilder() {
        descriptor = new EditCourseDescriptor();
    }

    public EditCourseDescriptorBuilder(EditCourseDescriptor descriptor) {
        this.descriptor = new EditCourseDescriptor(descriptor);
    }

    /**
     * Returns an {@code EditCourseDescriptor} with fields containing {@code module}'s details
     */
    public EditCourseDescriptorBuilder(CourseInfo courseInfo) {
        descriptor = new EditCourseDescriptor();
        descriptor.setName(courseInfo.getName());
        descriptor.setSemesters(courseInfo.getSemesters());
    }

    /**
     * Sets the {@code Name} of the {@code EditCourseDescriptor} that we are building.
     */
    public EditCourseDescriptorBuilder withName(String name) {
        descriptor.setName(Optional.of(new Name(name)));
        return this;
    }

    /**
     * Sets the {@code Semesters} of the {@code EditCourseDescriptor} that we are building.
     */
    public EditCourseDescriptorBuilder withSemesters(String semester) {
        descriptor.setSemesters(Optional.of(new Semesters(semester)));
        return this;
    }

    public EditCourseDescriptor build() {
        return descriptor;
    }
}
