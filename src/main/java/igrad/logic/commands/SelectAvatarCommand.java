package igrad.logic.commands;

import static java.util.Objects.requireNonNull;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import igrad.logic.commands.exceptions.CommandException;
import igrad.model.Model;
import igrad.model.avatar.Avatar;

/**
 * Adds a module to the course book.
 */
public class SelectAvatarCommand extends Command {

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

        try {
            Path courseBookFilePath = Paths.get("data", "avatar.txt");
            FileWriter myWriter = new FileWriter(courseBookFilePath.toString());
            myWriter.write(toAdd.getName());
            myWriter.close();
            System.out.println("Successfully wrote to the file.");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof SelectAvatarCommand // instanceof handles nulls
            && toAdd.equals(((SelectAvatarCommand) other).toAdd));
    }
}
