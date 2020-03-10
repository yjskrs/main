package seedu.address.model.module;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import seedu.address.model.tags.Tags;

/**
 * Represents a Person in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Module {

    // Identity fields
    private final Title title;
    private final ModuleCode moduleCode;
    private final Credits credits;

    // Data fields
    private final Memo memo;
    private final Description description;
    private final Semester semester;
    private final Set<Tags> tags = new HashSet<>();

    /**
     * Every field must be present and not null.
     */
    public Module( Title title, ModuleCode moduleCode, Credits credits, Memo memo, Description description, Semester semester, Set<Tags> tags ) {
        requireAllNonNull( title, moduleCode, credits, tags );
        this.title = title;
        this.moduleCode = moduleCode;
        this.credits = credits;
        this.memo = memo;
        this.description = description;
        this.semester = semester;
        this.tags.addAll( tags );
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

    public Memo getMemo() {
        return memo;
    }

    public Description getDescription(){
        return description;
    }

    public Semester getSemester(){ return semester; }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tags> getTags() {
        return Collections.unmodifiableSet( tags );
    }

    /**
     * Returns true if both persons of the same name have at least one other identity field that is the same.
     * This defines a weaker notion of equality between two persons.
     */
    public boolean isSamePerson( Module otherModule ) {
        if (otherModule == this) {
            return true;
        }

        return otherModule != null
                && otherModule.getTitle().equals( getTitle())
                && ( otherModule.getModuleCode().equals( getModuleCode()) || otherModule.getCredits().equals( getCredits()));
    }

    /**
     * Returns true if both persons have the same identity and data fields.
     * This defines a stronger notion of equality between two persons.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Module )) {
            return false;
        }

        Module otherModule = (Module) other;
        return otherModule.getTitle().equals( getTitle())
                && otherModule.getModuleCode().equals( getModuleCode())
                && otherModule.getCredits().equals( getCredits())
                && otherModule.getMemo().equals( getMemo())
                && otherModule.getDescription().equals( getDescription())
                && otherModule.getTags().equals( getTags())
                && otherModule.getSemester().equals( getSemester());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash( title, moduleCode, credits, memo, description, tags, semester );
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append( getTitle())
                .append(" Code: ")
                .append( getModuleCode())
                .append(" Email: ")
                .append( getCredits())
                .append(" Memo: ")
                .append( getMemo())
                .append(" Description: ")
                .append( getDescription())
                .append(" Semester: ")
                .append( getSemester() )
                .append("Tags: ");
        getTags().forEach(builder::append);
        return builder.toString();
    }

}
