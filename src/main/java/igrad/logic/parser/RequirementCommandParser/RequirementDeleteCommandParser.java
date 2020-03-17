package igrad.logic.parser.RequirementCommandParser;

import static igrad.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static igrad.logic.commands.RequirementCommand.RequirementDeleteCommand.MESSAGE_USAGE;

import igrad.logic.commands.RequirementCommand.RequirementDeleteCommand;
import igrad.logic.parser.Parser;
import igrad.logic.parser.ParserUtil;
import igrad.logic.parser.Specifier;
import igrad.logic.parser.exceptions.ParseException;
import igrad.model.requirement.Title;

/**
 * Parses requirement input argument and creates a new RequirementAddCommand object.
 */
public class RequirementDeleteCommandParser implements Parser<RequirementDeleteCommand> {

    @Override
    public RequirementDeleteCommand parse(String userInput) throws ParseException {
        try {
            Specifier specifier = ParserUtil.parseSpecifier(userInput);
            return new RequirementDeleteCommand(new Title(specifier.getValue()));
        } catch (ParseException pe) {
            throw new ParseException(
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE), pe);
        }
    }
}
