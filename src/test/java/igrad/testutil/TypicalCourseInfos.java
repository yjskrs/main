package igrad.testutil;

//@@author nathanaelseen

import static igrad.logic.commands.course.CourseCommandTestUtil.VALID_COURSE_CAP_BCOMPSCI;
import static igrad.logic.commands.course.CourseCommandTestUtil.VALID_COURSE_CAP_BCOMPSEC;
import static igrad.logic.commands.course.CourseCommandTestUtil.VALID_COURSE_CREDITS_FULFILLED_BCOMPSCI;
import static igrad.logic.commands.course.CourseCommandTestUtil.VALID_COURSE_CREDITS_FULFILLED_BCOMPSEC;
import static igrad.logic.commands.course.CourseCommandTestUtil.VALID_COURSE_CREDITS_REQUIRED_BCOMPSCI;
import static igrad.logic.commands.course.CourseCommandTestUtil.VALID_COURSE_CREDITS_REQUIRED_BCOMPSEC;
import static igrad.logic.commands.course.CourseCommandTestUtil.VALID_COURSE_NAME_BCOMPSCI;
import static igrad.logic.commands.course.CourseCommandTestUtil.VALID_COURSE_NAME_BCOMPSEC;
import static igrad.logic.commands.course.CourseCommandTestUtil.VALID_COURSE_SEMESTERS_BCOMPSCI;
import static igrad.logic.commands.course.CourseCommandTestUtil.VALID_COURSE_SEMESTERS_BCOMPSEC;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import igrad.model.course.CourseInfo;

/**
 * A utility class containing a list of {@code CourseInfo} objects to be used in tests.
 */
public class TypicalCourseInfos {
    public static final CourseInfo BCOMPSCI = new CourseInfoBuilder()
        .withName(VALID_COURSE_NAME_BCOMPSCI)
        .withCap(VALID_COURSE_CAP_BCOMPSCI)
        .withCredits(VALID_COURSE_CREDITS_REQUIRED_BCOMPSCI,
            VALID_COURSE_CREDITS_FULFILLED_BCOMPSCI)
        .withSemesters(VALID_COURSE_SEMESTERS_BCOMPSCI)
        .build();

    public static final CourseInfo BCOMPSEC = new CourseInfoBuilder()
        .withName(VALID_COURSE_NAME_BCOMPSEC)
        .withCap(VALID_COURSE_CAP_BCOMPSEC)
        .withCredits(VALID_COURSE_CREDITS_REQUIRED_BCOMPSEC,
            VALID_COURSE_CREDITS_FULFILLED_BCOMPSEC)
        .withSemesters(VALID_COURSE_SEMESTERS_BCOMPSEC)
        .build();

    private TypicalCourseInfos() {
    } // prevents instantiation

    /**
     * Returns a list of all the typical course infos.
     */
    public static List<CourseInfo> getTypicalCourseInfos() {
        return new ArrayList<>(Arrays.asList(BCOMPSCI, BCOMPSEC));
    }
}
