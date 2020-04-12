package igrad.logic.parser.requirement;

import static igrad.logic.commands.requirement.RequirementDeleteCommand.MESSAGE_REQUIREMENT_DELETE_HELP;
import static igrad.logic.parser.ParserUtil.REQUIREMENT_CODE_SPECIFIER_RULE;

import igrad.commons.core.Messages;
import igrad.logic.commands.requirement.RequirementDeleteCommand;
import igrad.logic.parser.ArgumentMultimap;
import igrad.logic.parser.ArgumentTokenizer;
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
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args);

        if (argMultimap.isEmpty(true)) {
            throw new ParseException(
                    String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_REQUIREMENT_DELETE_HELP));
        }

        // Throw parse exception if specifier is an empty string or is not a valid specifier.
        Specifier specifier = ParserUtil.parseSpecifier(args,
            REQUIREMENT_CODE_SPECIFIER_RULE, RequirementCode.MESSAGE_CONSTRAINTS);

        RequirementCode requirementCode = new RequirementCode(specifier.getValue());

        return new RequirementDeleteCommand(requirementCode);
    }
}
