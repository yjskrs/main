package igrad.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;

/**
 * Panel containing the current, target CAP for the user.
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
    private Label modulesCompleted;

    @FXML
    private Label requirementsCompleted;

    @FXML
    private Label currentCAP;

    @FXML
    private Label semestersLeftLabel;

    @FXML
    private Label goalCAP;

    @FXML
    private Label requiredCAP;

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
        setModularCreditsLabel();
        setModulesCompletedLabel();
        setRequirementsCompletedLabel();
        setCumulativeAverageLabel();
        setSemestersLeftLabel();
        setGoalCapLabel();
        setTargetCapLabel();
    }

    /**
     * Sets the progress for Modular Credits.
     */
    public void setModularCreditsLabel() {
        modularCredits.setText(countFormat(completedMcs, totalMcs));
    }

    /**
     * Sets the progress for Modules Completed.
     */
    public void setModulesCompletedLabel() {
        modulesCompleted.setText(countFormat(completedModules, totalModules));
    }

    /**
     * Sets the progress for Requirements Completed.
     */
    public void setRequirementsCompletedLabel() {
        requirementsCompleted.setText(countFormat(completedRequirements, totalRequirements));
    }

    /**
     * Sets the Cumulative Average.
     */
    public void setCumulativeAverageLabel() {
        currentCAP.setText(decimalFormat(cumulativeAverage));
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
        goalCAP.setText(decimalFormat(cumulativeAverageGoal));
    }

    /**
     * Sets the target Cumulative Average per semester.
     */
    public void setTargetCapLabel() {
        requiredCAP.setText(decimalFormat(cumulativeAverageRequired) + "/Sem");
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
    public String decimalFormat(double value){
        return String.format("%.2f", value);
    }
}
