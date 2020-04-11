package igrad.logic.commands.course;

//@@author nathanaelseen

import static igrad.commons.core.Messages.MESSAGE_COURSE_ALREADY_SET;
import static igrad.logic.commands.CommandTestUtil.assertExecuteFailure;
import static igrad.logic.commands.course.CourseCommandTestUtil.VALID_COURSE_NAME_BCOMPSCI;
import static igrad.logic.commands.course.CourseCommandTestUtil.VALID_COURSE_NAME_BCOMPSEC;
import static igrad.logic.commands.course.CourseCommandTestUtil.VALID_COURSE_SEMESTERS_BCOMPSCI;
import static igrad.logic.commands.course.CourseCommandTestUtil.VALID_COURSE_SEMESTERS_BCOMPSEC;
import static igrad.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import igrad.model.Model;
import igrad.model.ModelManager;
import igrad.model.course.CourseInfo;
import igrad.testutil.CourseInfoBuilder;

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
}
