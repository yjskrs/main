package igrad.ui;

import java.util.Optional;

import igrad.model.Model;
import igrad.model.course.CourseInfo;
import igrad.model.course.Name;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.Region;

/**
 * Panel containing the progress (MCs, modules completed, requirements completed, CAP info) of the user.
 */
public class ProgressSidePanel extends UiPart<Region> {

    public static final String FXML = "ProgressSidePanel.fxml";

    private double totalCreditsRequired;
    private double totalCreditsFulfilled;
    private double progressBarPercentage;

    @FXML
    private Label courseNameLabel;

    @FXML
    private ProgressBar progressBar;

    @FXML
    private Label creditsCount;

    @FXML
    private Label inspirationalQuote;

    public ProgressSidePanel(Model model) {
        super(FXML);

        updateProgress(model);

    }

    /**
     * Updates the progress panel
     */
    public void updateProgress(Model model) {

        String quote = model.getRandomQuoteString();

        inspirationalQuote.setText("\"" + quote + "\"");

        CourseInfo courseInfo = model.getCourseInfo();

        totalCreditsFulfilled = model.getTotalCreditsFulfilled();
        totalCreditsRequired = model.getTotalCreditsRequired();

        progressBarPercentage = totalCreditsFulfilled / totalCreditsRequired;

        progressBar.setProgress(progressBarPercentage);

        Optional<Name> courseName = courseInfo.getName();

        courseName.ifPresentOrElse(
            name -> courseNameLabel.setText(name.value), () -> courseNameLabel
                .setText("Your Course."));

        String creditsCountString = (int) totalCreditsFulfilled
            + " out of "
            + (int) totalCreditsRequired + " MCs completed";
        creditsCount.setText(creditsCountString);

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
