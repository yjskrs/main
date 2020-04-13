package igrad.ui;

import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;

// @@author dargohzy

/**
 * A (permanent) top-panel that displays course status information.
 */
public class StatusBar extends UiPart<Region> {

    private static final String FXML = "StatusBar.fxml";

    private String courseName;

    @FXML
    private StackPane pane;

    @FXML
    private Label course;


    public StatusBar() {
        super(FXML);

        this.courseName = "";

        pane = new StackPane();

        course.setText("Course:");

        StackPane.setAlignment(course, Pos.CENTER_LEFT);
        pane.getChildren().add(course);
    }

    /**
     * Sets the course name inputted.
     */
    public void setCourseName(String courseName) {
        this.courseName = courseName;
        course.setText(courseName);
    }

    /**
     * Checks if courseName is set.
     */
    public boolean isCourseNameSet() {
        return this.courseName.length() != 0;
    }

    /**
     * Returns the text that the label is current holding.
     */
    public String getLabelText() {
        return course.getText();
    }

    public StackPane getPane() {
        return this.pane;
    }
}
