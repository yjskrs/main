package igrad.logic.commands.requirement;

//@@author nathanaelseen

import static igrad.logic.commands.CommandTestUtil.assertExecuteFailure;
import static igrad.logic.commands.CommandTestUtil.assertExecuteSuccess;
import static igrad.logic.commands.module.ModuleCommandTestUtil.VALID_MODULE_CODE_CS1101S;
import static igrad.logic.commands.module.ModuleCommandTestUtil.VALID_MODULE_CODE_CS2040;
import static igrad.logic.commands.module.ModuleCommandTestUtil.VALID_MODULE_CODE_CS2100;
import static igrad.logic.commands.requirement.RequirementCommand.MESSAGE_REQUIREMENT_NON_EXISTENT;
import static igrad.logic.commands.requirement.RequirementCommand.getFormattedModulesStr;
import static igrad.logic.commands.requirement.RequirementCommandTestUtil.VALID_REQ_CODE_GE;
import static igrad.logic.commands.requirement.RequirementCommandTestUtil.VALID_REQ_CODE_UE;
import static igrad.logic.commands.requirement.RequirementUnassignCommand.MESSAGE_REQUIREMENT_UNASSIGN_SUCCESS;
import static igrad.testutil.Assert.assertThrows;
import static igrad.testutil.TypicalRequirements.GENERAL_ELECTIVES;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import igrad.model.Model;
import igrad.model.ModelManager;
import igrad.model.course.CourseInfo;
import igrad.model.module.Module;
import igrad.model.module.ModuleCode;
import igrad.model.requirement.Requirement;
import igrad.model.requirement.RequirementCode;
import igrad.testutil.CourseInfoBuilder;
import igrad.testutil.ModuleBuilder;
import igrad.testutil.RequirementBuilder;

public class RequirementUnassignCommandTest {
    @Test
    public void constructor_null_throwsNullPointerException() {
        // RequirementCode null, but moduleCodes not null
        RequirementCode requirementCodeA = null;
        List<ModuleCode> moduleCodesA = new ArrayList<ModuleCode>() {
            {
                add(new ModuleCode(VALID_MODULE_CODE_CS1101S));
                add(new ModuleCode(VALID_MODULE_CODE_CS2040));
                add(new ModuleCode(VALID_MODULE_CODE_CS2100));
            }
        };

        assertThrows(NullPointerException.class, (
        ) -> new RequirementUnassignCommand(requirementCodeA, moduleCodesA));

        // moduleCodes null, but RequirementCode not null
        RequirementCode requirementCodeB = new RequirementCode(VALID_REQ_CODE_UE);
        List<ModuleCode> moduleCodesB = null;

        assertThrows(NullPointerException.class, (
        ) -> new RequirementUnassignCommand(requirementCodeB, moduleCodesB));
    }

    @Test
    public void execute_nullModel_throwsNullPointerException() {
        Model model = null;
        RequirementCode requirementCode = new RequirementCode(VALID_REQ_CODE_UE);
        List<ModuleCode> moduleCodes = new ArrayList<ModuleCode>() {
            {
                add(new ModuleCode(VALID_MODULE_CODE_CS1101S));
                add(new ModuleCode(VALID_MODULE_CODE_CS2040));
                add(new ModuleCode(VALID_MODULE_CODE_CS2100));
            }
        };

        RequirementUnassignCommand cmd = new RequirementUnassignCommand(requirementCode, moduleCodes);
        assertThrows(NullPointerException.class, () -> cmd.execute(model));
    }

    @Test
    public void execute_requirementNonExistentOnEmptyModel_failure() {
        Model model = new ModelManager(); // set-up an empty Model
        RequirementCode requirementCode = new RequirementCode(VALID_REQ_CODE_UE);
        List<ModuleCode> moduleCodes = new ArrayList<ModuleCode>() {
            {
                add(new ModuleCode(VALID_MODULE_CODE_CS1101S));
                add(new ModuleCode(VALID_MODULE_CODE_CS2040));
                add(new ModuleCode(VALID_MODULE_CODE_CS2100));
            }
        };
        RequirementUnassignCommand cmd = new RequirementUnassignCommand(requirementCode, moduleCodes);

        assertExecuteFailure(cmd, model, MESSAGE_REQUIREMENT_NON_EXISTENT);
    }

    @Test
    public void execute_requirementNonExistentNonEmptyModel_failure() {
        // set-up a non-empty Model
        Model model = new ModelManager();
        model.addRequirement(GENERAL_ELECTIVES);

        RequirementCode requirementCode = new RequirementCode(VALID_REQ_CODE_UE);
        List<ModuleCode> moduleCodes = new ArrayList<ModuleCode>() {
            {
                add(new ModuleCode(VALID_MODULE_CODE_CS1101S));
                add(new ModuleCode(VALID_MODULE_CODE_CS2040));
                add(new ModuleCode(VALID_MODULE_CODE_CS2100));
            }
        };

        RequirementUnassignCommand cmd = new RequirementUnassignCommand(requirementCode, moduleCodes);

        assertExecuteFailure(cmd, model, MESSAGE_REQUIREMENT_NON_EXISTENT);
    }

