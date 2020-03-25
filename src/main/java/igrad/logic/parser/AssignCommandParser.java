package igrad.logic.parser;

import static igrad.logic.parser.CliSyntax.PREFIX_MODULE_CODE;

import java.util.Collection;
import java.util.List;

import igrad.logic.commands.requirement.AssignCommand;
import igrad.logic.parser.exceptions.ParseException;

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
