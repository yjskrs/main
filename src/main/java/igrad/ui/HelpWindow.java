package igrad.ui;

//@@author dargohzy

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.logging.Logger;

import igrad.commons.core.LogsCenter;
import javafx.fxml.FXML;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.stage.Stage;


/**
 * Controller for a help page.
 */
public class HelpWindow extends UiPart<Stage> {

    public static final String USERGUIDE_URL = "https://ay1920s2-cs2103t-f09-3.github.io/main/UserGuide.html";

    private static final Logger logger = LogsCenter.getLogger(HelpWindow.class);
    private static final String FXML = "HelpWindow.fxml";

    // Cheatsheet command formats
    private static final String COURSE_ADD = "course set n/COURSE_NAME s/TOTAL_SEMESTERS";
    private static final String COURSE_EDIT = "course edit n/COURSE_NAME s/TOTAL_SEMESTERS";
    private static final String COURSE_ACHIEVE = "course achieve c/DESIRED_CAP";
    private static final String COURSE_DELETE = "course delete";
    private static final String REQUIREMENT_ADD = "requirement add t/REQUIREMENT_TITLE u/MCS_REQUIRED";
    private static final String REQUIREMENT_EDIT =
        "requirement edit REQUIREMENT_CODE [t/REQUIREMENT_TITLE] [u/MCS_REQUIRED]";
    private static final String REQUIREMENT_ASSIGN = "requirement un/assign REQUIREMENT_CODE n/MODULE_CODE …";
    private static final String REQUIREMENT_DELETE = "requirement delete REQUIREMENT_CODE";
    private static final String MODULE_ADD =
        "module add n/MODULE_CODE t/MODULE_TITLE u/MCs [s/SEMESTER]";
    private static final String MODULE_EDIT =
        "module edit MODULE_CODE [n/MODULE_CODE] [t/MODULE_TITLE] [u/MCs] [s/SEMESTER]";
    private static final String MODULE_DONE = "module done MODULE_CODE [g/GRADE] [s/SEMESTER]";
    private static final String MODULE_DELETE = "module delete MODULE_CODE";
    private static final String UNDO = "undo";
    private static final String EXPORT = "export";
    private static final String EXIT = "exit";


    @FXML
    private Hyperlink hyperlink;

    @FXML
    private Label courseAdd;

    @FXML
    private Label courseEdit;

    @FXML
    private Label courseDelete;

    @FXML
    private Label courseAchieve;

    @FXML
    private Label requirementAdd;

    @FXML
    private Label requirementEdit;

    @FXML
    private Label requirementDelete;

    @FXML
    private Label requirementAssign;

    @FXML
    private Label moduleAdd;

    @FXML
    private Label moduleEdit;

    @FXML
    private Label moduleDelete;

    @FXML
    private Label moduleDone;

    @FXML
    private Label undo;

    @FXML
    private Label export;

    @FXML
    private Label exit;

    /**
     * Creates a new HelpWindow.
     *
     * @param root Stage to use as the root of the HelpWindow.
     */
    public HelpWindow(Stage root) {
        super(FXML, root);

        hyperlink.setText("iGrad User Guide");
        setLabels();
    }

    /**
     * Creates a new HelpWindow.
     */
    public HelpWindow() {
        this(new Stage());
    }

    /**
     * Shows the help window.
     *
     * @throws IllegalStateException <ul>
     *                               <li>
     *                               if this method is called on a thread other than the JavaFX Application Thread.
     *                               </li>
     *                               <li>
     *                               if this method is called during animation or layout processing.
     *                               </li>
     *                               <li>
     *                               if this method is called on the primary stage.
     *                               </li>
     *                               <li>
     *                               if {@code dialogStage} is already showing.
     *                               </li>
     *                               </ul>
     */
    public void show() {
        logger.fine("Showing help page about the application.");
        getRoot().show();
        getRoot().centerOnScreen();
    }

    /**
     * Returns true if the help window is currently being shown.
     */
    public boolean isShowing() {
        return getRoot().isShowing();
    }

    /**
     * Hides the help window.
     */
    public void hide() {
        getRoot().hide();
    }

    /**
     * Focuses on the help window.
     */
    public void focus() {
        getRoot().requestFocus();
    }

    /**
     * Opens the iGrad User Guide in the user's default browser.
     */
    public void openUserGuide() {
        if (Desktop.isDesktopSupported()) {
            try {
                Desktop.getDesktop().browse(new URI(USERGUIDE_URL));
            } catch (IOException e1) {
                e1.printStackTrace();
            } catch (URISyntaxException e1) {
                e1.printStackTrace();
            }
        }
    }

    /**
     * Sets the commands and their respective messages.
     */
    public void setLabels() {
        courseAdd.setText(COURSE_ADD);
        courseEdit.setText(COURSE_EDIT);
        courseAchieve.setText(COURSE_ACHIEVE);
        courseDelete.setText(COURSE_DELETE);
        requirementAdd.setText(REQUIREMENT_ADD);
        requirementEdit.setText(REQUIREMENT_EDIT);
        requirementAssign.setText(REQUIREMENT_ASSIGN);
        requirementDelete.setText(REQUIREMENT_DELETE);
        moduleAdd.setText(MODULE_ADD);
        moduleEdit.setText(MODULE_EDIT);
        moduleDone.setText(MODULE_DONE);
        moduleDelete.setText(MODULE_DELETE);
        undo.setText(UNDO);
        export.setText(EXPORT);
        exit.setText(EXIT);
    }

    /**
     * Copies the URL to the user guide to the clipboard.
     */
    @FXML
    private void copyUrl() {
        final Clipboard clipboard = Clipboard.getSystemClipboard();
        final ClipboardContent url = new ClipboardContent();
        url.putString(USERGUIDE_URL);
        clipboard.setContent(url);
    }
}
