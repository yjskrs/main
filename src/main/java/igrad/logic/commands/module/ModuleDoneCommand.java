package igrad.logic.commands.module;

import static igrad.commons.util.CollectionUtil.requireAllNonNull;
import static igrad.logic.parser.CliSyntax.PREFIX_GRADE;
import static igrad.logic.parser.CliSyntax.PREFIX_MODULE_CODE;
import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import igrad.logic.commands.CommandResult;
import igrad.logic.commands.exceptions.CommandException;
import igrad.model.Model;
import igrad.model.course.Cap;
import igrad.model.course.CourseInfo;
import igrad.model.course.Name;
import igrad.model.module.Credits;
import igrad.model.module.Description;
import igrad.model.module.Grade;
import igrad.model.module.Memo;
import igrad.model.module.Module;
import igrad.model.module.ModuleCode;
import igrad.model.module.Semester;
import igrad.model.module.Title;
import igrad.model.requirement.Requirement;
import igrad.model.tag.Tag;

/**
 * Marks the module as done, with a specified grade.
 */
public class ModuleDoneCommand extends ModuleCommand {
    public static final String MODULE_DONE_COMMAND_WORD = MODULE_COMMAND_WORD + SPACE + "done";

    public static final String MESSAGE_MODULE_DONE_DETAILS = MODULE_DONE_COMMAND_WORD + ": Marks a module as done "
        + "(with a grade) of the module identified by its module code. Existing module (grade) will be overwritten "
        + "by the input values.\n";

    public static final String MESSAGE_MODULE_DONE_USAGE = "Parameter(s): MODULE CODE "
        + PREFIX_GRADE + "GRADE\n"
        + "Example: " + MODULE_DONE_COMMAND_WORD + " "
        + PREFIX_MODULE_CODE + "CS2103T "
        + PREFIX_GRADE + "A+";

    public static final String MESSAGE_MODULE_DONE_HELP = MESSAGE_MODULE_DONE_DETAILS + MESSAGE_MODULE_DONE_USAGE;

    public static final String MESSAGE_MODULE_NOT_EDITED = "Grade must be provided.";

    public static final String MESSAGE_MODULE_DONE_SUCCESS = "Marked Module as done:\n%1$s";

    private ModuleCode moduleCode;
    private EditModuleGradeDescriptor editModuleGradeDescriptor;

    /**
     * @param moduleCode                of the module in the filtered module list to edit
     * @param editModuleGradeDescriptor details (grade) to edit the module with
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

        // Just copy everything from {@code moduleToEdit} to our new {@code Module}
        ModuleCode moduleCode = moduleToEdit.getModuleCode();
        Title updatedTitle = moduleToEdit.getTitle();
        Credits updatedCredits = moduleToEdit.getCredits();
        Optional<Memo> updatedMemo = moduleToEdit.getMemo();
        Optional<Semester> updatedSemester = moduleToEdit.getSemester();
        Optional<Description> updatedDescription = moduleToEdit.getDescription();
        Set<Tag> updatedTags = moduleToEdit.getTags();

        /*
         * But for Grade, It's compulsory for Grade to be optionally edited/updated. This should have already been
         * guaranteed through the validations in the ModuleDoneCommandParser
         */
        Optional<Grade> updatedGrade = editModuleGradeDescriptor.getGrade();

        return new Module(updatedTitle, moduleCode, updatedCredits, updatedMemo, updatedSemester,
            updatedDescription, updatedGrade, updatedTags);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        // Retrieve the module we want to mark a grade done with
        Module moduleToEdit = model.getModuleByModuleCode(moduleCode)
            .orElseThrow(() -> new CommandException(MESSAGE_MODULE_NON_EXISTENT));

        // Create a new module based on the edited grade.
        Module editedModule = createEditedModule(moduleToEdit, editModuleGradeDescriptor);

        // Update the module in our model
        model.setModule(moduleToEdit, editedModule);

        Optional<Name> currentName = model.getCourseInfo().getName();

        // Now we actually go to our model and recompute cap based on updated module list in model (coursebook)
        Optional<Cap> updatedCap = Optional.of(model.computeCap());

        CourseInfo courseInfo = new CourseInfo(currentName, updatedCap);

        // Updating the model with the latest course info (cap)
        model.setCourseInfo(courseInfo);


        List<Requirement> requirementsToUpdate = model.getRequirementsWithModule(editedModule);

        /*
         * Given that this module has been updated in the modules list, there are two things we need
         * to do, first is to update the copies of this  modules existing in the modules list of all
         * requirements containing that module. And the second is that we need to update the
         * creditsFulfilled of all requirements (which consists of that module).
         *
         * The code below does both of these, for each related Requirement.
         */
        requirementsToUpdate.stream()
            .forEach(requirementToEdit -> {
                // Copy over all the old values of requirementToEdit
                igrad.model.requirement.RequirementCode requirementCode = requirementToEdit.getRequirementCode();
                igrad.model.requirement.Title title = requirementToEdit.getTitle();

                // Now given that we've added a new module to requirement, we've to update (recompute) creditsFulfilled
                int creditsRequired = requirementToEdit.getCredits().getCreditsRequired();
                int creditsFulfilled = requirementToEdit.getCredits().getCreditsFulfilled()
                    + editedModule.getCredits().toInteger();

                // Construct a new Credits object to reflect this new Credits changes
                igrad.model.requirement.Credits updatedCredits =
                    new igrad.model.requirement.Credits(creditsRequired, creditsFulfilled);

                // Updates the existing requirement; requirementToEdit with the editedModule
                requirementToEdit.setModule(moduleToEdit, editedModule);

                // Get the most update module list (now with the new module replaced)
                List<Module> modules = requirementToEdit.getModuleList();

                // Finally, create a new Requirement with all the updated information (details).
                Requirement editedRequirement = new Requirement(requirementCode, title, updatedCredits, modules);

                // Update the current Requirement in the model (coursebook) with this latest version.
                model.setRequirement(requirementToEdit, editedRequirement);
            });

        return new CommandResult(String.format(MESSAGE_MODULE_DONE_SUCCESS, editedModule));
    }

    @Override
    public boolean equals(Object other) {
        /*
         * TODO (Teri): Please take a look at how ModuleEditCommand.java
         * implements this, and fill it up!
         */

        return false;
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
