package igrad.testutil;

import java.util.HashSet;
import java.util.Set;

import igrad.model.module.*;
import igrad.model.module.Module;
import igrad.model.util.SampleDataUtil;
import igrad.model.tag.Tag;

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
    private Memo memo;
    private Semester semester;
    private Description description;
    private Set<Tag> tags;

    public ModuleBuilder() {
        title = new Title(DEFAULT_TITLE);
        moduleCode = new ModuleCode(DEFAULT_MODULE_CODE);
        credits = new Credits(DEFAULT_CREDITS);
        memo = new Memo(DEFAULT_MEMO);
        semester = new Semester(DEFAULT_SEMESTER);
        description = new Description(DEFAULT_DESCRIPTION);
        tags = new HashSet<>();
    }

    /**
     * Initializes the ModuleBuilder with the data of {@code moduleToCopy}.
     */
    public ModuleBuilder(Module moduleToCopy) {
        title = moduleToCopy.getTitle();
        moduleCode = moduleToCopy.getModuleCode();
        credits = moduleToCopy.getCredits();
        memo = moduleToCopy.getMemo();
        semester = moduleToCopy.getSemester();
        tags = new HashSet<>(moduleToCopy.getTags());
    }

    /**
     * Sets the {@code Title} of the {@code Module} that we are building.
     */
    public ModuleBuilder withTitle(String title) {
        this.title = new Title(title);
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code Module} that we are building.
     */
    public ModuleBuilder withTags(String ... tags) {
        this.tags = SampleDataUtil.getTagSet(tags);
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
     * Sets the {@code Memo} of the {@code Module} that we are building.
     */
    public ModuleBuilder withMemo(String memo) {
        this.memo = new Memo(memo);
        return this;
    }

    /**
     * Sets the {@code Semester} of the {@code Module} that we are building.
     */
    public ModuleBuilder withSemester(String semester) {
        this.semester = new Semester(semester);
        return this;
    }

    /**
     * Sets the {@code Semester} of the {@code Module} that we are building.
     */
    public ModuleBuilder withDescription(String description) {
        this.description = new Description(description);
        return this;
    }

    public Module build() {
        return new Module(title, moduleCode, credits, memo, semester, description, tags);
    }

}
