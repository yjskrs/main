package igrad.testutil;

import java.util.Optional;

import igrad.model.course.Cap;
import igrad.model.course.CourseInfo;
import igrad.model.course.Credits;
import igrad.model.course.Name;

/**
 * A utility class to help with building {@code CourseInfo} objects.
 */
public class CourseInfoBuilder {

    public static final String DEFAULT_NAME = "Bachelor of Computing in Computer Science";
    public static final String DEFAULT_CAP = "4.5";
    public static final int DEFAULT_CREDITS_REQUIRED = 108;
    public static final int DEFAULT_CREDITS_FULFILLED = 45;

    private Optional<Name> name;
    private Optional<Cap> cap;
    private Optional<Credits> credits;
    // TODO: (Teri) add semsLeft

    public CourseInfoBuilder() {
        name = Optional.of(new Name(DEFAULT_NAME));
        cap = Optional.of(new Cap(DEFAULT_CAP));
        credits = Optional.of(new Credits(DEFAULT_CREDITS_REQUIRED, DEFAULT_CREDITS_FULFILLED));

    }

    /**
     * Initializes the CourseInfoBuilder with the data of {@code courseInfoToCopy}.
     */
    public CourseInfoBuilder(CourseInfo courseInfoToCopy) {
        name = courseInfoToCopy.getName();
        cap = courseInfoToCopy.getCap();
        credits = courseInfoToCopy.getCredits();
    }

    /**
     * Sets the {@code Name} of the {@code CourseInfo} that we are building.
     */
    public CourseInfoBuilder withName(String name) {
        this.name = Optional.of(new Name(name));
        return this;
    }

    /**
     * Sets the {@code Cap} of the {@code CourseInfo} that we are building.
     */
    public CourseInfoBuilder withCap(String cap) {
        this.cap = Optional.of(new Cap(cap));
        return this;
    }

    /**
     * Sets the {@code Credits} of the {@code CourseInfo} that we are building.
     */
    public CourseInfoBuilder withCredits(int creditsRequired, int creditsToFulfill) {
        this.credits = Optional.of(new Credits(creditsRequired, creditsToFulfill));
        return this;
    }

    /**
     * Constructs a {@code CourseInfo} object based on the details supplied (if any)
     */
    public CourseInfo build() {
        // TODO: just put optional for now, construct test cases involving cap as well
        return new CourseInfo(name, cap, credits, Optional.empty());
    }
}
