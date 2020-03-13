package igrad.logic.commands;

import static igrad.logic.parser.CliSyntax.PREFIX_CREDITS;
import static igrad.logic.parser.CliSyntax.PREFIX_MEMO;
import static igrad.logic.parser.CliSyntax.PREFIX_MODULE_CODE;
import static igrad.logic.parser.CliSyntax.PREFIX_SEMESTER;
import static igrad.logic.parser.CliSyntax.PREFIX_TITLE;
import static java.util.Objects.requireNonNull;
import igrad.logic.commands.exceptions.CommandException;
import igrad.model.Model;
import igrad.model.avatar.Avatar;
import igrad.model.module.Module;

/**
 * Adds a module to the course book.
 */
public class SelectAvatarCommand extends Command{

    public static final String MESSAGE_SUCCESS = "You have chosen an animal guide!";

    private final Avatar toAdd;

    /**
     * Creates an ModuleAddCommand to add the specified {@code Person}
     */
    public SelectAvatarCommand(Avatar avatar) {
        requireNonNull(avatar);
        toAdd = avatar;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        model.setAvatar(toAdd);
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof SelectAvatarCommand // instanceof handles nulls
            && toAdd.equals(((SelectAvatarCommand) other).toAdd));
    }
}
