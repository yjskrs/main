package igrad.logic.commands.module;

import static igrad.logic.parser.CliSyntax.PREFIX_CREDITS;
import static igrad.logic.parser.CliSyntax.PREFIX_MEMO;
import static igrad.logic.parser.CliSyntax.PREFIX_MODULE_CODE;
import static igrad.logic.parser.CliSyntax.PREFIX_SEMESTER;
import static igrad.logic.parser.CliSyntax.PREFIX_TAG;
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

    public static final String COMMAND_WORD = MODULE_COMMAND_WORD + "add";

    public static final String MESSAGE_DETAILS = COMMAND_WORD + ": Adds a module with relevant details specified.\n";

    public static final String MESSAGE_USAGE = "Parameter(s): "
        + PREFIX_MODULE_CODE + "MODULE CODE "
        + PREFIX_TITLE + " TITLE "
        + PREFIX_CREDITS + "CREDITS "
        + "[" + PREFIX_MEMO + "MEMO] "
        + "[" + PREFIX_SEMESTER + "SEMESTER] "
        + "[" + PREFIX_TAG + "TAGS]...\n"
        + "Example: " + COMMAND_WORD + " "
        + PREFIX_MODULE_CODE + "CS2103T "
        + PREFIX_TITLE + "Software Engineering "
        + PREFIX_CREDITS + "4 "
        + PREFIX_SEMESTER + "Y2S2";

    public static final String MESSAGE_HELP = MESSAGE_DETAILS + MESSAGE_USAGE;

    public static final String MESSAGE_SUCCESS = "New module added: %1$s";
    public static final String MESSAGE_DUPLICATE_MODULE = "This module already exists in the course book";

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
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof ModuleAddCommand // instanceof handles nulls
            && toAdd.equals(((ModuleAddCommand) other).toAdd));
    }
}
