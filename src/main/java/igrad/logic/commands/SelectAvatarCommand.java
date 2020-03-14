package igrad.logic.commands;

import static java.util.Objects.requireNonNull;

import igrad.logic.commands.exceptions.CommandException;
import igrad.model.Model;
import igrad.model.avatar.Avatar;

/**
 * Adds a module to the course book.
 */
public class SelectAvatarCommand extends Command {

    public final String MESSAGE_SUCCESS;

    private final Avatar toAdd;

    /**
     * Creates an ModuleAddCommand to add the specified {@code Person}
     */
    public SelectAvatarCommand(Avatar avatar) {
        requireNonNull(avatar);
        toAdd = avatar;

        String avatarName = avatar.getName();
        String capitalizeAvatarName = avatarName.substring(0, 1).toUpperCase() + avatarName.substring(1);
        MESSAGE_SUCCESS = "Hi, I'm " + capitalizeAvatarName + ", let's get started!";
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
