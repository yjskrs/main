package igrad.logic.parser.requirement;

import static java.util.Objects.requireNonNull;

import igrad.logic.commands.requirement.RequirementCommand;
import igrad.logic.parser.Parser;
import igrad.logic.parser.exceptions.ParseException;
import igrad.model.requirement.Credits;
import igrad.model.requirement.Title;

/**
 * Represents a generic requirement command parser.
 */
public abstract class RequirementCommandParser implements Parser<RequirementCommand> {

    /**
     * Parses a {@code String title} into a {@code Title}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException If the given {@code title} is invalid.
     */
    public static Title parseTitle(String title) throws ParseException {
        requireNonNull(title);

        String trimmedTitle = title.trim();
        if (!Title.isValidTitle(trimmedTitle)) {
            throw new ParseException(Title.MESSAGE_CONSTRAINTS);
        }

        return new Title(trimmedTitle);
    }

    /**
     * Parses a {@code String title} into a {@code Title}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException If the given {@code title} is invalid.
     */
    public static Title parseTitle(String title) throws ParseException {
        requireNonNull(title);

        String trimmedTitle = title.trim();
        if (!Title.isValidTitle(trimmedTitle)) {
            throw new ParseException(Title.MESSAGE_CONSTRAINTS);
        }

        return new Title(trimmedTitle);
    }

    /**
     * Parses a {@code String credits} into a {@code Credits}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code credits} is invalid.
     */
    public static Credits parseCredits(String credits) throws ParseException {
        requireNonNull(credits);

        String trimmedCredits = credits.trim();
        if (!Credits.isValidCredits(trimmedCredits)) {
            throw new ParseException(Credits.MESSAGE_CONSTRAINTS);
        }

        return new Credits(trimmedCredits);
    }
}
