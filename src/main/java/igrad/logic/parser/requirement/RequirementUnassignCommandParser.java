package igrad.logic.parser.requirement;

import static igrad.logic.commands.requirement.RequirementUnassignCommand.MESSAGE_REQUIREMENT_NO_MODULES;
import static igrad.logic.commands.requirement.RequirementUnassignCommand.REQUIREMENT_UNASSIGN_MESSAGE_HELP;
import static igrad.logic.parser.CliSyntax.PREFIX_MODULE_CODE;
import static igrad.logic.parser.ParserUtil.parseModuleCodes;

import java.util.Collection;
import java.util.List;

import igrad.commons.core.Messages;
import igrad.logic.commands.requirement.RequirementUnassignCommand;
import igrad.logic.parser.ArgumentMultimap;
import igrad.logic.parser.ArgumentTokenizer;
import igrad.logic.parser.Parser;
import igrad.logic.parser.ParserUtil;
import igrad.logic.parser.Specifier;
import igrad.logic.parser.exceptions.ParseException;
import igrad.model.module.ModuleCode;
import igrad.model.requirement.RequirementCode;

/**
 * Parses {@code Module}s to unassign (from {@code Requirement}) input argument and creates a new
 * {@code RequirementUnassignCommand} object.
 */
public class RequirementUnassignCommandParser implements Parser<RequirementUnassignCommand> {

    @Override
    public RequirementUnassignCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
            ArgumentTokenizer.tokenize(args, PREFIX_MODULE_CODE);

        /*
         * If all arguments in the command are empty; i.e, 'requirement unassign', and nothing else, show
         * the help message for this command
         */
        if (argMultimap.isEmpty(true)) {
            throw new ParseException(String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT,
                REQUIREMENT_UNASSIGN_MESSAGE_HELP));
        }

        Specifier specifier = ParserUtil.parseSpecifier(argMultimap.getPreamble(),
            ParserUtil.REQUIREMENT_CODE_SPECIFIER_RULE, RequirementCode.MESSAGE_CONSTRAINTS);

        List<ModuleCode> moduleCodes = parseModulesToUnassign(argMultimap.getAllValues(PREFIX_MODULE_CODE));

        return new RequirementUnassignCommand(new RequirementCode(specifier.getValue()), moduleCodes);
    }

    /**
     * Parses {@code Collection<String> moduleCodes} into a {@code List<ModuleCode>} if {@code moduleCodes} is
     * non-empty.
     * If {@code moduleCodes} contain only one element which is an empty string, it will be parsed into a
     * {@code List<ModuleCode>} containing zero tags.
     */
    private List<ModuleCode> parseModulesToUnassign(Collection<String> moduleCodes) throws ParseException {
        assert moduleCodes != null;

        if (moduleCodes.isEmpty()) {
            throw new ParseException(MESSAGE_REQUIREMENT_NO_MODULES);
        } else if (moduleCodes.size() == 1 && moduleCodes.contains("")) {
            throw new ParseException(MESSAGE_REQUIREMENT_NO_MODULES);
        }

        Collection<String> moduleCodesList = moduleCodes;

        return parseModuleCodes(moduleCodesList);
    }
}
