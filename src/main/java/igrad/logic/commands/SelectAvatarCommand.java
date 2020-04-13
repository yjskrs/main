package igrad.logic.commands;

import static java.util.Objects.requireNonNull;

import igrad.commons.core.Messages;
import igrad.model.Model;
import igrad.model.avatar.Avatar;

/**
 * Adds a module to the course book.
 */
public class SelectAvatarCommand extends Command {

    public static final String MESSAGE_SUCCESS = "You've chosen a guide!";
    private final Avatar toAdd;

    /**
     * Creates an SelectAvatarCommand for Avatar selection
     */
    public SelectAvatarCommand(Avatar avatar) {
        requireNonNull(avatar);
        toAdd = avatar;
    }

    /**
     * Generates the actual success message for the command replacing the placeholders in MESSAGE_SUCCESS
     * with the actual Avatar name. (This method also capitalises the first letter of the Avatar name)
     *
     * @return String the (command) success message
     */
    private String generateSuccessMessage() {
        String avatarName = toAdd.getName();
        String capitaliseAvatarName = avatarName.substring(0, 1).toUpperCase() + avatarName.substring(1);

        return String.format(MESSAGE_SUCCESS + " " + Messages.MESSAGE_ADD_COURSE, capitaliseAvatarName);
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);

        model.setAvatar(toAdd);

        return new CommandResult(String.format(generateSuccessMessage(), toAdd));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof SelectAvatarCommand // instanceof handles nulls
            && toAdd.equals(((SelectAvatarCommand) other).toAdd));
    }
}
