package igrad.logic.commands.module;

//@@author waynewee

import static igrad.logic.parser.CliSyntax.PREFIX_CREDITS;
import static igrad.logic.parser.CliSyntax.PREFIX_MODULE_CODE;
import static igrad.logic.parser.CliSyntax.PREFIX_SEMESTER;
import static igrad.logic.parser.CliSyntax.PREFIX_TITLE;
import static java.util.Objects.requireNonNull;

import igrad.logic.commands.CommandResult;
import igrad.logic.commands.exceptions.CommandException;
import igrad.model.Model;
import igrad.model.module.Module;

/**
 * Adds a module to the course book.
 */
public class ModuleAddCommand extends ModuleCommand {

    public static final String MODULE_ADD_COMMAND_WORD = MODULE_COMMAND_WORD + SPACE + "add";

    public static final String MESSAGE_MODULE_ADD_DETAILS = MODULE_ADD_COMMAND_WORD
        + ": Adds a module with relevant details specified.\n";

    public static final String MESSAGE_MODULE_ADD_USAGE = "Parameter(s): "
        + PREFIX_MODULE_CODE + "MODULE_CODE "
        + PREFIX_TITLE + "MODULE_TITLE "
        + PREFIX_CREDITS + "CREDITS "
        + "[" + PREFIX_SEMESTER + "SEMESTER]\n"
        + "e.g. " + MODULE_ADD_COMMAND_WORD + " "
        + PREFIX_MODULE_CODE + "CS2103T "
        + PREFIX_TITLE + "Software Engineering "
        + PREFIX_CREDITS + "4 "
        + PREFIX_SEMESTER + "Y2S2";

    public static final String MESSAGE_MODULE_ADD_HELP = MESSAGE_MODULE_ADD_DETAILS + MESSAGE_MODULE_ADD_USAGE;

    public static final String MESSAGE_MODULE_ADD_SUCCESS = "Got it! I have added this module for you:\n%1$s";
    public static final String MESSAGE_DUPLICATE_MODULE = "Sorry, this module already exists in the course book.";
    public static final String MESSAGE_MODULE_NOT_ADDED = "Added module must be provided with at least these "
        + "argument(s) " + PREFIX_MODULE_CODE + "MODULE_CODE " + PREFIX_TITLE + "TITLE " + PREFIX_CREDITS + "CREDITS ";

    private final Module toAdd;

    /**
     * Creates an ModuleAddCommand to add the specified {@code Module}
     */
    public ModuleAddCommand(Module module) {
        requireNonNull(module);
        toAdd = module;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (model.hasModule(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_MODULE);
        }

        model.addModule(toAdd);
        return new CommandResult(String.format(MESSAGE_MODULE_ADD_SUCCESS, toAdd));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof ModuleAddCommand // instanceof handles nulls
            && toAdd.equals(((ModuleAddCommand) other).toAdd));
    }
}
