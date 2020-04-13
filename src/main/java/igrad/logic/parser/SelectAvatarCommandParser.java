package igrad.logic.parser;

import igrad.logic.commands.SelectAvatarCommand;
import igrad.logic.parser.exceptions.ParseException;
import igrad.model.avatar.Avatar;

//@@author nathanaelseen

/**
 * Parses input arguments and creates a new SelectAvatarCommand object
 */
public class SelectAvatarCommandParser implements Parser<SelectAvatarCommand> {
    /**
     * Parses the given {@code String} representing the Avatar name in the context of the SelectAvatarCommand
     * and returns an SelectAvatarCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public SelectAvatarCommand parse(String name) throws ParseException {
        Avatar avatar = ParserUtil.parseAvatarName(name);

        return new SelectAvatarCommand(avatar);
    }

}
