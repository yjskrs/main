package igrad.logic.parser.requirement;

import static java.util.Objects.requireNonNull;

import igrad.logic.parser.exceptions.ParseException;
import igrad.model.requirement.Credits;
import igrad.model.requirement.Name;

/**
 * Represents a generic requirement command parser
 */
public abstract class RequirementCommandParser {
    /**
     * Parses a {@code String name} into a {@code Name}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code title} is invalid.
     */
    public static Name parseName(String name) throws ParseException {
        requireNonNull(name);
        String trimmedName = name.trim();
        if (!Name.isValidTitle(trimmedName)) {
            throw new ParseException(igrad.model.module.Title.MESSAGE_CONSTRAINTS);
        }
        return new Name(trimmedName);
    }

    /**
     * Parses a {@code String credits} into an {@code Credits}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code credits} is invalid.
     */
    public static Credits parseCredits(String credits) throws ParseException {
        requireNonNull(credits);
        String trimmedCredits = credits.trim();
        if (!igrad.model.module.Credits.isValidCredits(trimmedCredits)) {
            throw new ParseException(igrad.model.module.Credits.MESSAGE_CONSTRAINTS);
        }
        return new Credits(trimmedCredits);
    }
}
