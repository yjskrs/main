package igrad.model.course;

import static igrad.logic.commands.course.CourseCommandTestUtil.VALID_COURSE_CAP_BCOMPSCI;
import static igrad.logic.commands.course.CourseCommandTestUtil.VALID_COURSE_CREDITS_FULFILLED_BCOMPSCI;
import static igrad.logic.commands.course.CourseCommandTestUtil.VALID_COURSE_CREDITS_REQUIRED_BCOMPSCI;
import static igrad.logic.commands.course.CourseCommandTestUtil.VALID_COURSE_NAME_BCOMPSCI;
import static igrad.logic.commands.course.CourseCommandTestUtil.VALID_COURSE_SEMESTERS_BCOMPSCI;
import static igrad.testutil.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;

import igrad.model.requirement.Requirement;
import igrad.testutil.CourseInfoBuilder;
import igrad.testutil.TypicalCourseInfos;
import igrad.testutil.TypicalModules;

public class CourseInfoTest {
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
    public void getName_success() {
        Optional<Name> newName = Optional.of(new Name(VALID_COURSE_NAME_BCOMPSCI));
        CourseInfo newCourseInfo = new CourseInfoBuilder()
            .withName(VALID_COURSE_NAME_BCOMPSCI)
            .build();

        System.out.println(newName);
        System.out.println(newCourseInfo.getName());
        assertEquals(newName, newCourseInfo.getName());
    }

    @Test
    public void getCap_success() {
        Optional<Cap> newCap = Optional.of(new Cap(VALID_COURSE_CAP_BCOMPSCI));
        CourseInfo newCourseInfo = new CourseInfoBuilder()
            .withCap(VALID_COURSE_CAP_BCOMPSCI)
            .build();

        assertEquals(newCap, newCourseInfo.getCap());
    }

    @Test
    public void getCredits_success() {
        Optional<Credits> newCredits = Optional.of(new Credits(VALID_COURSE_CREDITS_REQUIRED_BCOMPSCI,
                VALID_COURSE_CREDITS_FULFILLED_BCOMPSCI));
        CourseInfo newCourseInfo = new CourseInfoBuilder()
            .withCredits(VALID_COURSE_CREDITS_REQUIRED_BCOMPSCI,
                    VALID_COURSE_CREDITS_FULFILLED_BCOMPSCI)
            .build();

        assertEquals(newCredits, newCourseInfo.getCredits());
    }

    @Test
    public void getSemesters_success() {
        Optional<Semesters> newSemesters = Optional.of(new Semesters(VALID_COURSE_SEMESTERS_BCOMPSCI));
        CourseInfo newCourseInfo = new CourseInfoBuilder()
            .withSemesters(VALID_COURSE_SEMESTERS_BCOMPSCI)
            .build();

        assertEquals(newSemesters, newCourseInfo.getSemesters());
    }

    @Test
    public void computeCredits_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> CourseInfo.computeCredits(null));
    }

    @Test
    public void computeCredits_emptyRequirementList_returnsOptionalEmpty() {
        List<Requirement> emptyRequirementList = new ArrayList<Requirement>();
        assertEquals(Optional.empty(), CourseInfo.computeCredits(emptyRequirementList));
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
        assertFalse(TypicalCourseInfos.B_COMP_SCI.equals(TypicalModules.CS2100));

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

