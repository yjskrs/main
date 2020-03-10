package igrad.logic.parser;

import static igrad.logic.parser.CliSyntax.PREFIX_CREDITS;
import static igrad.logic.parser.CliSyntax.PREFIX_MEMO;
import static igrad.logic.parser.CliSyntax.PREFIX_MODULE_CODE;
import static igrad.logic.parser.CliSyntax.PREFIX_TAG;
import static igrad.logic.parser.CliSyntax.PREFIX_TITLE;
import static igrad.logic.parser.CliSyntax.PREFIX_SEMESTER;

import java.util.Set;
import java.util.stream.Stream;

import igrad.commons.core.Messages;
import igrad.logic.commands.AddCommand;
import igrad.logic.parser.exceptions.ParseException;
import igrad.model.module.Credits;
import igrad.model.module.Memo;
import igrad.model.module.Module;
import igrad.model.module.ModuleCode;
import igrad.model.module.Semester;
import igrad.model.module.Title;
import igrad.model.tag.Tag;

/**
 * Parses input arguments and creates a new AddCommand object.
 */
public class AddCommandParser implements Parser<AddCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns an AddCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_TITLE, PREFIX_MODULE_CODE, PREFIX_CREDITS, PREFIX_MEMO, PREFIX_SEMESTER);

        if (!arePrefixesPresent(argMultimap, PREFIX_TITLE, PREFIX_MEMO, PREFIX_MODULE_CODE, PREFIX_CREDITS)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        }

        Title title = ParserUtil.parseTitle(argMultimap.getValue(PREFIX_TITLE).get());
        ModuleCode moduleCode = ParserUtil.parseModuleCode(argMultimap.getValue(PREFIX_MODULE_CODE).get());
        Credits credits = ParserUtil.parseCredits(argMultimap.getValue(PREFIX_CREDITS).get());
        Memo memo = ParserUtil.parseMemo(argMultimap.getValue(PREFIX_MEMO ).get());
        Semester semester = ParserUtil.parseSemester(argMultimap.getValue(PREFIX_SEMESTER ).get() );
        Set<Tag> tagList = ParserUtil.parseTag(argMultimap.getAllValues(PREFIX_TAG));

        Module module = new Module(title, moduleCode, credits, memo, semester, tagList);

        return new AddCommand( module );
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
