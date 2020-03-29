package igrad.logic.commands.module;

import static igrad.commons.util.CollectionUtil.requireAllNonNull;
import static igrad.logic.parser.CliSyntax.PREFIX_CREDITS;
import static igrad.logic.parser.CliSyntax.PREFIX_MEMO;
import static igrad.logic.parser.CliSyntax.PREFIX_MODULE_CODE;
import static igrad.logic.parser.CliSyntax.PREFIX_SEMESTER;
import static igrad.logic.parser.CliSyntax.PREFIX_TAG;
import static igrad.logic.parser.CliSyntax.PREFIX_TITLE;
import static java.util.Objects.requireNonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import igrad.commons.util.CollectionUtil;
import igrad.logic.commands.CommandResult;
import igrad.logic.commands.exceptions.CommandException;
import igrad.model.Model;
import igrad.model.module.Credits;
import igrad.model.module.Description;
import igrad.model.module.Grade;
import igrad.model.module.Memo;
import igrad.model.module.Module;
import igrad.model.module.ModuleCode;
import igrad.model.module.Semester;
import igrad.model.module.Title;
import igrad.model.tag.Tag;

/**
 * Edits the details (course name) of the existing module.
 */
public class ModuleEditCommand extends ModuleCommand {

    public static final String COMMAND_WORD = MODULE_COMMAND_WORD + "edit";

    public static final String MESSAGE_DETAILS = COMMAND_WORD + ": Edits the details of the module identified "
        + "by its module code. Existing module will be overwritten by the input values.\n";

    public static final String MESSAGE_USAGE = "Parameter(s): MODULE CODE "
        + "[" + PREFIX_MODULE_CODE + "MODULE_CODE] "
        + "[" + PREFIX_TITLE + "TITLE] "
        + "[" + PREFIX_CREDITS + "CREDITS] "
        + "[" + PREFIX_MEMO + "MEMO] "
        + "[" + PREFIX_SEMESTER + "SEMESTER] "
        + "[" + PREFIX_TAG + "TAGS]...\n"
        + "Example: " + COMMAND_WORD + " CS2040 "
        + PREFIX_MODULE_CODE + "CS2040S "
        + PREFIX_CREDITS + "4";

    public static final String MESSAGE_HELP = MESSAGE_DETAILS + MESSAGE_USAGE;

    public static final String MESSAGE_EDIT_MODULE_SUCCESS = "Edited Module: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_MODULE = "This module already exists in the course book.";

    protected final ModuleCode moduleCode;
    protected final EditModuleDescriptor editModuleDescriptor;

    /**
     * @param moduleCode           of the module in the filtered module list to edit
     * @param editModuleDescriptor details to edit the module with
     */
    public ModuleEditCommand(ModuleCode moduleCode, EditModuleDescriptor editModuleDescriptor) {
        requireAllNonNull(moduleCode, editModuleDescriptor);

        this.moduleCode = moduleCode;
        this.editModuleDescriptor = new EditModuleDescriptor(editModuleDescriptor);
    }

