package igrad.ui;

import static java.util.Objects.requireNonNull;

import igrad.model.avatar.Avatar;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Region;

/**
 * A ui for the status bar that is displayed at the header of the application.
 */
public class ResultDisplay extends UiPart<Region> {

    private static final String FXML = "ResultDisplay.fxml";

    @FXML
    private TextArea resultDisplay;

    @FXML
    private ImageView avatarDisplay;

    public ResultDisplay(Avatar avatar) {
        super(FXML);

        this.setAvatar(avatar);
    }

    public void setFeedbackToUser(String feedbackToUser) {
        requireNonNull(feedbackToUser);
        resultDisplay.setText(feedbackToUser);
    }

    public void setAvatar(Avatar avatar) {
        String imgPath = "/avatars/" + avatar.getName() + ".png";
        AvatarImage avatarImage = new AvatarImage(imgPath);
        avatarDisplay.setImage(avatarImage);
    }
}
