package igrad.logic.commands.course;

import static igrad.logic.parser.CliSyntax.PREFIX_NAME;

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
    public static final int VALID_COURSE_CREDITS_REQUIRED_BCOMPSEC = 108;
    public static final int VALID_COURSE_CREDITS_FULFILLED_BCOMPSCI = 64;
    public static final int VALID_COURSE_CREDITS_FULFILLED_BCOMPSEC = 40;
    public static final String VALID_COURSE_SEMESTERS_BCOMPSCI = "3";
    public static final String VALID_COURSE_SEMESTERS_BCOMPSEC = "4";

    // invalid course arguments
    /*public static final String INVALID_REQ_CODE_DECIMAL = "RE1.0";
    public static final String INVALID_REQ_CODE_SYMBOL = "RE<";

    public static final String INVALID_REQ_CREDITS_ALPHABET = "a";
    public static final String INVALID_REQ_CREDITS_DECIMAL = "40.0";
    public static final String INVALID_REQ_CREDITS_SYMBOL = "&";*/

    // course name descriptor for command entered
    public static final String COURSE_NAME_DESC_BCOMPSCI = " " + PREFIX_NAME + VALID_COURSE_NAME_BCOMPSCI;
    public static final String COURSE_NAME_DESC_BCOMPSEC = " " + PREFIX_NAME + VALID_COURSE_NAME_BCOMPSEC;
}
