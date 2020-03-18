package igrad.model.course;

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
        assertFalse(TypicalCourseInfos.B_COMP_SCI.equals(TypicalModules.COMPUTER_ORGANISATION));

        // different object -> returns false
        assertFalse(TypicalCourseInfos.B_COMP_SCI.equals(TypicalCourseInfos.B_SCI_MATH));

        // different name -> returns false
        CourseInfo bCompSciCopy = new CourseInfoBuilder(TypicalCourseInfos.B_COMP_SCI).build();
        assertFalse(TypicalCourseInfos.B_SCI_MATH.equals(bCompSciCopy));
    }
}

