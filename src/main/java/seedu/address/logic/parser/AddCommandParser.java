package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MEMO;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CREDITS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TITLE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MODULE_CODE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SEMESTER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAGS;

import java.util.Set;
import java.util.stream.Stream;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.module.*;
import seedu.address.model.module.Memo;
import seedu.address.model.module.Module;
import seedu.address.model.tags.Tags;

/**
 * Parses input arguments and creates a new AddCommand object
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
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        }

        Title title = ParserUtil.parseName(argMultimap.getValue(PREFIX_TITLE).get());
        ModuleCode moduleCode = ParserUtil.parsePhone(argMultimap.getValue(PREFIX_MODULE_CODE).get());
        Credits credits = ParserUtil.parseEmail(argMultimap.getValue(PREFIX_CREDITS).get());
        Memo memo = ParserUtil.parseAddress(argMultimap.getValue( PREFIX_MEMO ).get());
        Semester semester = ParserUtil.parseSemester( argMultimap.getValue( PREFIX_SEMESTER ).get() );
        Set<Tags> tagsList = ParserUtil.parseTags(argMultimap.getAllValues(PREFIX_TAGS));

        Module module = new Module( title, moduleCode, credits, memo, semester, tagsList );

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
