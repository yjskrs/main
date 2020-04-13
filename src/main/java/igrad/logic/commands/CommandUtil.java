package igrad.logic.commands;

import java.util.Optional;

import igrad.model.Model;
import igrad.model.course.Cap;
import igrad.model.course.CourseInfo;
import igrad.model.course.Name;
import igrad.model.course.Semesters;

//@@author nathanaelseen

/**
 * Contains general purpose utility methods used by some of the commands.
 */
public class CommandUtil {
    /**
     * Creates and returns a new {@code CourseInfo} (with the details of {@code courseInfoToEdit},
     * and also based on the most updated information in {@code model}
     */
    public static CourseInfo createEditedCourseInfo(CourseInfo courseInfoToEdit, Model model) {
        // Copy over all the old values of course
        Optional<Name> currentName = courseInfoToEdit.getName();

        // Now we actually go to our model and recompute cap based on updated module list in model (coursebook)
        Optional<Cap> updatedCap = CourseInfo.computeCap(model.getFilteredModuleList(),
            model.getRequirementList());

        /*
         * Now given that we've updated a new module to requirement (as done), we've to update (recompute)
         * creditsFulfilled and creditsRequired
         */
        Optional<igrad.model.course.Credits> updatedCredits = CourseInfo.computeCredits(
            model.getRequirementList());

        /*
         * Recompute semesters (total and updated)
         */
        Optional<Semesters> updatedSemesters = CourseInfo.computeSemesters(
            courseInfoToEdit.getSemesters(), model.getFilteredModuleList());

        return new CourseInfo(currentName, updatedCap, updatedCredits, updatedSemesters);
    }
}
