package igrad.logic.commands.course;

//@@author nathanaelseen

import static igrad.logic.commands.CommandTestUtil.assertExecuteSuccess;
import static igrad.logic.commands.course.CourseCommandTestUtil.VALID_COURSE_NAME_BCOMPSCI;
import static igrad.logic.commands.course.CourseCommandTestUtil.VALID_COURSE_NAME_BCOMPSEC;
import static igrad.logic.commands.course.CourseCommandTestUtil.VALID_COURSE_SEMESTERS_BCOMPSCI;
import static igrad.logic.commands.course.CourseCommandTestUtil.VALID_COURSE_SEMESTERS_BCOMPSEC;
import static igrad.logic.commands.course.CourseEditCommand.MESSAGE_COURSE_EDIT_SUCCESS;
import static igrad.testutil.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import igrad.logic.commands.course.CourseEditCommand.EditCourseDescriptor;
import igrad.model.Model;
import igrad.model.ModelManager;
import igrad.model.course.CourseInfo;
import igrad.model.module.Module;
import igrad.testutil.CourseInfoBuilder;
import igrad.testutil.EditCourseDescriptorBuilder;
import igrad.testutil.ModuleBuilder;

public class CourseEditCommandTest {
    @Test
    public void constructor_null_throwsNullPointerException() {
        // EditCourseDescriptor null
        EditCourseDescriptor descriptor = null;
        assertThrows(NullPointerException.class, (
        ) -> new CourseEditCommand(descriptor));
    }

    @Test
    public void execute_nullModel_throwsNullPointerException() {
        Model model = null;
        EditCourseDescriptor descriptor = new EditCourseDescriptorBuilder()
            .withName(VALID_COURSE_NAME_BCOMPSCI)
            .withSemesters(VALID_COURSE_SEMESTERS_BCOMPSCI)
            .build();
        CourseEditCommand cmd = new CourseEditCommand(descriptor);
        assertThrows(NullPointerException.class, () -> cmd.execute(model));
    }

    @Test
    public void execute_editCourse_success() {
        //set-up our Model
        Model model = new ModelManager();

        // Create a course info
        CourseInfo courseInfoToEdit = new CourseInfoBuilder()
            .withName(VALID_COURSE_NAME_BCOMPSCI)
            .withSemesters(VALID_COURSE_SEMESTERS_BCOMPSCI)
            .withCapOptional()
            .withCreditsOptional()
            .build();

        model.addCourseInfo(courseInfoToEdit);

        // set-up expected Model
        Model expectedModel = new ModelManager();
        CourseInfo editedCourseInfo = new CourseInfoBuilder()
            .withName(VALID_COURSE_NAME_BCOMPSEC)
            .withSemesters(VALID_COURSE_SEMESTERS_BCOMPSEC)
            .withCapOptional()
            .withCreditsOptional()
            .build();
        expectedModel.addCourseInfo(editedCourseInfo);


        EditCourseDescriptor descriptor = new EditCourseDescriptorBuilder()
            .withName(VALID_COURSE_NAME_BCOMPSEC)
            .withSemesters(VALID_COURSE_SEMESTERS_BCOMPSEC)
            .build();

        String expectedMessage = String.format(MESSAGE_COURSE_EDIT_SUCCESS, editedCourseInfo);

        CourseEditCommand cmd = new CourseEditCommand(descriptor);

        assertExecuteSuccess(cmd, model, expectedModel, expectedMessage);
    }

    @Test
    public void equals() {
        EditCourseDescriptor descriptor = new EditCourseDescriptorBuilder()
            .withName(VALID_COURSE_NAME_BCOMPSCI)
            .withSemesters(VALID_COURSE_SEMESTERS_BCOMPSCI)
            .build();

        final CourseEditCommand courseEditCommand = new CourseEditCommand(descriptor);

        // null
        assertFalse(courseEditCommand.equals(null));

        // same course add command
        assertTrue(courseEditCommand.equals(courseEditCommand));

        // different type
        Module module = new ModuleBuilder().build();
        assertFalse(courseEditCommand.equals(module));

        CourseEditCommand otherCourseEditCommand;
        EditCourseDescriptor otherDescriptor;

        // different course add command; descriptor different
        otherDescriptor = new EditCourseDescriptorBuilder()
            .withName(VALID_COURSE_NAME_BCOMPSEC)
            .withSemesters(VALID_COURSE_SEMESTERS_BCOMPSEC)
            .build();

        otherCourseEditCommand = new CourseEditCommand(otherDescriptor);

        assertFalse(courseEditCommand.equals(otherCourseEditCommand));
    }
}
