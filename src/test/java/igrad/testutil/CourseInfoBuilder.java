package igrad.testutil;

import java.util.Optional;

import igrad.model.course.CourseInfo;
import igrad.model.course.Name;

/**
 * A utility class to help with building CourseInfo objects.
 */
public class CourseInfoBuilder {

    public static final String DEFAULT_NAME = "Bachelor of Computing in Computer Science";

    private Optional<Name> name;

    public CourseInfoBuilder() {
        name = Optional.of(new Name(DEFAULT_NAME));
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
        this.name = Optional.of(new Name(name));
        return this;
    }

    /**
     * Constructs a {@code CourseInfo} object based on the details supplied (if any)
     */
    public CourseInfo build() {
        // TODO: just put optional for now, construct test cases involving cap as well
        return new CourseInfo(name, Optional.empty(), Optional.empty());
    }
}
