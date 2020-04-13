package igrad.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;

// @@author dargohzy

/**
 * Panel showing the last command input into the system.
 */
public class CommandReceivedPanel extends UiPart<Region> {

    private static final String FXML = "CommandReceivedPanel.fxml";

    @FXML
    private Label commandReceived;

    public CommandReceivedPanel() {
        super(FXML);
    }

    /**
     * Sets the string in the commandReceived box.
     */
    public void setCommandReceived(String command) {
        commandReceived.setText(command);
    }
}