    @Test
    public void execute_existentRequirement_success() {
        //set-up our Model
        Model model = new ModelManager();
        int creditsCs1101s = 4;
        int creditsCs2040 = 4;
        String gradeCs1101s = "A";
        String gradeCs2040 = "B";
        String semesterCs1101s = "Y1S1";
        int totalReqCreditsAssignedReqA = creditsCs2040 + creditsCs2040;
        int totalReqCreditsAssignedReqB = creditsCs1101s + creditsCs2040;
        int totalReqCreditsRequired = 16;
        int totalReqCreditsFulfilledReqA = creditsCs2040 + creditsCs2040;
        int totalReqCreditsFulfilledReqB = creditsCs1101s + creditsCs2040;
        int totalCourseCreditsRequired = (totalReqCreditsRequired * 2);
        int totalCourseCreditsFulfilled = totalReqCreditsFulfilledReqA
            + totalReqCreditsFulfilledReqB;
        double courseCap = 4.25;
        int totalSemesters = 5;

        // Create a module with grade 'A'
        Module moduleToUnassign = new ModuleBuilder()
            .withModuleCode(VALID_MODULE_CODE_CS1101S)
            .withCredits(Integer.toString(creditsCs1101s))
            .withoutOptionals()
            .withGrade(gradeCs1101s)
            .build();

        // Create a dummy module, with grade 'B'
        Module dummyModule = new ModuleBuilder()
            .withModuleCode(VALID_MODULE_CODE_CS2040)
            .withCredits(Integer.toString(creditsCs2040))
            .withoutOptionals()
            .withGrade(gradeCs2040)
            .build();

        model.addModule(moduleToUnassign);
        model.addModule(dummyModule);

        // Create a new requirement and add these two modules inside
        List<Module> moduleListA = new ArrayList<>();
        moduleListA.add(moduleToUnassign);
        moduleListA.add(dummyModule); // assign the dummy module inside
        Requirement requirementA = new RequirementBuilder()
            .withRequirementCode(VALID_REQ_CODE_GE)
            .withModules(moduleListA)
            .withCreditsThreeParameters(totalReqCreditsRequired,
                totalReqCreditsAssignedReqA, totalReqCreditsFulfilledReqA)
            .build();
        model.addRequirement(requirementA); // Add that requirement to our Model

        // Create another requirement with the two modules already assigned
        List<Module> moduleListB = new ArrayList<>();
        moduleListB.add(moduleToUnassign);
        moduleListB.add(dummyModule); // add another dummy module inside
        Requirement requirementB = new RequirementBuilder()
            .withRequirementCode(VALID_REQ_CODE_UE)
            .withModules(moduleListB)
            .withCreditsThreeParameters(totalReqCreditsRequired,
                totalReqCreditsAssignedReqB, totalReqCreditsFulfilledReqB)
            .build();
        model.addRequirement(requirementB);

        //Finally, create the appropriate course info and add it to Model too
        CourseInfo courseInfo = new CourseInfoBuilder()
            .withCap(courseCap)
            .withCredits(totalCourseCreditsRequired, totalCourseCreditsFulfilled)
            .withSemesters(Integer.toString(totalSemesters))
            .build();
        model.setCourseInfo(courseInfo);

        // set-up expected Model
        Model expectedModel = new ModelManager();

        // Create a module with grade 'A'
        Module moduleToUnassignCopy = new ModuleBuilder()
            .withModuleCode(VALID_MODULE_CODE_CS1101S)
            .withCredits(Integer.toString(creditsCs1101s))
            .withoutOptionals()
            .withGrade(gradeCs1101s)
            .build();

        // Create a dummy module, with grade 'B'
        Module dummyModuleCopy = new ModuleBuilder()
            .withModuleCode(VALID_MODULE_CODE_CS2040)
            .withCredits(Integer.toString(creditsCs2040))
            .withoutOptionals()
            .withGrade(gradeCs2040)
            .build();

        expectedModel.addModule(moduleToUnassignCopy);
        expectedModel.addModule(dummyModuleCopy);

        // Create an 'updated' requirement and assign only the dummy module inside
        List<Module> editedModuleListA = new ArrayList<>();
        editedModuleListA.add(dummyModuleCopy);
        Requirement editedRequirementA = new RequirementBuilder()
            .withRequirementCode(VALID_REQ_CODE_GE)
            .withModules(editedModuleListA)
            .withCreditsThreeParameters(totalReqCreditsRequired,
                totalReqCreditsAssignedReqA - creditsCs1101s, // total credits assigned should be updated
                totalReqCreditsFulfilledReqA - creditsCs1101s) // total credits fulfilled should be updated
            .build();
        expectedModel.addRequirement(editedRequirementA); // Add that requirement to our Model

        // Create another requirement with that module inside too
        List<Module> editedModuleListB = new ArrayList<>();
        editedModuleListB.add(moduleToUnassignCopy);
        editedModuleListB.add(dummyModuleCopy); // add another dummy module inside
        Requirement editedRequirementB = new RequirementBuilder()
            .withRequirementCode(VALID_REQ_CODE_UE)
            .withModules(editedModuleListB)
            .withCreditsThreeParameters(totalReqCreditsRequired,
                totalReqCreditsAssignedReqB, totalReqCreditsFulfilledReqB) // there should be no change here
            .build();
        expectedModel.addRequirement(editedRequirementB);

        // Finally, create an 'updated' course info and add that 'updated' course info inside
        /*
         * Here we cheat abit to get remaining semesters because the computation is complicated, and
         * currently the implementation is slightly buggy
         */
        List<Module> tempList = new ArrayList<>();
        tempList.add(moduleToUnassignCopy);
        tempList.add(dummyModuleCopy);
        int remainingSemesters = CourseInfo.computeSemesters(
                courseInfo.getSemesters(), tempList).get().getRemainingSemesters();

        CourseInfo editedCourseInfo = new CourseInfoBuilder()
            .withCap(courseCap) // total cap no change
            .withCredits(totalCourseCreditsRequired, totalCourseCreditsFulfilled
                    - (creditsCs1101s)) // total credits required should be updated
            .withSemestersTwoParameters(totalSemesters, remainingSemesters)
            .build();
        expectedModel.setCourseInfo(editedCourseInfo);

        // Now, specify 1 modules to unassign to that requirement (two currently assigned to it) and test it!
        RequirementCode requirementCode = new RequirementCode(VALID_REQ_CODE_GE);
        List<ModuleCode> moduleCodes = new ArrayList<ModuleCode>() {
            {
                add(new ModuleCode(VALID_MODULE_CODE_CS1101S));
            }
        };

        String formattedModulesToUnassign = getFormattedModulesStr(new ArrayList<Module>() {
            {
                add(moduleToUnassignCopy);
            }
        }, "\n");

        String expectedMessage = String.format(MESSAGE_REQUIREMENT_UNASSIGN_SUCCESS,
                editedRequirementA.getRequirementCode(),
                formattedModulesToUnassign);

        RequirementUnassignCommand cmd = new RequirementUnassignCommand(requirementCode, moduleCodes);

        assertExecuteSuccess(cmd, model, expectedModel, expectedMessage);
    }

