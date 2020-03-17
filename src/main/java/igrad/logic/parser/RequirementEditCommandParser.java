package igrad.logic.parser;

import static igrad.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static igrad.logic.commands.RequirementEditCommand.MESSAGE_REQUIREMENT_NOT_EDITED;
import static igrad.logic.commands.RequirementEditCommand.MESSAGE_USAGE;
import static igrad.logic.parser.CliSyntax.PREFIX_NAME;
import static java.util.Objects.requireNonNull;

import igrad.logic.commands.RequirementEditCommand;
import igrad.logic.parser.exceptions.ParseException;
import igrad.model.requirement.Title;

/**
 * Parses requirement edit command input arguments and creates a new RequirementEditCommand object.
 */
public class RequirementEditCommandParser implements Parser<RequirementEditCommand> {

    /**
     * Parses the given string of arguments {@code args} in the context of the
     * RequirementEditCommand and returns a RequirementEditCommand object
     * for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public RequirementEditCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_NAME);

        Specifier specifier;
        try {
            specifier = ParserUtil.parseSpecifier(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE), pe);
        }

        Title title;
        if (argMultimap.getValue(PREFIX_NAME).isPresent()) {
            title = new Title(argMultimap.getValue(PREFIX_NAME).get());
        } else {
            throw new ParseException(MESSAGE_REQUIREMENT_NOT_EDITED);
        }

        return new RequirementEditCommand(new Title(specifier.getValue()), title);
    }

}
