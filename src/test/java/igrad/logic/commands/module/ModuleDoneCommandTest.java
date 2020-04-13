package igrad.logic.commands.module;

//@@author nathanaelseen

import static igrad.logic.commands.CommandTestUtil.assertExecuteFailure;
import static igrad.logic.commands.CommandTestUtil.assertExecuteSuccess;
import static igrad.logic.commands.module.ModuleCommand.MESSAGE_MODULE_NON_EXISTENT;
import static igrad.logic.commands.module.ModuleCommandTestUtil.VALID_MODULE_CODE_CS1101S;
import static igrad.logic.commands.module.ModuleCommandTestUtil.VALID_MODULE_CODE_CS2040;
import static igrad.logic.commands.module.ModuleCommandTestUtil.VALID_MODULE_CODE_CS2100;
import static igrad.logic.commands.module.ModuleCommandTestUtil.VALID_MODULE_GRADE_CS1101S;
import static igrad.logic.commands.module.ModuleCommandTestUtil.VALID_MODULE_GRADE_CS2100;
import static igrad.logic.commands.module.ModuleDoneCommand.MESSAGE_MODULE_DONE_SUCCESS;
import static igrad.logic.commands.requirement.RequirementCommandTestUtil.VALID_REQ_CODE_GE;
import static igrad.logic.commands.requirement.RequirementCommandTestUtil.VALID_REQ_CODE_UE;
import static igrad.testutil.Assert.assertThrows;
import static igrad.testutil.TypicalModules.CS2040;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import igrad.logic.commands.module.ModuleDoneCommand.EditModuleDescriptor;
import igrad.model.Model;
import igrad.model.ModelManager;
import igrad.model.course.CourseInfo;
import igrad.model.module.Module;
import igrad.model.module.ModuleCode;
import igrad.model.requirement.Requirement;
import igrad.testutil.CourseInfoBuilder;
import igrad.testutil.EditModuleDescriptorBuilder2;
import igrad.testutil.ModuleBuilder;
import igrad.testutil.RequirementBuilder;

/**
 * Contains integration tests (interaction with the Model) and unit tests
 * for ModuleDoneCommand.
 */
public class ModuleDoneCommandTest {
    @Test
    public void constructor_null_throwsNullPointerException() {
        // ModuleCode null, but EditModuleDescriptor not null
        ModuleCode moduleCodeA = null;
        EditModuleDescriptor descriptorA = new EditModuleDescriptorBuilder2()
            .build();
        assertThrows(NullPointerException.class, (
        ) -> new ModuleDoneCommand(moduleCodeA, descriptorA));

        // EditModuleDescriptor null, but ModuleCode not null
        ModuleCode moduleCodeB = new ModuleCode(VALID_MODULE_CODE_CS1101S);
        EditModuleDescriptor descriptorB = null;
        assertThrows(NullPointerException.class, (
        ) -> new ModuleDoneCommand(moduleCodeB, descriptorB));
    }

    @Test
    public void execute_nullModel_throwsNullPointerException() {
        Model model = null;
        ModuleCode moduleCode = new ModuleCode(VALID_MODULE_CODE_CS1101S);
        EditModuleDescriptor descriptor = new EditModuleDescriptorBuilder2()
            .withGrade(VALID_MODULE_GRADE_CS1101S)
            .build();
        ModuleDoneCommand cmd = new ModuleDoneCommand(moduleCode, descriptor);
        assertThrows(NullPointerException.class, () -> cmd.execute(model));
    }

    @Test
    public void execute_moduleNonExistentOnEmptyModel_failure() {
        Model model = new ModelManager(); // set-up an empty Model
        ModuleCode moduleCode = new ModuleCode(VALID_MODULE_CODE_CS1101S);
        EditModuleDescriptor descriptor = new EditModuleDescriptorBuilder2()
            .withGrade(VALID_MODULE_GRADE_CS1101S)
            .build();
        ModuleDoneCommand cmd = new ModuleDoneCommand(moduleCode, descriptor);

        assertExecuteFailure(cmd, model, String.format(MESSAGE_MODULE_NON_EXISTENT, moduleCode.value));
    }

