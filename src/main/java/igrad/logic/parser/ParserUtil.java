package igrad.logic.parser;

import static igrad.commons.core.Messages.MESSAGE_SPECIFIER_INVALID;
import static igrad.commons.core.Messages.MESSAGE_SPECIFIER_NOT_SPECIFIED;
import static igrad.logic.parser.module.ModuleCommandParser.parseModuleCode;
import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Stream;

import igrad.logic.parser.exceptions.ParseException;
import igrad.model.avatar.Avatar;
import igrad.model.module.ModuleCode;
import igrad.model.requirement.RequirementCode;

/**
 * Contains utility methods used for parsing strings in the various *Parser classes.
 */
public class ParserUtil {
    //@@author nathanaelseen
    public static final Function<String, Boolean> REQUIREMENT_CODE_SPECIFIER_RULE =
        RequirementCode::isValidRequirementCode;

    public static final Function<String, Boolean> MODULE_MODULE_CODE_SPECIFIER_RULE = ModuleCode::isValidModuleCode;

    /**
     * Parses a generic {@code String specifier} into a {@code Specifier}.
     * The functional inteface {@code rule} should return true if valid and false otherwise.
     * Also, {@code messageError} is the error message to show when a {@code ParserException} is thrown.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code specifier} is invalid.
     */
    public static Specifier parseSpecifier(String specifier, Function<String, Boolean> rule, String messageError)
        throws ParseException {
        requireNonNull(specifier);

        String trimmedSpecifier = specifier.trim();

        // We know that in any case, a specifier can never be empty (empty string "")
        if (trimmedSpecifier.isEmpty()) {
            throw new ParseException(String.format(MESSAGE_SPECIFIER_NOT_SPECIFIED, messageError));
        }

        /*
         * Now apply other specifier specific semantic rule as according to {@code rule} parameter, and see if
         * there is any other violation.
         */
        if (!rule.apply(trimmedSpecifier)) {
            throw new ParseException(String.format(MESSAGE_SPECIFIER_INVALID, messageError));
        }

        return new Specifier(trimmedSpecifier);
    }
    //@@author

    /**
     * Parses a {@code String name} into an {@code Avatar}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code avatarName} is invalid.
     */
    public static Avatar parseAvatarName(String name) throws ParseException {
        requireNonNull(name);
        String trimmedName = name.trim();
        if (!Avatar.isValidName(trimmedName)) {
            throw new ParseException(Avatar.MESSAGE_CONSTRAINTS);
        }
        return new Avatar(trimmedName);
    }

    //@@author nathanaelseen

    /**
     * Parses {@code Collection<String> moduleCodes} into a {@code List<ModuleCode>}.
     */
    public static List<ModuleCode> parseModuleCodes(Collection<String> moduleCodes) throws ParseException {
        requireNonNull(moduleCodes);

        final List<ModuleCode> moduleCodesList = new ArrayList<>();

        for (String moduleCode : moduleCodes) {
            moduleCodesList.add(parseModuleCode(moduleCode));
        }

        return moduleCodesList;
    }

    //@@author

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    public static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> {
            Optional<String> value = argumentMultimap.getValue(prefix);
            return value.isPresent() && !value.get().isEmpty();
        });
    }
}
