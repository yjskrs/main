package igrad.ui;

import java.util.logging.Logger;
import igrad.commons.core.LogsCenter;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

/**
 * Panel containing the list of modules.
 */
public class AvatarSelectionPanel extends UiPart<Region> {
    private static final String FXML = "AvatarSelectionPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(AvatarSelectionPanel.class);

    private final String[] avatarAltText = new String[]{
        "Po",
        "Shibu",
        "Chikin",
        "Grizzly",
        "Koala",
        "Frogger"
    };

    @FXML
    private GridPane avatarGridPane;

    @FXML
    private Label avatarLabel;

    public AvatarSelectionPanel() {
        super(FXML);

        avatarLabel.setText("Welcome to iGrad.");

        int index = 0;

        for( int i = 0; i < 2; i++ ){
            for ( int j = 0; j < 3; j++ ){
                Label label = new Label();

                label.setText(avatarAltText[index]);
                label.setTextFill(Color.WHITE);
                label.setPrefHeight(300.0);
                label.setPrefWidth(300.0);
                label.setFont( new Font( "Arial", 30 ));

                GridPane.setRowIndex(label, i);
                GridPane.setColumnIndex(label, j);
                avatarGridPane.getChildren().add(label);
                index++;
            }
        }


    }

}
