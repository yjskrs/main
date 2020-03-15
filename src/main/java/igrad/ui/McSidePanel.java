package igrad.ui;

import javafx.fxml.FXML;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;

public class McSidePanel extends UiPart<Region> {

    public static final String FXML = "McSidePanel.fxml";
    @FXML
    HBox mcPanelPlaceholder;
    private String mcCounter;

   /* TODO: Replace with CAP information when link to UI is done.
    * @FXML Label currentCAP;

    * @FXML
    * Label goalCAP;

    * @FXML
    * Label targetCAP;
    */

    /**
     * Side panel displaying Modular Credits information.
     */
    public McSidePanel() {
        super(FXML);
    }

    public void setMcCounter(String mcCounter) {

    }
}
