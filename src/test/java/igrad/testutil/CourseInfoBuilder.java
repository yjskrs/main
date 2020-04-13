package igrad.testutil;

import java.util.Optional;

import igrad.model.course.Cap;
import igrad.model.course.CourseInfo;
import igrad.model.course.Credits;
import igrad.model.course.Name;
import igrad.model.course.Semesters;

//@@author nathanaelseen

/**
 * A utility class to help with building {@code CourseInfo} objects.
 */
public class CourseInfoBuilder {

    public static final String DEFAULT_NAME = "Bachelor of Computing in Computer Science";
    public static final String DEFAULT_CAP = "4.5";
    public static final int DEFAULT_CREDITS_REQUIRED = 108;
    public static final int DEFAULT_CREDITS_FULFILLED = 45;
    public static final String DEFAULT_SEMESTERS = "3";

    private Optional<Name> name;
    private Optional<Cap> cap;
    private Optional<Credits> credits;
    private Optional<Semesters> semesters;

    public CourseInfoBuilder() {
        name = Optional.of(new Name(DEFAULT_NAME));
        cap = Optional.of(new Cap(DEFAULT_CAP));
        credits = Optional.of(new Credits(DEFAULT_CREDITS_REQUIRED, DEFAULT_CREDITS_FULFILLED));
        semesters = Optional.of(new Semesters(DEFAULT_SEMESTERS));
    }

    /**
     * Initializes the CourseInfoBuilder with the data of {@code courseInfoToCopy}.
     */
    public CourseInfoBuilder(CourseInfo courseInfoToCopy) {
        name = courseInfoToCopy.getName();
        cap = courseInfoToCopy.getCap();
        credits = courseInfoToCopy.getCredits();
        semesters = courseInfoToCopy.getSemesters();
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
     * Sets the {@code Cap} of the {@code CourseInfo} that we are building.
     */
    public CourseInfoBuilder withCap(double cap) {
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
     * Sets the {@code Semesters} of the {@code CourseInfo} that we are building.
     */
    public CourseInfoBuilder withSemesters(String totalSemesters) {
        this.semesters = Optional.of(new Semesters(totalSemesters));
        return this;
    }

    /**
     * Sets the {@code Semesters} of the {@code CourseInfo} that we are building.
     */
    public CourseInfoBuilder withSemestersTwoParameters(int totalSemesters, int remainingSemesters) {
        this.semesters = Optional.of(new Semesters(totalSemesters, remainingSemesters));
        return this;
    }

    /**
     * Constructs a {@code CourseInfo} object based on the details supplied (if any)
     */
    public CourseInfo build() {
        return new CourseInfo(name, cap, credits, semesters);
    }

    /**
     * Set the Credits field to {@code Optional.empty}
     */
    public CourseInfoBuilder withCreditsOptional() {
        this.credits = Optional.empty();

        return this;
    }

    /**
     * Set the Cap field to {@code Optional.empty}
     */
    public CourseInfoBuilder withCapOptional() {
        this.cap = Optional.empty();

        return this;
    }

    /**
     * Set the Semesters field to {@code Optional.empty}
     */
    public CourseInfoBuilder withSemestersOptional() {
        this.semesters = Optional.empty();

        return this;
    }

    /**
     * Constructs a {@code CourseInfo} object with all fields Optional.empty()
     */
    public CourseInfo buildEmptyCourseInfo() {
        name = Optional.empty();
        cap = Optional.empty();
        credits = Optional.empty();
        semesters = Optional.empty();

        return new CourseInfo(name, cap, credits, semesters);
    }

    /**
     * Constructs a {@code CourseInfo} object with all fields null
     */
    public CourseInfo buildNullCourseInfo() {
        name = null;
        cap = null;
        credits = null;
        semesters = null;

        return new CourseInfo(name, cap, credits, semesters);
    }
}
