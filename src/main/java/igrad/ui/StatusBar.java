package igrad.ui;

import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;

public class StatusBar extends UiPart<Region> {

    private static final String FXML = "StatusBar.fxml";

    @FXML
    private StackPane pane;

    @FXML
    private Label course;

    @FXML
    private Label capMcDisplay;

    public StatusBar() {

        super(FXML);

        pane = new StackPane();

        course.setText("Course: Computer Science");
        capMcDisplay.setText("CAP: 4.24     MCs: 80/160");

        pane.setAlignment(course, Pos.CENTER_LEFT);
        pane.setAlignment(capMcDisplay, Pos.CENTER_RIGHT);
        pane.getChildren().addAll(course, capMcDisplay);
    }

    public StackPane getPane() {
        return this.pane;
    }
}
