package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.StringUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.module.*;
import seedu.address.model.tags.Tags;

/**
 * Contains utility methods used for parsing strings in the various *Parser classes.
 */
public class ParserUtil {

    public static final String MESSAGE_INVALID_INDEX = "Index is not a non-zero unsigned integer.";

    /**
     * Parses {@code oneBasedIndex} into an {@code Index} and returns it. Leading and trailing whitespaces will be
     * trimmed.
     * @throws ParseException if the specified index is invalid (not non-zero unsigned integer).
     */
    public static Index parseIndex(String oneBasedIndex) throws ParseException {
        String trimmedIndex = oneBasedIndex.trim();
        if (!StringUtil.isNonZeroUnsignedInteger(trimmedIndex)) {
            throw new ParseException(MESSAGE_INVALID_INDEX);
        }
        return Index.fromOneBased(Integer.parseInt(trimmedIndex));
    }

    /**
     * Parses a {@code String name} into a {@code Name}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code name} is invalid.
     */
    public static Title parseTitle(String name) throws ParseException {
        requireNonNull(name);
        String trimmedName = name.trim();
        if (!Title.isValidName(trimmedName)) {
            throw new ParseException( Title.MESSAGE_CONSTRAINTS);
        }
        return new Title(trimmedName);
    }

    /**
     * Parses a {@code String moduleCode} into a {@code Phone}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code moduleCode} is invalid.
     */
    public static ModuleCode parseModuleCode(String moduleCode) throws ParseException {
        requireNonNull(moduleCode);
        String trimmedPhone = moduleCode.trim();
        if (!ModuleCode.isValidModuleCode(trimmedPhone)) {
            throw new ParseException( ModuleCode.MESSAGE_CONSTRAINTS);
        }
        return new ModuleCode(trimmedPhone);
    }

    /**
     * Parses a {@code String address} into an {@code Address}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code address} is invalid.
     */
    public static Memo parseMemo(String memo) throws ParseException {
        requireNonNull(memo);
        String trimmedMemo = memo.trim();
//        if (!Address.isValidAddress(trimmedMemo)) {
//            throw new ParseException(Address.MESSAGE_CONSTRAINTS);
//        }
        return new Memo(trimmedMemo);
    }

    /**
     * Parses a {@code String address} into an {@code Address}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code address} is invalid.
     */
    public static Description parseDescription(String description) throws ParseException {
        requireNonNull(description);
        String trimmedDescription = description.trim();
//        if (!Address.isValidAddress(trimmedDescription)) {
//            throw new ParseException(Address.MESSAGE_CONSTRAINTS);
//        }
        return new Description(trimmedDescription);
    }

    /**
     * Parses a {@code String email} into an {@code Email}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code email} is invalid.
     */
    public static Credits parseCredits(String credits) throws ParseException {
        requireNonNull(credits);
        String trimmedCredits = credits.trim();
        if (!Credits.isValidCredits(trimmedCredits)) {
            throw new ParseException( Credits.MESSAGE_CONSTRAINTS);
        }
        return new Credits(trimmedCredits);
    }

    /**
     * Parses a {@code String semester} into an {@code Semester}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code semester} is invalid.
     */
    public static Semester parseSemester( String semester) throws ParseException {
        requireNonNull(semester);
        String trimmedSemester = semester.trim();
        if (!Semester.isValidSemester(trimmedSemester)) {
            throw new ParseException( Semester.MESSAGE_CONSTRAINTS);
        }
        return new Semester(trimmedSemester);
    }

    /**
     * Parses a {@code String tag} into a {@code Tag}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code tag} is invalid.
     */
    public static Tags parseTag( String tag) throws ParseException {
        requireNonNull(tag);
        String trimmedTag = tag.trim();
        if (!Tags.isValidSemester(trimmedTag)) {
            throw new ParseException( Tags.MESSAGE_CONSTRAINTS);
        }
        return new Tags(trimmedTag);
    }

    /**
     * Parses {@code Collection<String> tags} into a {@code Set<Tag>}.
     */
    public static Set<Tags> parseTags( Collection<String> tags) throws ParseException {
        requireNonNull(tags);
        final Set<Tags> tagsSet = new HashSet<>();
        for (String tagName : tags) {
            tagsSet.add(parseTag(tagName));
        }
        return tagsSet;
    }
}