    /**
     * Creates and returns a {@code Module} with the details of {@code moduleToEdit}
     * edited with {@code editModuleDescriptor}.
     */
    private static Module createEditedModule(Module moduleToEdit, EditModuleDescriptor editModuleDescriptor) {
        assert moduleToEdit != null;

        ModuleCode moduleCode = moduleToEdit.getModuleCode();

        // All fields can be optionally updated
        Title updatedTitle = editModuleDescriptor.getTitle().orElse(moduleToEdit.getTitle());
        ModuleCode updatedModuleCode = editModuleDescriptor.getModuleCode().orElse(moduleToEdit.getModuleCode());
        Credits updatedCredits = editModuleDescriptor.getCredits().orElse(moduleToEdit.getCredits());
        Optional<Memo> updatedMemo = editModuleDescriptor.getMemo().orElse(moduleToEdit.getMemo());
        Optional<Semester> updatedSemester = editModuleDescriptor.getSemester().orElse(moduleToEdit.getSemester());
        Optional<Description> updatedDescription = editModuleDescriptor.getDescription()
            .orElse(moduleToEdit.getDescription());
        Set<Tag> updatedTags = editModuleDescriptor.getTags().orElse(moduleToEdit.getTags());

        /*
         * (Note): Grade cannot be edited here (using the edit command), have to do so using the module done
         * command instead. Hence, we're just setting it to whatever value it originally was in the Model classes.
         */
        Optional<Grade> updatedGrade = moduleToEdit.getGrade();

        return new Module(updatedTitle, updatedModuleCode, updatedCredits, updatedMemo, updatedSemester,
            updatedDescription, updatedGrade, updatedTags);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Module> lastShownList = model.getFilteredModuleList();

        /*
         * TODO: For this i still prefer how yijie does it, using streams to query
         *  the list, it looks much neater, please take a look at RequirementEditCommand.java.
         *  However, over there I've also recommended her to have a method in the Model interface
         *  which gets a requirement/module (in your case), by RequirementName/ModuleCode
         *  ~ nathanael
         */
        Optional<Module> moduleToEditOpt = Optional.empty();

        for (Module module : lastShownList) {
            if (module.getModuleCode().equals(moduleCode)) {
                moduleToEditOpt = Optional.of(module);
            }
        }

        if (moduleToEditOpt.isEmpty()) {
            throw new CommandException(MESSAGE_MODULE_NON_EXISTENT);
        }

        Module moduleToEdit = moduleToEditOpt.get();
        Module editedModule = createEditedModule(moduleToEdit, editModuleDescriptor);

        if (!moduleToEdit.isSameModule(editedModule) && model.hasModule(editedModule)) {
            throw new CommandException(MESSAGE_DUPLICATE_MODULE);
        }

        model.setModule(moduleToEdit, editedModule);
        model.updateFilteredModuleList(Model.PREDICATE_SHOW_ALL_MODULES);
        return new CommandResult(String.format(MESSAGE_EDIT_MODULE_SUCCESS, editedModule));
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ModuleEditCommand)) {
            return false;
        }

        // state check
        ModuleEditCommand e = (ModuleEditCommand) other;
        return moduleCode.equals(e.moduleCode)
            && editModuleDescriptor.equals(e.editModuleDescriptor);
    }

    /**
     * Stores the details to edit the module with. Each non-empty field value will replace the
     * corresponding field value of the module.
     */
    public static class EditModuleDescriptor {
        private Title title;
        private ModuleCode moduleCode;
        private Credits credits;
        private Optional<Memo> memo;
        private Optional<Description> description;
        private Optional<Semester> semester;
        private Set<Tag> tags;

        public EditModuleDescriptor() {
        }

        /**
         * Copy constructor.
         * A defensive copy of {@code tags} is used internally.
         */
        public EditModuleDescriptor(EditModuleDescriptor toCopy) {
            setTitle(toCopy.title);
            setModuleCode(toCopy.moduleCode);
            setCredits(toCopy.credits);
            setMemo(toCopy.memo);
            setSemester(toCopy.semester);
            setDescription(toCopy.description);
            setTags(toCopy.tags);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(title, moduleCode, credits, memo, description, semester, tags);
        }

        public Optional<Title> getTitle() {
            return Optional.ofNullable(title);
        }

        public void setTitle(Title title) {
            this.title = title;
        }

        public Optional<ModuleCode> getModuleCode() {
            return Optional.ofNullable(moduleCode);
        }

        public void setModuleCode(ModuleCode moduleCode) {
            this.moduleCode = moduleCode;
        }

        public Optional<Credits> getCredits() {
            return Optional.ofNullable(credits);
        }

        public void setCredits(Credits credits) {
            this.credits = credits;
        }

        public Optional<Optional<Memo>> getMemo() {
            return Optional.ofNullable(memo);
        }

        public void setMemo(Optional<Memo> memo) {
            this.memo = memo;
        }

        public Optional<Optional<Semester>> getSemester() {
            return Optional.ofNullable(semester);
        }

        public void setSemester(Optional<Semester> semester) {
            this.semester = semester;
        }

        public Optional<Optional<Description>> getDescription() {
            return Optional.ofNullable(description);
        }

        public void setDescription(Optional<Description> description) {
            this.description = description;
        }

        /**
         * Returns an unmodifiable tag set, which throws {@code UnsupportedOperationException}
         * if modification is attempted.
         * Returns {@code Optional#empty()} if {@code tags} is null.
         */
        public Optional<Set<Tag>> getTags() {
            return (tags != null) ? Optional.of(Collections.unmodifiableSet(tags)) : Optional.empty();
        }

        /**
         * Sets {@code tags} to this object's {@code tags}.
         * A defensive copy of {@code tags} is used internally.
         */
        public void setTags(Set<Tag> tags) {
            this.tags = (tags != null) ? new HashSet<>(tags) : null;
        }

        @Override
        public boolean equals(Object other) {
            // short circuit if same object
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditModuleDescriptor)) {
                return false;
            }

            // state check
            EditModuleDescriptor e = (EditModuleDescriptor) other;

            return getTitle().equals(e.getTitle())
                && getModuleCode().equals(e.getModuleCode())
                && getCredits().equals(e.getCredits())
                && getMemo().equals(e.getMemo())
                && getDescription().equals(e.getDescription())
                && getSemester().equals(e.getSemester())
                && getTags().equals(e.getTags());
        }
    }
}
