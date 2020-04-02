package igrad.logic.parser.module;

import static igrad.logic.commands.module.ModuleEditCommand.EditModuleDescriptor;
import static igrad.logic.commands.module.ModuleEditCommand.MESSAGE_MODULE_EDIT_HELP;
import static igrad.logic.commands.module.ModuleEditCommand.MESSAGE_MODULE_NOT_EDITED;
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
import igrad.logic.commands.module.ModuleEditCommand;
import igrad.logic.parser.ArgumentMultimap;
import igrad.logic.parser.ArgumentTokenizer;
import igrad.logic.parser.Parser;
import igrad.logic.parser.ParserUtil;
import igrad.logic.parser.Specifier;
import igrad.logic.parser.exceptions.ParseException;
import igrad.model.module.ModuleCode;
import igrad.model.tag.Tag;

/**
 * Parses input arguments and creates a new ModuleEditCommand object.
 */
public class ModuleEditCommandParser extends ModuleCommandParser implements Parser<ModuleEditCommand> {

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

        /*
         * If all arguments in the command are empty; i.e, 'module edit', and nothing else, show
         * the help message for this command
         */
        if (argMultimap.isEmpty(true)) {
            throw new ParseException(String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT,
                MESSAGE_MODULE_EDIT_HELP));
        }

        EditModuleDescriptor editModuleDescriptor = new ModuleEditCommand.EditModuleDescriptor();

        Specifier specifier = ParserUtil.parseSpecifier(argMultimap.getPreamble(),
            ParserUtil.MODULE_MODULE_CODE_SPECIFIER_RULE, ModuleCode.MESSAGE_CONSTRAINTS);

        ModuleCode moduleCode = new ModuleCode(specifier.getValue());

        if (argMultimap.getValue(PREFIX_MODULE_CODE).isPresent()) {
            editModuleDescriptor.setModuleCode(parseModuleCode(argMultimap.getValue(PREFIX_MODULE_CODE).get()));
        }

        if (argMultimap.getValue(PREFIX_TITLE).isPresent()) {
            editModuleDescriptor.setTitle(parseTitle(argMultimap.getValue(PREFIX_TITLE).get()));
        }

        if (argMultimap.getValue(PREFIX_CREDITS).isPresent()) {
            editModuleDescriptor.setCredits(parseCredits(argMultimap.getValue(PREFIX_CREDITS).get()));
        }

        if (argMultimap.getValue(PREFIX_MEMO).isPresent()) {
            editModuleDescriptor.setMemo(parseMemo(argMultimap.getValue(PREFIX_MEMO).get()));
        }

        if (argMultimap.getValue(PREFIX_SEMESTER).isPresent()) {
            editModuleDescriptor.setSemester(parseSemester(argMultimap.getValue(PREFIX_SEMESTER).get()));
        }

        parseTagsForEdit(argMultimap.getAllValues(PREFIX_TAG)).ifPresent(editModuleDescriptor::setTags);

        if (!editModuleDescriptor.isAnyFieldEdited()) {
            throw new ParseException(MESSAGE_MODULE_NOT_EDITED);
        }

        return new ModuleEditCommand(moduleCode, editModuleDescriptor);
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
