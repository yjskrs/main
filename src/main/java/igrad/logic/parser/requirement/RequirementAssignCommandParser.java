package igrad.logic.parser.requirement;

import static igrad.logic.commands.requirement.RequirementAssignCommand.MESSAGE_REQUIREMENT_NO_MODULES;
import static igrad.logic.parser.CliSyntax.PREFIX_MODULE_CODE;
import static igrad.logic.parser.ParserUtil.parseModuleCodes;

import java.util.Collection;
import java.util.List;

import igrad.logic.commands.requirement.RequirementAssignCommand;
import igrad.logic.parser.ArgumentMultimap;
import igrad.logic.parser.ArgumentTokenizer;
import igrad.logic.parser.Parser;
import igrad.logic.parser.ParserUtil;
import igrad.logic.parser.Specifier;
import igrad.logic.parser.exceptions.ParseException;
import igrad.model.module.ModuleCode;
import igrad.model.requirement.Name;

/**
 * Parses module assign (to requirement) input argument and creates a new AssignCommand object.
 */
public class RequirementAssignCommandParser implements Parser<RequirementAssignCommand> {

    @Override
    public RequirementAssignCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
            ArgumentTokenizer.tokenize(args, PREFIX_MODULE_CODE);

        Specifier specifier = ParserUtil.parseSpecifier(argMultimap.getPreamble(),
            ParserUtil.MODULE_MODULE_CODE_SPECIFIER_RULE, ModuleCode.MESSAGE_CONSTRAINTS);

        List<ModuleCode> moduleCodes = parseModulesToAssign(argMultimap.getAllValues(PREFIX_MODULE_CODE));

        return new RequirementAssignCommand(new Name(specifier.getValue()), moduleCodes);
    }

    /**
     * Parses {@code Collection<String> moduleCodes} into a {@code List<ModuleCode>} if {@code moduleCodes} is
     * non-empty.
     * If {@code moduleCodes} contain only one element which is an empty string, it will be parsed into a
     * {@code List<ModuleCode>} containing zero tags.
     */
    private List<ModuleCode> parseModulesToAssign(Collection<String> moduleCodes) throws ParseException {
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
