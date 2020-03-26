package igrad.logic.parser.module;

import static igrad.logic.parser.CliSyntax.PREFIX_MODULE_CODE;

import igrad.commons.core.Messages;
import igrad.logic.commands.module.ModuleDeleteCommand;
import igrad.logic.parser.ArgumentMultimap;
import igrad.logic.parser.ArgumentTokenizer;
import igrad.logic.parser.Parser;
import igrad.logic.parser.ParserUtil;
import igrad.logic.parser.exceptions.ParseException;
import igrad.model.module.ModuleCode;

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
        ArgumentMultimap argMultimap =
            ArgumentTokenizer.tokenize(args, PREFIX_MODULE_CODE);

        if (!ParserUtil.arePrefixesPresent(argMultimap, PREFIX_MODULE_CODE)
            || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT,
                ModuleDeleteCommand.MESSAGE_USAGE));
        }
        ModuleCode moduleCode = ModuleCommandParser.parseModuleCode(argMultimap.getValue(PREFIX_MODULE_CODE).get());

        return new ModuleDeleteCommand(moduleCode);
    }

}
