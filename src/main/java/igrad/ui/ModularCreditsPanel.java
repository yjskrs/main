package igrad.ui;

import javafx.fxml.FXML;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import javax.swing.text.html.ImageView;
import javax.swing.text.html.ListView;
import java.awt.*;

public class ModularCreditsPanel extends UiPart<Region> {

    private static final String FXML = "ModularCreditsPanel.fxml";

    @FXML
    VBox vbox;

    @FXML
    StackPane stackPane;

    @FXML
    HBox hBox;

    @FXML
    ImageView imageView;

    @FXML
    Label label;

    @FXML
    ListView listView;

    public ModularCreditsPanel() {
        super(FXML);


    }
}
