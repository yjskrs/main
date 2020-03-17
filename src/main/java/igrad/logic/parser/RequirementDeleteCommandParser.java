package igrad.logic.parser;

import java.io.IOException;

import igrad.logic.commands.RequirementDeleteCommand;
import igrad.logic.parser.exceptions.ParseException;
import igrad.services.exceptions.ServiceException;

/**
 * Parses requirement input argument and creates a new RequirementAddCommand object.
 */
public class RequirementDeleteCommandParser implements Parser<RequirementDeleteCommand> {

    @Override
    public RequirementDeleteCommand parse(String userInput) throws ParseException, IOException, ServiceException {
        return null;
    }
}
