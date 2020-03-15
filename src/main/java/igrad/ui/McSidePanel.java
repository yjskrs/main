package igrad.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class McSidePanel extends UiPart<Region> {

    public static final String FXML = "McSidePanel.fxml";

    private String mcCounter;

    @FXML
    HBox mcPanelPlaceholder;

/*     TODO: Replace with CAP information when link to UI is done.
    @FXML
    Label currentCAP;

    @FXML
    Label goalCAP;

    @FXML
    Label targetCAP;*/

    /**
     * Side panel displaying Modular Credits information.
     */
    public McSidePanel() {
        super(FXML);
    }

    public void setMcCounter(String mcCounter) {

    }
}
