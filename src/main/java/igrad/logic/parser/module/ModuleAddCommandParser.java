package igrad.logic.parser.module;

import static igrad.logic.commands.module.ModuleAddCommand.MESSAGE_MODULE_ADD_HELP;
import static igrad.logic.commands.module.ModuleAddCommand.MESSAGE_MODULE_NOT_ADDED;
import static igrad.logic.parser.CliSyntax.PREFIX_CREDITS;
import static igrad.logic.parser.CliSyntax.PREFIX_MODULE_CODE;
import static igrad.logic.parser.CliSyntax.PREFIX_SEMESTER;
import static igrad.logic.parser.CliSyntax.PREFIX_TITLE;

import java.util.Optional;

import igrad.commons.core.Messages;
import igrad.logic.commands.module.ModuleAddCommand;
import igrad.logic.parser.ArgumentMultimap;
import igrad.logic.parser.ArgumentTokenizer;
import igrad.logic.parser.Parser;
import igrad.logic.parser.ParserUtil;
import igrad.logic.parser.exceptions.ParseException;
import igrad.model.module.Credits;
import igrad.model.module.Grade;
import igrad.model.module.Module;
import igrad.model.module.ModuleCode;
import igrad.model.module.Semester;
import igrad.model.module.Title;

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
            ArgumentTokenizer.tokenize(args, PREFIX_TITLE, PREFIX_MODULE_CODE, PREFIX_CREDITS, PREFIX_SEMESTER);

        /*
         * If all arguments in the command are empty; i.e, 'module add', and nothing else (except preambles), show
         * the help message for this command
         */
        if (argMultimap.isEmpty(false)) {
            throw new ParseException(String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT,
                MESSAGE_MODULE_ADD_HELP));
        }

        /*
         * module add n/MODULE_CODE t/TITLE u/MCs [m/MEMO_NOTES] [s/SEMESTER] [x/TAGS]...
         *
         * We have that; MODULE_CODE, TITLE, MCs, are the compulsory fields, so we're just validating for its
         * presence in the below.
         */
        if (!ParserUtil.arePrefixesPresent(argMultimap, PREFIX_MODULE_CODE, PREFIX_TITLE, PREFIX_CREDITS)) {
            throw new ParseException(String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT,
                MESSAGE_MODULE_NOT_ADDED));
        }

        Title title = parseTitle(argMultimap.getValue(PREFIX_TITLE).get());
        ModuleCode moduleCode = parseModuleCode(argMultimap.getValue(PREFIX_MODULE_CODE).get());
        Credits credits = parseCredits(argMultimap.getValue(PREFIX_CREDITS).get());

        Optional<Semester> semester = argMultimap.getValue(PREFIX_SEMESTER).isPresent()
            ? parseSemester(argMultimap.getValue(PREFIX_SEMESTER).get())
            : Optional.empty();

        /*
         * Grade is not allowed to be here, as we have the module done command for that, hence
         * we're initialising it to Optional.empty()
         */
        Optional<Grade> grade = Optional.empty();

        Module module = new Module(title, moduleCode, credits, semester, grade);

        return new ModuleAddCommand(module);
    }

}
