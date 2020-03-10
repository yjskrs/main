package seedu.address.logic.parser;

import seedu.address.logic.commands.AddAutoCommand;
import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.module.Module;
import seedu.address.model.module.*;
import seedu.address.model.tags.Tags;
import seedu.address.services.JsonParsedModule;
import seedu.address.services.NusModsRequester;
import seedu.address.services.exceptions.ServiceException;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Set;
import java.util.stream.Stream;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.*;

/**
 * Parses input arguments and creates a new AddCommand object
 */
public class AddAutoCommandParser implements Parser<AddAutoCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns an AddCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddAutoCommand parse(String args) throws ParseException, IOException, ServiceException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_TITLE, PREFIX_MODULE_CODE, PREFIX_CREDITS, PREFIX_TAGS, PREFIX_MEMO, PREFIX_DESCRIPTION, PREFIX_SEMESTER);

        if (!arePrefixesPresent(argMultimap, PREFIX_MODULE_CODE)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddAutoCommand.MESSAGE_USAGE));
        }

        String moduleCodeStr = argMultimap.getValue((PREFIX_MODULE_CODE)).get();
        Set<Tags> tagsList = ParserUtil.parseTags(argMultimap.getAllValues(PREFIX_TAGS));
        JsonParsedModule jsonParsedModule = NusModsRequester.getModule(moduleCodeStr);

        Title title = ParserUtil.parseTitle(jsonParsedModule.getTitle());
        Credits credits = ParserUtil.parseCredits(jsonParsedModule.getCredits());
        Description description = ParserUtil.parseDescription(jsonParsedModule.getDescription());
        ModuleCode moduleCode = ParserUtil.parseModuleCode(jsonParsedModule.getModuleCode());
        Memo memo = argMultimap.getValue(PREFIX_MEMO).isPresent()? ParserUtil.parseMemo(argMultimap.getValue( PREFIX_MEMO ).get()): null;
        Semester semester = argMultimap.getValue(PREFIX_SEMESTER).isPresent()?ParserUtil.parseSemester( argMultimap.getValue( PREFIX_SEMESTER ).get()): null;

        Module module = new Module( title, moduleCode, credits, memo, description, semester, tagsList );

        return new AddAutoCommand( module );
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
