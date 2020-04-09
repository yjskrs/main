package igrad.logic.parser.requirement;

import static igrad.logic.parser.ParserUtil.REQUIREMENT_CODE_SPECIFIER_RULE;

import igrad.logic.commands.requirement.RequirementDeleteCommand;
import igrad.logic.parser.ParserUtil;
import igrad.logic.parser.Specifier;
import igrad.logic.parser.exceptions.ParseException;
import igrad.model.requirement.RequirementCode;

//@@author yjskrs

/**
 * Parses requirement input argument and creates a new RequirementDeleteCommand object.
 */
public class RequirementDeleteCommandParser extends RequirementCommandParser {

    /**
     * Parses the given string {@code args} in the context of the RequirementDeleteCommand
     * and returns a RequirementDeleteCommand object for execution.
     *
     * @throws ParseException If the user input does not conform to the expected format.
     */
    @Override
    public RequirementDeleteCommand parse(String args) throws ParseException {

        // Throw parse exception if specifier is an empty string or is not a valid specifier.
        Specifier specifier = ParserUtil.parseSpecifier(args,
            REQUIREMENT_CODE_SPECIFIER_RULE, RequirementCode.MESSAGE_CONSTRAINTS);

        RequirementCode requirementCode = new RequirementCode(specifier.getValue());

        return new RequirementDeleteCommand(requirementCode);
    }
}
