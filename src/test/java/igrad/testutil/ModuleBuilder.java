package igrad.testutil;

import java.util.Optional;

import igrad.model.module.Credits;
import igrad.model.module.Description;
import igrad.model.module.Grade;
import igrad.model.module.Module;
import igrad.model.module.ModuleCode;
import igrad.model.module.Semester;
import igrad.model.module.Title;

/**
 * A utility class to help with building Module objects.
 */
public class ModuleBuilder {

    public static final String DEFAULT_TITLE = "Programming Methodology";
    public static final String DEFAULT_MODULE_CODE = "CS1101S";
    public static final String DEFAULT_CREDITS = "4";
    public static final String DEFAULT_MEMO = "this is about recursion";
    public static final String DEFAULT_SEMESTER = "Y1S1";
    public static final String DEFAULT_DESCRIPTION = "blah";

    private Title title;
    private ModuleCode moduleCode;
    private Credits credits;
    private Optional<Semester> semester;
    private Optional<Description> description;

    public ModuleBuilder() {
        title = new Title(DEFAULT_TITLE);
        moduleCode = new ModuleCode(DEFAULT_MODULE_CODE);
        credits = new Credits(DEFAULT_CREDITS);
        semester = Optional.of(new Semester(DEFAULT_SEMESTER));
        description = Optional.of(new Description(DEFAULT_DESCRIPTION));
    }

    /**
     * Initializes the ModuleBuilder with the data of {@code moduleToCopy}.
     */
    public ModuleBuilder(Module moduleToCopy) {
        title = moduleToCopy.getTitle();
        moduleCode = moduleToCopy.getModuleCode();
        credits = moduleToCopy.getCredits();
        semester = moduleToCopy.getSemester();
    }

    /**
     * Sets the {@code Title} of the {@code Module} that we are building.
     */
    public ModuleBuilder withTitle(String title) {
        this.title = new Title(title);
        return this;
    }

    /**
     * Sets the {@code ModuleCode} of the {@code Module} that we are building.
     */
    public ModuleBuilder withModuleCode(String moduleCode) {
        this.moduleCode = new ModuleCode(moduleCode);
        return this;
    }

    /**
     * Sets the {@code Credits} of the {@code Module} that we are building.
     */
    public ModuleBuilder withCredits(String credits) {
        this.credits = new Credits(credits);
        return this;
    }

    /**
     * Sets the {@code Semester} of the {@code Module} that we are building.
     */
    public ModuleBuilder withSemester(String semester) {
        this.semester = Optional.of(new Semester(semester));
        return this;
    }

    /**
     * Sets the {@code Semester} of the {@code Module} that we are building.
     */
    public ModuleBuilder withDescription(String description) {
        this.description = Optional.of(new Description(description));
        return this;
    }

    /**
     * Builds a {@code Module} for testing
     */
    public Module build() {
        // TODO: Add support for grade
        Optional<Grade> grade = Optional.empty();
        return new Module(title, moduleCode, credits, semester, description, grade);
    }

}
