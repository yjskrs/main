package igrad.model;

import static igrad.commons.util.CollectionUtil.requireAllNonNull;
import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import igrad.commons.core.GuiSettings;
import igrad.commons.core.LogsCenter;
import igrad.commons.exceptions.DataConversionException;
import igrad.csvwriter.CsvWriter;
import igrad.model.avatar.Avatar;
import igrad.model.course.CourseInfo;
import igrad.model.module.Module;
import igrad.model.module.ModuleCode;
import igrad.model.module.ModulePreclusions;
import igrad.model.module.ModulePrerequisites;
import igrad.model.module.sorters.SortBySemester;
import igrad.model.quotes.QuoteGenerator;
import igrad.model.requirement.Requirement;
import igrad.model.requirement.RequirementCode;
import igrad.storage.CourseBookStorage;
import igrad.storage.JsonCourseBookStorage;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;

/**
 * Represents the in-memory model of the course book data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);
    private final CourseBook courseBook;
    private final UserPrefs userPrefs;
    private final FilteredList<Module> filteredModules;
    private final FilteredList<Requirement> requirements;
    private final QuoteGenerator quoteGenerator = new QuoteGenerator();

    /**
     * Initializes a ModelManager with the given courseBook and userPrefs.
     */
    public ModelManager(ReadOnlyCourseBook courseBook, ReadOnlyUserPrefs userPrefs) {
        super();
        requireAllNonNull(courseBook, userPrefs);

        logger.fine("Initializing with course book: " + courseBook + " and user prefs " + userPrefs);

        // Retrieving all course book data (modules, course info, requirements, from storage)
        this.courseBook = new CourseBook(courseBook);
        this.userPrefs = new UserPrefs(userPrefs);

        this.requirements = new FilteredList<>(this.courseBook.getRequirementList());
        this.filteredModules = new FilteredList<>(this.courseBook.getModuleList());
        //this.courseInfo = this.courseBook.getCourseInfo();
    }

    public ModelManager() {
        this(new CourseBook(), new UserPrefs());
    }

    //=========== UserPrefs ==================================================================================

    @Override
    public ReadOnlyUserPrefs getUserPrefs() {
        return userPrefs;
    }

    @Override
    public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
        requireNonNull(userPrefs);
        this.userPrefs.resetData(userPrefs);
    }

    @Override
    public String getRandomQuoteString() {
        return quoteGenerator.getRandomQuote();
    }

    @Override
    public GuiSettings getGuiSettings() {
        return userPrefs.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        requireNonNull(guiSettings);
        userPrefs.setGuiSettings(guiSettings);
    }

    @Override
    public Path getCourseBookFilePath() {
        return userPrefs.getCourseBookFilePath();
    }

    @Override
    public void setCourseBookFilePath(Path courseBookFilePath) {
        requireNonNull(courseBookFilePath);
        userPrefs.setCourseBookFilePath(courseBookFilePath);
    }

    @Override
    public Path getBackupCourseBookFilePath() {
        return userPrefs.getBackupCourseBookFilePath();
    }

    @Override
    public Avatar getAvatar() {
        return userPrefs.getAvatar();
    }

    @Override
    public void setAvatar(Avatar avatar) {
        requireNonNull(avatar);
        userPrefs.setAvatar(avatar);
    }

    @Override
    public boolean isSampleAvatar() {
        return this.getUserPrefs().getAvatar().equals(Avatar.getSampleAvatar());
    }

    //=========== CourseBook ================================================================================

    @Override
    public void resetCourseBook(ReadOnlyCourseBook courseBook) {
        this.setCourseBook(new CourseBook());
    }

    @Override
    public ReadOnlyCourseBook getCourseBook() {
        return courseBook;
    }

    @Override
    public void setCourseBook(ReadOnlyCourseBook courseBook) {
        this.courseBook.resetData(courseBook);
    }

    @Override
    public boolean undoCourseBook() throws IOException, DataConversionException {

        boolean hasChanged = false;

        CourseBookStorage courseBookStorage = new JsonCourseBookStorage(
            getBackupCourseBookFilePath()
        );

        Optional<ReadOnlyCourseBook> backupCourseBookOpt = courseBookStorage.readCourseBook();

        if (backupCourseBookOpt.isPresent()) {

            ReadOnlyCourseBook backupCourseBook = backupCourseBookOpt.get();

            if (!courseBook.equals(backupCourseBook)) {
                hasChanged = true;
                setCourseBook(backupCourseBook);
            }
        }

        return hasChanged;
    }

    @Override
    public List<Module> exportModuleList() throws IOException {
        List<Module> moduleList = getFilteredModuleList()
            .stream()
            .filter(m -> m.getSemester().isPresent())
            .sorted(new SortBySemester())
            .collect(Collectors.toList());

        if (moduleList.size() > 0) {
            CsvWriter csvWriter = new CsvWriter(moduleList);
            csvWriter.write();
        }

        return moduleList;
    }

    @Override
    public boolean hasModule(Module module) {
        requireNonNull(module);

        return courseBook.hasModule(module);
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

    @Override
    public void deleteModule(Module target) {
        courseBook.removeModule(target);
    }

    @Override
    public CourseInfo getCourseInfo() {
        return courseBook.getCourseInfo();
    }

    @Override
    public void setCourseInfo(CourseInfo editedCourseInfo) {
        courseBook.setCourseInfo(editedCourseInfo);
    }

    @Override
    public boolean isCourseNameSet() {
        return courseBook.getCourseInfo().getName().isPresent();
    }

    @Override
    public void addCourseInfo(CourseInfo courseInfo) {
        courseBook.addCourseInfo(courseInfo);
    }

    @Override
    public void addModule(Module module) {
        courseBook.addModule(module);
        updateFilteredModuleList(PREDICATE_SHOW_ALL_MODULES);
    }

    @Override
    public void setModule(Module target, Module editedModule) {
        requireAllNonNull(target, editedModule);

        courseBook.setModule(target, editedModule);
    }

    @Override
    public Optional<Module> getModule(ModuleCode moduleCode) {
        requireNonNull(moduleCode);
        return courseBook.getModule(moduleCode);
    }

    @Override
    public List<Module> getModules(List<ModuleCode> moduleCodes) {
        requireNonNull(moduleCodes);
        return courseBook.getModules(moduleCodes);
    }

    @Override
    public boolean hasRequirement(Requirement requirement) {
        requireNonNull(requirement);

        return courseBook.hasRequirement(requirement);
    }

    @Override
    public Optional<Requirement> getRequirement(RequirementCode requirementCode) {
        requireNonNull(requirementCode);

        return courseBook.getRequirement(requirementCode);
    }

    @Override
    public List<Requirement> getRequirementsWithModule(Module module) {
        requireNonNull(module);

        return courseBook.getRequirementsWithModule(module);
    }

    @Override
    public void addRequirement(Requirement requirement) {
        requireNonNull(requirement);

        courseBook.addRequirement(requirement);
        updateRequirementList(PREDICATE_SHOW_ALL_REQUIREMENTS);
    }

    @Override
    public void setRequirement(Requirement target, Requirement editedRequirement) {
        requireAllNonNull(target, editedRequirement);

        courseBook.setRequirement(target, editedRequirement);

    }

    @Override
    public void deleteRequirement(Requirement requirement) {
        requireNonNull(requirement);

        courseBook.removeRequirement(requirement);
    }

    //=========== Filtered Module List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Module} backed by the internal list of
     * {@code versionedCourseBook}
     */
    @Override
    public ObservableList<Module> getFilteredModuleList() {
        return filteredModules;
    }

    @Override
    public List<Module> getSortedModuleList(Comparator<Module> comparator) {

        ObservableList<Module> tempList = getFilteredModuleList();
        List<Module> sortedList = new ArrayList<>(tempList);
        sortedList.sort(comparator);

        return sortedList;
    }

    @Override
    public void updateFilteredModuleList(Predicate<Module> predicate) {
        requireNonNull(predicate);
        filteredModules.setPredicate(predicate);
    }

    //=========== Requirement List Accessors =============================================================

    @Override
    public ObservableList<Requirement> getRequirementList() {
        return requirements;
    }

    @Override
    public void updateRequirementList(Predicate<Requirement> predicate) {
        requireNonNull(predicate);

        requirements.setPredicate(predicate);
    }

    // util

    //@author teriaiw
    @Override
    public boolean equals(Object obj) {
        // short circuit if same object
        if (obj == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(obj instanceof ModelManager)) {
            return false;
        }

        // state check
        ModelManager other = (ModelManager) obj;
        return courseBook.equals(other.courseBook)
            && userPrefs.equals(other.userPrefs)
            && filteredModules.equals(other.filteredModules)
            && requirements.equals(other.requirements);
    }

}
