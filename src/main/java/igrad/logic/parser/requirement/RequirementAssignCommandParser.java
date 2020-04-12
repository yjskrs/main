package igrad.logic.parser.requirement;

import static igrad.logic.commands.requirement.RequirementAssignCommand.MESSAGE_REQUIREMENT_ASSIGN_HELP;
import static igrad.logic.commands.requirement.RequirementAssignCommand.MESSAGE_REQUIREMENT_NO_MODULES;
import static igrad.logic.parser.CliSyntax.PREFIX_MODULE_CODE;
import static igrad.logic.parser.ParserUtil.parseModuleCodes;

import java.util.Collection;
import java.util.List;

import igrad.commons.core.Messages;
import igrad.logic.commands.requirement.RequirementAssignCommand;
import igrad.logic.parser.ArgumentMultimap;
import igrad.logic.parser.ArgumentTokenizer;
import igrad.logic.parser.Parser;
import igrad.logic.parser.ParserUtil;
import igrad.logic.parser.Specifier;
import igrad.logic.parser.exceptions.ParseException;
import igrad.model.module.ModuleCode;
import igrad.model.requirement.RequirementCode;

//@@author nathanaelseen

/**
 * Parses {@code Module}s to assign (to {@code Requirement}) input argument and creates a new
 * {@code RequirementAssignCommand} object.
 */
public class RequirementAssignCommandParser implements Parser<RequirementAssignCommand> {

    @Override
    public RequirementAssignCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
            ArgumentTokenizer.tokenize(args, PREFIX_MODULE_CODE);

        /*
         * If all arguments in the command are empty; i.e, 'requirement assign', and nothing else, show
         * the help message for this command
         */
        if (argMultimap.isEmpty(true)) {
            throw new ParseException(String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT,
                MESSAGE_REQUIREMENT_ASSIGN_HELP));
        }

        RequirementCode requirementCode = parseRequirementCodeSpecifier(argMultimap);
        List<ModuleCode> moduleCodes = parseModulesToAssign(argMultimap.getAllValues(PREFIX_MODULE_CODE));

        return new RequirementAssignCommand(requirementCode, moduleCodes);
    }

    /**
     * Parses specifier from {@code argMultimap} into {@code RequirementCode}.
     *
     * @throws ParseException If user input does not conform to the expected format.
     */
    public RequirementCode parseRequirementCodeSpecifier(ArgumentMultimap argMultimap) throws ParseException {
        Specifier specifier = ParserUtil.parseSpecifier(argMultimap.getPreamble(),
            ParserUtil.REQUIREMENT_CODE_SPECIFIER_RULE, RequirementCode.MESSAGE_CONSTRAINTS);

        return new RequirementCode(specifier.getValue());
    }

    /**
     * Parses {@code Collection<String> moduleCodes} into a {@code List<ModuleCode>} if {@code moduleCodes} is
     * non-empty.
     * If {@code moduleCodes} contain only one element which is an empty string, it will be parsed into a
     * {@code List<ModuleCode>} containing zero tags.
     */
    private List<ModuleCode> parseModulesToAssign(Collection<String> moduleCodes) throws ParseException {
        assert moduleCodes != null;

        // If no module codes are specified, throw exception
        if (moduleCodes.isEmpty() || (moduleCodes.size() == 1 && moduleCodes.contains(""))) {
            throw new ParseException(MESSAGE_REQUIREMENT_NO_MODULES);
        }

        Collection<String> moduleCodesList = moduleCodes;

        return parseModuleCodes(moduleCodesList);
    }
}
