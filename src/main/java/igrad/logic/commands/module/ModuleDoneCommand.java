package igrad.logic.commands.module;

import static igrad.commons.util.CollectionUtil.requireAllNonNull;
import static igrad.logic.parser.CliSyntax.PREFIX_GRADE;
import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.Optional;

import igrad.logic.commands.CommandResult;
import igrad.logic.commands.CommandUtil;
import igrad.logic.commands.exceptions.CommandException;
import igrad.model.Model;
import igrad.model.course.CourseInfo;
import igrad.model.module.Credits;
import igrad.model.module.Description;
import igrad.model.module.Grade;
import igrad.model.module.Module;
import igrad.model.module.ModuleCode;
import igrad.model.module.Semester;
import igrad.model.module.Title;
import igrad.model.requirement.Requirement;

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
        + "Example: " + MODULE_DONE_COMMAND_WORD + " CS2103T "
        + PREFIX_GRADE + "A+";

    public static final String MESSAGE_MODULE_DONE_HELP = MESSAGE_MODULE_DONE_DETAILS + MESSAGE_MODULE_DONE_USAGE;

    public static final String MESSAGE_MODULE_NOT_EDITED = "Grade must be provided.";

    public static final String MESSAGE_MODULE_DONE_SUCCESS = "Marked Module as done:\n%1$s";

    private ModuleCode moduleCode;
    private EditModuleDescriptor editModuleGradeDescriptor;

    /**
     * @param moduleCode                of the module in the filtered module list to edit
     * @param editModuleGradeDescriptor details (grade) to edit the module with
     */
    public ModuleDoneCommand(ModuleCode moduleCode, EditModuleDescriptor editModuleGradeDescriptor) {
        requireAllNonNull(moduleCode, editModuleGradeDescriptor);

        this.moduleCode = moduleCode;
        this.editModuleGradeDescriptor = new EditModuleDescriptor(editModuleGradeDescriptor);
    }

    /**
     * Creates and returns a {@code Module} with the details of {@code moduleToEdit}
     * edited with {@code editModuleGradeDescriptor}.
     */
    private static Module createEditedModule(Module moduleToEdit, EditModuleDescriptor editModuleDescriptor) {
        assert moduleToEdit != null;

        // Just copy everything from the original {@code moduleToEdit} to our new {@code Module}
        ModuleCode moduleCode = moduleToEdit.getModuleCode();
        Title title = moduleToEdit.getTitle();
        Credits credits = moduleToEdit.getCredits();
        Optional<Description> description = moduleToEdit.getDescription();

        /*
         * But for Semester, since it is an optional field, we copy its value over from the
         * EditModuleDescriptor if any
         */
        Optional<Semester> updatedSemester = editModuleDescriptor.getSemester().orElse(moduleToEdit.getSemester());

        /*
         * But for Grade, It's compulsory for Grade to be optionally edited/updated. This should have already been
         * guaranteed through the validations in the ModuleDoneCommandParser
         */
        Optional<Grade> updatedGrade = editModuleDescriptor.getGrade();

        return new Module(title, moduleCode, credits, updatedSemester, description, updatedGrade);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        // Retrieve the module we want to mark a grade done with
        Module moduleToEdit = model.getModule(moduleCode)
            .orElseThrow(() -> new CommandException(MESSAGE_MODULE_NON_EXISTENT));

        // Create a new module based on the edited grade.
        Module editedModule = createEditedModule(moduleToEdit, editModuleGradeDescriptor);

        // Update the module in our model
        model.setModule(moduleToEdit, editedModule);



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

                /*
                 * Now given that we've marked a module in a requirement as done, we've to update (recompute)
                 * creditsFulfilled in the relevant Requirements, but since Requirement constructor already does
                 * it for us, based on the module list passed in, we don't have to do anything here, just
                 * propagate the old credits value.
                 */
                igrad.model.requirement.Credits credits = requirementToEdit.getCredits();

                // Updates the existing requirement; requirementToEdit with the editedModule
                requirementToEdit.setModule(moduleToEdit, editedModule);

                // Get the most update module list (now with the new module replaced)
                List<Module> modules = requirementToEdit.getModuleList();

                // Finally, create a new Requirement with all the updated information (details).
                Requirement editedRequirement = new Requirement(requirementCode, title, credits, modules);

                // Update the current Requirement in the model (coursebook) with this latest version.
                model.setRequirement(requirementToEdit, editedRequirement);
            });

        /*
         * Now that we've deleted a module in the system, we need to update CourseInfo, specifically its cap,
         * and the Credits (creditsFulfilled) property.
         *
         * However, in the method below, we just recompute everything (field in course info).
         */
        CourseInfo courseToEdit = model.getCourseInfo();
        CourseInfo editedCourseInfo = CommandUtil.retrieveLatestCourseInfo(courseToEdit, model);

        // Updating the model with the latest course info
        model.setCourseInfo(editedCourseInfo);

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
     * a module with a grade, and semester. Each non-empty field value will replace the
     * corresponding field value of the module.
     */
    public static class EditModuleDescriptor {
        private Optional<Grade> grade;
        private Optional<Semester> semester;

        public EditModuleDescriptor() {
        }

        /**
         * Copy constructor.
         */
        public EditModuleDescriptor(EditModuleDescriptor toCopy) {
            setGrade(toCopy.grade);
            setSemester(toCopy.semester);
        }

        public Optional<Grade> getGrade() {
            return grade;
        }

        public void setGrade(Optional<Grade> grade) {
            this.grade = grade;
        }

        public Optional<Optional<Semester>> getSemester() {
            return Optional.ofNullable(semester);
        }

        public void setSemester(Optional<Semester> semester) {
            this.semester = semester;
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
            ModuleDoneCommand.EditModuleDescriptor e = (EditModuleDescriptor) other;

            return getGrade().equals(e.getGrade())
                && getSemester().equals(e.getSemester());
        }
    }

}
