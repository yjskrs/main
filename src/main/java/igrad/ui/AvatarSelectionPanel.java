package igrad.ui;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import igrad.commons.core.LogsCenter;
import igrad.model.avatar.Avatar;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.text.Font;

/**
 * Panel containing the list of modules.
 */
public class AvatarSelectionPanel extends UiPart<Region> {
    private static final String FXML = "AvatarSelectionPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(AvatarSelectionPanel.class);

    @FXML
    private GridPane avatarGridPane;

    @FXML
    private Label avatarLabel;

    private List<AvatarImage> avatarImgList = new ArrayList<>();

    public AvatarSelectionPanel() {
        super(FXML);

        showMainLabel();
        initAvatarImgList();
        showAvatarImgList();
    }

    private void showMainLabel() {
        avatarLabel.setText("Welcome to iGrad.");
        Font.getFamilies().forEach(System.out::println);
    }

    /**
     * Initializes a list of valid Avatars
     */
    private void initAvatarImgList() {

        List<Avatar> avatarList = Avatar.getAvatarList();

        int index = 0;

        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 3; j++) {
                AvatarImage avatarImg = new AvatarImage("/avatars/" + avatarList.get(index).getName() + ".png", i, j);
                avatarImgList.add(avatarImg);
                index++;
            }
        }
    }

    /**
     * Displays avatars in a {@code GridPane}
     */
    private void showAvatarImgList() {
        for (AvatarImage avatarImg : avatarImgList) {
            ImageView avatarDisplay = new ImageView();
            avatarDisplay.setFitHeight(250.0);
            avatarDisplay.setFitWidth(250.0);
            avatarDisplay.setImage(avatarImg);
            GridPane.setRowIndex(avatarDisplay, avatarImg.getRowIndex());
            GridPane.setColumnIndex(avatarDisplay, avatarImg.getColIndex());
            avatarGridPane.getChildren().add(avatarDisplay);
        }
    }

}
