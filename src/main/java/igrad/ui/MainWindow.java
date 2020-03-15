package igrad.ui;

import java.io.IOException;
import java.util.logging.Logger;

import igrad.commons.core.GuiSettings;
import igrad.commons.core.LogsCenter;
import igrad.logic.Logic;
import igrad.logic.commands.CommandResult;
import igrad.logic.commands.exceptions.CommandException;
import igrad.logic.parser.exceptions.ParseException;
import igrad.model.Model;
import igrad.model.avatar.Avatar;
import igrad.services.exceptions.ServiceException;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
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
    private HBox sidePanelPlaceholder;

    @FXML
    private VBox capPanelPlaceholder;

    @FXML
    private StackPane resultDisplayPlaceholder;

    @FXML
    private StackPane statusBarPlaceholder;

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

        moduleList.getChildren().add(avatarSelectionPanelPlaceholder);

        logger.info("Avatar not found. Displaying avatar selection screen instead.");
        avatarSelectionPanel = new AvatarSelectionPanel();
        avatarSelectionPanelPlaceholder.getChildren().add(avatarSelectionPanel.getRoot());

        resultDisplay = new ResultDisplay(model.getAvatar());
        resultDisplayPlaceholder.getChildren().add(resultDisplay.getRoot());
        resultDisplay.setFeedbackToUser("Choose an animal guide by entering the NAME of the animal");

        displayCommandBox(model);
    }

    /**
     * Fills up and displays the window of all module placeholders, when in the module management state.
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

        displayStatusBar(model);
        displayCommandBox(model);
        displaySidePanels(model);
    }

    /**
     * Fills up and displays the the placeholders of the side panels (Modular credits info, CAP info).
     */
    void displaySidePanels(Model model) {
        McSidePanel mcSidePanel = new McSidePanel();
        sidePanelPlaceholder.getChildren().add(mcSidePanel.getRoot());

        CapPanel capPanel = new CapPanel();
        capPanelPlaceholder.getChildren().add(capPanel.getRoot());
    }

    /**
     * Fills up and displays the placeholder of the status bar.
     */
    void displayStatusBar(Model model) {
        StatusBar statusBar = new StatusBar();
        statusBarPlaceholder.getChildren().add(statusBar.getPane());
    }

    /**
     * Fills up and displays the placeholder of the command box.
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
            CommandResult commandResult;

            boolean isSelectingAvatar = model.getAvatar().getIsSample();

            if (isSelectingAvatar) {
                commandResult = logic.executeAvatar(commandText);

                Avatar selectedAvatar = new Avatar(commandText);
                resultDisplay.setAvatar(selectedAvatar);

                model.setAvatar(selectedAvatar);

                this.displayModulePanel(model);
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

            return commandResult;
        } catch (CommandException | ParseException | IOException | ServiceException e) {
            logger.info("Invalid command: " + commandText);
            resultDisplay.setFeedbackToUser(e.getMessage());
            throw e;
        }
    }
}
