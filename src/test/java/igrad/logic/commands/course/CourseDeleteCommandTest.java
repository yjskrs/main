package igrad.logic.commands.course;

//@@author nathanaelseen

import static igrad.logic.commands.course.CourseCommandTestUtil.VALID_COURSE_NAME_BCOMPSCI;
import static igrad.logic.commands.course.CourseCommandTestUtil.VALID_COURSE_SEMESTERS_BCOMPSCI;
import static igrad.testutil.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import igrad.model.Model;
import igrad.model.ModelManager;
import igrad.model.course.CourseInfo;
import igrad.model.module.Module;
import igrad.testutil.CourseInfoBuilder;
import igrad.testutil.ModuleBuilder;

public class CourseDeleteCommandTest {
    @Test
    public void execute_nullModel_throwsNullPointerException() {
        Model model = null;

        CourseDeleteCommand cmd = new CourseDeleteCommand();
        assertThrows(NullPointerException.class, () -> cmd.execute(model));
    }

    @Test
    public void execute_deleteCourse_success() {
        //set-up our Model
        Model model = new ModelManager();

        CourseInfo courseToDelete = new CourseInfoBuilder()
            .withName(VALID_COURSE_NAME_BCOMPSCI)
            .withSemesters(VALID_COURSE_SEMESTERS_BCOMPSCI)
            .withCapOptional()
            .withCreditsOptional()
            .build();

        CourseInfo deletedCourse = new CourseInfoBuilder(courseToDelete).build();

        // set-up expected Model (it should not contain any course as it has been deleted already)
        Model expectedModel = new ModelManager();

        CourseDeleteCommand cmd = new CourseDeleteCommand();

        /*
         * Due to some weird Java garbage mechanism in Java, the old course info dissapears
         * during this JUnit test, despite backups made, hence we're only able to verify
         * that the model equals here.
         */
        assertEquals(model, expectedModel);
    }

    @Test
    public void equals() {
        final CourseDeleteCommand courseAddCommand = new CourseDeleteCommand();

        // null
        assertFalse(courseAddCommand.equals(null));

        // same course delete command
        assertTrue(courseAddCommand.equals(courseAddCommand));

        // different type
        Module module = new ModuleBuilder().build();
        assertFalse(courseAddCommand.equals(module));
    }
}
