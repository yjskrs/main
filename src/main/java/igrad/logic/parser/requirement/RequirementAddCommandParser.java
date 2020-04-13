package igrad.logic.parser.requirement;

import static igrad.logic.commands.requirement.RequirementAddCommand.MESSAGE_REQUIREMENT_ADD_HELP;
import static igrad.logic.commands.requirement.RequirementAddCommand.MESSAGE_REQUIREMENT_NOT_ADDED;
import static igrad.logic.parser.CliSyntax.PREFIX_CREDITS;
import static igrad.logic.parser.CliSyntax.PREFIX_TITLE;

import igrad.commons.core.Messages;
import igrad.logic.commands.requirement.RequirementAddCommand;
import igrad.logic.parser.ArgumentMultimap;
import igrad.logic.parser.ArgumentTokenizer;
import igrad.logic.parser.ParserUtil;
import igrad.logic.parser.exceptions.ParseException;
import igrad.model.requirement.Credits;
import igrad.model.requirement.Requirement;
import igrad.model.requirement.RequirementCode;
import igrad.model.requirement.Title;

//@@author yjskrs

/**
 * Parses requirement input arguments and returns a new RequirementAddCommand object.
 */
public class RequirementAddCommandParser extends RequirementCommandParser {

    /**
     * Parses the given string of arguments {@code args} in the context of the RequirementAddCommand
     * and returns a RequirementAddCommand object for execution.
     *
     * @throws ParseException If the user input does not conform to the expected format.
     */
    @Override
    public RequirementAddCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_TITLE, PREFIX_CREDITS);

        // Show help message if prefixes are not entered
        if (argMultimap.isEmpty(false)) {
            throw new ParseException(
                String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_REQUIREMENT_ADD_HELP));
        }

        // Check if title and MCs are provided by user
        if (!ParserUtil.arePrefixesPresent(argMultimap, PREFIX_TITLE, PREFIX_CREDITS)) {
            throw new ParseException(
                String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_REQUIREMENT_NOT_ADDED));
        }

        // Use the title to parse into a requirement code
        RequirementCode requirementCode = parseRequirementCode(argMultimap.getValue(PREFIX_TITLE).get());
        Title title = parseTitle(argMultimap.getValue(PREFIX_TITLE).get());
        Credits credits = parseCredits(argMultimap.getValue(PREFIX_CREDITS).get());

        Requirement requirement = new Requirement(requirementCode, title, credits);

        return new RequirementAddCommand(requirement);
    }
}
