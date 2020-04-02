package igrad.logic.commands;

import static igrad.testutil.Assert.assertThrows;
import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import igrad.commons.core.GuiSettings;
import igrad.logic.commands.exceptions.CommandException;
import igrad.logic.commands.module.ModuleAddCommand;
import igrad.model.CourseBook;
import igrad.model.Model;
import igrad.model.ReadOnlyCourseBook;
import igrad.model.ReadOnlyUserPrefs;
import igrad.model.avatar.Avatar;
import igrad.model.course.Cap;
import igrad.model.course.CourseInfo;
import igrad.model.module.Module;
import igrad.model.module.ModuleCode;
import igrad.model.requirement.Requirement;
import igrad.model.requirement.RequirementCode;
import igrad.testutil.ModuleBuilder;
import javafx.collections.ObservableList;

public class AddCommandTest {

    @Test
    public void constructor_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new ModuleAddCommand(null));
    }

    @Test
    public void execute_personAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingModuleAdded modelStub = new ModelStubAcceptingModuleAdded();
        Module validModule = new ModuleBuilder().build();

        CommandResult commandResult = new ModuleAddCommand(validModule).execute(modelStub);

        assertEquals(String.format(ModuleAddCommand.MESSAGE_MODULE_ADD_SUCCESS, validModule),
                commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(validModule), modelStub.personsAdded);
    }

    @Test
    public void execute_duplicatePerson_throwsCommandException() {
        Module validModule = new ModuleBuilder().build();
        ModuleAddCommand moduleAddCommand = new ModuleAddCommand(validModule);
        ModelStub modelStub = new ModelStubWithModule(validModule);

        assertThrows(CommandException.class, ModuleAddCommand.MESSAGE_DUPLICATE_MODULE, (
        ) -> moduleAddCommand.execute(modelStub));
    }

    @Test
    public void equals() {
        /*Module programmingMethodology = new ModuleBuilder().withTitle("Programming Methodology").build();
        Module computerOrganisation = new ModuleBuilder().withTitle("Computer Organisation").build();
        ModuleAddCommand addProgrammingMethodologyCommand = new ModuleAddCommand(programmingMethodology);
        ModuleAddCommand addComputerOrganisationCommand = new ModuleAddCommand(computerOrganisation);

        // same object -> returns true
        assertTrue(addProgrammingMethodologyCommand.equals(addComputerOrganisationCommand));

        // same values -> returns true
        ModuleAddCommand addProgrammingMethodologyCommandCopy = new ModuleAddCommand(programmingMethodology);
        assertTrue(addProgrammingMethodologyCommand.equals(addProgrammingMethodologyCommandCopy));

        // different types -> returns false
        assertFalse(addProgrammingMethodologyCommand.equals(1));

        // null -> returns false
        assertFalse(addProgrammingMethodologyCommand.equals(null));

        // different module -> returns false
        assertFalse(addProgrammingMethodologyCommand.equals(addComputerOrganisationCommand));
         */
    }

    /**
     * A default model stub that have all of the methods failing.
     */
    private class ModelStub implements Model {
        @Override
        public ReadOnlyUserPrefs getUserPrefs() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public GuiSettings getGuiSettings() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setGuiSettings(GuiSettings guiSettings) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Path getCourseBookFilePath() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setCourseBookFilePath(Path courseBookFilePath) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Path getBackupCourseBookFilePath() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void resetCourseBook(ReadOnlyCourseBook courseBook) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Avatar getAvatar() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setAvatar(Avatar avatar) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean isSampleAvatar() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyCourseBook getCourseBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setCourseBook(ReadOnlyCourseBook newData) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasModule(Module module) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteModule(Module target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addCourseInfo(CourseInfo courseInfo) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public CourseInfo getCourseInfo() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Cap recomputeCap() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setCourseInfo(CourseInfo editedCourseInfo) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean isCourseNameSet() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addModule(Module module) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setModule(Module target, Module editedModule) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasRequirement(Requirement requirement) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Optional<Requirement> getRequirementByRequirementCode(RequirementCode requirementCode) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Optional<Module> getModuleByModuleCode(ModuleCode moduleCode) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public List<Module> getModulesByModuleCode(List<ModuleCode> moduleCodes) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addRequirement(Requirement requirement) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setRequirement(Requirement target, Requirement editedRequirement) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteRequirement(Requirement requirement) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Module> getFilteredModuleList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public List<Module> getSortedModuleList(Comparator<Module> comparator) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Requirement> getRequirementList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredModuleList(Predicate<Module> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateRequirementList(Predicate<Requirement> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void recalculateRequirementList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public int getTotalCreditsFulfilled() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public int getTotalCreditsRequired() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Cap computeEstimatedCap(Cap capToAchieve, int semsLeft) {
            throw new AssertionError("This method should not be called.");
        }
    }

    /**
     * A Model stub that contains a single module.
     */
    private class ModelStubWithModule extends ModelStub {
        private final Module module;

        ModelStubWithModule(Module module) {
            requireNonNull(module);
            this.module = module;
        }

        @Override
        public boolean hasModule(Module module) {
            requireNonNull(module);
            return this.module.isSameModule(module);
        }
    }

    /**
     * A Model stub that always accept the module being added.
     */
    private class ModelStubAcceptingModuleAdded extends ModelStub {
        final ArrayList<Module> personsAdded = new ArrayList<>();

        @Override
        public boolean hasModule(Module module) {
            requireNonNull(module);
            return personsAdded.stream().anyMatch(module::isSameModule);
        }

        @Override
        public void addModule(Module module) {
            requireNonNull(module);
            personsAdded.add(module);
        }

        @Override
        public ReadOnlyCourseBook getCourseBook() {
            return new CourseBook();
        }
    }
}
