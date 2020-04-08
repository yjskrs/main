package igrad.model.course;

import static igrad.logic.commands.CommandTestUtil.VALID_NAME_B_ARTS_PHILO;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import igrad.testutil.CourseInfoBuilder;
import igrad.testutil.TypicalCourseInfos;
import igrad.testutil.TypicalModules;

public class CourseInfoTest {
    @Test
    public void equals() {
        // same values -> returns true
        CourseInfo bSciMathCopy = new CourseInfoBuilder(TypicalCourseInfos.B_SCI_MATH).build();
        assertTrue(TypicalCourseInfos.B_SCI_MATH.equals(bSciMathCopy));

        // same object -> returns true
        assertTrue(TypicalCourseInfos.B_SCI_MATH.equals(TypicalCourseInfos.B_SCI_MATH));

        // null -> returns false
        assertFalse(TypicalCourseInfos.B_SCI_MATH.equals(null));

        // different type -> returns false
        assertFalse(TypicalCourseInfos.B_COMP_SCI.equals(TypicalModules.CS2100));

        // different object -> returns false
        assertFalse(TypicalCourseInfos.B_COMP_SCI.equals(TypicalCourseInfos.B_SCI_MATH));

        // different name -> returns false
        CourseInfo editedCourseName = new CourseInfoBuilder(TypicalCourseInfos.B_COMP_SCI)
            .withName(VALID_NAME_B_ARTS_PHILO).build();
        assertFalse(TypicalCourseInfos.B_COMP_SCI.equals(editedCourseName));

        //different cap -> returns false
        CourseInfo editedCourseCap = new CourseInfoBuilder(TypicalCourseInfos.B_INFO_SYS)
            .withCap("1.0").build();
        assertFalse(TypicalCourseInfos.B_INFO_SYS.equals(editedCourseCap));
    }
}

