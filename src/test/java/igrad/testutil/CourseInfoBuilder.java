package igrad.testutil;

import java.util.Optional;

import igrad.model.course.Cap;
import igrad.model.course.CourseInfo;
import igrad.model.course.Name;

/**
 * A utility class to help with building {@code CourseInfo} objects.
 */
public class CourseInfoBuilder {

    public static final String DEFAULT_NAME = "Bachelor of Computing in Computer Science";
    public static final String DEFAULT_CAP = "5";

    private Optional<Name> name;
    private Optional<Cap> cap;

    public CourseInfoBuilder() {
        name = Optional.of(new Name(DEFAULT_NAME));
        cap = Optional.of(new Cap(DEFAULT_CAP));
    }

    /**
     * Initializes the CourseInfoBuilder with the data of {@code courseInfoToCopy}.
     */
    public CourseInfoBuilder(CourseInfo courseInfoToCopy) {
        name = courseInfoToCopy.getName();
        cap = courseInfoToCopy.getCap();
    }

    /**
     * Sets the {@code Name} of the {@code CourseInfo} that we are building.
     */
    public CourseInfoBuilder withName(String name) {
        this.name = Optional.of(new Name(name));
        return this;
    }

    /**
     * Sets the {@code Cap} of the {@CourseInfo} that we are building.
     */
    public CourseInfoBuilder withCap(String cap) {
        this.cap = Optional.of(new Cap(cap));
        return this;
    }

    /**
     * Constructs a {@code CourseInfo} object based on the details supplied (if any)
     */
    public CourseInfo build() {
        // TODO: just put optional for now, construct test cases involving cap as well
        return new CourseInfo(name, cap, Optional.empty());
    }
}