    @Test
    public void execute_moduleNonExistentNonEmptyModel_failure() {
        // set-up a non-empty Model
        Model model = new ModelManager();
        model.addModule(CS2040);

        ModuleCode moduleCode = new ModuleCode(VALID_MODULE_CODE_CS1101S);
        EditModuleDescriptor descriptor = new EditModuleDescriptorBuilder2()
            .withGrade(VALID_MODULE_GRADE_CS1101S)
            .build();
        ModuleDoneCommand cmd = new ModuleDoneCommand(moduleCode, descriptor);

        assertExecuteFailure(cmd, model, String.format(MESSAGE_MODULE_NON_EXISTENT, moduleCode.value));
    }

    @Test
    public void execute_existentModule_success() {
        //set-up our Model
        Model model = new ModelManager();
        int creditsCs1101s = 4;
        int creditsCs2040 = 4;
        String gradeCs1101s = "A";
        String gradeCs2040 = "B";
        String semesterCs1101s = "Y1S1";
        int totalReqCreditsAssigned = creditsCs1101s + creditsCs2040;
        int totalReqCreditsFulfilled = creditsCs2040;
        int totalReqCreditsRequired = 16;
        int totalCourseCreditsRequired = (totalReqCreditsRequired * 2);
        int totalCourseCreditsFulfilled = creditsCs2040 * 2;
        double courseCap = 3.5;
        int totalSemesters = 5;

        // Create a module with no grade
        Module moduleToEdit = new ModuleBuilder()
            .withModuleCode(VALID_MODULE_CODE_CS1101S)
            .withCredits(Integer.toString(creditsCs1101s))
            .withoutOptionals()
            .build();

        // Create a dummy module, with grade 'B'
        Module dummyModule = new ModuleBuilder()
            .withModuleCode(VALID_MODULE_CODE_CS2040)
            .withCredits(Integer.toString(creditsCs2040))
            .withoutOptionals()
            .withGrade(gradeCs2040)
            .build();

        model.addModule(moduleToEdit);
        model.addModule(dummyModule);

        // Create a new requirement and add that module inside
        List<Module> moduleListA = new ArrayList<>();
        moduleListA.add(moduleToEdit);
        moduleListA.add(dummyModule); // add another dummy module inside
        Requirement requirementA = new RequirementBuilder()
            .withRequirementCode(VALID_REQ_CODE_GE)
            .withModules(moduleListA)
            .withCreditsThreeParameters(totalReqCreditsRequired,
                totalReqCreditsAssigned, totalReqCreditsFulfilled)
            .build();
        model.addRequirement(requirementA); // Add that requirement to our Model

        // Create another requirement with that module inside too
        List<Module> moduleListB = new ArrayList<>();
        moduleListB.add(moduleToEdit);
        moduleListB.add(dummyModule); // add another dummy module inside
        Requirement requirementB = new RequirementBuilder()
            .withRequirementCode(VALID_REQ_CODE_UE)
            .withModules(moduleListB)
            .withCreditsThreeParameters(totalReqCreditsRequired,
                totalReqCreditsAssigned, totalReqCreditsFulfilled)
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

        // The module should have its grade updated to 'A'
        Module editedModule = new ModuleBuilder()
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

        expectedModel.addModule(editedModule);
        expectedModel.addModule(dummyModuleCopy);

        // Create an 'updated' requirement and add that 'updated' module inside
        List<Module> editedModuleListA = new ArrayList<>();
        editedModuleListA.add(editedModule);
        editedModuleListA.add(dummyModuleCopy); // add another dummy module inside
        Requirement editedRequirementA = new RequirementBuilder()
            .withRequirementCode(VALID_REQ_CODE_GE)
            .withModules(editedModuleListA)
            .withCreditsThreeParameters(totalReqCreditsRequired,
                totalReqCreditsAssigned, totalReqCreditsFulfilled + creditsCs1101s) // credits fulfilled should update
            .build();
        expectedModel.addRequirement(editedRequirementA); // Add that requirement to our Model

        // Create another requirement with that module inside too
        List<Module> editedModuleListB = new ArrayList<>();
        editedModuleListB.add(editedModule);
        editedModuleListB.add(dummyModuleCopy); // add another dummy module inside
        Requirement editedRequirementB = new RequirementBuilder()
            .withRequirementCode(VALID_REQ_CODE_UE)
            .withModules(editedModuleListB)
            .withCreditsThreeParameters(totalReqCreditsRequired,
                totalReqCreditsAssigned, totalReqCreditsFulfilled + creditsCs1101s) // credits ulfilled should update
            .build();
        expectedModel.addRequirement(editedRequirementB);

        // Finally, create an 'updated' course info and add that 'updated' course info inside
        /*
         * Here we cheat abit to get remaining semesters because the computation is complicated, and
         * currently the implementation is slightly buggy
         */
        List<Module> tempList = new ArrayList<>();
        tempList.add(editedModule);
        tempList.add(dummyModuleCopy);
        int remainingSemesters = CourseInfo.computeSemesters(
                courseInfo.getSemesters(), tempList).get().getRemainingSemesters();

        CourseInfo editedCourseInfo = new CourseInfoBuilder()
            .withCap(4.25)
            .withCredits(totalCourseCreditsRequired, totalCourseCreditsFulfilled
                    + (2 * creditsCs1101s)) // total credits fulfilled should be updated in both requirements
            .withSemestersTwoParameters(totalSemesters, remainingSemesters)
            .build();
        expectedModel.setCourseInfo(editedCourseInfo);

        // Now, specify the edits on Module through the descriptor and test it!
        ModuleCode moduleCode = new ModuleCode(VALID_MODULE_CODE_CS1101S);
        EditModuleDescriptor descriptor = new EditModuleDescriptorBuilder2()
            .withGrade(gradeCs1101s)
            .build();
        ModuleDoneCommand cmd = new ModuleDoneCommand(moduleCode, descriptor);
        String expectedMessage = String.format(MESSAGE_MODULE_DONE_SUCCESS, editedModule);

        assertExecuteSuccess(cmd, model, expectedModel, expectedMessage);
    }

