package igrad.logic.parser;

import java.io.IOException;

import igrad.logic.commands.RequirementAddCommand;
import igrad.logic.parser.exceptions.ParseException;
import igrad.services.exceptions.ServiceException;

/**
 * Parses requirement input argument and creates a new RequirementAddCommand object.
 */
public class RequirementAddCommandParser implements Parser<RequirementAddCommand> {

    @Override
    public RequirementAddCommand parse(String userInput) throws ParseException, IOException, ServiceException {
        return null;
    }
}
