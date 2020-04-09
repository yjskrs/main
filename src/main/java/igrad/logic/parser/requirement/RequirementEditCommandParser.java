package igrad.logic.parser.requirement;

import static igrad.logic.commands.requirement.RequirementEditCommand.MESSAGE_REQUIREMENT_EDIT_HELP;
import static igrad.logic.commands.requirement.RequirementEditCommand.MESSAGE_REQUIREMENT_NOT_EDITED;
import static igrad.logic.parser.CliSyntax.PREFIX_CREDITS;
import static igrad.logic.parser.CliSyntax.PREFIX_TITLE;

import igrad.commons.core.Messages;
import igrad.logic.commands.requirement.RequirementEditCommand;
import igrad.logic.commands.requirement.RequirementEditCommand.EditRequirementDescriptor;
import igrad.logic.parser.ArgumentMultimap;
import igrad.logic.parser.ArgumentTokenizer;
import igrad.logic.parser.ParserUtil;
import igrad.logic.parser.Specifier;
import igrad.logic.parser.exceptions.ParseException;
import igrad.model.requirement.Credits;
import igrad.model.requirement.RequirementCode;
import igrad.model.requirement.Title;

//@@author yjskrs

/**
 * Parses requirement edit command input arguments and creates a new RequirementEditCommand object.
 */
public class RequirementEditCommandParser extends RequirementCommandParser {

    /**
     * Parses the given string of arguments {@code args} in the context of the
     * RequirementEditCommand and returns a RequirementEditCommand object for execution.
     *
     * @throws ParseException If the user input does not conform to the expected format.
     */
    @Override
    public RequirementEditCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_TITLE, PREFIX_CREDITS);

        // Show help message for the command if no arguments are provided, i.e. 'requirement edit'
        if (argMultimap.isEmpty(true)) {
            throw new ParseException(String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT,
                MESSAGE_REQUIREMENT_EDIT_HELP));
        }

        Specifier specifier = ParserUtil.parseSpecifier(argMultimap.getPreamble(),
            ParserUtil.REQUIREMENT_CODE_SPECIFIER_RULE, RequirementCode.MESSAGE_CONSTRAINTS);

        // If neither the requirement title nor credits are specified, throw exception
        if (argMultimap.getValue(PREFIX_TITLE).isEmpty() && argMultimap.getValue(PREFIX_CREDITS).isEmpty()) {
            throw new ParseException(MESSAGE_REQUIREMENT_NOT_EDITED);
        }

        RequirementCode requirementCode = new RequirementCode(specifier.getValue());
        EditRequirementDescriptor editRequirementDescriptor = parseRequirementEdited(argMultimap);

        return new RequirementEditCommand(requirementCode, editRequirementDescriptor);
    }

    public EditRequirementDescriptor parseRequirementEdited(ArgumentMultimap argMultimap) throws ParseException {
        EditRequirementDescriptor editRequirementDescriptor = new EditRequirementDescriptor();

        // Check if the title is a valid title, if any
        if (argMultimap.getValue(PREFIX_TITLE).isPresent()) {
            Title title = parseTitle(argMultimap.getValue(PREFIX_TITLE).get());
            editRequirementDescriptor.setTitle(title);
        }

        // Check if the credits is a valid credits, if any
        if (argMultimap.getValue(PREFIX_CREDITS).isPresent()) {
            Credits credits = parseCredits(argMultimap.getValue(PREFIX_CREDITS).get());
            editRequirementDescriptor.setCredits(credits);
        }

        // Check if at least one field has been edited
        if (!editRequirementDescriptor.isAnyFieldEdited()) {
            throw new ParseException(MESSAGE_REQUIREMENT_NOT_EDITED);
        }

        return editRequirementDescriptor;
    }

}
