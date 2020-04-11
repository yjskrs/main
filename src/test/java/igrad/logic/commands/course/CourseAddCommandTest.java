package igrad.logic.commands.course;

//@@author nathanaelseen

import static igrad.commons.core.Messages.MESSAGE_COURSE_ALREADY_SET;
import static igrad.logic.commands.CommandTestUtil.assertExecuteFailure;
import static igrad.logic.commands.CommandTestUtil.assertExecuteSuccess;
import static igrad.logic.commands.course.CourseAddCommand.MESSAGE_COURSE_ADD_SUCCESS;
import static igrad.logic.commands.course.CourseCommandTestUtil.VALID_COURSE_NAME_BCOMPSCI;
import static igrad.logic.commands.course.CourseCommandTestUtil.VALID_COURSE_NAME_BCOMPSEC;
import static igrad.logic.commands.course.CourseCommandTestUtil.VALID_COURSE_SEMESTERS_BCOMPSCI;
import static igrad.logic.commands.course.CourseCommandTestUtil.VALID_COURSE_SEMESTERS_BCOMPSEC;
import static igrad.testutil.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import igrad.model.Model;
import igrad.model.ModelManager;
import igrad.model.course.CourseInfo;
import igrad.model.module.Module;
import igrad.testutil.CourseInfoBuilder;
import igrad.testutil.ModuleBuilder;

public class CourseAddCommandTest {
    @Test
    public void constructor_null_throwsNullPointerException() {
        // CourseInfo null
        CourseInfo courseInfo = null;
        assertThrows(NullPointerException.class, (
            ) -> new CourseAddCommand(courseInfo));
    }

    @Test
    public void execute_nullModel_throwsNullPointerException() {
        Model model = null;
        CourseInfo courseInfo = new CourseInfoBuilder().build();

        CourseAddCommand cmd = new CourseAddCommand(courseInfo);
        assertThrows(NullPointerException.class, () -> cmd.execute(model));
    }

    @Test
    public void execute_courseAlreadySet_failure() {
        Model model = new ModelManager(); // set-up an empty Model
        CourseInfo courseInfoA = new CourseInfoBuilder()
            .withName(VALID_COURSE_NAME_BCOMPSCI)
            .withSemesters(VALID_COURSE_SEMESTERS_BCOMPSCI)
            .withCapOptional()
            .withCreditsOptional()
            .build();

        model.addCourseInfo(courseInfoA); // Add the course to our model

        CourseInfo courseInfoB = new CourseInfoBuilder()
            .withName(VALID_COURSE_NAME_BCOMPSEC)
            .withSemesters(VALID_COURSE_SEMESTERS_BCOMPSEC)
            .withCapOptional()
            .withCreditsOptional()
            .build();

        model.addCourseInfo(courseInfoA); // Add the course to our model

        CourseAddCommand cmd = new CourseAddCommand(courseInfoB);

        assertExecuteFailure(cmd, model, MESSAGE_COURSE_ALREADY_SET);
    }

    @Test
    public void execute_addCourse_success() {
        //set-up our Model
        Model model = new ModelManager();

        // set-up expected Model
        Model expectedModel = new ModelManager();
        CourseInfo addedCourseInfo = new CourseInfoBuilder()
            .withName(VALID_COURSE_NAME_BCOMPSCI)
            .withSemesters(VALID_COURSE_SEMESTERS_BCOMPSCI)
            .withCapOptional()
            .withCreditsOptional()
            .build();
        expectedModel.addCourseInfo(addedCourseInfo);

        CourseInfo courseInfoToAdd = new CourseInfoBuilder()
            .withName(VALID_COURSE_NAME_BCOMPSCI)
            .withSemesters(VALID_COURSE_SEMESTERS_BCOMPSCI)
            .withCapOptional()
            .withCreditsOptional()
            .build();

        CourseAddCommand cmd = new CourseAddCommand(courseInfoToAdd);

        String expectedMessage = String.format(MESSAGE_COURSE_ADD_SUCCESS, addedCourseInfo);

        assertExecuteSuccess(cmd, model, expectedModel, expectedMessage);
    }

    @Test
    public void equals() {
        CourseInfo courseInfo = new CourseInfoBuilder()
            .withName(VALID_COURSE_NAME_BCOMPSCI)
            .withSemesters(VALID_COURSE_SEMESTERS_BCOMPSCI)
            .withCapOptional()
            .withCreditsOptional()
            .build();

        final CourseAddCommand courseAddCommand = new CourseAddCommand(courseInfo);

        // null
        assertFalse(courseAddCommand.equals(null));

        // same course add command
        assertTrue(courseAddCommand.equals(courseAddCommand));

        // different type
        Module module = new ModuleBuilder().build();
        assertFalse(courseAddCommand.equals(module));

        CourseAddCommand otherCourseAddCommand;
        CourseInfo otherCourseInfo;

        // different course add command; course info different
        otherCourseInfo = new CourseInfoBuilder()
            .withName(VALID_COURSE_NAME_BCOMPSEC)
            .withSemesters(VALID_COURSE_SEMESTERS_BCOMPSEC)
            .withCapOptional()
            .withCreditsOptional()
            .build();

        otherCourseAddCommand = new CourseAddCommand(otherCourseInfo);

        assertFalse(courseAddCommand.equals(otherCourseAddCommand));
    }
}
