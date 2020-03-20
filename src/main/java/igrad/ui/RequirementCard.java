package igrad.ui;

import igrad.model.Model;
import igrad.model.requirement.Requirement;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;

/**
 * Shows requirements, where each {@code requirement} is a list of {@code Module}
 */
public class RequirementCard extends UiPart<Region> {

    private final static String FXML = "RequirementListCard.fxml";

    private Requirement requirement;

    @FXML
    private Label requirementTitle;

    @FXML
    private Label mcCount;

    public RequirementCard(Requirement requirement) {
        super(FXML);

        this.requirement = requirement;
    }
}
