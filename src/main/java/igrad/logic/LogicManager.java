package igrad.logic;

import static igrad.commons.core.Messages.MESSAGE_COURSE_NOT_SET;
import static igrad.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.io.IOException;
import java.nio.file.Path;
import java.util.logging.Logger;

import igrad.commons.core.GuiSettings;
import igrad.commons.core.LogsCenter;
import igrad.logic.commands.Command;
import igrad.logic.commands.CommandResult;
import igrad.logic.commands.HelpCommand;
import igrad.logic.commands.SelectAvatarCommand;
import igrad.logic.commands.UndoCommand;
import igrad.logic.commands.course.CourseAddCommand;
import igrad.logic.commands.exceptions.CommandException;
import igrad.logic.parser.CourseBookParser;
import igrad.logic.parser.exceptions.ParseException;
import igrad.model.Model;
import igrad.model.ReadOnlyCourseBook;
import igrad.model.module.Module;
import igrad.model.requirement.Requirement;
import igrad.services.exceptions.ServiceException;
import igrad.storage.Storage;
import javafx.collections.ObservableList;

/**
 * The main LogicManager of the app.
 */
public class LogicManager implements Logic {
    public static final String FILE_OPS_ERROR_MESSAGE = "Could not save data to file: ";
    private final Logger logger = LogsCenter.getLogger(LogicManager.class);

    private final Model model;
    private final Storage storage;
    private final CourseBookParser courseBookParser;

    public LogicManager(Model model, Storage storage) {
        this.model = model;
        this.storage = storage;
        courseBookParser = new CourseBookParser();
    }

    @Override
    public CommandResult executeAvatar(String avatarName) throws ParseException, CommandException {
        CommandResult commandResult;


        SelectAvatarCommand selectAvatarCommand = courseBookParser.parseAvatarName(avatarName);
        commandResult = selectAvatarCommand.execute(model);

        try {
            // Saves to UserPref data file to save new Avatar, after successful Avatar command execution
            storage.saveUserPrefs(model.getUserPrefs());
        } catch (IOException ioe) {
            throw new CommandException(FILE_OPS_ERROR_MESSAGE + ioe, ioe);
        }

        return commandResult;
    }

    //@@author nathanaelseen
    @Override
    public CommandResult execute(String commandText) throws CommandException,
        ParseException, IOException, ServiceException {

        logger.info("----------------[USER COMMAND][" + commandText + "]");

        CommandResult commandResult;

        Command command = null;

        try {
            command = courseBookParser.parseCommand(commandText);
        } catch (ParseException pe) {
            if (!model.isCourseNameSet()
                    && !(pe.getMessage().equals(MESSAGE_UNKNOWN_COMMAND))) {
                /*
                 * If the command is not properly formmated, and it's a command
                 * which the system partially recognises, but the course (name) is not set,
                 * show the course not set error.
                 */
                throw new CommandException(MESSAGE_COURSE_NOT_SET);
            } else {
                /* There are 2 cases here:
                 * 1) If the command is not properly formmated, and it's a command
                 * which the system entirely does not recognises, and the course (name) is not set,
                 * show the help message (by propagating this exception).
                 *
                 * 2) If the command is not properly formatted, and its a command which the
                 * system partially recognises/entirely does not recognise, then just
                 * propagate this exception too (as exactly thrown).
                 */
                throw pe;
            }
        }

        /*
         * If the command is a properly formatted command, but the command is not and undo,
         * help, or course add command, and where the course (name) is not set, then we have
         * to prevent its execution here. If on the contrary, the course were not set,
         * but the command is properly formatted as undo, help or course add, then we still
         * allow it to execute.
         */
        if (!model.isCourseNameSet()
            && !(command instanceof CourseAddCommand || command instanceof UndoCommand
            || command instanceof HelpCommand)) {
            throw new CommandException(MESSAGE_COURSE_NOT_SET);
        }

        //@@author waynewee
        if (!(command instanceof UndoCommand)) {
            try {
                // First, load current state into backup
                Path backupCourseBookFilePath = model.getBackupCourseBookFilePath();
                storage.saveCourseBook(model.getCourseBook(), backupCourseBookFilePath);
            } catch (IOException ioe) {
                throw new CommandException(FILE_OPS_ERROR_MESSAGE + ioe, ioe);
            }
        }

        //@@author nathanaelseen

        commandResult = command.execute(model);

        try {
            // Saves to data file after every command
            storage.saveCourseBook(model.getCourseBook());
        } catch (IOException ioe) {
            throw new CommandException(FILE_OPS_ERROR_MESSAGE + ioe, ioe);
        }

        return commandResult;
    }

    @Override
    public ReadOnlyCourseBook getCourseBook() {
        return model.getCourseBook();
    }

    @Override
    public ObservableList<Module> getFilteredModuleList() {
        return model.getFilteredModuleList();
    }

    @Override
    public ObservableList<Requirement> getRequirementList() {
        return model.getRequirementList();
    }

    @Override
    public Path getCourseBookFilePath() {
        return model.getCourseBookFilePath();
    }

    @Override
    public GuiSettings getGuiSettings() {
        return model.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        model.setGuiSettings(guiSettings);
    }
}
