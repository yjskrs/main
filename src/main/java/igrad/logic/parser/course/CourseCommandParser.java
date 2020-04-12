package igrad.logic.parser.course;

import static java.util.Objects.requireNonNull;

import java.util.Optional;

import igrad.logic.parser.exceptions.ParseException;
import igrad.model.course.Cap;
import igrad.model.course.Name;
import igrad.model.course.Semesters;

//@@author nathanaelseen

/**
 * Represents a generic course command parser
 */
public class CourseCommandParser {
    /**
     * Parses a {@code String name} into a {@code Name}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code name} is invalid.
     */
    public static Optional<Name> parseName(String name) throws ParseException {
        requireNonNull(name);
        String trimmedName = name.trim();
        if (!Name.isValidName(trimmedName)) {
            throw new ParseException(Name.MESSAGE_CONSTRAINTS);
        }
        return Optional.of(new Name(trimmedName));
    }

    /**
     * Parses a (@code String semesters} into a (@code Semesters).
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code semesters} is invalid.
     */
    public static Optional<Semesters> parseSemesters(String semesters) throws ParseException {
        requireNonNull(semesters);
        String trimmedSemesters = semesters.trim();
        if (!Semesters.isValidSemesters(trimmedSemesters)) {
            throw new ParseException(Semesters.MESSAGE_CONSTRAINTS);
        }
        return Optional.of(new Semesters(trimmedSemesters));
    }

    /**
     * Parses a (@code String cap} into a (@code Cap).
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code cap} is invalid.
     */
    public static Optional<Cap> parseCap(String cap) throws ParseException {
        requireNonNull(cap);
        String trimmedCap = cap.trim();
        if (!Cap.isValidCap(trimmedCap)) {
            throw new ParseException(Cap.MESSAGE_CONSTRAINTS);
        }
        return Optional.of(new Cap(trimmedCap));
    }
}
