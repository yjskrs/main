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
     * Retrieves the latest {@code CourseInfo} fields, based on the most updated {@code Requirement}
     * list and {@code Module} list in {@code Model}
     */
    public static CourseInfo retrieveLatestCourseInfo(CourseInfo course, Model model) {
        // Copy over all the old values of course
        Optional<Name> currentName = course.getName();

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
            course.getSemesters(), model.getFilteredModuleList());

        CourseInfo latestCourseInfo = new CourseInfo(currentName, updatedCap, updatedCredits, updatedSemesters);

        return latestCourseInfo;
    }
}
