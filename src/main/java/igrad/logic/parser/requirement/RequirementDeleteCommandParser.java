package igrad.logic.parser.requirement;

import igrad.logic.commands.requirement.RequirementDeleteCommand;
import igrad.logic.parser.ParserUtil;
import igrad.logic.parser.Specifier;
import igrad.logic.parser.exceptions.ParseException;
import igrad.model.requirement.RequirementCode;

/**
 * Parses requirement input argument and creates a new RequirementDeleteCommand object.
 */
public class RequirementDeleteCommandParser extends RequirementCommandParser {

    @Override
    public RequirementDeleteCommand parse(String args) throws ParseException {
        Specifier specifier = ParserUtil.parseSpecifier(args,
            ParserUtil.REQUIREMENT_CODE_SPECIFIER_RULE, RequirementCode.MESSAGE_CONSTRAINTS);

        RequirementCode requirementCode = new RequirementCode(specifier.getValue());

        return new RequirementDeleteCommand(requirementCode);
    }
}
