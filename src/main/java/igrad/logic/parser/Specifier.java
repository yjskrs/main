package igrad.logic.parser;

//@@author teriaiw

import static java.util.Objects.requireNonNull;

/**
 * Represents a command specifier.
 *
 * <p>
 * A command specifier is the preamble string that occurs before any prefix tokens
 * and occurs after the command word(s).
 * A specifier is only used for {@code edit} commands and {@code delete} commands.
 */
public class Specifier {
    private final String specifier;

    public Specifier(String specifier) {
        requireNonNull(specifier);

        this.specifier = specifier;
    }

    public String getValue() {
        return specifier;
    }

    public String toString() {
        return getValue();
    }

    @Override
    public int hashCode() {
        return specifier.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return obj == this
            || (obj instanceof Specifier
            && specifier.equals(((Specifier) obj).specifier));
    }
}
