package igrad.ui;

import java.io.IOException;
import java.util.logging.Logger;
import igrad.commons.core.GuiSettings;
import igrad.commons.core.LogsCenter;
import igrad.logic.Logic;
import igrad.logic.commands.CommandResult;
import igrad.logic.commands.SelectAvatarCommand;
import igrad.logic.commands.exceptions.CommandException;
import igrad.logic.parser.SelectAvatarCommandParser;
import igrad.logic.parser.exceptions.ParseException;
import igrad.model.Model;
import igrad.model.avatar.Avatar;
import igrad.services.exceptions.ServiceException;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * The Main Window. Provides the basic application layout containing
 * a menu bar and space where other JavaFX elements can be placed.
 */
public class MainWindow extends UiPart<Stage> {

    private static final String FXML = "MainWindow.fxml";

    private final Logger logger = LogsCenter.getLogger(getClass());

    private Stage primaryStage;
    private Logic logic;
    private StackPane avatarSelectionPanelPlaceholder;
    private StackPane moduleListPanelPlaceholder;

    // Independent Ui parts residing in this Ui container
    private AvatarSelectionPanel avatarSelectionPanel;
    private ModuleListPanel moduleListPanel;
    private ResultDisplay resultDisplay;
    private HelpWindow helpWindow;

    private boolean isAvatarSet;

    @FXML
    private StackPane commandBoxPlaceholder;

    @FXML
    private VBox moduleList;

    @FXML
    private StackPane resultDisplayPlaceholder;

    @FXML
    private StackPane statusBar;

    @FXML
    private StackPane modularCreditsPanelPlaceholder;

    @FXML
    private Label mcCount;


    public MainWindow(Stage primaryStage, Logic logic) {
        super(FXML, primaryStage);

        // Set dependencies
        this.primaryStage = primaryStage;
        this.logic = logic;

        // Configure the UI
        setWindowDefaultSize(logic.getGuiSettings());

        helpWindow = new HelpWindow();
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    /**
     * Displays the window when in the {@code Avatar} selection state
     */
    void displayAvatarSelection(Model model) {
        avatarSelectionPanelPlaceholder = new StackPane();

        moduleList.getChildren().add(avatarSelectionPanelPlaceholder);

        logger.info("Avatar not found. Displaying avatar selection screen instead.");
        avatarSelectionPanel = new AvatarSelectionPanel();
        avatarSelectionPanelPlaceholder.getChildren().add(avatarSelectionPanel.getRoot());

        resultDisplay = new ResultDisplay(model.getAvatar());
        resultDisplayPlaceholder.getChildren().add(resultDisplay.getRoot());
        resultDisplay.setFeedbackToUser("Choose an animal guide by entering the NAME of the animal");

        displayStatusPanels(model);
    }

    /**
     * Fills up all the placeholders of this window.
     */
    void displayModulePanel(Model model) {
        moduleList.getChildren().remove(avatarSelectionPanelPlaceholder);

        moduleListPanelPlaceholder = new StackPane();

        moduleList.getChildren().add(moduleListPanelPlaceholder);

        moduleListPanel = new ModuleListPanel(logic.getFilteredModuleList());
        moduleListPanelPlaceholder.getChildren().add(moduleListPanel.getRoot());

        moduleListPanelPlaceholder.setPrefHeight(2000.0);

        resultDisplay = new ResultDisplay(model.getAvatar());
        resultDisplayPlaceholder.getChildren().add(resultDisplay.getRoot());

        displayStatusPanels(model);
    }

    /**
     * Displays the window when in the module management state
     */
    void displayStatusPanels(Model model) {
        StatusBar statusBar2 = new StatusBar();
        statusBar.getChildren().add(statusBar2.getPane());

        CommandBox commandBox = new CommandBox(c -> executeCommand(c, model));
        commandBoxPlaceholder.getChildren().add(commandBox.getRoot());

        mcCount.setText("MCs:\n40/160");
    }

    /**
     * Sets the default size based on {@code guiSettings}.
     */
    private void setWindowDefaultSize(GuiSettings guiSettings) {
        primaryStage.setHeight(guiSettings.getWindowHeight());
        primaryStage.setWidth(guiSettings.getWindowWidth());
        if (guiSettings.getWindowCoordinates() != null) {
            primaryStage.setX(guiSettings.getWindowCoordinates().getX());
            primaryStage.setY(guiSettings.getWindowCoordinates().getY());
        }
    }

    /**
     * Opens the help window or focuses on it if it's already opened.
     */
    @FXML
    public void handleHelp() {
        if (!helpWindow.isShowing()) {
            helpWindow.show();
        } else {
            helpWindow.focus();
        }
    }

    void show() {
        primaryStage.show();
    }

    /**
     * Closes the application.
     */
    @FXML
    private void handleExit() {
        GuiSettings guiSettings = new GuiSettings(primaryStage.getWidth(), primaryStage.getHeight(),
            (int) primaryStage.getX(), (int) primaryStage.getY());
        logic.setGuiSettings(guiSettings);
        helpWindow.hide();
        primaryStage.hide();
    }

    public ModuleListPanel getModuleListPanel() {
        return moduleListPanel;
    }

    /**
     * Executes the command and returns the result.
     *
     * @see Logic#execute(String)
     */
    private CommandResult executeCommand(String commandText, Model model) throws CommandException,
        ParseException,
        IOException,
        ServiceException {

        try {

            boolean isSelectingAvatar = model.getAvatar().getIsSample();

            if (isSelectingAvatar) {

                Avatar selectedAvatar = new Avatar(commandText);
                resultDisplay.setAvatar(selectedAvatar);

                model.setAvatar(selectedAvatar);

                this.displayModulePanel(model);
            }

            CommandResult commandResult;

            if (isSelectingAvatar) {
                SelectAvatarCommand selectAvatarCommand = new SelectAvatarCommandParser().parse(commandText);
                commandResult = selectAvatarCommand.execute(model);
            } else {
                commandResult = logic.execute(commandText);
            }

            logger.info("Result: " + commandResult.getFeedbackToUser());
            resultDisplay.setFeedbackToUser(commandResult.getFeedbackToUser());

            if (commandResult.isShowHelp()) {
                handleHelp();
            }

            if (commandResult.isExit()) {
                handleExit();
            }

            System.out.println("PRINTED" + commandResult);
            return commandResult;
        } catch (CommandException | ParseException | IOException | ServiceException e) {
            logger.info("Invalid command: " + commandText);
            resultDisplay.setFeedbackToUser(e.getMessage());
            throw e;
        }
    }
}
