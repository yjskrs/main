package igrad.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.Region;

/**
 * Panel containing the progress (MCs, modules completed, requirements completed, CAP info) of the user.
 */
public class ProgressSidePanel extends UiPart<Region> {

    public static final String FXML = "ProgressSidePanel.fxml";

    private int completedMcs;
    private int completedModules;
    private int completedRequirements;
    private int totalMcs;
    private int totalModules;
    private int totalRequirements;

    private double cumulativeAverage;
    private int completedSemesters;
    private int totalSemesters;

    private int cumulativeAverageGoal;
    private int cumulativeAverageRequired;

    @FXML
    private Label modularCredits;

    @FXML
    private ProgressBar creditsProgressBar;

    @FXML
    private ProgressBar modulesProgressBar;

    @FXML
    private ProgressBar requirementsProgressBar;

    @FXML
    private Label modulesCompleted;

    @FXML
    private Label requirementsCompleted;

    @FXML
    private Label currentCap;

    @FXML
    private Label semestersLeftLabel;

    @FXML
    private Label goalCap;

    @FXML
    private Label requiredCap;

    public ProgressSidePanel() {
        super(FXML);

        this.completedMcs = 0;
        this.completedModules = 0;
        this.completedRequirements = 0;
        this.totalMcs = 0;
        this.totalModules = 0;
        this.totalRequirements = 0;

        this.completedSemesters = 0;
        this.totalSemesters = 0;

        this.cumulativeAverage = 0;
        this.cumulativeAverageGoal = 0;
        this.cumulativeAverageRequired = 0;

        updateProgress();
    }

    /**
     * Sets value of the total number of MCs.
     */
    public void setTotalMcs(int totalMcs) {
        this.totalMcs = totalMcs;
    }

    /**
     * Sets value of the total number of modules.
     */
    public void setTotalModules(int totalModules) {
        this.totalModules = totalModules;
    }

    /**
     * Sets value of the total number of requirements.
     */
    public void setTotalRequirements(int totalRequirements) {
        this.totalRequirements = totalRequirements;
    }

    /**
     * Sets the value of the total number of semesters.
     */
    public void setTotalSemesters(int totalMcs) {
        this.totalSemesters = totalMcs / 20;
    }

    /**
     * Updates the progress panel.
     */
    public void updateProgress() {
//        setModularCreditsLabel();
//        setModulesCompletedLabel();
//        setRequirementsCompletedLabel();
//        setCumulativeAverageLabel();
//        setSemestersLeftLabel();
//        setGoalCapLabel();
//        setTargetCapLabel();
    }

    /**
     * Sets the progress for Modular Credits.
     */
    public void setModularCreditsLabel() {

        modularCredits.setText(countFormat(completedMcs, totalMcs));

        double progress = 0.0;

        if (totalMcs > 0) {
            progress = completedMcs / totalMcs;
        }

        creditsProgressBar.setProgress(progress);
    }

    /**
     * Sets the progress for Modules Completed.
     */
    public void setModulesCompletedLabel() {
        modulesCompleted.setText(countFormat(completedModules, totalModules));

        double progress = 0.0;

        if (totalModules > 0) {
            progress = completedModules / totalModules;
        }

        modulesProgressBar.setProgress(progress);

    }

    /**
     * Sets the progress for Requirements Completed.
     */
    public void setRequirementsCompletedLabel() {
        requirementsCompleted.setText(countFormat(completedRequirements, totalRequirements));

        double progress = 0.0;

        if (totalRequirements > 0) {
            progress = completedRequirements / totalRequirements;
        }

        requirementsProgressBar.setProgress(progress);

    }

    /**
     * Sets the Cumulative Average.
     */
    public void setCumulativeAverageLabel() {
        currentCap.setText(decimalFormat(cumulativeAverage));
    }

    /**
     * Sets the number of Semesters remaining.
     */
    public void setSemestersLeftLabel() {
        semestersLeftLabel.setText(countFormat(completedSemesters, totalSemesters));
    }

    /**
     * Sets the goal Cumulative Average.
     */
    public void setGoalCapLabel() {
        goalCap.setText(decimalFormat(cumulativeAverageGoal));
    }

    /**
     * Sets the target Cumulative Average per semester.
     */
    public void setTargetCapLabel() {
        requiredCap.setText(decimalFormat(cumulativeAverageRequired) + "/Sem");
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
