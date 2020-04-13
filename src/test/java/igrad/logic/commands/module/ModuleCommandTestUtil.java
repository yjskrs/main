package igrad.logic.commands.module;

import static igrad.logic.parser.CliSyntax.PREFIX_CREDITS;
import static igrad.logic.parser.CliSyntax.PREFIX_GRADE;
import static igrad.logic.parser.CliSyntax.PREFIX_MODULE_CODE;
import static igrad.logic.parser.CliSyntax.PREFIX_SEMESTER;
import static igrad.logic.parser.CliSyntax.PREFIX_TITLE;
import static igrad.testutil.Assert.assertThrows;
import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

import igrad.commons.core.GuiSettings;
import igrad.commons.exceptions.DataConversionException;
import igrad.logic.commands.Command;
import igrad.logic.commands.CommandResult;
import igrad.logic.commands.exceptions.CommandException;
import igrad.model.CourseBook;
import igrad.model.Model;
import igrad.model.ReadOnlyCourseBook;
import igrad.model.ReadOnlyUserPrefs;
import igrad.model.avatar.Avatar;
import igrad.model.course.CourseInfo;
import igrad.model.module.Module;
import igrad.model.module.ModuleCode;
import igrad.model.module.ModulePreclusions;
import igrad.model.module.ModulePrerequisites;
import igrad.model.requirement.Requirement;
import igrad.model.requirement.RequirementCode;
import igrad.testutil.EditModuleDescriptorBuilder;
import javafx.collections.ObservableList;

/**
 * Contains helper methods for testing commands.
 */
public class ModuleCommandTestUtil {

    // valid requirement arguments
    public static final String VALID_MODULE_TITLE_CS1101S = "Programming Methodology";
    public static final String VALID_MODULE_TITLE_CS2100 = "Computer Organisation";
    public static final String VALID_MODULE_TITLE_CS2103T = "Software Engineering";
    public static final String VALID_MODULE_TITLE_CS2101 = "Effective Communication for Computing Professionals";
    public static final String VALID_MODULE_TITLE_CS2040 = "Data Structures and Algorithms";

    public static final String VALID_MODULE_CODE_CS1101S = "CS1101S";
    public static final String VALID_MODULE_CODE_CS2100 = "CS2100";
    public static final String VALID_MODULE_CODE_CS2103T = "CS2103T";
    public static final String VALID_MODULE_CODE_CS2101 = "CS2101";
    public static final String VALID_MODULE_CODE_CS2040 = "CS2040";

    public static final String VALID_MODULE_CREDITS_4 = "4";
    public static final String VALID_MODULE_CREDITS_6 = "6";

    public static final String VALID_MODULE_SEMESTER_Y1S1 = "Y1S1";
    public static final String VALID_MODULE_SEMESTER_Y2S2 = "Y2S2";

    public static final String VALID_MODULE_SEMESTER_CS1101S = "Y1S1";
    public static final String VALID_MODULE_SEMESTER_CS2100 = "Y2S2";

    public static final String VALID_MODULE_GRADE_A = "A";
    public static final String VALID_MODULE_GRADE_B = "B";

    public static final String VALID_MODULE_GRADE_CS1101S = "A";
    public static final String VALID_MODULE_GRADE_CS2100 = "B";

    // invalid requirement arguments
    public static final String INVALID_MODULE_CODE = "CS2040S&";
    public static final String INVALID_MODULE_GRADE = "A*";
    public static final String INVALID_MODULE_CREDITS = "4%";
    public static final String INVALID_MODULE_SEMESTER = "4%";


    // module title descriptor for command entered
    public static final String MODULE_TITLE_DESC_CS1101S = " " + PREFIX_TITLE + VALID_MODULE_TITLE_CS1101S;
    public static final String MODULE_TITLE_DESC_CS2100 = " " + PREFIX_TITLE + VALID_MODULE_TITLE_CS2100;

