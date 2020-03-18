package igrad.logic.parser.requirement;

import static igrad.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static igrad.logic.commands.requirement.RequirementAddCommand.MESSAGE_USAGE;
import static igrad.logic.parser.CliSyntax.PREFIX_CREDITS;
import static igrad.logic.parser.CliSyntax.PREFIX_NAME;
import static java.util.Objects.requireNonNull;

import java.util.ArrayList;

import igrad.logic.commands.requirement.RequirementAddCommand;
import igrad.logic.parser.ArgumentMultimap;
import igrad.logic.parser.ArgumentTokenizer;
import igrad.logic.parser.Parser;
import igrad.logic.parser.ParserUtil;
import igrad.logic.parser.exceptions.ParseException;
import igrad.model.requirement.Credits;
import igrad.model.requirement.Name;
import igrad.model.requirement.Requirement;

/**
 * Parses requirement input argument and creates a new RequirementAddCommand object.
 */
public class RequirementAddCommandParser extends RequirementCommandParser implements Parser<RequirementAddCommand> {

    /**
     * Parses the given string of arguments {@code args} in the context of the
     * RequirementAddCommand and returns a RequirementAddCommand object
     * for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    @Override
    public RequirementAddCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_CREDITS);

        if (!ParserUtil.arePrefixesPresent(argMultimap, PREFIX_NAME, PREFIX_CREDITS)) {
            throw new ParseException(
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE));
        }

        Name name = parseName(argMultimap.getValue(PREFIX_NAME).get());
        Credits credits = parseCredits(argMultimap.getValue(PREFIX_CREDITS).get());

        Requirement requirement = new Requirement(name, credits, new ArrayList<>());

        return new RequirementAddCommand(requirement);
    }


}
