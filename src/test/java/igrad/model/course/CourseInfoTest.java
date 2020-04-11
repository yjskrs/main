package igrad.model.course;

//@@author nathanaelseen

import static igrad.logic.commands.course.CourseCommandTestUtil.VALID_COURSE_CAP_BCOMPSCI;
import static igrad.logic.commands.course.CourseCommandTestUtil.VALID_COURSE_CAP_BCOMPSEC;
import static igrad.logic.commands.course.CourseCommandTestUtil.VALID_COURSE_CREDITS_FULFILLED_BCOMPSCI;
import static igrad.logic.commands.course.CourseCommandTestUtil.VALID_COURSE_CREDITS_FULFILLED_BCOMPSEC;
import static igrad.logic.commands.course.CourseCommandTestUtil.VALID_COURSE_CREDITS_REQUIRED_BCOMPSCI;
import static igrad.logic.commands.course.CourseCommandTestUtil.VALID_COURSE_CREDITS_REQUIRED_BCOMPSEC;
import static igrad.logic.commands.course.CourseCommandTestUtil.VALID_COURSE_NAME_BCOMPSCI;
import static igrad.logic.commands.course.CourseCommandTestUtil.VALID_COURSE_SEMESTERS_BCOMPSCI;
import static igrad.logic.commands.course.CourseCommandTestUtil.VALID_COURSE_SEMESTERS_BCOMPSEC;
import static igrad.logic.commands.module.ModuleCommandTestUtil.VALID_MODULE_CODE_CS1101S;
import static igrad.logic.commands.module.ModuleCommandTestUtil.VALID_MODULE_CODE_CS2100;
import static igrad.model.course.Cap.CAP_ZERO;
import static igrad.testutil.Assert.assertThrows;
import static igrad.testutil.TypicalCourseInfos.BCOMPSCI;
import static igrad.testutil.TypicalModules.CS2040;
import static igrad.testutil.TypicalModules.CS2101;
import static igrad.testutil.TypicalRequirements.CS_FOUNDATION;
import static igrad.testutil.TypicalRequirements.GENERAL_ELECTIVES;
import static igrad.testutil.TypicalRequirements.IT_PROFESSIONALISM;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;

