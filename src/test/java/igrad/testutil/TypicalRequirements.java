package igrad.testutil;

//@@author yjskrs

import static igrad.logic.commands.requirement.RequirementCommandTestUtil.VALID_REQ_CODE_CSF;
import static igrad.logic.commands.requirement.RequirementCommandTestUtil.VALID_REQ_CODE_GE;
import static igrad.logic.commands.requirement.RequirementCommandTestUtil.VALID_REQ_CODE_IP;
import static igrad.logic.commands.requirement.RequirementCommandTestUtil.VALID_REQ_CODE_UE;
import static igrad.logic.commands.requirement.RequirementCommandTestUtil.VALID_REQ_CREDITS_ASSIGNED_IP;
import static igrad.logic.commands.requirement.RequirementCommandTestUtil.VALID_REQ_CREDITS_CSF;
import static igrad.logic.commands.requirement.RequirementCommandTestUtil.VALID_REQ_CREDITS_FULFILLED_IP;
import static igrad.logic.commands.requirement.RequirementCommandTestUtil.VALID_REQ_CREDITS_GE;
import static igrad.logic.commands.requirement.RequirementCommandTestUtil.VALID_REQ_CREDITS_REQUIRED_IP;
import static igrad.logic.commands.requirement.RequirementCommandTestUtil.VALID_REQ_CREDITS_UE;
import static igrad.logic.commands.requirement.RequirementCommandTestUtil.VALID_REQ_TITLE_CSF;
import static igrad.logic.commands.requirement.RequirementCommandTestUtil.VALID_REQ_TITLE_GE;
import static igrad.logic.commands.requirement.RequirementCommandTestUtil.VALID_REQ_TITLE_IP;
import static igrad.logic.commands.requirement.RequirementCommandTestUtil.VALID_REQ_TITLE_UE;
import static igrad.testutil.TypicalModules.getTypicalModules;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import igrad.model.CourseBook;
import igrad.model.requirement.Requirement;

/**
 * A utility class containing a list of {@code Requirement} objects to be used in tests.
 */
public class TypicalRequirements {

    public static final Requirement CS_FOUNDATION = new RequirementBuilder()
        .withRequirementCode(VALID_REQ_CODE_CSF)
        .withTitle(VALID_REQ_TITLE_CSF)
        .withCreditsOneParameter(VALID_REQ_CREDITS_CSF)
        .withModules(getTypicalModules())
        .build();

    public static final Requirement UNRESTRICTED_ELECTIVES = new RequirementBuilder()
        .withRequirementCode(VALID_REQ_CODE_UE)
        .withTitle(VALID_REQ_TITLE_UE)
        .withCreditsOneParameter(VALID_REQ_CREDITS_UE)
        .build();

    public static final Requirement GENERAL_ELECTIVES = new RequirementBuilder()
        .withRequirementCode(VALID_REQ_CODE_GE)
        .withTitle(VALID_REQ_TITLE_GE)
        .withCreditsOneParameter(VALID_REQ_CREDITS_GE)
        .build();

    public static final Requirement IT_PROFESSIONALISM = new RequirementBuilder()
        .withRequirementCode(VALID_REQ_CODE_IP)
        .withTitle(VALID_REQ_TITLE_IP)
        .withCreditsThreeParameters(VALID_REQ_CREDITS_REQUIRED_IP, VALID_REQ_CREDITS_ASSIGNED_IP,
            VALID_REQ_CREDITS_FULFILLED_IP)
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
