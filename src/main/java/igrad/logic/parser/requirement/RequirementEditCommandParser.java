package igrad.logic.parser.requirement;

import static igrad.commons.core.Messages.MESSAGE_SPECIFIER_NOT_SPECIFIED;
import static igrad.logic.commands.requirement.RequirementEditCommand.MESSAGE_REQUIREMENT_NOT_EDITED;
import static igrad.logic.commands.requirement.RequirementEditCommand.MESSAGE_USAGE;
import static igrad.logic.parser.CliSyntax.PREFIX_CREDITS;
import static igrad.logic.parser.CliSyntax.PREFIX_TITLE;
import static java.util.Objects.requireNonNull;

import java.util.Optional;

import igrad.logic.commands.requirement.RequirementEditCommand;
import igrad.logic.parser.ArgumentMultimap;
import igrad.logic.parser.ArgumentTokenizer;
import igrad.logic.parser.ParserUtil;
import igrad.logic.parser.Specifier;
import igrad.logic.parser.exceptions.ParseException;
import igrad.model.requirement.Credits;
import igrad.model.requirement.RequirementCode;
import igrad.model.requirement.Title;

/**
 * Parses requirement edit command input arguments and creates a new RequirementEditCommand object.
 */
public class RequirementEditCommandParser extends RequirementCommandParser {

    /**
     * Parses the given string of arguments {@code args} in the context of the
     * RequirementEditCommand and returns a RequirementEditCommand object for execution.
     *
     * @throws ParseException If the user input does not conform the expected format.
     */
    @Override
    public RequirementEditCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_TITLE, PREFIX_CREDITS);

        Specifier specifier;
        try {
            specifier = ParserUtil.parseSpecifier(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_SPECIFIER_NOT_SPECIFIED, MESSAGE_USAGE), pe);
        }

        if (!argMultimap.getValue(PREFIX_TITLE).isPresent() && !argMultimap.getValue(PREFIX_CREDITS).isPresent()) {
            throw new ParseException(MESSAGE_REQUIREMENT_NOT_EDITED);
        }

        Title title = null;
        Credits credits = null;

        if (argMultimap.getValue(PREFIX_TITLE).isPresent()) {
            title = parseTitle(argMultimap.getValue(PREFIX_TITLE).get());
        }

        if (argMultimap.getValue(PREFIX_CREDITS).isPresent()) {
            credits = parseCredits(argMultimap.getValue(PREFIX_CREDITS).get());
        }

        return new RequirementEditCommand(new RequirementCode(specifier.getValue()),
            Optional.ofNullable(title),
            Optional.ofNullable(credits));
    }

}
