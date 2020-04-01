package igrad.ui;

import igrad.model.Model;
import javafx.fxml.FXML;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.Region;

/**
 * Panel containing the progress (MCs, modules completed, requirements completed, CAP info) of the user.
 */
public class ProgressSidePanel extends UiPart<Region> {

    public static final String FXML = "ProgressSidePanel.fxml";

    @FXML
    private ProgressBar progressBar;

    public ProgressSidePanel(Model model) {
        super(FXML);

        double num = 0.5;
        progressBar.setProgress(num);

    }

    /**
     * Returns the string to be displayed as progress.
     */
    public String countFormat(int completed, int total) {
        if (total == 0) {
            return "-/-";
        } else {
            return completed + "/" + total;
        }
    }

    /**
     * Returns the decimal formatted string required for progress display.
     */
    public String decimalFormat(double value) {
        return String.format("%.2f", value);
    }
}