import igrad.model.module.Module;
import igrad.model.requirement.Requirement;
import igrad.testutil.CourseInfoBuilder;
import igrad.testutil.ModuleBuilder;
import igrad.testutil.RequirementBuilder;

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
    public void computeCredits_totalCreditsRequiredTallyCreditsAllModules_success() {
        List<Requirement> requirementList = new ArrayList<Requirement>();
        requirementList.add(CS_FOUNDATION);
        requirementList.add(GENERAL_ELECTIVES);

        Optional<Credits> credits = CourseInfo.computeCredits(requirementList);

        int computedCreditsRequired = credits.get().getCreditsRequired();
        int expectedCreditsRequired = (CS_FOUNDATION.getCredits().getCreditsRequired()
            + GENERAL_ELECTIVES.getCredits().getCreditsRequired());

        assertEquals(expectedCreditsRequired, computedCreditsRequired);
    }

    @Test
    public void computeCredits_totalCreditsFulfilledTallyCreditsAllModules_success() {
        List<Requirement> requirementList = new ArrayList<Requirement>();
        requirementList.add(CS_FOUNDATION);
        requirementList.add(IT_PROFESSIONALISM);

        Optional<Credits> credits = CourseInfo.computeCredits(requirementList);

        int computedCreditsFulfilled = credits.get().getCreditsFulfilled();
        int expectedCreditsFulfilled = (CS_FOUNDATION.getCredits().getCreditsFulfilled()
            + IT_PROFESSIONALISM.getCredits().getCreditsFulfilled());

        assertEquals(expectedCreditsFulfilled, computedCreditsFulfilled);
    }

    @Test
    public void computeCap_moduleListNull_throwsNullPointerException() {
        // In this test case, moduleList is null but requirementList not null
        List<Requirement> requirementList = new ArrayList<Requirement>();
        requirementList.add(CS_FOUNDATION);
        List<Module> moduleList = null;
        assertThrows(NullPointerException.class, () -> CourseInfo.computeCap(moduleList,
            requirementList));
    }

    @Test
    public void computeCap_requirementListNull_throwsNullPointerException() {
        // In this test case, requirementList is null but moduleList not null
        List<Requirement> requirementList = null;
        List<Module> moduleList = new ArrayList<Module>();
        moduleList.add(CS2040);
        assertThrows(NullPointerException.class, () -> CourseInfo.computeCap(moduleList,
            requirementList));
    }

    @Test
    public void computeCap_moduleListAndRequirementListNull_throwsNullPointerException() {
        // In this test case, both moduleList and requirementList is null
        List<Requirement> requirementList = null;
        List<Module> moduleList = null;
        assertThrows(NullPointerException.class, () -> CourseInfo.computeCap(moduleList,
            requirementList));
    }

    @Test
    public void computeCap_moduleListEmpty_returnsOptionalEmpty() {
        // In this test case, moduleList is empty but requirementList not empty
        List<Requirement> requirementList = new ArrayList<Requirement>();
        requirementList.add(CS_FOUNDATION);
        List<Module> moduleList = new ArrayList<Module>();
        assertEquals(Optional.empty(), CourseInfo.computeCap(moduleList, requirementList));
    }

    @Test
    public void computeCap_requirementListEmpty_returnsOptionalEmpty() {
        // In this test case, requirementList is empty but moduleList not empty
        List<Requirement> requirementList = new ArrayList<Requirement>();
        List<Module> moduleList = new ArrayList<Module>();
        moduleList.add(CS2040);
        assertEquals(Optional.empty(), CourseInfo.computeCap(moduleList, requirementList));
    }

    @Test
    public void computeCap_moduleListAndRequirementListEmpty_returnsOptionalEmpty() {
        // In this test case, both moduleList and requirementList is empty
        List<Requirement> requirementList = new ArrayList<Requirement>();
        List<Module> moduleList = new ArrayList<Module>();
        moduleList.add(CS2040);
        assertEquals(Optional.empty(), CourseInfo.computeCap(moduleList, requirementList));
    }

    @Test
    public void computeCap_noModulesInAnyRequirements_returnsCapZero() {
        List<Requirement> requirementList = new ArrayList<Requirement>();
        requirementList.add(CS_FOUNDATION);
        requirementList.add(GENERAL_ELECTIVES);

        List<Module> moduleList = new ArrayList<Module>();
        moduleList.add(CS2040);
        moduleList.add(CS2101);

        Optional<Cap> computedCap = CourseInfo.computeCap(moduleList, requirementList);
        Optional<Cap> expectedCap = Optional.of(CAP_ZERO);

        assertEquals(expectedCap, computedCap);
    }

    @Test
    public void computeCap_allModulesNoGrade_returnsCapZero() {
        List<Requirement> requirementList = new ArrayList<Requirement>();

        Requirement requirement = new RequirementBuilder().build();

        List<Module> moduleList = new ArrayList<Module>();

        // Create 2 modules without any grade
        Module moduleOne = new ModuleBuilder()
            .withModuleCode(VALID_MODULE_CODE_CS1101S)
            .withoutOptionals().build();
        Module moduleTwo = new ModuleBuilder()
            .withModuleCode(VALID_MODULE_CODE_CS2100)
            .withoutOptionals().build();

        moduleList.add(moduleOne);
        moduleList.add(moduleTwo);

        // Add the modules to the requirement
        requirement.addModule(moduleOne);
        requirement.addModule(moduleTwo);

        // Finally, add the requirement to the requirementList
        requirementList.add(requirement);

        Optional<Cap> computedCap = CourseInfo.computeCap(moduleList, requirementList);
        Optional<Cap> expectedCap = Optional.of(CAP_ZERO);

        assertEquals(expectedCap, computedCap);
    }

    @Test
    public void computeCap_oneModuleGradeA_returnsCapFive() {
        List<Requirement> requirementList = new ArrayList<Requirement>();

        Requirement requirement = new RequirementBuilder().build();

        List<Module> moduleList = new ArrayList<Module>();

        // Create a modules with grade; A
        Module module = new ModuleBuilder()
            .withModuleCode(VALID_MODULE_CODE_CS1101S)
            .withGrade("A").build();

        moduleList.add(module);

        // Add the modules to the requirement
        requirement.addModule(module);

        // Finally, add the requirement to the requirementList
        requirementList.add(requirement);

        Optional<Cap> computedCap = CourseInfo.computeCap(moduleList, requirementList);
        Optional<Cap> expectedCap = Optional.of(new Cap(5));

        assertEquals(expectedCap, computedCap);
    }

    @Test
    public void computeCap_twoModulesGradeA_returnsCapFive() {
        List<Requirement> requirementList = new ArrayList<Requirement>();

        Requirement requirement = new RequirementBuilder().build();

        List<Module> moduleList = new ArrayList<Module>();

        // Create 2 modules with grade; A
        Module moduleOne = new ModuleBuilder()
            .withModuleCode(VALID_MODULE_CODE_CS1101S)
            .withGrade("A").build();
        Module moduleTwo = new ModuleBuilder()
            .withModuleCode(VALID_MODULE_CODE_CS2100)
            .withGrade("A").build();

        moduleList.add(moduleOne);
        moduleList.add(moduleTwo);

        // Add the modules to the requirement
        requirement.addModule(moduleOne);
        requirement.addModule(moduleTwo);

        // Finally, add the requirement to the requirementList
        requirementList.add(requirement);

        Optional<Cap> computedCap = CourseInfo.computeCap(moduleList, requirementList);
        Optional<Cap> expectedCap = Optional.of(new Cap(5));

        assertEquals(expectedCap, computedCap);
    }

    @Test
    public void computeCap_oneModuleGradeAOtherModuleGradeBMinus_returnsCapFour() {
        List<Requirement> requirementList = new ArrayList<Requirement>();

        Requirement requirement = new RequirementBuilder().build();

        List<Module> moduleList = new ArrayList<Module>();

        // Create 2 modules with grade; A
        Module moduleOne = new ModuleBuilder()
            .withModuleCode(VALID_MODULE_CODE_CS1101S)
            .withGrade("A").build();
        Module moduleTwo = new ModuleBuilder()
            .withModuleCode(VALID_MODULE_CODE_CS2100)
            .withGrade("B-").build();

        moduleList.add(moduleOne);
        moduleList.add(moduleTwo);

        // Add the modules to the requirement
        requirement.addModule(moduleOne);
        requirement.addModule(moduleTwo);

        // Finally, add the requirement to the requirementList
        requirementList.add(requirement);

        Optional<Cap> computedCap = CourseInfo.computeCap(moduleList, requirementList);
        Optional<Cap> expectedCap = Optional.of(new Cap(4));

        assertEquals(expectedCap, computedCap);
    }

    @Test
    public void computeCap_oneModuleGradeAOtherModuleSuGrade_returnsCapFive() {
        List<Requirement> requirementList = new ArrayList<Requirement>();

        Requirement requirement = new RequirementBuilder().build();

        List<Module> moduleList = new ArrayList<Module>();

        // Create 2 modules with grade; A
        Module moduleOne = new ModuleBuilder()
            .withModuleCode(VALID_MODULE_CODE_CS1101S)
            .withGrade("A").build();
        Module moduleTwo = new ModuleBuilder()
            .withModuleCode(VALID_MODULE_CODE_CS2100)
            .withGrade("S").build();

        moduleList.add(moduleOne);
        moduleList.add(moduleTwo);

        // Add the modules to the requirement
        requirement.addModule(moduleOne);
        requirement.addModule(moduleTwo);

        // Finally, add the requirement to the requirementList
        requirementList.add(requirement);

        Optional<Cap> computedCap = CourseInfo.computeCap(moduleList, requirementList);
        Optional<Cap> expectedCap = Optional.of(new Cap(5));

        assertEquals(expectedCap, computedCap);
    }

    @Test
    public void computeCap_twoModulesSuGrade_returnsCapZero() {
        List<Requirement> requirementList = new ArrayList<Requirement>();

        Requirement requirement = new RequirementBuilder().build();

        List<Module> moduleList = new ArrayList<Module>();

        // Create 2 modules with grade; A
        Module moduleOne = new ModuleBuilder()
            .withModuleCode(VALID_MODULE_CODE_CS1101S)
            .withGrade("A").build();
        Module moduleTwo = new ModuleBuilder()
            .withModuleCode(VALID_MODULE_CODE_CS2100)
            .withGrade("S").build();

        moduleList.add(moduleOne);
        moduleList.add(moduleTwo);

        // Add the modules to the requirement
        requirement.addModule(moduleOne);
        requirement.addModule(moduleTwo);

        // Finally, add the requirement to the requirementList
        requirementList.add(requirement);

        Optional<Cap> computedCap = CourseInfo.computeCap(moduleList, requirementList);
        Optional<Cap> expectedCap = Optional.of(new Cap(5));

        assertEquals(expectedCap, computedCap);
    }

    //@@author teriaiw

    @Test
    public void computeSemesters_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> CourseInfo.computeSemesters(null, null));
    }

    @Test
    public void computeSemesters_emptySemesters_returnsOptionalEmpty() {
        List<Module> emptyModuleList = new ArrayList<Module>();
        assertEquals(Optional.empty(), CourseInfo.computeSemesters(Optional.empty(), emptyModuleList));
    }

    @Test
    public void computeSemesters_emptyModuleList_returnsOriginalSemesters() {
        List<Module> emptyModuleList = new ArrayList<Module>();
        Optional<Semesters> semesters = Optional.of(new Semesters("5"));
        assertEquals(semesters, CourseInfo.computeSemesters(semesters, emptyModuleList));
    }

    @Test
    public void equals() {
        // null
        assertFalse(BCOMPSCI.equals(null));

        // same course info
        assertTrue(BCOMPSCI.equals(BCOMPSCI));

        // copied course info
        CourseInfo courseInfoCopy = new CourseInfoBuilder(BCOMPSCI).build();
        assertTrue(BCOMPSCI.equals(courseInfoCopy));

        // different type
        Module module = new ModuleBuilder().build();
        assertFalse(BCOMPSCI.equals(module));

        assertFalse(BCOMPSCI.equals(GENERAL_ELECTIVES));

        CourseInfo otherCourseInfo;

        // different course info; only cap different
        otherCourseInfo = new CourseInfoBuilder(BCOMPSCI)
            .withCap(VALID_COURSE_CAP_BCOMPSEC)
            .build();
        assertFalse(BCOMPSCI.equals(otherCourseInfo));

        // different course info; only credits different
        otherCourseInfo = new CourseInfoBuilder(BCOMPSCI)
            .withCredits(VALID_COURSE_CREDITS_REQUIRED_BCOMPSEC,
                VALID_COURSE_CREDITS_FULFILLED_BCOMPSEC)
            .build();
        assertFalse(BCOMPSCI.equals(otherCourseInfo));

        //different course info; only semesters different
        otherCourseInfo = new CourseInfoBuilder(BCOMPSCI)
                .withSemesters(VALID_COURSE_SEMESTERS_BCOMPSEC)
                .build();
        assertFalse(BCOMPSCI.equals(otherCourseInfo));

        // different course info; both cap and credits, different
        CourseInfo other = new CourseInfoBuilder(BCOMPSCI)
            .withCap(VALID_COURSE_CAP_BCOMPSEC)
            .withCredits(VALID_COURSE_CREDITS_REQUIRED_BCOMPSEC,
                VALID_COURSE_CREDITS_FULFILLED_BCOMPSEC)
            .build();
        assertFalse(BCOMPSCI.equals(other));

        //different course info; cap, credits, semesters, all different
        other = new CourseInfoBuilder(BCOMPSCI)
                .withCap(VALID_COURSE_CAP_BCOMPSEC)
                .withCredits(VALID_COURSE_CREDITS_REQUIRED_BCOMPSEC,
                        VALID_COURSE_CREDITS_FULFILLED_BCOMPSEC)
                .withSemesters(VALID_COURSE_SEMESTERS_BCOMPSEC)
                .build();
        assertFalse(BCOMPSCI.equals(other));
    }
}

