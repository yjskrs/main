package igrad.logic;

import java.io.IOException;
import java.nio.file.Path;
import java.util.logging.Logger;

import igrad.commons.core.GuiSettings;
import igrad.commons.core.LogsCenter;
import igrad.logic.commands.Command;
import igrad.logic.commands.CommandResult;
import igrad.logic.commands.SelectAvatarCommand;
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
    public CommandResult executeAvatar(String avatarName) throws ParseException {

        CommandResult commandResult;


        SelectAvatarCommand selectAvatarCommand = courseBookParser.parseAvatarName(avatarName);
        commandResult = selectAvatarCommand.execute(model);

        return commandResult;
    }

    @Override
    public CommandResult execute(String commandText) throws CommandException,
        ParseException, IOException, ServiceException {
        logger.info("----------------[USER COMMAND][" + commandText + "]");

        CommandResult commandResult;
        Command command = courseBookParser.parseCommand(commandText);
        commandResult = command.execute(model);

        try {
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
