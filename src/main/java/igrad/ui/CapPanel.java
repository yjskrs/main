package igrad.ui;

import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

/**
 * Panel containing the current, target CAP for the user.
 */
public class CapPanel extends UiPart<Region> {

    public static final String FXML = "ProgressSidePanel.fxml";


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
    }

   /* public void showPanels() {

    }

    public void setCap(double cap) {
        currentCap.setText("cap");
    }

    public void setGoalCap(String cap) {

    }

    public void setTargetCap(String cap) {

    }*/
}
