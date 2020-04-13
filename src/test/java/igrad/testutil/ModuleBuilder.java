package igrad.testutil;

import java.util.Optional;

import igrad.model.module.Credits;
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
    public static final String DEFAULT_SEMESTER = "Y1S1";
    public static final String DEFAULT_DESCRIPTION = "Good Module";
    public static final String DEFAULT_GRADE = "A+";

    private Title title;
    private ModuleCode moduleCode;
    private Credits credits;
    private Optional<Semester> semester;
    private Optional<Grade> grade;

    public ModuleBuilder() {
        title = new Title(DEFAULT_TITLE);
        moduleCode = new ModuleCode(DEFAULT_MODULE_CODE);
        credits = new Credits(DEFAULT_CREDITS);
        semester = Optional.of(new Semester(DEFAULT_SEMESTER));
        grade = Optional.of(new Grade(DEFAULT_GRADE));
    }

    /**
     * Initializes the ModuleBuilder with the data of {@code moduleToCopy}.
     */
    public ModuleBuilder(Module moduleToCopy) {
        title = moduleToCopy.getTitle();
        moduleCode = moduleToCopy.getModuleCode();
        credits = moduleToCopy.getCredits();
        semester = moduleToCopy.getSemester();
        grade = moduleToCopy.getGrade();
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
     * Sets the {@code Grade} of the {@code Module} that we are building.
     */
    public ModuleBuilder withGrade(String grade) {
        this.grade = Optional.of(new Grade(grade));
        return this;
    }

    /**
     * Sets all the optional fields to empty
     */
    public ModuleBuilder withoutOptionals() {
        this.grade = Optional.empty();
        this.semester = Optional.empty();

        return this;
    }

    /**
     * Builds a {@code Module} for testing
     */
    public Module build() {
        return new Module(title, moduleCode, credits, semester, grade);
    }

}
