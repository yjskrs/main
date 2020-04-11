package igrad.ui;

import java.util.logging.Logger;

import igrad.commons.core.LogsCenter;
import igrad.model.requirement.Requirement;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;

// @@author dargohzy

/**
 * Panel showing the list of requirements entered.
 */
public class RequirementListPanel extends UiPart<Region> {

    private static final String FXML = "RequirementListPanel.fxml";
    private Logger logger = LogsCenter.getLogger(RequirementListPanel.class);

    @FXML
    private ListView<Requirement> requirementListView;

    public RequirementListPanel(ObservableList<Requirement> requirementList) {
        super(FXML);

        requirementListView.setItems(requirementList);
        requirementListView.setCellFactory(listView -> new RequirementListViewCell());
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Module} using a {@code ModuleCard}.
     */
    class RequirementListViewCell extends ListCell<Requirement> {
        @Override
        protected void updateItem(Requirement requirement, boolean empty) {
            super.updateItem(requirement, empty);

            if (empty || requirement == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new RequirementCard(requirement, getIndex() + 1).getRoot());
            }
        }
    }
}
