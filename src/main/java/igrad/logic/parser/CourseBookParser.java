package igrad.logic.parser;

//@@teriaiw

import static igrad.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static igrad.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static igrad.commons.core.Messages.MESSAGE_UNKNOWN_COURSE_COMMAND;
import static igrad.commons.core.Messages.MESSAGE_UNKNOWN_MODULE_COMMAND;
import static igrad.commons.core.Messages.MESSAGE_UNKNOWN_REQUIREMENT_COMMAND;
import static igrad.logic.parser.CliSyntax.FLAG_AUTO;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import igrad.logic.commands.Command;
import igrad.logic.commands.ExitCommand;
import igrad.logic.commands.ExportCommand;
import igrad.logic.commands.HelpCommand;
import igrad.logic.commands.SelectAvatarCommand;
import igrad.logic.commands.UndoCommand;
import igrad.logic.commands.course.CourseAchieveCommand;
import igrad.logic.commands.course.CourseAddCommand;
import igrad.logic.commands.course.CourseCommand;
import igrad.logic.commands.course.CourseDeleteCommand;
import igrad.logic.commands.course.CourseEditCommand;
import igrad.logic.commands.module.ModuleAddCommand;
import igrad.logic.commands.module.ModuleCommand;
import igrad.logic.commands.module.ModuleDeleteCommand;
import igrad.logic.commands.module.ModuleDoneCommand;
import igrad.logic.commands.module.ModuleEditCommand;
import igrad.logic.commands.module.ModuleFilterCommand;
import igrad.logic.commands.requirement.RequirementAddCommand;
import igrad.logic.commands.requirement.RequirementAssignCommand;
import igrad.logic.commands.requirement.RequirementCommand;
import igrad.logic.commands.requirement.RequirementDeleteCommand;
import igrad.logic.commands.requirement.RequirementEditCommand;
import igrad.logic.commands.requirement.RequirementUnassignCommand;
import igrad.logic.parser.course.CourseAchieveCommandParser;
import igrad.logic.parser.course.CourseAddCommandParser;
import igrad.logic.parser.course.CourseEditCommandParser;
import igrad.logic.parser.exceptions.ParseException;
import igrad.logic.parser.module.ModuleAddAutoCommandParser;
import igrad.logic.parser.module.ModuleAddCommandParser;
import igrad.logic.parser.module.ModuleDeleteCommandParser;
import igrad.logic.parser.module.ModuleDoneCommandParser;
import igrad.logic.parser.module.ModuleEditCommandParser;
import igrad.logic.parser.module.ModuleFilterCommandParser;
import igrad.logic.parser.requirement.RequirementAddCommandParser;
import igrad.logic.parser.requirement.RequirementAssignCommandParser;
import igrad.logic.parser.requirement.RequirementDeleteCommandParser;
import igrad.logic.parser.requirement.RequirementEditCommandParser;
import igrad.logic.parser.requirement.RequirementUnassignCommandParser;
import igrad.services.exceptions.ServiceException;

/**
 * Parses user input.
 */
public class CourseBookParser {
    /**
     * Used for initial separation of command words and args.
     */
    private static final Pattern BASIC_COMMAND_FORMAT = Pattern.compile(
        "(?<commandWord>[a-z]+(\\s[a-z]{3,})?)(?<arguments>.*)");

    /**
     * Parses avatar name entered by user into {@code SelectAvatarCommand} for execution.
     *
     * @param avatarName full user input string (consisting the avatarName)
     * @return the command based on the user input
     * @throws ParseException if the user input does not conform the expected format
     */
    public SelectAvatarCommand parseAvatarName(String avatarName) throws ParseException {
        SelectAvatarCommandParser selectAvatarCommandParser = new SelectAvatarCommandParser();
        SelectAvatarCommand selectAvatarCommand = selectAvatarCommandParser.parse(avatarName);

        return selectAvatarCommand;

    }

    /**
     * Parses user input into command for execution.
     *
     * @param userInput full user input string
     * @return the command based on the user input
     * @throws ParseException if the user input does not conform the expected format
     */
    public Command parseCommand(String userInput) throws ParseException, IOException, ServiceException {
        final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(userInput.trim());
        if (!matcher.matches()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        }

        final String commandWord = matcher.group("commandWord");
        final String argumentsWithFlags = matcher.group("arguments");
        final String arguments = ArgumentTokenizer.removeFlags(argumentsWithFlags);

        switch (commandWord) {

        /*
         * If there is only one command word provided instead of the supposed two-word commands, flag an error
         * to feedback to the user.
         */
        case CourseCommand.COURSE_COMMAND_WORD:
            throw new ParseException(MESSAGE_UNKNOWN_COURSE_COMMAND);

        case RequirementCommand.REQUIREMENT_COMMAND_WORD:
            throw new ParseException(MESSAGE_UNKNOWN_REQUIREMENT_COMMAND);

        case ModuleCommand.MODULE_COMMAND_WORD:
            throw new ParseException(MESSAGE_UNKNOWN_MODULE_COMMAND);

        /*
         * Process the command if it matches its command word.
         */
        case CourseAddCommand.COURSE_ADD_COMMAND_WORD:
            return new CourseAddCommandParser().parse(arguments);

        case CourseEditCommand.COURSE_EDIT_COMMAND_WORD:
            return new CourseEditCommandParser().parse(arguments);

        case CourseDeleteCommand.COURSE_DELETE_COMMAND_WORD:
            return new CourseDeleteCommand();

        case CourseAchieveCommand.COURSE_ACHIEVE_COMMAND_WORD:
            return new CourseAchieveCommandParser().parse(arguments);

        case RequirementAddCommand.REQUIREMENT_ADD_COMMAND_WORD:
            return new RequirementAddCommandParser().parse(arguments);

        case RequirementEditCommand.REQUIREMENT_EDIT_COMMAND_WORD:
            return new RequirementEditCommandParser().parse(arguments);

        case RequirementDeleteCommand.REQUIREMENT_DELETE_COMMAND_WORD:
            return new RequirementDeleteCommandParser().parse(arguments);

        case RequirementAssignCommand.REQUIREMENT_ASSIGN_COMMAND_WORD:
            return new RequirementAssignCommandParser().parse(arguments);

        case RequirementUnassignCommand.REQUIREMENT_UNASSIGN_COMMAND_WORD:
            return new RequirementUnassignCommandParser().parse(arguments);

        case ModuleAddCommand.MODULE_ADD_COMMAND_WORD:

            if (ArgumentTokenizer.isFlagPresent(argumentsWithFlags, FLAG_AUTO.getFlag())) {
                return new ModuleAddAutoCommandParser().parse(arguments);
            } else {
                return new ModuleAddCommandParser().parse(arguments);
            }

        case ModuleEditCommand.MODULE_EDIT_COMMAND_WORD:
            return new ModuleEditCommandParser().parse(arguments);

        case ModuleDeleteCommand.MODULE_DELETE_COMMAND_WORD:
            return new ModuleDeleteCommandParser().parse(arguments);

        case ModuleDoneCommand.MODULE_DONE_COMMAND_WORD:
            return new ModuleDoneCommandParser().parse(arguments);

        case ModuleFilterCommand.MODULE_FILTER_COMMAND_WORD:
            return new ModuleFilterCommandParser().parse(argumentsWithFlags);

        case ExitCommand.COMMAND_WORD:
            return new ExitCommand();

        case HelpCommand.COMMAND_WORD:
            return new HelpCommand();

        case ExportCommand.COMMAND_WORD:
            return new ExportCommand();

        case UndoCommand.COMMAND_WORD:
            return new UndoCommand();

        default:
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }
    }

}
