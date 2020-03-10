package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CREDITS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MEMO;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MODULE_CODE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SEMESTER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TITLE;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.module.Module;

/**
 * Adds a person to the address book.
 */
public class AddAutoCommand extends Command {

    public static final String COMMAND_WORD = "add";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a module. "
        + "Parameters: "
        + PREFIX_TITLE + "MODULE TITLE "
        + PREFIX_MODULE_CODE + "MODULE CODE "
        + PREFIX_CREDITS + "CREDITS "
        + PREFIX_MEMO + "MEMO "
        + "[" + PREFIX_SEMESTER + "SEMESTER]...\n"
        + "Example: " + COMMAND_WORD + " "
        + PREFIX_TITLE + "Software Engineering "
        + PREFIX_MODULE_CODE + "CS2103T "
        + PREFIX_CREDITS + "4 "
        + PREFIX_MEMO + "Hard module. Good teachers. "
        + PREFIX_SEMESTER + "Y2S2 ";

    public static final String MESSAGE_SUCCESS = "New module added based on NUS Mods data: %1$s";
    public static final String MESSAGE_DUPLICATE_PERSON = "This person already exists in the address book";

    private final Module toAdd;

    /**
     * Creates an AddCommand to add the specified {@code Person}
     */
    public AddAutoCommand(Module module) {
        requireNonNull(module);
        toAdd = module;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (model.hasPerson(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        }

        model.addPerson(toAdd);
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof AddAutoCommand // instanceof handles nulls
            && toAdd.equals(((AddAutoCommand) other).toAdd));
    }
}
