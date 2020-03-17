package igrad.ui;

import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;

/**
 * Panel containing the current, target CAP for the user.
 */
public class CapPanel extends UiPart<Region> {

    public static final String FXML = "CapPanel.fxml";

    @FXML
    private AnchorPane capPanel;
    /*
     * @FXML
     * Label currentCAP;

     * @FXML
     * Label goalCAP;

     * @FXML
     * Label targetCAP;
     */

    public CapPanel() {
        super(FXML);

        this.capPanel = new AnchorPane();
    }

    public void showPanels() {

    }

    public void setCap(String cap) {

    }

    public void setGoalCap(String cap) {

    }

    public void setTargetCap(String cap) {

    }
}
