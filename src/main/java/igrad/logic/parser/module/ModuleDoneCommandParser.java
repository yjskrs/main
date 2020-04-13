package igrad.logic.parser.module;

import static igrad.logic.commands.module.ModuleDoneCommand.MESSAGE_MODULE_DONE_HELP;
import static igrad.logic.commands.module.ModuleDoneCommand.MESSAGE_MODULE_NOT_EDITED;
import static igrad.logic.parser.CliSyntax.PREFIX_GRADE;
import static igrad.logic.parser.CliSyntax.PREFIX_SEMESTER;
import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.util.Optional;

import igrad.commons.core.Messages;
import igrad.logic.commands.module.ModuleDoneCommand;
import igrad.logic.commands.module.ModuleDoneCommand.EditModuleDescriptor;
import igrad.logic.parser.ArgumentMultimap;
import igrad.logic.parser.ArgumentTokenizer;
import igrad.logic.parser.Parser;
import igrad.logic.parser.ParserUtil;
import igrad.logic.parser.Specifier;
import igrad.logic.parser.exceptions.ParseException;
import igrad.model.module.Grade;
import igrad.model.module.ModuleCode;
import igrad.model.module.Semester;
import igrad.services.exceptions.ServiceException;

//@@author nathanaelseen

/**
 * Parses input arguments and creates a new ModuleDoneCommand object.
 */
public class ModuleDoneCommandParser extends ModuleCommandParser implements Parser<ModuleDoneCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the ModuleDoneCommand
     * and returns an ModuleDoneCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    @Override
    public ModuleDoneCommand parse(String args) throws ParseException, IOException, ServiceException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_GRADE, PREFIX_SEMESTER);

        /*
         * If all arguments in the command are empty; i.e, 'module done', and nothing else, show
         * the help message for this command
         */
        if (argMultimap.isEmpty(true)) {
            throw new ParseException(String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT,
                MESSAGE_MODULE_DONE_HELP));
        }

        ModuleCode moduleCode = parseModuleCodeSpecifier(argMultimap);
        EditModuleDescriptor editModuleDescriptor = parseEditedModule(argMultimap);

        return new ModuleDoneCommand(moduleCode, editModuleDescriptor);
    }

    /**
     * Parses specifier from {@code argMultimap} into {@code ModuleCode}.
     *
     * @throws ParseException If user input does not conform to the expected format.
     */
    private ModuleCode parseModuleCodeSpecifier(ArgumentMultimap argMultimap) throws ParseException {
        Specifier specifier = ParserUtil.parseSpecifier(argMultimap.getPreamble(),
            ParserUtil.MODULE_MODULE_CODE_SPECIFIER_RULE, ModuleCode.MESSAGE_CONSTRAINTS);

        return new ModuleCode(specifier.getValue());
    }

    /**
     * Parses grade and/or semesters from {@code argMultimap} into {@code EditRequirementDescriptor}.
     *
     * @throws ParseException If user input does not conform to the expected format.
     */
    private EditModuleDescriptor parseEditedModule(ArgumentMultimap argMultimap) throws ParseException {
        EditModuleDescriptor editModuleDescriptor = new EditModuleDescriptor();

        Optional<String> gradeString = argMultimap.getValue(PREFIX_GRADE);
        Optional<String> semesterString = argMultimap.getValue(PREFIX_SEMESTER);

        // Grade is mandatory for marking a module as done
        if (gradeString.isEmpty() || gradeString.get().isEmpty()) {
            throw new ParseException(MESSAGE_MODULE_NOT_EDITED);
        }

        Optional<Grade> grade = parseGrade(gradeString.get());
        editModuleDescriptor.setGrade(grade);

        // If semester is specified, add it into our editModuleDescriptor
        if (semesterString.isPresent()) {
            Optional<Semester> semester = parseSemester(semesterString.get());
            editModuleDescriptor.setSemester(semester);
        }

        return editModuleDescriptor;
    }

}
