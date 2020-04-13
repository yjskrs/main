package igrad.logic.commands.requirement;

//@@author yjskrs

import static igrad.logic.parser.CliSyntax.PREFIX_CREDITS;
import static igrad.logic.parser.CliSyntax.PREFIX_TITLE;
import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

import igrad.commons.core.GuiSettings;
import igrad.logic.commands.Command;
import igrad.logic.commands.CommandResult;
import igrad.logic.commands.CommandTestUtil;
import igrad.logic.commands.CommandUtil;
import igrad.logic.commands.exceptions.CommandException;
import igrad.model.CourseBook;
import igrad.model.Model;
import igrad.model.ReadOnlyCourseBook;
import igrad.model.ReadOnlyUserPrefs;
import igrad.model.avatar.Avatar;
import igrad.model.course.CourseInfo;
import igrad.model.module.Module;
import igrad.model.module.ModuleCode;
import igrad.model.module.UniqueModuleList;
import igrad.model.requirement.Requirement;
import igrad.model.requirement.RequirementCode;
import igrad.model.requirement.UniqueRequirementList;
import igrad.model.requirement.exceptions.RequirementNotFoundException;
import javafx.collections.ObservableList;

/**
 * Utility class that stores static strings/ints used in creating Requirement objects
 * or RequirementCommand objects.
 */
public class RequirementCommandTestUtil extends CommandTestUtil {

    // valid requirement arguments
    public static final String VALID_REQ_TITLE_CSF = "Computer Science Foundation";
    public static final String VALID_REQ_TITLE_CSBD = "Computer Science Breadth and Depth";
    public static final String VALID_REQ_TITLE_MS = "Mathematics and Sciences";
    public static final String VALID_REQ_TITLE_IP = "IT Professionalism";
    public static final String VALID_REQ_TITLE_UE = "Unrestricted Electives";
    public static final String VALID_REQ_TITLE_GE = "General Electives";
    public static final String VALID_REQ_CODE_CSF = "CSF0";
    public static final String VALID_REQ_CODE_CSBD = "CSBD0";
    public static final String VALID_REQ_CODE_MS = "MS0";
    public static final String VALID_REQ_CODE_IP = "IP0";
    public static final String VALID_REQ_CODE_UE = "UE0";
    public static final String VALID_REQ_CODE_GE = "GE0";
    public static final String VALID_REQ_CREDITS_CSF = "32";
    public static final String VALID_REQ_CREDITS_CSBD = "48";
    public static final String VALID_REQ_CREDITS_MS = "16";
    public static final String VALID_REQ_CREDITS_IP = "12";
    public static final String VALID_REQ_CREDITS_UE = "32";
    public static final String VALID_REQ_CREDITS_GE = "20";
    public static final int VALID_REQ_CREDITS_REQUIRED_IP = 12;
    public static final int VALID_REQ_CREDITS_ASSIGNED_IP = 4;
    public static final int VALID_REQ_CREDITS_FULFILLED_IP = 4;

    // invalid requirement arguments
    public static final String INVALID_REQ_CODE_DECIMAL = "RE1.0";
    public static final String INVALID_REQ_CODE_SYMBOL = "RE<";

    public static final String INVALID_REQ_CREDITS_ALPHABET = "a";
    public static final String INVALID_REQ_CREDITS_DECIMAL = "40.0";
    public static final String INVALID_REQ_CREDITS_SYMBOL = "&";

    // requirement title descriptor for command entered
    public static final String REQ_TITLE_DESC_CSF = " " + PREFIX_TITLE + VALID_REQ_TITLE_CSF;
    public static final String REQ_TITLE_DESC_CSBD = " " + PREFIX_TITLE + VALID_REQ_TITLE_CSBD;
    public static final String REQ_TITLE_DESC_MS = " " + PREFIX_TITLE + VALID_REQ_TITLE_MS;
    public static final String REQ_TITLE_DESC_IP = " " + PREFIX_TITLE + VALID_REQ_TITLE_IP;
    public static final String REQ_TITLE_DESC_UE = " " + PREFIX_TITLE + VALID_REQ_TITLE_UE;
    public static final String REQ_TITLE_DESC_GE = " " + PREFIX_TITLE + VALID_REQ_TITLE_GE;

    public static final String REQ_CREDITS_DESC_CSF = " " + PREFIX_CREDITS + VALID_REQ_CREDITS_CSF;
    public static final String REQ_CREDITS_DESC_CSBD = " " + PREFIX_CREDITS + VALID_REQ_CREDITS_CSBD;
    public static final String REQ_CREDITS_DESC_MS = " " + PREFIX_CREDITS + VALID_REQ_CREDITS_MS;
    public static final String REQ_CREDITS_DESC_IP = " " + PREFIX_CREDITS + VALID_REQ_CREDITS_REQUIRED_IP;
    public static final String REQ_CREDITS_DESC_UE = " " + PREFIX_CREDITS + VALID_REQ_CREDITS_UE;
    public static final String REQ_CREDITS_DESC_GE = " " + PREFIX_CREDITS + VALID_REQ_CREDITS_GE;

    public static final String INVALID_REQ_CREDITS_DESC = " " + PREFIX_CREDITS + INVALID_REQ_CREDITS_ALPHABET;

