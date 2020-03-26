package igrad.logic.parser.requirement;

import static java.util.Objects.requireNonNull;

import java.util.function.Function;

import igrad.logic.commands.requirement.RequirementCommand;
import igrad.logic.parser.Parser;
import igrad.logic.parser.exceptions.ParseException;
import igrad.model.module.ModuleCode;
import igrad.model.module.Title;
import igrad.model.requirement.Credits;
import igrad.model.requirement.Name;

/**
 * Represents a generic requirement command parser.
 */
public abstract class RequirementCommandParser implements Parser<RequirementCommand> {
    public static final Function<String, Boolean> REQUIREMENT_NAME_SPECIFIER_RULE = Title::isValidTitle;
    public static final Function<String, Boolean> MODULE_MODULE_CODE_SPECIFIER_RULE = ModuleCode::isValidModuleCode;

    /**
     * Parses a {@code String name} into a {@code Name}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException If the given {@code name} is invalid.
     */
    public static Name parseName(String name) throws ParseException {
        requireNonNull(name);

        String trimmedName = name.trim();
        if (!Name.isValidName(trimmedName)) {
            throw new ParseException(Name.MESSAGE_CONSTRAINTS);
        }

        return new Name(trimmedName);
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
