package igrad.logic.parser.module;

import static igrad.logic.parser.CliSyntax.PREFIX_CREDITS;
import static igrad.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static igrad.logic.parser.CliSyntax.PREFIX_MEMO;
import static igrad.logic.parser.CliSyntax.PREFIX_MODULE_CODE;
import static igrad.logic.parser.CliSyntax.PREFIX_SEMESTER;
import static igrad.logic.parser.CliSyntax.PREFIX_TAG;
import static igrad.logic.parser.CliSyntax.PREFIX_TITLE;

import java.util.Optional;
import java.util.Set;

import igrad.commons.core.Messages;
import igrad.logic.commands.module.ModuleAddCommand;
import igrad.logic.parser.ArgumentMultimap;
import igrad.logic.parser.ArgumentTokenizer;
import igrad.logic.parser.Parser;
import igrad.logic.parser.ParserUtil;
import igrad.logic.parser.exceptions.ParseException;
import igrad.model.module.Credits;
import igrad.model.module.Description;
import igrad.model.module.Grade;
import igrad.model.module.Memo;
import igrad.model.module.Module;
import igrad.model.module.ModuleCode;
import igrad.model.module.Semester;
import igrad.model.module.Title;
import igrad.model.tag.Tag;

/**
 * Parses input arguments and creates a new ModuleAddCommand object.
 */
public class ModuleAddCommandParser extends ModuleCommandParser implements Parser<ModuleAddCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the ModuleAddCommand
     * and returns an ModuleAddCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public ModuleAddCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
            ArgumentTokenizer.tokenize(args, PREFIX_TITLE, PREFIX_MODULE_CODE, PREFIX_CREDITS,
                PREFIX_MEMO, PREFIX_SEMESTER);

        /*
         * module add n/MODULE_CODE [n/MODULE_TITLE] [u/MCs] [s/SEMESTER] [g/GRADE] [m/MEMO_NOTES]
         *
         * As can be seen, MODULE_CODE is the only compulsory field, so we're just validating for its
         * presence in the below.
         */
        if (!ParserUtil.arePrefixesPresent(argMultimap, PREFIX_MODULE_CODE)
            || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT,
                ModuleAddCommand.MESSAGE_USAGE));
        }

        Title title = parseTitle(argMultimap.getValue(PREFIX_TITLE).get());
        ModuleCode moduleCode = parseModuleCode(argMultimap.getValue(PREFIX_MODULE_CODE).get());
        Credits credits = parseCredits(argMultimap.getValue(PREFIX_CREDITS).get());

        Optional<Memo> memo = argMultimap.getValue(PREFIX_MEMO).isPresent()
            ? parseMemo(argMultimap.getValue(PREFIX_MEMO).get())
            : Optional.empty();
        Optional<Description> description = argMultimap.getValue(PREFIX_DESCRIPTION).isPresent()
            ? parseDescription(argMultimap.getValue(PREFIX_DESCRIPTION).get())
            : Optional.empty();
        Optional<Semester> semester = argMultimap.getValue(PREFIX_SEMESTER).isPresent()
            ? parseSemester(argMultimap.getValue(PREFIX_SEMESTER).get())
            : Optional.empty();

        // TODO: support grade parsing too! i'll just leave it like that for now
        Optional<Grade> grade = Optional.empty();

        Set<Tag> tagList = ParserUtil.parseTag(argMultimap.getAllValues(PREFIX_TAG));

        Module module = new Module(title, moduleCode, credits, memo, semester, description, grade, tagList);

        return new ModuleAddCommand(module);
    }

}
