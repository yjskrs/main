package igrad.logic.commands;

import static java.util.Objects.requireNonNull;

import igrad.model.Model;
import igrad.model.avatar.Avatar;
import igrad.storage.AvatarStorage;

/**
 * Adds a module to the course book.
 */
public class SelectAvatarCommand extends Command {

    public final String MESSAGE_SUCCESS;
    public final String MESSAGE_ADD_COURSE = "Enter your course in the format: 'course add n/<NAME OF COURSE>'";

    private final Avatar toAdd;

    /**
     * Creates an SelectAvatarCommand for Avatar selection
     */
    public SelectAvatarCommand(Avatar avatar) {
        requireNonNull(avatar);
        toAdd = avatar;

        MESSAGE_SUCCESS = generateCapitalisedAvatarName();
    }

    private String generateCapitalisedAvatarName() {
        String avatarName = toAdd.getName();
        String capitalizeAvatarName = avatarName.substring(0, 1).toUpperCase() + avatarName.substring(1);

        String result = "Hi, I'm " + capitalizeAvatarName + ", let's get started!\n" + MESSAGE_ADD_COURSE;

        return result;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);

        model.setAvatar(toAdd);

        AvatarStorage.writeAvatar(toAdd);

        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof SelectAvatarCommand // instanceof handles nulls
            && toAdd.equals(((SelectAvatarCommand) other).toAdd));
    }
}
