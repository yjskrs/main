package igrad.logic.parser.requirement;

import static igrad.logic.commands.requirement.RequirementEditCommand.MESSAGE_REQUIREMENT_EDIT_HELP;
import static igrad.logic.commands.requirement.RequirementEditCommand.MESSAGE_REQUIREMENT_NOT_EDITED;
import static igrad.logic.parser.CliSyntax.PREFIX_CREDITS;
import static igrad.logic.parser.CliSyntax.PREFIX_TITLE;
import static java.util.Objects.requireNonNull;

import java.util.Optional;

import igrad.commons.core.Messages;
import igrad.logic.commands.requirement.RequirementEditCommand;
import igrad.logic.parser.ArgumentMultimap;
import igrad.logic.parser.ArgumentTokenizer;
import igrad.logic.parser.ParserUtil;
import igrad.logic.parser.Specifier;
import igrad.logic.parser.exceptions.ParseException;
import igrad.model.requirement.Credits;
import igrad.model.requirement.RequirementCode;
import igrad.model.requirement.Title;

/**
 * Parses requirement edit command input arguments and creates a new RequirementEditCommand object.
 */
public class RequirementEditCommandParser extends RequirementCommandParser {

    /**
     * Parses the given string of arguments {@code args} in the context of the
     * RequirementEditCommand and returns a RequirementEditCommand object for execution.
     *
     * @throws ParseException If the user input does not conform the expected format.
     */
    @Override
    public RequirementEditCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_TITLE, PREFIX_CREDITS);

        /*
         * If all arguments in the command are empty; i.e, 'requirement edit', and nothing else, show
         * the help message for this command
         */
        if (argMultimap.isEmpty(true)) {
            throw new ParseException(String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT,
                MESSAGE_REQUIREMENT_EDIT_HELP));
        }

        Specifier specifier = ParserUtil.parseSpecifier(argMultimap.getPreamble(),
            ParserUtil.REQUIREMENT_CODE_SPECIFIER_RULE, RequirementCode.MESSAGE_CONSTRAINTS);

        // If both (neither) the requirement title and credits have not been specified, flag an error
        if (argMultimap.getValue(PREFIX_TITLE).isEmpty() && argMultimap.getValue(PREFIX_CREDITS).isEmpty()) {
            throw new ParseException(MESSAGE_REQUIREMENT_NOT_EDITED);
        }

        Title title = null;
        Credits credits = null;

        if (argMultimap.getValue(PREFIX_TITLE).isPresent()) {
            title = parseTitle(argMultimap.getValue(PREFIX_TITLE).get());
        }

        if (argMultimap.getValue(PREFIX_CREDITS).isPresent()) {
            credits = parseCredits(argMultimap.getValue(PREFIX_CREDITS).get());
        }

        /*
         * TODO: you might want to follow how ModuleEditCommandParser is done, i.e,
         *  wrap all of these into a class like EditModuleDescriptor, before passing it to
         *  the RequirementEditCommand(..) constructor, to keep it neater.
         *  ~ nathanael
         */
        return new RequirementEditCommand(new RequirementCode(specifier.getValue()),
            Optional.ofNullable(title),
            Optional.ofNullable(credits));
    }

}
