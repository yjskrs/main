package igrad.logic.parser.RequirementCommandParser;

import static igrad.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static igrad.logic.commands.RequirementCommand.RequirementEditCommand.MESSAGE_REQUIREMENT_EMPTY_PARAMETERS;
import static igrad.logic.commands.RequirementCommand.RequirementEditCommand.MESSAGE_REQUIREMENT_NOT_EDITED;
import static igrad.logic.commands.RequirementCommand.RequirementEditCommand.MESSAGE_USAGE;
import static igrad.logic.parser.CliSyntax.PREFIX_CREDITS;
import static igrad.logic.parser.CliSyntax.PREFIX_NAME;
import static java.util.Objects.requireNonNull;

import java.util.Optional;

import igrad.logic.commands.RequirementCommand.RequirementEditCommand;
import igrad.logic.parser.ArgumentMultimap;
import igrad.logic.parser.ArgumentTokenizer;
import igrad.logic.parser.Parser;
import igrad.logic.parser.ParserUtil;
import igrad.logic.parser.Specifier;
import igrad.logic.parser.exceptions.ParseException;
import igrad.model.requirement.Credits;
import igrad.model.requirement.Title;

/**
 * Parses requirement edit command input arguments and creates a new RequirementEditCommand object.
 */
public class RequirementEditCommandParser implements Parser<RequirementEditCommand> {

    /**
     * Parses the given string of arguments {@code args} in the context of the
     * RequirementEditCommand and returns a RequirementEditCommand object for execution.
     * @throws ParseException If the user input does not conform the expected format.
     */
    @Override
    public RequirementEditCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_CREDITS);

        Specifier specifier;
        try {
            specifier = ParserUtil.parseSpecifier(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE), pe);
        }

        Title title = null;
        Credits credits = null;

        if (!argMultimap.getValue(PREFIX_NAME).isPresent() && !argMultimap.getValue(PREFIX_CREDITS).isPresent()) {
            throw new ParseException(MESSAGE_REQUIREMENT_NOT_EDITED);
        }

        if (argMultimap.getValue(PREFIX_NAME).isPresent()) {
            String titleValue = argMultimap.getValue(PREFIX_NAME).get();
            if (titleValue.isEmpty()) {
                throw new ParseException(MESSAGE_REQUIREMENT_EMPTY_PARAMETERS);
            }
            title = new Title(titleValue);
        }

        if (argMultimap.getValue(PREFIX_CREDITS).isPresent()) {
            String creditsValue = argMultimap.getValue(PREFIX_CREDITS).get();
            if (creditsValue.isEmpty()) {
                throw new ParseException(MESSAGE_REQUIREMENT_EMPTY_PARAMETERS);
            }
            credits = new Credits(creditsValue);
        }

        return new RequirementEditCommand(new Title(specifier.getValue()),
            Optional.ofNullable(title),
            Optional.ofNullable(credits));
    }

}
