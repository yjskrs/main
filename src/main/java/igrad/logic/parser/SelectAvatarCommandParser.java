package igrad.logic.parser;

import igrad.logic.commands.SelectAvatarCommand;
import igrad.logic.parser.exceptions.ParseException;
import igrad.model.avatar.Avatar;

/**
 * Parses input arguments and creates a new ModuleAddCommand object
 */
public class SelectAvatarCommandParser implements Parser<SelectAvatarCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the ModuleAddCommand
     * and returns an ModuleAddCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public SelectAvatarCommand parse(String name) {

        Avatar avatar = new Avatar(name);

        return new SelectAvatarCommand(avatar);
    }

}
