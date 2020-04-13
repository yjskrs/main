package igrad.ui;

import igrad.model.module.Module;
import igrad.model.requirement.Requirement;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;

// @@author dargohzy

/**
 * Shows requirements, where each {@code requirement} is a list of {@code Module}
 */
public class RequirementCard extends UiPart<Region> {

    private static final String FXML = "RequirementListCard.fxml";

    private Requirement requirement;
    private int displayedIndex;

    @FXML
    private Label requirementCode;

    @FXML
    private Label requirementTitle;

    @FXML
    private Label creditsCount;

    @FXML
    private FlowPane moduleCodes;

    @FXML
    private HBox requirementCardPane;

    public RequirementCard(Requirement requirement, int displayedIndex) {
        super(FXML);

        this.requirement = requirement;
        this.displayedIndex = displayedIndex;

        requirementCode.setText(requirement.getRequirementCode().toString());
        requirementTitle.setText(requirement.getTitle().toString());

        int creditsFulfilled = requirement.getCreditsFulfilled();
        int creditsRequired = requirement.getCreditsRequired();
        int creditsAssigned = requirement.getCreditsAssigned();

        String creditsCountText = creditsFulfilled
            + " out of " + creditsRequired
            + " MCs fulfilled ("
            + creditsAssigned + " assigned)";

        if (requirement.isFulfilled()) {
            requirementCardPane.getStyleClass().add("done");
        }

        creditsCount.setText(creditsCountText);

        ObservableList<Module> moduleList = requirement.getModuleList();

        for (Module module : moduleList) {

            Label moduleCode = new Label(module.getModuleCode().toString());
            moduleCode.getStyleClass().add("module-tag");

            moduleCodes.getChildren().add(moduleCode);
        }

    }
}
