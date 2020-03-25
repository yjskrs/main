package igrad.logic.parser;

import static igrad.commons.core.Messages.MESSAGE_SPECIFIER_NOT_SPECIFIED;
import static igrad.logic.commands.requirement.RequirementDeleteCommand.MESSAGE_USAGE;
import static igrad.logic.parser.CliSyntax.PREFIX_CREDITS;
import static igrad.logic.parser.CliSyntax.PREFIX_MEMO;
import static igrad.logic.parser.CliSyntax.PREFIX_MODULE_CODE;
import static igrad.logic.parser.CliSyntax.PREFIX_NAME;
import static igrad.logic.parser.CliSyntax.PREFIX_SEMESTER;
import static igrad.logic.parser.CliSyntax.PREFIX_TAG;
import static igrad.logic.parser.CliSyntax.PREFIX_TITLE;

import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import igrad.logic.commands.AssignCommand;
import igrad.logic.commands.requirement.RequirementDeleteCommand;
import igrad.logic.parser.exceptions.ParseException;
import igrad.model.requirement.Name;
import igrad.model.tag.Tag;
import igrad.services.exceptions.ServiceException;

/**
 * Parses module assign (to requirement) input argument and creates a new AssignCommand object.
 */
public class AssignCommandParser implements Parser<AssignCommand> {

    @Override
    public AssignCommand parse(String userInput) throws ParseException {
        ArgumentMultimap argMultimap =
            ArgumentTokenizer.tokenize(userInput, PREFIX_MODULE_CODE);

        Specifier specifier = ParserUtil.parseSpecifier(argMultimap.getPreamble());

        List<Module> modulesToAssign = parseModulesToAssign(argMultimap.getAllValues(PREFIX_MODULE_CODE));

        return null;
        //return new AssignCommand(new Name(specifier.getValue()));
    }

    /**
     * Parses {@code Collection<String> tags} into a {@code Set<Tag>} if {@code tags} is non-empty.
     * If {@code tags} contain only one element which is an empty string, it will be parsed into a
     * {@code Set<Tag>} containing zero tags.
     */
    private List<Module> parseModulesToAssign(Collection<String> tags) throws ParseException {
        assert tags != null;

        /*if (tags.isEmpty()) {
            return Optional.empty();
        }
        Collection<String> tagSet = tags.size() == 1 && tags.contains("") ? Collections.emptySet() : tags;
        return Optional.of(ParserUtil.parseTag(tagSet));
         */

        return null;
    }
}
