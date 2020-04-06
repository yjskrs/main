package igrad.logic;

import static igrad.commons.core.Messages.MESSAGE_COURSE_NOT_SET;

import java.io.IOException;
import java.nio.file.Path;
import java.util.logging.Logger;

import igrad.commons.core.GuiSettings;
import igrad.commons.core.LogsCenter;
import igrad.logic.commands.Command;
import igrad.logic.commands.CommandResult;
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

    @Override
    public CommandResult execute(String commandText) throws CommandException,
        ParseException, IOException, ServiceException {

        logger.info("----------------[USER COMMAND][" + commandText + "]");

        CommandResult commandResult;
        Command command = courseBookParser.parseCommand(commandText);

        /*
         * If user has not selected her course name, and she is trying to execute any other
         * command except the 'course add', prevent her from doing so.
         *
         * With the addition of the 'undo' command, there is a slight catch to this.
         *
         * Suppose the situation where the user has done a 'course delete' command
         * and thus the course name is not set (as all the data in the system has been reverted to
         * a blank state), we must still allow the undo command.
         *
         * However, if indeed, the user hasn't initially set a course, and undo is still entered,
         * we are unable to distinguish the first case from this case.
         *
         * Hence, in addition to allowing only the 'course add' command, when a course name is
         * not set, we allow the undo command too.
         *
         * In the second case, where there is indeed nothing to undo, and we still 'undo',
         * the 'undo' command would be able to handle this error and gracefully flag
         * and error message to the user.
         */
        if (!model.isCourseNameSet()
                && !(command instanceof CourseAddCommand || command instanceof UndoCommand)) {
            throw new CommandException(MESSAGE_COURSE_NOT_SET);
        }

        if (!(command instanceof UndoCommand)) {
            try {
                // First, load current state into backup
                storage.saveBackupCourseBook(model.getCourseBook());
            } catch (IOException ioe) {
                throw new CommandException(FILE_OPS_ERROR_MESSAGE + ioe, ioe);
            }
        }

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
