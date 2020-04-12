package igrad.ui;

import java.io.IOException;
import java.util.logging.Logger;

import igrad.commons.core.GuiSettings;
import igrad.commons.core.LogsCenter;
import igrad.commons.core.Messages;
import igrad.logic.Logic;
import igrad.logic.commands.CommandResult;
import igrad.logic.commands.exceptions.CommandException;
import igrad.logic.parser.exceptions.ParseException;
import igrad.model.Model;
import igrad.model.avatar.Avatar;
import igrad.services.exceptions.ServiceException;
import javafx.fxml.FXML;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
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
    private StackPane requirementListPanelPlaceholder;

    // Independent Ui parts residing in this Ui container
    private AvatarSelectionPanel avatarSelectionPanel;
    private ModuleListPanel moduleListPanel;
    private RequirementListPanel requirementListPanel;
    private ResultDisplay resultDisplay;
    private HelpWindow helpWindow;
    private ProgressSidePanel progressSidePanel;
    private CommandReceivedPanel commandReceivedPanel;

    @FXML
    private StackPane commandBoxPlaceholder;

    @FXML
    private VBox moduleList;

    @FXML
    private VBox requirementList;

    @FXML
    private VBox progressPanelPlaceholder;

    @FXML
    private VBox commandReceivedPanelPlaceholder;

    @FXML
    private StackPane resultDisplayPlaceholder;

    @FXML
    private StackPane statusBarPlaceholder;

    @FXML
    private StackPane modularCreditsPanelPlaceholder;

    @FXML
    private HBox mainContainer;

    @FXML
    private Label creditsCount;

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
     * Sets the accelerator of a MenuItem.
     *
     * @param keyCombination the KeyCombination value of the accelerator
     *//*
    private void setAccelerator(MenuItem menuItem, KeyCombination keyCombination) {
        menuItem.setAccelerator(keyCombination);

        *//*
     * TODO: the code below can be removed once the bug reported here
     * https://bugs.openjdk.java.net/browse/JDK-8131666
     * is fixed in later version of SDK.
     *
     * According to the bug report, TextInputControl (TextField, TextArea) will
     * consume function-key events. Because CommandBox contains a TextField, and
     * ResultDisplay contains a TextArea, thus some accelerators (e.g F1) will
     * not work when the focus is in them because the key event is consumed by
     * the TextInputControl(s).
     *
     * For now, we add following event filter to capture such key events and open
     * help window purposely so to support accelerators even when focus is
     * in CommandBox or ResultDisplay.
     *//*
        getRoot().addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            if (event.getTarget() instanceof TextInputControl && keyCombination.match(event)) {
                menuItem.getOnAction().handle(new ActionEvent());
                event.consume();
            }
        });
    }*/

    /**
     * Fills and displays the window of all the {@code Avatar} placeholders, when in the
     * {@code Avatar} selection state.
     */
    void displayAvatarSelectionPanel(Model model) {
        avatarSelectionPanelPlaceholder = new StackPane();

        mainContainer.getChildren().removeAll(moduleList, requirementList);
        mainContainer.getChildren().add(avatarSelectionPanelPlaceholder);

        logger.info("Avatar not found. Displaying avatar selection screen instead.");
        avatarSelectionPanel = new AvatarSelectionPanel();
        avatarSelectionPanelPlaceholder.getChildren().add(avatarSelectionPanel.getRoot());

        commandReceivedPanel = new CommandReceivedPanel();

        resultDisplay = new ResultDisplay(model.getAvatar());
        resultDisplayPlaceholder.getChildren().add(resultDisplay.getRoot());
        resultDisplay.setFeedbackToUser("Choose an animal guide by entering the NAME of the animal");

        displayCommandBox(model);
    }

    /**
     * Fills up and displays/refreshes all the panels in the module management state.
     */
    void displayMainPanel(Model model) {

        mainContainer.getChildren().remove(avatarSelectionPanelPlaceholder);

        moduleListPanelPlaceholder = new StackPane();
        requirementListPanelPlaceholder = new StackPane();

        moduleList.getChildren().add(moduleListPanelPlaceholder);
        requirementList.getChildren().add(requirementListPanelPlaceholder);

        if (!mainContainer.getChildren().contains(requirementList)) {
            mainContainer.getChildren().add(requirementList);
        }

        if (!mainContainer.getChildren().contains(moduleList)) {
            mainContainer.getChildren().add(moduleList);
        }

        moduleListPanel = new ModuleListPanel(logic.getFilteredModuleList());
        moduleListPanelPlaceholder.getChildren().add(moduleListPanel.getRoot());
        requirementListPanel = new RequirementListPanel(logic.getRequirementList());
        requirementListPanelPlaceholder.getChildren().add(requirementListPanel.getRoot());

        commandReceivedPanel = new CommandReceivedPanel();
        commandReceivedPanelPlaceholder.getChildren().add(commandReceivedPanel.getRoot());

        moduleListPanelPlaceholder.setPrefHeight(Integer.MAX_VALUE);
        requirementListPanelPlaceholder.setPrefHeight(Integer.MAX_VALUE);

        resultDisplay = new ResultDisplay(model.getAvatar());
        resultDisplayPlaceholder.getChildren().add(resultDisplay.getRoot());

        if (!model.isCourseNameSet()) {
            resultDisplay.setFeedbackToUser(Messages.MESSAGE_ADD_COURSE);
        } else {
            resultDisplay.setFeedbackToUser(Messages.MESSAGE_WELCOME_BACK);
        }

        displayCommandBox(model);
        displayProgressPanel(model);
    }

    // @@author dargohzy

    /**
     * Fills up and displays/refreshes the the placeholders of the side panels (Modular credits info, CAP info).
     */
    void displayProgressPanel(Model model) {
        progressSidePanel = new ProgressSidePanel(model);
        progressPanelPlaceholder.getChildren().add(progressSidePanel.getRoot());
    }

    /**
     * Refreshes the result display (UI component) with information from {@code CommandResult}.
     */
    void refreshResultDisplay(CommandResult commandResult) {
        resultDisplay.setFeedbackToUser(commandResult.getFeedbackToUser());
    }

    /**
     * Refreshes the result display (UI component) to reflect an error from error String.
     */
    void refreshResultDisplayError(String errorMessage) {
        resultDisplay.setFeedbackToUser(errorMessage);
    }

    // @@author dargohzy

    /**
     * Refreshes the avatar expression on the result display (UI component).
     */
    void refreshResultDisplayAvatar(Avatar avatar) {
        resultDisplay.setAvatar(avatar);
    }

    /**
     * Gets the avatar with the appropriate expression to the success of the command.
     */
    Avatar getAvatar(Model model, boolean isSuccessful) {
        if (isSuccessful) {
            return model.getAvatar();
        } else {
            Avatar currentAvatar = model.getAvatar();
            Avatar sadAvatar = new Avatar(currentAvatar.getName() + "-sad");
            return sadAvatar;
        }
    }

    /**
     * Refreshes the last command received in the last command received panel.
     */
    private void refreshCommandReceived(String command) {
        commandReceivedPanel.setCommandReceived(command);
    }

    /**
     * Sets the progress panel on startup.
     */
    void refreshProgressPanel(Model model) {
        progressSidePanel.updateProgress(model);
    }

    /**
     * Fills up and displays/refreshes the placeholder of the command box.
     */
    void displayCommandBox(Model model) {
        CommandBox commandBox = new CommandBox(c -> executeCommand(c, model));
        commandBoxPlaceholder.getChildren().add(commandBox.getRoot());
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
        Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();
        primaryStage.setMaxHeight(primaryScreenBounds.getHeight());
        primaryStage.show();
    }

    /**
     * Displays the sad (loading) version of the avatar when loading
     */
    private void handleStartLoading(Avatar avatar) {
        Avatar sadAvatar = new Avatar(avatar.getName() + "-sad");
        resultDisplay.setAvatar(sadAvatar);
    }

    private void handleStopLoading(Avatar avatar) {
        resultDisplay.setAvatar(avatar);
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

    //@@author nathanaelseen

    /**
     * Executes the command and returns the result.
     *
     * @see Logic#execute(String)
     */
    private CommandResult executeCommand(String commandText, Model model) throws CommandException,
        ParseException,
        IOException,
        ServiceException {

        refreshCommandReceived(commandText);

        try {
            CommandResult commandResult;

            boolean isSelectingAvatar = model.isSampleAvatar();

            if (isSelectingAvatar) {
                // If user has not selected avatar, get her to do so.
                commandResult = logic.executeAvatar(commandText);

                // Now we've already selected Avatar, remove Avatar selection panel to display the Main module panel
                displayMainPanel(model);
            } else {
                // Else, let user execute commands normally.
                commandResult = logic.execute(commandText);
            }

            logger.info("Result: " + commandResult.getFeedbackToUser());

            Avatar avatar = getAvatar(model, true);

            refreshResultDisplayAvatar(avatar);
            refreshResultDisplay(commandResult);
            refreshProgressPanel(model);

            if (commandResult.isShowHelp()) {
                handleHelp();
            } else if (commandResult.isExit()) {
                handleExit();
            }


            return commandResult;
        } catch (CommandException | ParseException | IOException | ServiceException e) {
            logger.info("Invalid command: " + commandText);

            Avatar avatar = getAvatar(model, false);

            refreshResultDisplayAvatar(avatar);
            refreshResultDisplayError(e.getMessage());
            throw e;
        }

    }
}
