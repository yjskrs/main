package igrad.logic.commands.course;

//@@author nathanaelseen

import static igrad.logic.parser.CliSyntax.PREFIX_CAP;
import static igrad.logic.parser.CliSyntax.PREFIX_NAME;
import static igrad.logic.parser.CliSyntax.PREFIX_SEMESTER;

import igrad.logic.commands.CommandTestUtil;

/**
 * Utility class that stores static strings/ints used in creating CourseInfo objects
 * or CourseCommand objects.
 */
public class CourseCommandTestUtil extends CommandTestUtil {

    // valid course arguments
    public static final String VALID_COURSE_NAME_BCOMPSCI = "Bachelor of Computer Science";
    public static final String VALID_COURSE_NAME_BCOMPSEC = "Bachelor of Computer Security";
    public static final String VALID_COURSE_CAP_BCOMPSCI = "4.5";
    public static final String VALID_COURSE_CAP_BCOMPSEC = "4.8";
    public static final int VALID_COURSE_CREDITS_REQUIRED_BCOMPSCI = 108;
    public static final int VALID_COURSE_CREDITS_REQUIRED_BCOMPSEC = 180;
    public static final int VALID_COURSE_CREDITS_FULFILLED_BCOMPSCI = 64;
    public static final int VALID_COURSE_CREDITS_FULFILLED_BCOMPSEC = 40;
    public static final String VALID_COURSE_SEMESTERS_BCOMPSCI = "3";
    public static final String VALID_COURSE_SEMESTERS_BCOMPSEC = "4";
    public static final int VALID_COURSE_TOTAL_SEMESTERS_BCOMPSCI = 6;
    public static final int VALID_COURSE_REMAINING_SEMESTERS_BCOMPSCI = 5;
    public static final int VALID_COURSE_TOTAL_SEMESTERS_BCOMPSEC = 4;
    public static final int VALID_COURSE_REMAINING_SEMESTERS_BCOMPSEC = 3;

    // invalid course arguments
    public static final String INVALID_COURSE_NAME = " Bachelor of Mathematics";
    public static final String INVALID_COURSE_SEMESTERS = "4<";

    // course name descriptor for command entered
    public static final String COURSE_NAME_DESC_BCOMPSCI = " " + PREFIX_NAME + VALID_COURSE_NAME_BCOMPSCI;
    public static final String COURSE_NAME_DESC_BCOMPSEC = " " + PREFIX_NAME + VALID_COURSE_NAME_BCOMPSEC;

    // course semesters descriptor for command entered
    public static final String COURSE_SEMESTERS_DESC_BCOMPSCI = " " + PREFIX_SEMESTER + VALID_COURSE_SEMESTERS_BCOMPSCI;
    public static final String COURSE_SEMESTERS_DESC_BCOMPSEC = " " + PREFIX_SEMESTER + VALID_COURSE_SEMESTERS_BCOMPSEC;

    public static final String INVALID_COURSE_NAME_DESC = " " + PREFIX_NAME + INVALID_COURSE_NAME;
    public static final String INVALID_COURSE_SEMESTERS_DESC = " " + PREFIX_SEMESTER + INVALID_COURSE_SEMESTERS;

    // cap descriptor for command entered
    public static final String VALID_CAP_DESC = " " + PREFIX_CAP + "4.5";
    public static final String INVALID_CAP_DESC = " " + PREFIX_CAP + "6";
}
