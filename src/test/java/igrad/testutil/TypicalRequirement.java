package igrad.testutil;

import static igrad.testutil.TypicalModules.getTypicalModules;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import igrad.model.CourseBook;
import igrad.model.requirement.Requirement;

/**
 * A utility class containing a list of {@code Requirement} objects to be used in tests.
 */
public class TypicalRequirement {

    public static final Requirement CS_FOUNDATION = new RequirementBuilder()
                                                        .withName("Computer Science Foundation")
                                                        .withCreditsOneParameter("48")
                                                        .withModules(getTypicalModules())
                                                        .build();

    public static final Requirement UNRESTRICTED_ELECTIVES = new RequirementBuilder()
                                                                 .withName("Unrestricted Electives")
                                                                 .withCreditsOneParameter("32")
                                                                 .build();

    public static final Requirement GENERAL_ELECTIVES = new RequirementBuilder()
                                                            .withName("General Electives")
                                                            .withCreditsOneParameter("20")
                                                            .build();

    /**
     * Returns an {@code CourseBook} with all the typical requirements.
     */
    public static CourseBook getTypicalCourseBook() {
        CourseBook courseBook = new CourseBook();
        for (Requirement requirement : getTypicalRequirements()) {
            courseBook.addRequirement(requirement);
        }
        return courseBook;
    }

    /**
     * Returns a list of all the typical requirements.
     */
    public static List<Requirement> getTypicalRequirements() {
        return new ArrayList<>(Arrays.asList(CS_FOUNDATION, UNRESTRICTED_ELECTIVES, GENERAL_ELECTIVES));
    }

}
