package igrad.logic.parser;

import static igrad.logic.parser.CliSyntax.PREFIX_CREDITS;
import static igrad.logic.parser.CliSyntax.PREFIX_MEMO;
import static igrad.logic.parser.CliSyntax.PREFIX_MODULE_CODE;
import static igrad.logic.parser.CliSyntax.PREFIX_SEMESTER;
import static igrad.logic.parser.CliSyntax.PREFIX_TAG;
import static igrad.logic.parser.CliSyntax.PREFIX_TITLE;
import static java.util.Objects.requireNonNull;
import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;
import igrad.commons.core.Messages;
import igrad.commons.core.index.Index;
import igrad.logic.commands.ModuleEditCommand;
import igrad.logic.parser.exceptions.ParseException;
import igrad.model.tag.Tag;

/**
 * Parses input arguments and creates a new ModuleEditCommand object.
 */
public class EditCommandParser implements Parser<ModuleEditCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the ModuleEditCommand
     * and returns an ModuleEditCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public ModuleEditCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
            ArgumentTokenizer.tokenize(args, PREFIX_TITLE, PREFIX_MODULE_CODE, PREFIX_CREDITS,
                PREFIX_MEMO, PREFIX_SEMESTER, PREFIX_TAG);

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT,
                ModuleEditCommand.MESSAGE_USAGE), pe);
        }

        ModuleEditCommand.EditModuleDescriptor editModuleDescriptor = new ModuleEditCommand.EditModuleDescriptor();
        if (argMultimap.getValue(PREFIX_TITLE).isPresent()) {
            editModuleDescriptor.setTitle(ParserUtil.parseTitle(argMultimap.getValue(PREFIX_TITLE).get()));
        }

        if (argMultimap.getValue(PREFIX_MODULE_CODE).isPresent()) {
            editModuleDescriptor.setModuleCode(ParserUtil.parseModuleCode(
                argMultimap.getValue(PREFIX_MODULE_CODE).get()));
        }

        if (argMultimap.getValue(PREFIX_CREDITS).isPresent()) {
            editModuleDescriptor.setCredits(ParserUtil.parseCredits(argMultimap.getValue(PREFIX_CREDITS).get()));
        }

        if (argMultimap.getValue(PREFIX_MEMO).isPresent()) {
            editModuleDescriptor.setMemo(ParserUtil.parseMemo(argMultimap.getValue(PREFIX_MEMO).get()));
        }

        if (argMultimap.getValue(PREFIX_SEMESTER).isPresent()) {
            editModuleDescriptor.setSemester(ParserUtil.parseSemester(argMultimap.getValue(PREFIX_SEMESTER).get()));
        }

        parseTagsForEdit(argMultimap.getAllValues(PREFIX_TAG)).ifPresent(editModuleDescriptor::setTags);

        if (!editModuleDescriptor.isAnyFieldEdited()) {
            throw new ParseException(ModuleEditCommand.MESSAGE_NOT_EDITED);
        }

        return new ModuleEditCommand(index, editModuleDescriptor);
    }

    /**
     * Parses {@code Collection<String> tags} into a {@code Set<Tag>} if {@code tags} is non-empty.
     * If {@code tags} contain only one element which is an empty string, it will be parsed into a
     * {@code Set<Tag>} containing zero tags.
     */
    private Optional<Set<Tag>> parseTagsForEdit(Collection<String> tags) throws ParseException {
        assert tags != null;

        if (tags.isEmpty()) {
            return Optional.empty();
        }
        Collection<String> tagSet = tags.size() == 1 && tags.contains("") ? Collections.emptySet() : tags;
        return Optional.of(ParserUtil.parseTag(tagSet));
    }

}
