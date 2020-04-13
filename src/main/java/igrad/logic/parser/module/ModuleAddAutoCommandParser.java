package igrad.logic.parser.module;

import static igrad.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static igrad.logic.parser.CliSyntax.PREFIX_MODULE_CODE;

import java.io.IOException;
import java.util.List;
import java.util.stream.Stream;

import igrad.logic.commands.module.ModuleAddAutoCommand;
import igrad.logic.parser.ArgumentMultimap;
import igrad.logic.parser.ArgumentTokenizer;
import igrad.logic.parser.Parser;
import igrad.logic.parser.Prefix;
import igrad.logic.parser.exceptions.ParseException;
import igrad.services.exceptions.ServiceException;

/**
 * Parses input arguments and creates a new ModuleAddCommand object
 */
public class ModuleAddAutoCommandParser extends ModuleCommandParser implements Parser<ModuleAddAutoCommand> {

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

    /**
     * Parses the given {@code String} of arguments in the context of the ModuleAddCommand
     * and returns an ModuleAddCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public ModuleAddAutoCommand parse(String args) throws ParseException, IOException, ServiceException {
        ArgumentMultimap argMultimap =
            ArgumentTokenizer.tokenize(
                args,
                PREFIX_MODULE_CODE
            );

        if (!arePrefixesPresent(argMultimap, PREFIX_MODULE_CODE)
            || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ModuleAddAutoCommand.MESSAGE_USAGE));
        }

        List<String> moduleCodes = argMultimap.getAllValues((PREFIX_MODULE_CODE));

        return new ModuleAddAutoCommand(moduleCodes);
    }

}
