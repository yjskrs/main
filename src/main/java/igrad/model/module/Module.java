package igrad.model.module;

//@@author waynewee

import static igrad.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;
import java.util.Optional;
/**
 * Represents a Module in the course book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Module {

    // Identity fields
    private final Title title;
    private final ModuleCode moduleCode;
    private final Credits credits;

    private final ModulePrerequisites prequisites;
    private final ModulePreclusions preclusions;

    // A module object can be created without all these fields (which are optional)
    private final Optional<Semester> semester;
    private final Optional<Grade> grade;

    /**
     * Every field must be present and not null.
     */
    public Module(
        Title title,
        ModuleCode moduleCode,
        Credits credits,
        Optional<Semester> semester,
        Optional<Grade> grade
    ) {
        requireAllNonNull(title, moduleCode, credits);
        this.title = title;
        this.moduleCode = moduleCode;
        this.credits = credits;
        this.semester = semester;
        this.grade = grade;
        this.preclusions = new ModulePreclusions();
        this.prequisites = new ModulePrerequisites();
    }

    public Module(
        Title title,
        ModuleCode moduleCode,
        Credits credits,
        ModulePreclusions preclusions,
        ModulePrerequisites prequisites
    ) {
        requireAllNonNull(title, moduleCode, credits);
        this.title = title;
        this.moduleCode = moduleCode;
        this.credits = credits;
        this.semester = Optional.empty();
        this.grade = Optional.empty();
        this.preclusions = preclusions;
        this.prequisites = prequisites;
    }

    public Title getTitle() {
        return title;
    }

    public ModuleCode getModuleCode() {
        return moduleCode;
    }

    public Credits getCredits() {
        return credits;
    }

    public Optional<Semester> getSemester() {
        return semester;
    }

    public Optional<Grade> getGrade() {
        return grade;
    }

    public ModulePrerequisites getPrequisites() {
        return prequisites;
    }

    public ModulePreclusions getPreclusions() {
        return preclusions;
    }

    /**
     * Returns true if module is done, else false.
     */
    public boolean isDone() {
        return grade.isPresent();
    }

    /**
     * Returns true if both modules have the same module code.
     * This defines a weaker notion of equality between two modules than Module#isSameModule.
     */
    public boolean hasModuleCodeOf(ModuleCode moduleCode) {
        if (moduleCode == this.moduleCode) {
            return true;
        }

        return getModuleCode().equals(moduleCode);
    }

    /**
     * Returns true if both modules of the same name have at least one other identity field that is the same.
     * This defines a weaker notion of equality between two modules.
     */
    public boolean isSameModule(Module otherModule) {
        if (otherModule == this) {
            return true;
        }

        return otherModule != null
            && otherModule.getModuleCode().equals(getModuleCode());
    }

    /**
     * Returns true if both modules have the same identity and data fields.
     * This defines a stronger notion of equality between two modules.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Module)) {
            return false;
        }

        Module otherModule = (Module) other;

        return otherModule.getTitle().equals(getTitle())
            && otherModule.getModuleCode().equals(getModuleCode())
            && otherModule.getCredits().equals(getCredits())
            && otherModule.getGrade().equals(getGrade())
            && otherModule.getSemester().equals(getSemester());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(title, moduleCode, credits, semester);
    }

    @Override
    public String toString() {

        Title title = getTitle();
        ModuleCode moduleCode = getModuleCode();
        Credits credits = getCredits();

        final StringBuilder builder = new StringBuilder();

        builder
            .append("Module Code: ")
            .append(moduleCode)
            .append("\nTitle: ")
            .append(title)
            .append("\nCredits: ")
            .append(credits);

        semester.ifPresent(x -> builder.append("\nSemester: ").append(x));
        grade.ifPresent(x -> builder.append("\nGrade: ").append(x));

        return builder.toString();
    }

}