    @Test
    public void equals() {
        final ModuleCode moduleCode = new ModuleCode(VALID_MODULE_CODE_CS1101S);
        final EditModuleDescriptor descriptor = new EditModuleDescriptorBuilder2()
            .withGrade(VALID_MODULE_GRADE_CS1101S)
            .build();

        final ModuleDoneCommand moduleDoneCommand = new ModuleDoneCommand(moduleCode, descriptor);

        // null
        assertFalse(moduleDoneCommand.equals(null));

        // same module done command
        assertTrue(moduleDoneCommand.equals(moduleDoneCommand));

        // different type
        Module module = new ModuleBuilder().build();
        assertFalse(moduleDoneCommand.equals(module));

        ModuleDoneCommand otherModuleDoneCommand;
        ModuleCode otherModuleCode;
        EditModuleDescriptor otherDescriptor;

        // different module done command; only module code different
        otherModuleCode = new ModuleCode(VALID_MODULE_CODE_CS2100);
        otherModuleDoneCommand = new ModuleDoneCommand(otherModuleCode, descriptor);
        assertFalse(moduleDoneCommand.equals(otherModuleDoneCommand));

        // different module done command; only descriptor different
        otherDescriptor = new EditModuleDescriptorBuilder2()
            .withGrade(VALID_MODULE_GRADE_CS2100)
            .build();
        otherModuleDoneCommand = new ModuleDoneCommand(moduleCode, otherDescriptor);
        assertFalse(moduleDoneCommand.equals(otherModuleDoneCommand));

        // different module done command; both module code and descriptor, different
        otherModuleCode = new ModuleCode(VALID_MODULE_CODE_CS2100);
        otherDescriptor = new EditModuleDescriptorBuilder2()
            .withGrade(VALID_MODULE_GRADE_CS2100)
            .build();
        otherModuleDoneCommand = new ModuleDoneCommand(otherModuleCode, otherDescriptor);
        assertFalse(moduleDoneCommand.equals(otherModuleDoneCommand));
    }
}
