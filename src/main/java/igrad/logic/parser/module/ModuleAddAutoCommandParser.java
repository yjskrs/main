package igrad.logic.parser.module;

import static igrad.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static igrad.commons.core.Messages.MESSAGE_REQUEST_FAILED;
import static igrad.logic.parser.CliSyntax.PREFIX_CREDITS;
import static igrad.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static igrad.logic.parser.CliSyntax.PREFIX_MEMO;
import static igrad.logic.parser.CliSyntax.PREFIX_MODULE_CODE;
import static igrad.logic.parser.CliSyntax.PREFIX_SEMESTER;
import static igrad.logic.parser.CliSyntax.PREFIX_TAG;
import static igrad.logic.parser.CliSyntax.PREFIX_TITLE;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

import igrad.logic.commands.module.ModuleAddAutoCommand;
import igrad.logic.parser.ArgumentMultimap;
import igrad.logic.parser.ArgumentTokenizer;
import igrad.logic.parser.Parser;
import igrad.logic.parser.ParserUtil;
import igrad.logic.parser.Prefix;
import igrad.logic.parser.exceptions.ParseException;
import igrad.model.module.Credits;
import igrad.model.module.Description;
import igrad.model.module.Grade;
import igrad.model.module.Memo;
import igrad.model.module.Module;
import igrad.model.module.ModuleCode;
import igrad.model.module.Semester;
import igrad.model.module.Title;
import igrad.model.tag.Tag;
import igrad.services.JsonParsedModule;
import igrad.services.NusModsRequester;
import igrad.services.exceptions.ServiceException;

/**
 * Parses input arguments and creates a new ModuleAddCommand object
 */
public class ModuleAddAutoCommandParser extends ModuleCommandParser implements Parser<ModuleAddAutoCommand> {

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

    /**
     * Parses the given {@code String} of arguments in the context of the ModuleAddCommand
     * and returns an ModuleAddCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public ModuleAddAutoCommand parse(String args) throws ParseException, IOException, ServiceException {
        ArgumentMultimap argMultimap =
            ArgumentTokenizer.tokenize(
                args,
                PREFIX_TITLE,
                PREFIX_MODULE_CODE,
                PREFIX_CREDITS,
                PREFIX_TAG,
                PREFIX_MEMO,
                PREFIX_DESCRIPTION,
                PREFIX_SEMESTER
            );

        if (!arePrefixesPresent(argMultimap, PREFIX_MODULE_CODE)
            || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ModuleAddAutoCommand.MESSAGE_USAGE));
        }

        List<String> moduleCodes = argMultimap.getAllValues((PREFIX_MODULE_CODE));

        Set<Tag> tagsList = ParserUtil.parseTag(argMultimap.getAllValues(PREFIX_TAG));

        ArrayList<Module> modules = new ArrayList<>();

        String messageAdditional = "";

        for (String moduleCodeStr : moduleCodes) {

            JsonParsedModule jsonParsedModule;

            try {
                jsonParsedModule = NusModsRequester.getModule(moduleCodeStr);
            } catch (IOException | ServiceException e) {
                messageAdditional += String.format(MESSAGE_REQUEST_FAILED, moduleCodeStr);
                continue;
            }

            Title title = parseTitle(jsonParsedModule.getTitle());
            Credits credits = parseCredits(jsonParsedModule.getCredits());
            ModuleCode moduleCode = parseModuleCode(jsonParsedModule.getModuleCode());

            String prerequisiteModulesString = jsonParsedModule.getPrerequisite();
            String preclusionModulesString = jsonParsedModule.getPreclusion();

            ModuleStringParser prerequisiteParser = new ModuleStringParser(prerequisiteModulesString);
            Optional<ModuleCode[]> prerequisites = Optional.of(prerequisiteParser.getModuleCodes());

            ModuleStringParser preclusionParser = new ModuleStringParser(preclusionModulesString);
            Optional<ModuleCode[]> preclusions = Optional.of(preclusionParser.getModuleCodes());

            Optional<Description> description = parseDescription(jsonParsedModule.getDescription());

            Optional<Memo> memo = argMultimap.getValue(PREFIX_MEMO).isPresent()
                ? parseMemo(argMultimap.getValue(PREFIX_MEMO).get())
                : Optional.empty();
            Optional<Semester> semester = argMultimap.getValue(PREFIX_SEMESTER).isPresent()
                ? parseSemester(argMultimap.getValue(PREFIX_SEMESTER).get())
                : Optional.empty();

            // TODO: support grade parsing too! i'll just leave it like that for now
            Optional<Grade> grade = Optional.empty();

            Module module = new Module(
                title,
                moduleCode,
                credits,
                memo,
                semester,
                description,
                grade,
                preclusions,
                prerequisites,
                tagsList
            );

            modules.add(module);

        }

        System.out.println("Printing all modules retrieved");
        for (Module module : modules) {
            System.out.println(module);
        }

        return new ModuleAddAutoCommand(modules, messageAdditional);
    }

}
