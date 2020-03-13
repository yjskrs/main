package igrad.logic.parser;

import static igrad.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static igrad.logic.parser.CliSyntax.PREFIX_CREDITS;
import static igrad.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static igrad.logic.parser.CliSyntax.PREFIX_MEMO;
import static igrad.logic.parser.CliSyntax.PREFIX_MODULE_CODE;
import static igrad.logic.parser.CliSyntax.PREFIX_SEMESTER;
import static igrad.logic.parser.CliSyntax.PREFIX_TAG;
import static igrad.logic.parser.CliSyntax.PREFIX_TITLE;
import java.io.IOException;
import java.util.Set;
import java.util.stream.Stream;
import igrad.logic.commands.ModuleAddAutoCommand;
import igrad.logic.commands.SelectAvatarCommand;
import igrad.logic.parser.exceptions.ParseException;
import igrad.model.avatar.Avatar;
import igrad.model.module.Credits;
import igrad.model.module.Description;
import igrad.model.module.Memo;
import igrad.model.module.Module;
import igrad.model.module.ModuleCode;
import igrad.model.module.Semester;
import igrad.model.module.Title;
import igrad.model.tag.Tag;
import igrad.services.JsonParsedModule;
import igrad.services.NusModsRequester;
import igrad.services.exceptions.ServiceException;

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
    public SelectAvatarCommand parse(String imgPath){

        Avatar avatar = new Avatar(imgPath);

        return new SelectAvatarCommand(avatar);
    }

}
