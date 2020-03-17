package igrad.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;

/**
 * Panel containing the current over total number of Modular Credits for modules cleared.
 */
public class McSidePanel extends UiPart<Region> {

    public static final String FXML = "McSidePanelIndicator.fxml";

    @FXML
    private AnchorPane mcPanelPlaceholder;

    @FXML
    Label mcCount;

    @FXML
    ProgressIndicator progressIndicator;

    private int mcCounter;
    private int totalMcs;

    /*
     * TODO: Replace with CAP information when link to UI is done.
     * @FXML Label currentCAP;
     *
     * @FXML
     * Label goalCAP;
     *
     * @FXML
     * Label targetCAP;
     */

    /**
     * Side panel displaying Modular Credits information.
     */
    public McSidePanel() {
        super(FXML);

        this.totalMcs = 160;
        setMcCounter(40);
    }

    public void setMcCounter(int count) {

        if (count < 100) {
            mcCount.setText(" " + count + "/" + this.totalMcs);
        } else {
            mcCount.setText(count + "/" + this.totalMcs);
        }
        double progress = (double) count / (double) totalMcs;
        progressIndicator.setProgress(progress);
    }
}