    // module code descriptor for command entered
    public static final String MODULE_MODULE_CODE_DESC_CS1101S = " " + PREFIX_MODULE_CODE + VALID_MODULE_CODE_CS1101S;
    public static final String MODULE_MODULE_CODE_DESC_CS2100 = " " + PREFIX_MODULE_CODE + VALID_MODULE_CODE_CS2100;

    // module credits descriptor for command entered
    public static final String MODULE_CREDITS_DESC_CS1101S = " " + PREFIX_CREDITS + VALID_MODULE_CREDITS_4;
    public static final String MODULE_CREDITS_DESC_CS2100 = " " + PREFIX_CREDITS + VALID_MODULE_CREDITS_6;

    // module grade descriptor for command entered
    public static final String MODULE_GRADE_DESC_CS1101S = " " + PREFIX_GRADE + VALID_MODULE_GRADE_CS1101S;
    public static final String MODULE_GRADE_DESC_CS2100 = " " + PREFIX_GRADE + VALID_MODULE_GRADE_CS2100;

    // module semester descriptor for command entered
    public static final String MODULE_SEMESTER_DESC_CS1101S = " " + PREFIX_SEMESTER + VALID_MODULE_SEMESTER_CS1101S;
    public static final String MODULE_SEMESTER_DESC_CS2100 = " " + PREFIX_SEMESTER + VALID_MODULE_SEMESTER_CS2100;

    // Starting with ' ' not allowed in titles
    public static final String INVALID_MODULE_TITLE_DESC = " " + PREFIX_TITLE + " Programming Methodology";

    // Starting with '/' not allowed in titles
    public static final String INVALID_MODULE_TITLE_SLASH_DESC = " " + PREFIX_TITLE + "/Programming Methodology";

    // Empty titles are not allowed
    public static final String INVALID_MODULE_TITLE_EMPTY_DESC = " " + PREFIX_TITLE;

    // '&' not allowed in module codes
    public static final String INVALID_MODULE_CODE_DESC = " " + PREFIX_MODULE_CODE + INVALID_MODULE_CODE;

    // '&' not allowed in credits
    public static final String INVALID_MODULE_CREDITS_DESC = " " + PREFIX_CREDITS + INVALID_MODULE_CREDITS;

    // '&' not allowed in semester
    public static final String INVALID_MODULE_SEMESTER_DESC = " " + PREFIX_SEMESTER + INVALID_MODULE_SEMESTER;

    // '*' not allowed in grade
    public static final String INVALID_MODULE_GRADE_DESC = " " + PREFIX_GRADE + INVALID_MODULE_GRADE;

    public static final String PREAMBLE_WHITESPACE = "\t  \r  \n";
    public static final String PREAMBLE_NON_EMPTY = "NonEmptyPreamble";

    public static final ModuleEditCommand.EditModuleDescriptor DESC_PROGRAMMING_METHODOLOGY;
    public static final ModuleEditCommand.EditModuleDescriptor DESC_COMPUTER_ORGANISATION;

