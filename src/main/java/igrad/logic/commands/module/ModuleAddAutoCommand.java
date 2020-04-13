package igrad.logic.commands.module;

import static igrad.logic.parser.CliSyntax.PREFIX_CREDITS;
import static igrad.logic.parser.CliSyntax.PREFIX_MODULE_CODE;
import static igrad.logic.parser.CliSyntax.PREFIX_SEMESTER;
import static igrad.logic.parser.CliSyntax.PREFIX_TITLE;
import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import igrad.logic.commands.CommandResult;
import igrad.logic.commands.exceptions.CommandException;
import igrad.logic.parser.module.ModuleStringParser;
import igrad.model.Model;
import igrad.model.module.Credits;
import igrad.model.module.Module;
import igrad.model.module.ModuleCode;
import igrad.model.module.ModulePreclusions;
import igrad.model.module.ModulePrerequisites;
import igrad.model.module.Title;
import igrad.services.JsonParsedModule;
import igrad.services.NusModsRequester;

/**
 * Adds a module to the course book.
 */
public class ModuleAddAutoCommand extends ModuleCommand {

    public static final String COMMAND_WORD = MODULE_COMMAND_WORD + SPACE + "add";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a module. "
        + "Parameter(s): "
        + PREFIX_TITLE + "MODULE_TITLE "
        + PREFIX_MODULE_CODE + "MODULE_CODE "
        + PREFIX_CREDITS + "CREDITS "
        + "[" + PREFIX_SEMESTER + "SEMESTER]...\n"
        + "Example: " + COMMAND_WORD + " "
        + PREFIX_TITLE + "Software Engineering "
        + PREFIX_MODULE_CODE + "CS2103T "
        + PREFIX_CREDITS + "4 "
        + PREFIX_SEMESTER + "Y2S2 ";

    public static final int MAX_SIZE = 10;

    public static final String MESSAGE_MODULE_OVERLOAD = "Please do not attempt to "
        + "add more than %d modules.\nYou attempted to add %d modules.\n";
    public static final String MESSAGE_COMPLETE = "%d module(s) added through NUSMods API.\n";
    public static final String MESSAGE_SUCCESS = "Got it! I have added the following module(s) for you:\n%s";
    public static final String MESSAGE_DUPLICATE_MODULE = "Sorry, this module already exists in the course book: %s\n";
    public static final String MESSAGE_MODULE_NOT_FOUND =
        "Sorry, I was unable to find this module: %s\nIs your internet down?\n";
    public static final String MESSAGE_PREREQUISITE_NOT_PRESENT =
        "WARNING: Prerequisite not found!\n";
    public static final String MESSAGE_PRECLUSION_PRESENT =
        "WARNING: Preclusion found!\n";

    private final List<String> toAddList;

    /**
     * Creates an ModuleAddCommand to add the specified {@code Module}
     */
    public ModuleAddAutoCommand(List<String> moduleCodes) {
        requireNonNull(moduleCodes);

        toAddList = moduleCodes;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (toAddList.size() > MAX_SIZE) {
            throw new CommandException(String.format(MESSAGE_MODULE_OVERLOAD, MAX_SIZE, toAddList.size()));
        }

        ArrayList<Module> modules = new ArrayList<>();

        StringBuilder messageAdditional = new StringBuilder();

        for (String moduleCodeStr : toAddList) {

            JsonParsedModule jsonParsedModule;

            try {
                jsonParsedModule = NusModsRequester.getModule(moduleCodeStr);
            } catch (IOException e) {
                messageAdditional.append(String.format(MESSAGE_MODULE_NOT_FOUND, moduleCodeStr));
                continue;
            }

            Title title = new Title(jsonParsedModule.getTitle());
            Credits credits = new Credits(jsonParsedModule.getCredits());
            ModuleCode moduleCode = new ModuleCode(jsonParsedModule.getModuleCode());

            String prerequisiteModulesString = jsonParsedModule.getPrerequisite();
            String preclusionModulesString = jsonParsedModule.getPreclusion();

            ModuleStringParser prerequisiteParser = new ModuleStringParser(prerequisiteModulesString);
            ModulePrerequisites prerequisites = new ModulePrerequisites(prerequisiteParser.getModuleCodes());


            ModuleStringParser preclusionParser = new ModuleStringParser(preclusionModulesString);
            ModulePreclusions preclusions = new ModulePreclusions(preclusionParser.getModuleCodes());

            Module module = new Module(
                title,
                moduleCode,
                credits,
                preclusions,
                prerequisites
            );

            modules.add(module);

        }

        ArrayList<Module> modulesToAdd = modules
            .stream()
            .filter(m -> !model.hasModule(m))
            .collect(Collectors.toCollection(ArrayList::new));

        StringBuilder message = getMessage(modules, model, modulesToAdd);

        for (Module module : modulesToAdd) {
            model.addModule(module);
        }

        message.append(messageAdditional);

        return new CommandResult(message.toString());
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof ModuleAddAutoCommand // instanceof handles nulls
            && toAddList.equals(((ModuleAddAutoCommand) other).toAddList));
    }

    private StringBuilder getMessage(List<Module> modules, Model model, List<Module> modulesToAdd) {

        StringBuilder message = new StringBuilder();

        for (Module module : modules) {

            if (model.hasModule(module)) {
                message.append(String.format(MESSAGE_DUPLICATE_MODULE, module.getModuleCode().value));
            } else {
                message.append(String.format(MESSAGE_SUCCESS, module.toString()));

                if (!model.hasModulePrerequisites(module)) {
                    message.append(MESSAGE_PREREQUISITE_NOT_PRESENT);
                }

                if (model.hasModulePreclusions(module)) {
                    message.append(MESSAGE_PRECLUSION_PRESENT);
                }
            }
        }

        message.append(String.format(MESSAGE_COMPLETE, modulesToAdd.size()));

        return message;
    }

    /**
     * Formats the exception message for when a preclusion module is present in the model.
     */
    private String formatPreclusionExceptionMessage(ModuleCode moduleCode) {
        String moduleCodeString = "(" + moduleCode.toString() + ")";

        return String.format(MESSAGE_PRECLUSION_PRESENT, moduleCodeString);
    }

    /**
     * Formats the exception message for when a prerequisite module is not present in the model.
     */
    private String formatPrerequisiteExceptionMessage(ModuleCode moduleCode) {
        String moduleCodeString = "(" + moduleCode.toString() + ")";

        return String.format(MESSAGE_PREREQUISITE_NOT_PRESENT, moduleCodeString);
    }
}
