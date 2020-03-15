package igrad.logic.parser;

import static igrad.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import igrad.commons.core.index.Index;
import igrad.logic.commands.ModuleDeleteCommand;
import igrad.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new ModuleDeleteCommand object.
 */
public class ModuleDeleteCommandParser implements Parser<ModuleDeleteCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the ModuleDeleteCommand
     * and returns a ModuleDeleteCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public ModuleDeleteCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new ModuleDeleteCommand(index);
        } catch (ParseException pe) {
            throw new ParseException(
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ModuleDeleteCommand.MESSAGE_USAGE), pe);
        }
    }

}
