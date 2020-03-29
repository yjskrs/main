package igrad.logic.parser.requirement;

import static igrad.logic.commands.requirement.RequirementAddCommand.MESSAGE_HELP;
import static igrad.logic.commands.requirement.RequirementAddCommand.MESSAGE_NOT_ADDED;
import static igrad.logic.parser.CliSyntax.PREFIX_CREDITS;
import static igrad.logic.parser.CliSyntax.PREFIX_TITLE;
import static java.util.Objects.requireNonNull;

import java.util.ArrayList;

import igrad.commons.core.Messages;
import igrad.logic.commands.requirement.RequirementAddCommand;
import igrad.logic.parser.ArgumentMultimap;
import igrad.logic.parser.ArgumentTokenizer;
import igrad.logic.parser.ParserUtil;
import igrad.logic.parser.exceptions.ParseException;
import igrad.model.requirement.Credits;
import igrad.model.requirement.Requirement;
import igrad.model.requirement.Title;

/**
 * Parses requirement input arguments and returns a new RequirementAddCommand object.
 */
public class RequirementAddCommandParser extends RequirementCommandParser {

    /**
     * Parses the given string of arguments {@code args} in the context of the RequirementAddCommand
     * and returns a RequirementAddCommand object for execution.
     *
     * @throws ParseException If the user input does not conform the expected format.
     */
    @Override
    public RequirementAddCommand parse(String args) throws ParseException {
        requireNonNull(args);

        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_TITLE, PREFIX_CREDITS);

        /*
         * If all arguments in the command are empty; i.e, 'requirement add', and nothing else (except preambles), show
         * the help message for this command
         */
        if (argMultimap.isEmpty(false)) {
            throw new ParseException(String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT,
                MESSAGE_HELP));
        }

        /*
         * requirement add t/TITLE u/MCs
         *
         * We have that; TITLE, and MCs, are the compulsory fields, so we're just validating for its
         * presence in the below.
         */
        if (!ParserUtil.arePrefixesPresent(argMultimap, PREFIX_TITLE, PREFIX_CREDITS)) {
            throw new ParseException(String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT,
                MESSAGE_NOT_ADDED));
        }

        Title title = parseTitle(argMultimap.getValue(PREFIX_TITLE).get());
        Credits credits = parseCredits(argMultimap.getValue(PREFIX_CREDITS).get());

        // Intialise a new requirement with an empty module list.
        Requirement requirement = new Requirement(title, credits, new ArrayList<>());

        return new RequirementAddCommand(requirement);
    }
}
