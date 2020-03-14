package igrad.ui;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import igrad.commons.core.LogsCenter;
import igrad.model.avatar.Avatar;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.stage.Screen;

/**
 * Panel containing the list of modules.
 */
public class AvatarSelectionPanel extends UiPart<Region> {
    private static final String FXML = "AvatarSelectionPanel.fxml";
    private static final String WELCOME_MESSAGE = "Welcome to iGrad";
    private final Logger logger = LogsCenter.getLogger(AvatarSelectionPanel.class);

    @FXML
    private GridPane avatarGridPane;

    @FXML
    private Label avatarLabel;

    private List<AvatarImage> avatarImgList = new ArrayList<>();
    private Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();

    public AvatarSelectionPanel() {
        super(FXML);

        initAvatarImgList();
        showMainLabel();
        showAvatarImgList();
    }

    /**
     * Displays the welcome message, on top of the panel.
     */
    private void showMainLabel() {
        avatarLabel.setText(WELCOME_MESSAGE);

        // TODO: delete the line (below) when no need
        Font.getFamilies().forEach(System.out::println);
    }

    /**
     * Initialises an internal list of {@code AvatarImage}, maintained by this class.
     */
    private void initAvatarImgList() {

        List<Avatar> avatarList = Avatar.getAvatarList();

        int index = 0;

        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 3; j++) {
                String avatarName = avatarList.get(index).getName();
                AvatarImage avatarImg = new AvatarImage(
                        "/avatars/" + avatarName + ".png", avatarName, i, j);
                avatarImgList.add(avatarImg);
                index++;
            }
        }
    }

    /**
     * Displays the Avatar images in a {@code GridPane}, in the panel.
     */
    private void showAvatarImgList() {
        // TODO: (Wayne) you could also display the Avatar names too, I had a hard time figuring that out
        for (AvatarImage avatarImg : avatarImgList) {
            StackPane pane = new StackPane();
            Label avatarName = new Label(avatarImg.getAvatarName());
            ImageView avatarDisplay = new ImageView();

            // TODO: (Wayne) try to find a way to set these preferences in the FXML files instead of here
            avatarDisplay.setFitHeight(0.12 * primaryScreenBounds.getHeight());
            avatarDisplay.setFitWidth(0.12 * primaryScreenBounds.getHeight());
            avatarDisplay.setImage(avatarImg);

            avatarName.setStyle("-fx-font-family: 'Calibri Light'; -fx-font-size: 30; -fx-text-fill: white");
            avatarName.setPadding(new Insets(200, 0, 0, 0));

            pane.setAlignment(avatarName, Pos.BOTTOM_CENTER);
            pane.getChildren().addAll(avatarDisplay, avatarName);
            GridPane.setRowIndex(pane, avatarImg.getRowIndex());
            GridPane.setColumnIndex(pane, avatarImg.getColIndex());
            avatarGridPane.getChildren().add(pane);
        }
    }
}