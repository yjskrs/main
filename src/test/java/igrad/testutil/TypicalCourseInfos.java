package igrad.testutil;

import static igrad.logic.commands.CommandTestUtil.VALID_NAME_B_ARTS_PHILO;
import static igrad.logic.commands.CommandTestUtil.VALID_NAME_B_COMP_SCI;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import igrad.model.course.CourseInfo;

/**
 * A utility class containing a list of {@code CourseInfo} objects to be used in tests.
 */
public class TypicalCourseInfos {
    public static final CourseInfo B_SCI_MATH = new CourseInfoBuilder()
        .withName("Bachelor of Science in Mathematics").build();

    /*
     * TODO: (Teri) Add one more CourseInfo test case example (same as above), and update
     * getTypicalCourseInfos() (below) accordingly
     */

    // Manually added - CourseInfo's details found in {@code CommandTestUtil}
    public static final CourseInfo B_ARTS_PHILO = new CourseInfoBuilder()
        .withName(VALID_NAME_B_ARTS_PHILO).build();

    public static final CourseInfo B_COMP_SCI = new CourseInfoBuilder()
        .withName(VALID_NAME_B_COMP_SCI).build();

    private TypicalCourseInfos() {
    } // prevents instantiation

    public static List<CourseInfo> getTypicalCourseInfos() {
        return new ArrayList<>(Arrays.asList(B_COMP_SCI));
    }
}
