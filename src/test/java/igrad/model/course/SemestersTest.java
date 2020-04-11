package igrad.model.course;

//@@author teriaiw

import static igrad.logic.commands.course.CourseCommandTestUtil.VALID_COURSE_REMAINING_SEMESTERS_BCOMPSCI;
import static igrad.logic.commands.course.CourseCommandTestUtil.VALID_COURSE_REMAINING_SEMESTERS_BCOMPSEC;
import static igrad.logic.commands.course.CourseCommandTestUtil.VALID_COURSE_SEMESTERS_BCOMPSCI;
import static igrad.logic.commands.course.CourseCommandTestUtil.VALID_COURSE_SEMESTERS_BCOMPSEC;
import static igrad.logic.commands.course.CourseCommandTestUtil.VALID_COURSE_TOTAL_SEMESTERS_BCOMPSCI;
import static igrad.logic.commands.course.CourseCommandTestUtil.VALID_COURSE_TOTAL_SEMESTERS_BCOMPSEC;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import igrad.model.module.Module;
import igrad.testutil.ModuleBuilder;

public class SemestersTest {
    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Semesters(null));
    }

    @Test
    public void constructor_invalidSemesters_throwsNullPointerException() {
        String invalidSemestersStr = ""; //empty string
        assertThrows(IllegalArgumentException.class, () -> new Semesters(invalidSemestersStr));

        int invalidTotalSemesters;
        int invalidRemainingSemesters;
        int validTotalSemesters;
        int validRemainingSemesters;

        validTotalSemesters = 1;
        invalidRemainingSemesters = -1;
        assertThrows(IllegalArgumentException.class, () -> new Semesters(
                validTotalSemesters, invalidRemainingSemesters
        ));

        invalidTotalSemesters = -1;
        validRemainingSemesters = 1;
        assertThrows(IllegalArgumentException.class, () -> new Semesters(
                invalidTotalSemesters, validRemainingSemesters
        ));
    }

    @Test
    public void isValidSemesters() {
        assertFalse(Semesters.isValidSemesters("")); //empty string
        assertFalse(Semesters.isValidSemesters(" ")); //whitespace
        assertFalse(Semesters.isValidSemesters("0")); //zero value
        assertFalse(Semesters.isValidSemesters("-1")); //negative value

        assertTrue(Semesters.isValidSemesters("1")); //positive value
    }

    @Test
    public void isValidTotalSemesters() {
        assertFalse(Semesters.isValidTotalSemesters(0)); //zero value
        assertFalse(Semesters.isValidTotalSemesters(-1)); //negative value

        assertTrue(Semesters.isValidTotalSemesters(1)); //positive value
    }

    @Test
    public void isValidRemainingSemesters() {
        assertFalse(Semesters.isValidRemainingSemesters(-1)); //negative value

        assertTrue(Semesters.isValidRemainingSemesters(0)); //zero value
        assertTrue(Semesters.isValidRemainingSemesters(1)); //positive value
    }

    @Test
    public void equals() {
        Semesters semestersA;
        Semesters semestersB;

        //null
        semestersA = new Semesters(VALID_COURSE_SEMESTERS_BCOMPSCI);
        assertFalse(semestersA.equals(null));

        //same semester string
        semestersA = new Semesters(VALID_COURSE_SEMESTERS_BCOMPSCI);
        semestersB = new Semesters(VALID_COURSE_SEMESTERS_BCOMPSCI);
        assertTrue(semestersA.equals(semestersB));

        //different semester string
        semestersA = new Semesters(VALID_COURSE_SEMESTERS_BCOMPSCI);
        semestersB = new Semesters(VALID_COURSE_SEMESTERS_BCOMPSEC);
        assertFalse(semestersA.equals(semestersB));

        //same semesters int
        semestersA = new Semesters(VALID_COURSE_TOTAL_SEMESTERS_BCOMPSCI,
                VALID_COURSE_REMAINING_SEMESTERS_BCOMPSCI);
        semestersB = new Semesters(VALID_COURSE_TOTAL_SEMESTERS_BCOMPSCI,
                VALID_COURSE_REMAINING_SEMESTERS_BCOMPSCI);
        assertTrue(semestersA.equals(semestersB));

        //different total and remaining semesters
        semestersA = new Semesters(VALID_COURSE_TOTAL_SEMESTERS_BCOMPSCI,
                VALID_COURSE_REMAINING_SEMESTERS_BCOMPSCI);
        semestersB = new Semesters(VALID_COURSE_TOTAL_SEMESTERS_BCOMPSEC,
                VALID_COURSE_REMAINING_SEMESTERS_BCOMPSEC);
        assertFalse(semestersA.equals(semestersB));

        //same total semesters but different remaining semesters
        semestersA = new Semesters(5, 4);
        semestersB = new Semesters(5, 3);
        assertFalse(semestersA.equals(semestersB));

        //different total semesters but same remaining semesters
        semestersA = new Semesters(4, 5);
        semestersB = new Semesters(3, 5);
        assertFalse(semestersA.equals(semestersB));

        //different type
        semestersA = new Semesters(VALID_COURSE_TOTAL_SEMESTERS_BCOMPSCI,
                VALID_COURSE_REMAINING_SEMESTERS_BCOMPSCI);
        Module module = new ModuleBuilder().build();
        assertFalse(semestersA.equals(module));
    }
}