    @Test
    public void equals() {
        final RequirementCode requirementCode = new RequirementCode(VALID_REQ_CODE_UE);
        final List<ModuleCode> moduleCodes = new ArrayList<ModuleCode>() {
            {
                add(new ModuleCode(VALID_MODULE_CODE_CS1101S));
                add(new ModuleCode(VALID_MODULE_CODE_CS2040));
                add(new ModuleCode(VALID_MODULE_CODE_CS2100));
            }
        };

        final RequirementUnassignCommand requirementAssignCommand = new RequirementUnassignCommand(
                requirementCode, moduleCodes);

        // null
        assertFalse(requirementAssignCommand.equals(null));

        // same requirement unassign command
        assertTrue(requirementAssignCommand.equals(requirementAssignCommand));

        // different type
        Module module = new ModuleBuilder().build();
        assertFalse(requirementAssignCommand.equals(module));

        RequirementUnassignCommand otherRequirementUnassignCommand;
        RequirementCode otherRequirementCode;
        List<ModuleCode> otherModuleCodes;

        // different requirement unassign command; only requirement code different
        otherRequirementCode = new RequirementCode(VALID_REQ_CODE_GE);
        otherRequirementUnassignCommand = new RequirementUnassignCommand(otherRequirementCode, moduleCodes);
        assertFalse(requirementAssignCommand.equals(otherRequirementUnassignCommand));

        // different requirement unassign command; only module codes different
        otherModuleCodes = new ArrayList<ModuleCode>() {
            {
                add(new ModuleCode(VALID_MODULE_CODE_CS1101S));
                add(new ModuleCode(VALID_MODULE_CODE_CS2040));
            }
        };
        otherRequirementUnassignCommand = new RequirementUnassignCommand(requirementCode,
                otherModuleCodes);
        assertFalse(requirementAssignCommand.equals(otherRequirementUnassignCommand));

        // different requirement unassign command; both requirement code and module codes, different
        otherRequirementCode = new RequirementCode(VALID_REQ_CODE_GE);
        otherModuleCodes = new ArrayList<ModuleCode>() {
            {
                add(new ModuleCode(VALID_MODULE_CODE_CS1101S));
                add(new ModuleCode(VALID_MODULE_CODE_CS2040));
            }
        };
        otherRequirementUnassignCommand = new RequirementUnassignCommand(otherRequirementCode, otherModuleCodes);
        assertFalse(requirementAssignCommand.equals(otherRequirementUnassignCommand));
    }
}
