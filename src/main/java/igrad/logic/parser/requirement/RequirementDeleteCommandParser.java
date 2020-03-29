package igrad.logic.parser.requirement;

import static igrad.commons.core.Messages.MESSAGE_SPECIFIER_NOT_SPECIFIED;
import static igrad.logic.commands.requirement.RequirementDeleteCommand.MESSAGE_USAGE;

import igrad.logic.commands.requirement.RequirementDeleteCommand;
import igrad.logic.parser.ParserUtil;
import igrad.logic.parser.Specifier;
import igrad.logic.parser.exceptions.ParseException;
import igrad.model.requirement.RequirementCode;

/**
 * Parses requirement input argument and creates a new RequirementDeleteCommand object.
 */
public class RequirementDeleteCommandParser extends RequirementCommandParser {

    @Override
    public RequirementDeleteCommand parse(String userInput) throws ParseException {
        try {
            Specifier specifier = ParserUtil.parseSpecifier(userInput);
            return new RequirementDeleteCommand(new RequirementCode(specifier.getValue()));
        } catch (ParseException pe) {
            throw new ParseException(
                String.format(MESSAGE_SPECIFIER_NOT_SPECIFIED, MESSAGE_USAGE), pe);
        }
    }
}
