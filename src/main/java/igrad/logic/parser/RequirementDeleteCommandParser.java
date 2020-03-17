package igrad.logic.parser;

import static igrad.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static igrad.logic.commands.RequirementDeleteCommand.MESSAGE_USAGE;

import java.io.IOException;

import igrad.logic.commands.RequirementDeleteCommand;
import igrad.logic.parser.exceptions.ParseException;
import igrad.model.requirement.Title;
import igrad.services.exceptions.ServiceException;

/**
 * Parses requirement input argument and creates a new RequirementAddCommand object.
 */
public class RequirementDeleteCommandParser implements Parser<RequirementDeleteCommand> {

    @Override
    public RequirementDeleteCommand parse(String userInput) throws ParseException, IOException, ServiceException {
        try {
            Specifier specifier = ParserUtil.parseSpecifier(userInput);
            return new RequirementDeleteCommand(new Title(specifier.getValue()));
        } catch (ParseException pe) {
            throw new ParseException(
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE), pe);
        }
    }
}
