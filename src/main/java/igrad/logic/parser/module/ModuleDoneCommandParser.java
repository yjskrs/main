package igrad.logic.parser.module;

import java.io.IOException;

import igrad.logic.commands.module.ModuleDoneCommand;
import igrad.logic.parser.Parser;
import igrad.logic.parser.exceptions.ParseException;
import igrad.services.exceptions.ServiceException;

/**
 * Parses input arguments and creates a new ModuleDoneCommand object.
 */
public class ModuleDoneCommandParser extends ModuleCommandParser implements Parser<ModuleDoneCommand> {
    @Override
    public ModuleDoneCommand parse(String userInput) throws ParseException, IOException, ServiceException {
        return null;
    }
}
