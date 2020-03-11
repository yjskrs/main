package igrad.ui;

import java.util.Comparator;

import igrad.model.module.Module;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;

/**
 * An UI component that displays information of a {@code Module}.
 */
public class ModuleCard extends UiPart<Region> {

    private static final String FXML = "ModuleListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on CourseBook level 4</a>
     */

    public final Module module;

    @FXML
    private HBox cardPane;
    @FXML
    private Label title;
    @FXML
    private Label id;
    @FXML
    private Label moduleCode;
    @FXML
    private Label memo;
    @FXML
    private Label semester;
    @FXML
    private Label credits;
    @FXML
    private Label description;
    @FXML
    private FlowPane tags;

    public ModuleCard(Module module, int displayedIndex) {
        super(FXML);
        this.module = module;
        id.setText(displayedIndex + ". ");
        title.setText(module.getTitle().value);
        moduleCode.setText("Code:\t\t" + module.getModuleCode().value);
        credits.setText("Credits:\t\t" + module.getCredits().value);

        if (module.getMemo() != null) {
            memo.setText("Memo:\t\t\t" + module.getMemo().value);
        }
        if (module.getSemester() != null) {
            semester.setText("Semester:\t\t\t" + module.getSemester().value);
        }
        if (module.getDescription() != null) {
            description.setText("Description:\t\t" + module.getDescription().value);
        }

        module.getTags().stream()
            .sorted(Comparator.comparing(tag -> tag.tagName))
            .forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ModuleCard)) {
            return false;
        }

        // state check
        ModuleCard card = (ModuleCard) other;
        return id.getText().equals(card.id.getText())
            && module.equals(card.module);
    }
}