    /**
     * Executes the {@code command} and checks if the returned {@link CommandResult} matches CommandResult
     * created with {@code expectedMessage} and that the result requirement lists are the same.
     */
    public static void assertCommandSuccess(Command command, Model actualModel, String expectedMessage,
                                            Model expectedModel) {
        try {
            CommandResult expectedCommandResult = new CommandResult(expectedMessage);
            CommandResult result = command.execute(actualModel);
            assertEquals(expectedCommandResult, result);
            assertEquals(expectedModel.getRequirementList(), actualModel.getRequirementList());
        } catch (CommandException ce) {
            throw new AssertionError(ce.getMessage(), ce);
        }
    }

    /**
     * Executes the {@code command} and checks if the exception thrown has the same message
     * as {@code expectedMessage} and the resultant {@code expectedModel} is the same
     * as {@code actualModel}.
     */
    public static void assertCommandThrows(Command command, Model actualModel, String expectedMessage,
                                            Model expectedModel) {
        try {
            command.execute(actualModel);
        } catch (CommandException ce) {
            assertEquals(ce.getMessage(), expectedMessage);
            assertEquals(expectedModel.getRequirementList(), actualModel.getRequirementList());
        }
    }

    public static void setupCourseInfo(Model model) {
        CourseInfo courseToEdit = model.getCourseInfo();
        CourseInfo editedCourseInfo = CommandUtil.createEditedCourseInfo(courseToEdit, model);
        model.setCourseInfo(editedCourseInfo);
    }

    /**
     * A default model stub with all non-essential methods failing.
     */
    public static class ModelStub implements Model {

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
        public boolean undoCourseBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public List<Module> exportModuleList() {
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
            return new CourseBook();
        }

        @Override
        public void setCourseBook(ReadOnlyCourseBook courseBook) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasModule(Module module) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasModulePrerequisites(Module module) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasModulePreclusions(Module module) {
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
            return new CourseInfo();
        }

        @Override
        public void setCourseInfo(CourseInfo editedCourseInfo) {
        }

        @Override
        public boolean isCourseNameSet() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public String getRandomQuoteString() {
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
        public Optional<Requirement> getRequirement(RequirementCode requirementCode) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public List<Requirement> getRequirementsWithModule(Module module) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Optional<Module> getModule(ModuleCode moduleCode) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public List<Module> getModules(List<ModuleCode> moduleCodes) {
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
            return new UniqueModuleList().asUnmodifiableObservableList();
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
    }

    /**
     * A Model stub that contains a single requirement.
     */
    public static class ModelStubWithRequirement extends ModelStub {
        private Requirement requirement;

        ModelStubWithRequirement(Requirement requirement) {
            requireNonNull(requirement);
            this.requirement = requirement;
        }

        @Override
        public Optional<Requirement> getRequirement(RequirementCode requirementCode) {
            if (requirement == null) {
                return Optional.empty();
            }

            if (requirementCode == null) {
                return Optional.empty();
            }

            return requirementCode.equals(requirement.getRequirementCode())
                       ? Optional.of(requirement)
                       : Optional.empty();
        }

        @Override
        public ObservableList<Requirement> getRequirementList() {
            UniqueRequirementList list = new UniqueRequirementList();
            list.setRequirements(Collections.singletonList(requirement));
            return list.asUnmodifiableObservableList();
        }

        @Override
        public boolean hasRequirement(Requirement requirement) {
            requireNonNull(requirement);
            return this.requirement.isSameRequirement(requirement);
        }

        @Override
        public void setRequirement(Requirement target, Requirement newRequirement) {
            if (!hasRequirement(target)) {
                throw new RequirementNotFoundException();
            }

            requirement = newRequirement;
        }

        @Override
        public boolean equals(Object other) {
            return other == this
                       || (other instanceof ModelStubWithRequirement
                               && requirement.equals(((ModelStubWithRequirement) other).requirement));
        }
    }

    /**
     * A Model stub that accepts the requirement being added.
     */
    public static class ModelStubAcceptingRequirementAdded extends ModelStub {
        final ArrayList<Requirement> requirements = new ArrayList<>();

        @Override
        public boolean hasRequirement(Requirement requirement) {
            requireNonNull(requirement);
            return requirements.stream().anyMatch(requirement::isSameRequirement);
        }

        @Override
        public void addRequirement(Requirement requirement) {
            requireNonNull(requirement);
            requirements.add(requirement);
        }

        @Override
        public ObservableList<Requirement> getRequirementList() {
            UniqueRequirementList list = new UniqueRequirementList();
            list.setRequirements(requirements);
            return list.asUnmodifiableObservableList();
        }

        @Override
        public boolean equals(Object other) {
            if (other == null) {
                return false;
            }

            if (other == this) {
                return true;
            }

            ModelStubAcceptingRequirementAdded stub = (ModelStubAcceptingRequirementAdded) other;

            return requirements.stream().allMatch(requirement -> stub.requirements.stream()
                                                                     .anyMatch(requirement::equals))
                && stub.requirements.stream().allMatch(requirement -> requirements.stream()
                                                                          .anyMatch(requirement::equals));
        }
    }
}
