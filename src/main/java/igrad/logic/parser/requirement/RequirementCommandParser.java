package igrad.logic.parser.requirement;

import static igrad.model.requirement.RequirementCode.STRIP_DIGITS_REGEX;
import static java.util.Objects.requireNonNull;

import java.util.ArrayList;

import igrad.logic.commands.requirement.RequirementCommand;
import igrad.logic.parser.Parser;
import igrad.logic.parser.exceptions.ParseException;
import igrad.model.requirement.Credits;
import igrad.model.requirement.RequirementCode;
import igrad.model.requirement.Title;

/**
 * Represents a generic requirement command parser.
 */
public abstract class RequirementCommandParser implements Parser<RequirementCommand> {

    //@@author waynewee

    /**
     * Parses a {@code String title} into a {@code RequirementCode} (without the identifying number).
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException If the given {@code title} is invalid.
     */
    public static RequirementCode parseRequirementCode(String title) throws ParseException {
        requireNonNull(title);

        String trimmedTitle = stripDigits(title).trim();
        if (!Title.isValidTitle(trimmedTitle)) {
            throw new ParseException(Title.MESSAGE_CONSTRAINTS);
        }

        // parses the title into a requirement code, but without the identifying number
        final String and = "and";
        final String or = "or";

        ArrayList<String> conjunctives = new ArrayList<>();
        conjunctives.add(and);
        conjunctives.add(or);

        StringBuilder code = new StringBuilder();
        String[] words = trimmedTitle.split(" ");

        for (String word : words) {
            if (!conjunctives.contains(word)) {
                code.append(word.split("")[0].toUpperCase());
            }
        }

        return new RequirementCode(code.toString());
    }

    //@@author yjskrs

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

    /**
     * Removes all digits from the string {@code str}.
     */
    private static String stripDigits(String str) {
        return str.replaceAll(STRIP_DIGITS_REGEX, "");
    }
}