    static {
        DESC_PROGRAMMING_METHODOLOGY = new EditModuleDescriptorBuilder()
            .withTitle(VALID_MODULE_TITLE_CS1101S)
            .withModuleCode(VALID_MODULE_CODE_CS1101S)
            .withCredits(VALID_MODULE_CREDITS_4)
            .withSemester(VALID_MODULE_SEMESTER_Y1S1).build();

        DESC_COMPUTER_ORGANISATION = new EditModuleDescriptorBuilder()
            .withTitle(VALID_MODULE_TITLE_CS2100)
            .withModuleCode(VALID_MODULE_CODE_CS2100)
            .withCredits(VALID_MODULE_CREDITS_6)
            .withSemester(VALID_MODULE_SEMESTER_Y2S2).build();
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - the returned {@link CommandResult} matches {@code expectedCommandResult} <br>
     * - the {@code actualModel} matches {@code expectedModel}
     */
    public static void assertCommandSuccess(Command command, Model actualModel, CommandResult expectedCommandResult,
                                            Model expectedModel) {
        try {
            CommandResult result = command.execute(actualModel);
            assertEquals(expectedCommandResult, result);
            assertEquals(expectedModel, actualModel);
        } catch (CommandException ce) {
            throw new AssertionError("Execution of command should not fail.", ce);
        }
    }

    /**
     * Convenience wrapper to {@link #assertCommandSuccess(Command, Model, CommandResult, Model)}
     * that takes a string {@code expectedMessage}.
     */
    public static void assertCommandSuccess(Command command, Model actualModel, String expectedMessage,
                                            Model expectedModel) {
        CommandResult expectedCommandResult = new CommandResult(expectedMessage);
        assertCommandSuccess(command, actualModel, expectedCommandResult, expectedModel);
    }


    /**
     * Executes the given {@code command}, confirms that <br>
     * - a {@code CommandException} is thrown <br>
     * - the CommandException message matches {@code expectedMessage} <br>
     * - the course book, filtered module list and selected module in {@code actualModel} remain unchanged
     */
    public static void assertCommandFailure(Command command, Model actualModel, String expectedMessage) {
        // we are unable to defensively copy the model for comparison later, so we can
        // only do so by copying its components.
        CourseBook expectedCourseBook = new CourseBook(actualModel.getCourseBook());
        List<Module> expectedFilteredList = new ArrayList<>(actualModel.getFilteredModuleList());

        assertThrows(CommandException.class, expectedMessage, () -> command.execute(actualModel));
        assertEquals(expectedCourseBook, actualModel.getCourseBook());
        assertEquals(expectedFilteredList, actualModel.getFilteredModuleList());
    }


    /**
     * A default model stub that have all of the methods failing.
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
        public boolean undoCourseBook() throws IOException, DataConversionException {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public List<Module> exportModuleList() throws IOException {
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
        public Optional<Requirement> getRequirement(RequirementCode requirementCode) {
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
        public List<Requirement> getRequirementsWithModule(Module module) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasModulePreclusions(Module module) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasModulePrerequisites(Module module) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public String getRandomQuoteString() {
            throw new AssertionError("This method should not be called.");
        }
    }

    /**
     * A Model stub that contains a single module.
     */
    public static class ModelStubWithModule extends ModelStub {
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
    public static class ModelStubAcceptingModuleAdded extends ModelStub {
        final ArrayList<Module> modulesAdded = new ArrayList<>();

        @Override
        public boolean hasModule(Module module) {
            requireNonNull(module);
            return modulesAdded.stream().anyMatch(module::isSameModule);
        }

        @Override
        public void addModule(Module module) {
            requireNonNull(module);
            modulesAdded.add(module);
        }

        @Override
        public ReadOnlyCourseBook getCourseBook() {
            return new CourseBook();
        }

        public ArrayList<Module> getModulesAdded() {
            return modulesAdded;
        }

        @Override
        public boolean hasModulePreclusions(Module module) {
            requireNonNull(module);

            boolean hasModulePreclusions = false;

            ModulePreclusions preclusions = module.getPreclusions();

            if (!preclusions.isEmpty()) {

                List<ModuleCode> moduleCodes = preclusions.getModuleCodes();

                for (ModuleCode preclusion : moduleCodes) {
                    Optional<Module> mOpt = getModule(preclusion);
                    if (mOpt.isPresent()) {
                        hasModulePreclusions = true;
                    }
                }

            }

            return hasModulePreclusions;

        }

        @Override
        public boolean hasModulePrerequisites(Module module) {
            requireNonNull(module);

            boolean hasModulePrerequisites = true;

            ModulePrerequisites prerequisites = module.getPrequisites();

            if (!prerequisites.isEmpty()) {

                List<ModuleCode> moduleCodes = prerequisites.getModuleCodes();

                for (ModuleCode moduleCode : moduleCodes) {
                    Optional<Module> mOpt = getModule(moduleCode);
                    if (mOpt.isEmpty()) {
                        hasModulePrerequisites = false;
                    } else {
                        Module m = mOpt.get();
                        if (!m.isDone()) {
                            hasModulePrerequisites = false;
                        }
                    }
                }

            }

            return hasModulePrerequisites;

        }
    }
}

