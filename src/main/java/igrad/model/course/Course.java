package igrad.model.course;

import static igrad.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;

/**
 * Represents a Course in the course book (There is only one such course).
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Course {

    // Identity fields
    private final Name name;

    // Data fields

    /**
     * Every field must be present and not null.
     */
    public Course(Name name) {
        requireAllNonNull(name);
        this.name = name;
    }

    public Name getName() {
        return name;
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName());
        return builder.toString();
    }

}
