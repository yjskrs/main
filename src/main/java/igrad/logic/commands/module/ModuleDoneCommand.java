package igrad.logic.commands.module;

import static igrad.commons.util.CollectionUtil.requireAllNonNull;
import static igrad.logic.parser.CliSyntax.PREFIX_GRADE;
import static igrad.logic.parser.CliSyntax.PREFIX_MODULE_CODE;
import static java.util.Objects.requireNonNull;

import java.util.Optional;
import java.util.Set;

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
 * Marks the module as done, with a specified grade.
 */
public class ModuleDoneCommand extends ModuleCommand {
    public static final String COMMAND_WORD = MODULE_COMMAND_WORD + "done";

    public static final String MESSAGE_DETAILS = COMMAND_WORD + ": Marks a module as done (with a grade) of the "
        + "module identified by its module code. Existing module (grade) will be overwritten by the input values.\n";

    public static final String MESSAGE_USAGE = "Parameter(s): MODULE CODE "
        + PREFIX_GRADE + "GRADE\n"
        + "Example: " + COMMAND_WORD + " "
        + PREFIX_MODULE_CODE + "CS2103T "
        + PREFIX_GRADE + "A+";

    public static final String MESSAGE_HELP = MESSAGE_DETAILS + MESSAGE_USAGE;

    public static final String MESSAGE_NOT_EDITED = "Grade must be provided.";

    public static final String MESSAGE_SUCCESS = "Marked Module as done: %1$s";

    private ModuleCode moduleCode;
    private EditModuleGradeDescriptor editModuleGradeDescriptor;

    /**
     * @param moduleCode                of the module in the filtered module list to edit
     * @param editModuleGradeDescriptor details to edit the module with
     */
    public ModuleDoneCommand(ModuleCode moduleCode, EditModuleGradeDescriptor editModuleGradeDescriptor) {
        requireAllNonNull(moduleCode, editModuleGradeDescriptor);

        this.moduleCode = moduleCode;
        this.editModuleGradeDescriptor = new EditModuleGradeDescriptor(editModuleGradeDescriptor);
    }

    /**
     * Creates and returns a {@code Module} with the details of {@code moduleToEdit}
     * edited with {@code editModuleGradeDescriptor}.
     */
    private static Module createEditedModule(Module moduleToEdit,
                                             ModuleDoneCommand.EditModuleGradeDescriptor editModuleGradeDescriptor) {
        assert moduleToEdit != null;

        ModuleCode moduleCode = moduleToEdit.getModuleCode();

        // Just copy everything from {@code moduleToEdit} to our new {@code Module}
        Title updatedTitle = moduleToEdit.getTitle();
        Credits updatedCredits = moduleToEdit.getCredits();
        Optional<Memo> updatedMemo = moduleToEdit.getMemo();
        Optional<Semester> updatedSemester = moduleToEdit.getSemester();
        Optional<Description> updatedDescription = moduleToEdit.getDescription();
        Set<Tag> updatedTags = moduleToEdit.getTags();

        /*
         * It's compulsory for Grade to be optionally edited/updated. This should have already been
         * guaranteed through the validations in the ModuleDoneCommandParser
         */
        Optional<Grade> updatedGrade = editModuleGradeDescriptor.getGrade();

        return new Module(updatedTitle, moduleCode, updatedCredits, updatedMemo, updatedSemester,
            updatedDescription, updatedGrade, updatedTags);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        Module moduleToEdit = model.getModuleByModuleCode(moduleCode)
            .orElseThrow(() -> new CommandException(MESSAGE_MODULE_NON_EXISTENT));
        Module editedModule = createEditedModule(moduleToEdit, editModuleGradeDescriptor);

        model.setModule(moduleToEdit, editedModule);
        model.updateFilteredModuleList(Model.PREDICATE_SHOW_ALL_MODULES);
        return new CommandResult(String.format(MESSAGE_SUCCESS, editedModule));
    }

    /**
     * Stores the grade to edit the module with, and its used in the module done command to mark (edit)
     * a module with a grade. Each non-empty field value will replace the
     * corresponding field value of the module.
     */
    public static class EditModuleGradeDescriptor {
        private Optional<Grade> grade;

        public EditModuleGradeDescriptor() {
        }

        /**
         * Copy constructor.
         */
        public EditModuleGradeDescriptor(ModuleDoneCommand.EditModuleGradeDescriptor toCopy) {
            setGrade(toCopy.grade);
        }

        public Optional<Grade> getGrade() {
            return grade;
        }

        public void setGrade(Optional<Grade> grade) {
            this.grade = grade;
        }

        @Override
        public boolean equals(Object other) {
            // short circuit if same object
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof ModuleDoneCommand.EditModuleGradeDescriptor)) {
                return false;
            }

            // state check
            ModuleDoneCommand.EditModuleGradeDescriptor e = (ModuleDoneCommand.EditModuleGradeDescriptor) other;

            return getGrade().equals(e.getGrade());
        }
    }

}
