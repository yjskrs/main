package igrad.ui;

import igrad.model.module.Module;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;

// @@author dargohzy

/**
 * An UI component that displays information about {@code Module}.
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

    private final Module module;
    private int displayedIndex;

    @FXML
    private HBox moduleCardPane;
    @FXML
    private Label title;
    @FXML
    private Label moduleCode;
    @FXML
    private Label semester;
    @FXML
    private Label credits;
    @FXML
    private Label grade;

    public ModuleCard(Module module, int displayedIndex) {
        super(FXML);
        this.module = module;
        this.displayedIndex = displayedIndex;
        //       id.setText(displayedIndex + ". ");
        title.setText(module.getTitle().value);
        moduleCode.setText(module.getModuleCode().value);
        credits.setText(module.getCredits().value + " MCs");
        credits.getStyleClass().add("module-card-default-present");

        if (module.getSemester().isPresent()) {
            semester.setText(module.getSemester().get().value);
            semester.getStyleClass().add("module-card-default-present");
        }

        if (module.getGrade().isPresent()) {
            grade.setText(module.getGrade().get().value);
            grade.getStyleClass().add("module-card-default-present");
        }
    }

    public int getDisplayedIndex() {
        return this.displayedIndex;
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
        return this.displayedIndex == card.displayedIndex
            && module.equals(card.module);
    }
}
