package igrad.model.course;

// import static igrad.logic.commands.course.CourseCommandTestUtil.VALID_COURSE_NAME_BCOMPSCI;
import static igrad.testutil.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

import org.junit.jupiter.api.Test;

import igrad.testutil.CourseInfoBuilder;
import igrad.testutil.TypicalCourseInfos;
import igrad.testutil.TypicalModules;

public class CourseInfoTest {
    private final CourseInfo courseInfo = new CourseInfoBuilder().build();

    @Test
    public void constructor_withNoArgs_createsCourseInfoWithAllFieldsOptionalEmpty() {
        CourseInfo emptyCourseInfo = new CourseInfoBuilder().buildEmptyCourseInfo();

        assertEquals(Optional.empty(), emptyCourseInfo.getName());
        assertEquals(Optional.empty(), emptyCourseInfo.getCap());
        assertEquals(Optional.empty(), emptyCourseInfo.getCredits());
        assertEquals(Optional.empty(), emptyCourseInfo.getSemesters());
    }

    @Test
    public void constructor_nullFields_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new CourseInfoBuilder().buildNullCourseInfo());
    }

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
        assertFalse(TypicalCourseInfos.B_COMP_SCI.equals(TypicalModules.COMPUTER_ORGANISATION));

        // different object -> returns false
        assertFalse(TypicalCourseInfos.B_COMP_SCI.equals(TypicalCourseInfos.B_SCI_MATH));

        // different name -> returns false
        /*CourseInfo editedCourseName = new CourseInfoBuilder(TypicalCourseInfos.B_COMP_SCI)
            .withName(VALID_NAME_B_ARTS_PHILO).build();*/
        // assertFalse(TypicalCourseInfos.B_COMP_SCI.equals(editedCourseName));

        //different cap -> returns false
        CourseInfo editedCourseCap = new CourseInfoBuilder(TypicalCourseInfos.B_INFO_SYS)
            .withCap("1.0").build();
        assertFalse(TypicalCourseInfos.B_INFO_SYS.equals(editedCourseCap));
    }
}

