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
import igrad.model.course.CourseInfo;
import igrad.model.requirement.Requirement;
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
    private StackPane requirementListPanelPlaceholder;

    // Independent Ui parts residing in this Ui container
    private AvatarSelectionPanel avatarSelectionPanel;
    private ModuleListPanel moduleListPanel;
    private RequirementListPanel requirementListPanel;
    private ResultDisplay resultDisplay;
    private HelpWindow helpWindow;
    private StatusBar statusBar;
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

        commandReceivedPanel = new CommandReceivedPanel();

        resultDisplay = new ResultDisplay(model.getAvatar());
        resultDisplayPlaceholder.getChildren().add(resultDisplay.getRoot());
        resultDisplay.setFeedbackToUser("Choose an animal guide by entering the NAME of the animal");

        displayCommandBox(model);
    }

    /**
     * Fills up and displays/refreshes the window of all module placeholders, when in the module management state.
     */
    void displayModulePanel(Model model) {

        moduleList.getChildren().remove(avatarSelectionPanelPlaceholder);

        moduleListPanelPlaceholder = new StackPane();
        requirementListPanelPlaceholder = new StackPane();

        moduleList.getChildren().add(moduleListPanelPlaceholder);
        requirementList.getChildren().add(requirementListPanelPlaceholder);

        moduleListPanel = new ModuleListPanel(logic.getFilteredModuleList());
        moduleListPanelPlaceholder.getChildren().add(moduleListPanel.getRoot());
        requirementListPanel = new RequirementListPanel(logic.getRequirementList());
        requirementListPanelPlaceholder.getChildren().add(requirementListPanel.getRoot());

        commandReceivedPanel = new CommandReceivedPanel();
        commandReceivedPanelPlaceholder.getChildren().add(commandReceivedPanel.getRoot());

        moduleListPanelPlaceholder.setPrefHeight(2000.0);
        requirementListPanelPlaceholder.setPrefHeight(2000.0);

        resultDisplay = new ResultDisplay(model.getAvatar());
        resultDisplayPlaceholder.getChildren().add(resultDisplay.getRoot());

        displayStatusBar(model);
        refreshStatusBar(model);
        displayCommandBox(model);
        displaySidePanels(model);
    }

    /**
     * Fills up and displays/refreshes the the placeholders of the side panels (Modular credits info, CAP info).
     */
    void displaySidePanels(Model model) {
        progressSidePanel = new ProgressSidePanel();
        refreshProgressPanel(model);
        progressPanelPlaceholder.getChildren().add(progressSidePanel.getRoot());
    }

    /**
     * Fills up and displays the placeholder of the status bar.
     */
    void displayStatusBar(Model model) {
        statusBar = new StatusBar();
        statusBarPlaceholder.getChildren().add(statusBar.getPane());
    }

    /**
     * Refreshes the status bar (UI component) with information from the {@code Model}.
     */
    void refreshStatusBar(Model model) {
        // Extract the updated CourseInfo from our model.
        CourseInfo courseInfo = model.getCourseInfo();

        logger.fine("courseInfo.getName = " + courseInfo.getName().toString());
        // Refresh the status bar now, with the updated course name.
        courseInfo.getName().ifPresentOrElse(
            x -> statusBar.setCourseName(x.toString()), () -> statusBar.setCourseName(""));
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

    /**
     * Sets the progress panel on startup.
     */
    void refreshProgressPanel(Model model) {
        int totalMcs = 0;
        int totalModules = 0;
        for (Requirement req: model.getRequirementList()) {
            totalMcs += Integer.parseInt(req.getCreditsRequired());
            totalModules += req.getModuleList().size();
        }
        int totalRequirements = model.getRequirementList().size();

        progressSidePanel.setTotalMcs(totalMcs);
        progressSidePanel.setTotalModules(totalModules);
        progressSidePanel.setTotalRequirements(totalRequirements);
        progressSidePanel.setTotalSemesters(totalMcs);
        progressSidePanel.updateProgress();
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
     * Sets the last command received in the command received panel.
     */
    private void setCommandReceived(String command) {
        commandReceivedPanel.setCommandReceived(command);
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
     * Displays the sad (loading) version of the avatar when loading
     */
    private void handleLoading(Avatar avatar) {

        Avatar sadAvatar = new Avatar(avatar.getName() + "-sad");

        resultDisplay.setAvatar(sadAvatar);
    }

    private void handleStopLoading(Avatar avatar) {
        resultDisplay.setAvatar(avatar);
    }

    @FXML
    private void handleStopLoading() {

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

        handleLoading(model.getAvatar());

        setCommandReceived(commandText);

        try {
            CommandResult commandResult;

            boolean isSelectingAvatar = model.isSampleAvatar();
            boolean isCourseNameSet = model.isCourseNameSet();

            logger.info("courseName = " + model.isCourseNameSet());

            if (isSelectingAvatar) {
                // If user has not selected avatar, get her to do so.
                commandResult = logic.executeAvatar(commandText);

                // Now we've already selected Avatar, time to display the Main module panel
                displayModulePanel(model);
            } else if (!isCourseNameSet) {
                /*
                 * if user has not selected her course name, and she is trying to execute any other
                 * command than course add n/course_name, prevent her from doing so.
                 */
                commandResult = logic.executeSetCourseName(commandText);
            } else {
                // Finally, once the above 2 conditions are satisfied, let user execute commands normally.
                commandResult = logic.execute(commandText);
            }

            logger.info("Result: " + commandResult.getFeedbackToUser());
            refreshResultDisplay(commandResult);
            refreshProgressPanel(model);

            if (commandResult.isShowHelp()) {
                handleHelp();
            } else if (commandResult.isExit()) {
                handleExit();
            } else if (commandResult.isCourseAdd()) {
                refreshStatusBar(model);
            }

            handleStopLoading(model.getAvatar());

            return commandResult;
        } catch (CommandException | ParseException | IOException | ServiceException e) {
            logger.info("Invalid command: " + commandText);
            refreshResultDisplayError(e.getMessage());
            throw e;
        }

    }
}
