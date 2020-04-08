package igrad.logic.parser.module;

import static igrad.logic.parser.CliSyntax.PREFIX_CREDITS;
import static igrad.logic.parser.CliSyntax.PREFIX_GRADE;
import static igrad.logic.parser.CliSyntax.PREFIX_SEMESTER;

import java.util.Optional;

import igrad.logic.commands.module.ModuleFilterCommand;
import igrad.logic.parser.ArgumentMultimap;
import igrad.logic.parser.ArgumentTokenizer;
import igrad.logic.parser.CliSyntax;
import igrad.logic.parser.Parser;
import igrad.logic.parser.exceptions.ParseException;
import igrad.model.module.Credits;
import igrad.model.module.Grade;
import igrad.model.module.Semester;

/**
 * Parses input arguments and creates a new ModuleFilterCommandParser object.
 */
public class ModuleFilterCommandParser extends ModuleCommandParser implements Parser<ModuleFilterCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the ModuleCommandParser
     * and returns an ModuleCommandParser object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public ModuleFilterCommand parse(String argumentsWithFlags) throws ParseException {

        String operator = ArgumentTokenizer.isFlagPresent(argumentsWithFlags, CliSyntax.FLAG_OPERATOR_OR.getFlag())
            ? ModuleFilterCommand.OR
            : ModuleFilterCommand.AND;

        String args = ArgumentTokenizer.removeFlags(argumentsWithFlags);

        ArgumentMultimap argMultimap =
            ArgumentTokenizer.tokenize(args, PREFIX_SEMESTER, PREFIX_CREDITS, PREFIX_GRADE);

        /*
         * If all arguments in the command are empty; i.e, 'module filter', and nothing else (except preambles),
         * refresh the list (show full module list)
         */
        if (argMultimap.isEmpty(false)) {
            return new ModuleFilterCommand();
        }

        Optional<Credits> credits = argMultimap.getValue(PREFIX_CREDITS).isPresent()
            ? Optional.of(parseCredits(argMultimap.getValue(PREFIX_CREDITS).get()))
            : Optional.empty();

        Optional<Grade> grade = argMultimap.getValue(PREFIX_GRADE).isPresent()
            ? parseGrade(argMultimap.getValue(PREFIX_GRADE).get())
            : Optional.empty();

        Optional<Semester> semester = argMultimap.getValue(PREFIX_SEMESTER).isPresent()
            ? parseSemester(argMultimap.getValue(PREFIX_SEMESTER).get())
            : Optional.empty();

        return new ModuleFilterCommand(semester, credits, grade, operator);
    }

}
