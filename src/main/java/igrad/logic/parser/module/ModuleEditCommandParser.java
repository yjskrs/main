package igrad.logic.parser.module;

import static igrad.logic.commands.module.ModuleEditCommand.EditModuleDescriptor;
import static igrad.logic.commands.module.ModuleEditCommand.MESSAGE_MODULE_EDIT_HELP;
import static igrad.logic.commands.module.ModuleEditCommand.MESSAGE_MODULE_NOT_EDITED;
import static igrad.logic.parser.CliSyntax.PREFIX_CREDITS;
import static igrad.logic.parser.CliSyntax.PREFIX_MODULE_CODE;
import static igrad.logic.parser.CliSyntax.PREFIX_SEMESTER;
import static igrad.logic.parser.CliSyntax.PREFIX_TITLE;
import static java.util.Objects.requireNonNull;

import igrad.commons.core.Messages;
import igrad.logic.commands.module.ModuleEditCommand;
import igrad.logic.parser.ArgumentMultimap;
import igrad.logic.parser.ArgumentTokenizer;
import igrad.logic.parser.Parser;
import igrad.logic.parser.ParserUtil;
import igrad.logic.parser.Specifier;
import igrad.logic.parser.exceptions.ParseException;
import igrad.model.module.ModuleCode;

/**
 * Parses input arguments and creates a new ModuleEditCommand object.
 */
public class ModuleEditCommandParser extends ModuleCommandParser implements Parser<ModuleEditCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the ModuleEditCommand
     * and returns an ModuleEditCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public ModuleEditCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
            ArgumentTokenizer.tokenize(args, PREFIX_TITLE, PREFIX_MODULE_CODE, PREFIX_CREDITS, PREFIX_SEMESTER);

        /*
         * If all arguments in the command are empty; i.e, 'module edit', and nothing else, show
         * the help message for this command
         */
        if (argMultimap.isEmpty(true)) {
            throw new ParseException(String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT,
                MESSAGE_MODULE_EDIT_HELP));
        }

        EditModuleDescriptor editModuleDescriptor = new ModuleEditCommand.EditModuleDescriptor();

        Specifier specifier = ParserUtil.parseSpecifier(argMultimap.getPreamble(),
            ParserUtil.MODULE_MODULE_CODE_SPECIFIER_RULE, ModuleCode.MESSAGE_CONSTRAINTS);

        ModuleCode moduleCode = new ModuleCode(specifier.getValue());

        if (argMultimap.getValue(PREFIX_MODULE_CODE).isPresent()) {
            editModuleDescriptor.setModuleCode(parseModuleCode(argMultimap.getValue(PREFIX_MODULE_CODE).get()));
        }

        if (argMultimap.getValue(PREFIX_TITLE).isPresent()) {
            editModuleDescriptor.setTitle(parseTitle(argMultimap.getValue(PREFIX_TITLE).get()));
        }

        if (argMultimap.getValue(PREFIX_CREDITS).isPresent()) {
            editModuleDescriptor.setCredits(parseCredits(argMultimap.getValue(PREFIX_CREDITS).get()));
        }

        if (argMultimap.getValue(PREFIX_SEMESTER).isPresent()) {
            editModuleDescriptor.setSemester(parseSemester(argMultimap.getValue(PREFIX_SEMESTER).get()));
        }

        if (!editModuleDescriptor.isAnyFieldEdited()) {
            throw new ParseException(MESSAGE_MODULE_NOT_EDITED);
        }

        return new ModuleEditCommand(moduleCode, editModuleDescriptor);
    }

}
