package igrad.ui;

import java.io.IOException;
import java.util.logging.Logger;

import igrad.commons.core.GuiSettings;
import igrad.commons.core.LogsCenter;
import igrad.logic.Logic;
import igrad.logic.commands.CommandResult;
import igrad.logic.commands.exceptions.CommandException;
import igrad.logic.parser.exceptions.ParseException;
import igrad.model.avatar.Avatar;
import igrad.services.exceptions.ServiceException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextInputControl;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;
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

    // Independent Ui parts residing in this Ui container
    private AvatarSelectionPanel avatarSelectionPanel;
    private ModuleListPanel moduleListPanel;
    private ResultDisplay resultDisplay;
    private HelpWindow helpWindow;

    @FXML
    private StackPane commandBoxPlaceholder;

/*    @FXML
    private MenuItem helpMenuItem;*/

/*    @FXML
    private StackPane statusbarPlaceholder;*/

    @FXML
    private StackPane avatarSelectionPanelPlaceholder;

    @FXML
    private StackPane moduleListPanelPlaceholder;

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

/*        setAccelerators();*/

        helpWindow = new HelpWindow();
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

/*    private void setAccelerators() {
        setAccelerator(KeyCombination.valueOf("F1"));
    }

    *//**
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
     * Fills up all the placeholders of this window.
     */
    void fillInnerParts(Avatar avatar) {

        if( avatar.isPlaceholder ){
            logger.info("Avatar not found. Displaying avatar selection screen instead.");
            avatarSelectionPanel = new AvatarSelectionPanel();
            avatarSelectionPanelPlaceholder.getChildren().add(avatarSelectionPanel.getRoot());
        } else {
            moduleListPanel = new ModuleListPanel(logic.getFilteredModuleList());
            moduleListPanelPlaceholder.getChildren().add(moduleListPanel.getRoot());
        }

        resultDisplay = new ResultDisplay(avatar);
        resultDisplayPlaceholder.getChildren().add(resultDisplay.getRoot());

        if( avatar.isPlaceholder ){
            resultDisplay.setFeedbackToUser("Choose an animal guide by entering the NAME of the animal");
        }

        StatusBar statusBar2 = new StatusBar();
        statusBar.getChildren().add(statusBar2.getPane());

        CommandBox commandBox = new CommandBox(c -> executeCommand(c, avatar));
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
    private CommandResult executeCommand(String commandText, Avatar avatar ) throws CommandException,
        ParseException,
        IOException,
        ServiceException {
        try {
            System.out.println(commandText);
            CommandResult commandResult = logic.execute(commandText);
            System.out.println(commandResult.getFeedbackToUser());
            logger.info("Result: " + commandResult.getFeedbackToUser());
            resultDisplay.setFeedbackToUser(commandResult.getFeedbackToUser());

            if( avatar.isPlaceholder ){
                Avatar selectedAvatar = new Avatar("/avatars/" + commandText + ".png" );
                resultDisplay.setAvatar(selectedAvatar);
            }

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
