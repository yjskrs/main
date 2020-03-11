package igrad.testutil;

import igrad.model.course.CourseInfo;
import igrad.model.course.Name;

/**
 * A utility class to help with building CourseInfo objects.
 */
public class CourseInfoBuilder {

    public static final String DEFAULT_NAME = "Bachelor of Computing in Computer Science";

    private Name name;

    public CourseInfoBuilder() {
        name = new Name(DEFAULT_NAME);
    }

    /**
     * Initializes the CourseInfoBuilder with the data of {@code courseInfoToCopy}.
     */
    public CourseInfoBuilder(CourseInfo courseInfoToCopy) {
        name = courseInfoToCopy.getName();
    }

    /**
     * Sets the {@code Name} of the {@code CourseInfo} that we are building.
     */
    public CourseInfoBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    public CourseInfo build() {
        return new CourseInfo(name);
    }
}
