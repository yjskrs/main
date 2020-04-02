package igrad.logic.parser.module;

import static igrad.logic.commands.module.ModuleDoneCommand.MESSAGE_MODULE_DONE_HELP;
import static igrad.logic.commands.module.ModuleDoneCommand.MESSAGE_MODULE_NOT_EDITED;
import static igrad.logic.parser.CliSyntax.PREFIX_GRADE;
import static java.util.Objects.requireNonNull;

import java.io.IOException;

import igrad.commons.core.Messages;
import igrad.logic.commands.module.ModuleDoneCommand;
import igrad.logic.parser.ArgumentMultimap;
import igrad.logic.parser.ArgumentTokenizer;
import igrad.logic.parser.Parser;
import igrad.logic.parser.ParserUtil;
import igrad.logic.parser.Specifier;
import igrad.logic.parser.exceptions.ParseException;
import igrad.model.module.ModuleCode;
import igrad.services.exceptions.ServiceException;

/**
 * Parses input arguments and creates a new ModuleDoneCommand object.
 */
public class ModuleDoneCommandParser extends ModuleCommandParser implements Parser<ModuleDoneCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the ModuleDoneCommand
     * and returns an ModuleDoneCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    @Override
    public ModuleDoneCommand parse(String args) throws ParseException, IOException, ServiceException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_GRADE);

        /*
         * If all arguments in the command are empty; i.e, 'module done', and nothing else, show
         * the help message for this command
         */
        if (argMultimap.isEmpty(true)) {
            throw new ParseException(String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT,
                MESSAGE_MODULE_DONE_HELP));
        }

        Specifier specifier = ParserUtil.parseSpecifier(argMultimap.getPreamble(),
            ParserUtil.MODULE_MODULE_CODE_SPECIFIER_RULE, ModuleCode.MESSAGE_CONSTRAINTS);

        ModuleDoneCommand.EditModuleGradeDescriptor editModuleGradeDescriptor =
            new ModuleDoneCommand.EditModuleGradeDescriptor();

        ModuleCode moduleCode = new ModuleCode(specifier.getValue());

        if (argMultimap.getValue(PREFIX_GRADE).isEmpty()) {
            throw new ParseException(MESSAGE_MODULE_NOT_EDITED);
        }

        editModuleGradeDescriptor.setGrade(parseGrade(argMultimap.getValue(PREFIX_GRADE).get()));

        return new ModuleDoneCommand(moduleCode, editModuleGradeDescriptor);
    }
}
