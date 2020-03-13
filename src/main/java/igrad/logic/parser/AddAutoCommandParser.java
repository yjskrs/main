package igrad.logic.parser;

import static igrad.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static igrad.logic.parser.CliSyntax.PREFIX_CREDITS;
import static igrad.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static igrad.logic.parser.CliSyntax.PREFIX_MEMO;
import static igrad.logic.parser.CliSyntax.PREFIX_MODULE_CODE;
import static igrad.logic.parser.CliSyntax.PREFIX_SEMESTER;
import static igrad.logic.parser.CliSyntax.PREFIX_TAG;
import static igrad.logic.parser.CliSyntax.PREFIX_TITLE;
import java.io.IOException;
import java.util.Set;
import java.util.stream.Stream;
import igrad.logic.commands.ModuleAddAutoCommand;
import igrad.logic.parser.exceptions.ParseException;
import igrad.model.module.Credits;
import igrad.model.module.Description;
import igrad.model.module.Memo;
import igrad.model.module.Module;
import igrad.model.module.ModuleCode;
import igrad.model.module.Semester;
import igrad.model.module.Title;
import igrad.model.tag.Tag;
import igrad.services.JsonParsedModule;
import igrad.services.NusModsRequester;
import igrad.services.exceptions.ServiceException;

/**
 * Parses input arguments and creates a new ModuleAddCommand object
 */
public class AddAutoCommandParser implements Parser<ModuleAddAutoCommand> {

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
                PREFIX_TITLE,
                PREFIX_MODULE_CODE,
                PREFIX_CREDITS,
                PREFIX_TAG,
                PREFIX_MEMO,
                PREFIX_DESCRIPTION,
                PREFIX_SEMESTER
            );

        if (!arePrefixesPresent(argMultimap, PREFIX_MODULE_CODE)
            || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ModuleAddAutoCommand.MESSAGE_USAGE));
        }

        String moduleCodeStr = argMultimap.getValue((PREFIX_MODULE_CODE)).get();
        Set<Tag> tagsList = ParserUtil.parseTag(argMultimap.getAllValues(PREFIX_TAG));
        JsonParsedModule jsonParsedModule = NusModsRequester.getModule(moduleCodeStr);

        Title title = ParserUtil.parseTitle(jsonParsedModule.getTitle());
        Credits credits = ParserUtil.parseCredits(jsonParsedModule.getCredits());
        Description description = ParserUtil.parseDescription(jsonParsedModule.getDescription());
        ModuleCode moduleCode = ParserUtil.parseModuleCode(jsonParsedModule.getModuleCode());
        Memo memo = argMultimap.getValue(PREFIX_MEMO).isPresent()
            ? ParserUtil.parseMemo(argMultimap.getValue(PREFIX_MEMO).get())
            : null;
        Semester semester = argMultimap.getValue(PREFIX_SEMESTER).isPresent()
            ? ParserUtil.parseSemester(argMultimap.getValue(PREFIX_SEMESTER).get())
            : null;

        Module module = new Module(title, moduleCode, credits, memo, semester, description, tagsList);

        return new ModuleAddAutoCommand(module);
    }

}
