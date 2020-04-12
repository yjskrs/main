package igrad.logic.commands.course;

//@@author teriaiw

import static igrad.logic.commands.course.CourseAchieveCommand.MESSAGE_COURSE_ACHIEVE_SUCCESS;
import static igrad.logic.commands.course.CourseCommandTestUtil.VALID_COURSE_CAP_BCOMPSCI;
import static igrad.logic.commands.course.CourseCommandTestUtil.VALID_COURSE_NAME_BCOMPSCI;
import static igrad.logic.commands.course.CourseCommandTestUtil.VALID_COURSE_SEMESTERS_BCOMPSCI;
import static igrad.logic.commands.course.CourseCommandTestUtil.assertExecuteSuccess;
import static igrad.testutil.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

import org.junit.jupiter.api.Test;

import igrad.model.Model;
import igrad.model.ModelManager;
import igrad.model.course.Cap;
import igrad.model.course.CourseInfo;
import igrad.model.module.Module;
import igrad.testutil.CourseInfoBuilder;
import igrad.testutil.ModuleBuilder;

public class CourseAchieveCommandTest {
    @Test
    public void constructor_null_throwsNullPointerException() {
        Optional<Cap> cap = null;
        assertThrows(NullPointerException.class, () -> new CourseAchieveCommand(cap));
    }

    @Test
    public void execute_nullModel_throwsNullPointerException() {
        Model model = null;
        Optional<Cap> cap = Optional.of(new Cap("0"));

        CourseAchieveCommand cmd = new CourseAchieveCommand(cap);
        assertThrows(NullPointerException.class, () -> cmd.execute(model));
    }

    @Test
    public void execute_achieveCourse_success() {
        //set-up our Model
        Model model = new ModelManager();
        CourseInfo courseInfo = new CourseInfoBuilder()
                .withName(VALID_COURSE_NAME_BCOMPSCI)
                .withSemesters(VALID_COURSE_SEMESTERS_BCOMPSCI)
                .withCap(VALID_COURSE_CAP_BCOMPSCI)
                .withCreditsOptional()
                .build();
        model.addCourseInfo(courseInfo);

        // set-up expected Model
        Model expectedModel = new ModelManager();
        CourseInfo courseInfoCopy = new CourseInfoBuilder()
                .withName(VALID_COURSE_NAME_BCOMPSCI)
                .withSemesters(VALID_COURSE_SEMESTERS_BCOMPSCI)
                .withCap(VALID_COURSE_CAP_BCOMPSCI)
                .withCreditsOptional()
                .build();
        expectedModel.addCourseInfo(courseInfoCopy);

        Optional<Cap> capToAchieve = Optional.of(new Cap(4.5));

        Optional<Cap> estimatedCap = CourseInfo.computeEstimatedCap(model.getCourseInfo(), capToAchieve.get());

        CourseAchieveCommand cmd = new CourseAchieveCommand(capToAchieve);

        String expectedMessage = String.format(MESSAGE_COURSE_ACHIEVE_SUCCESS, estimatedCap.get());

        assertExecuteSuccess(cmd, model, expectedModel, expectedMessage);
    }

    @Test
    public void equals() {
        Optional<Cap> capToAchieve = Optional.of(new Cap(4.5));

        final CourseAchieveCommand courseAchieveCommand = new CourseAchieveCommand(capToAchieve);

        //null
        assertFalse(courseAchieveCommand.equals(null));

        //same course achieve command
        assertTrue(courseAchieveCommand.equals(courseAchieveCommand));

        // different type
        Module module = new ModuleBuilder().build();
        assertFalse(courseAchieveCommand.equals(module));

        //different course achieve command
        CourseAchieveCommand otherCourseAchieveCommand;
        Optional<Cap> otherCapToAchieve = Optional.of(new Cap("0"));

        otherCourseAchieveCommand = new CourseAchieveCommand(otherCapToAchieve);

        assertFalse(courseAchieveCommand.equals(otherCourseAchieveCommand));
    }
}
