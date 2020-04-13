package igrad.logic.commands.module;

//@@author nathanaelseen

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

    public static final String MESSAGE_MODULE_DONE_USAGE = "Parameter(s): MODULE_CODE "
        + PREFIX_GRADE + "GRADE\n"
        + "e.g. " + MODULE_DONE_COMMAND_WORD + " CS2103T "
        + PREFIX_GRADE + "A+";

    public static final String MESSAGE_MODULE_DONE_HELP = MESSAGE_MODULE_DONE_DETAILS + MESSAGE_MODULE_DONE_USAGE;

    public static final String MESSAGE_MODULE_NOT_EDITED = "Grade must be provided.";

    public static final String MESSAGE_MODULE_DONE_SUCCESS = "Yay! I have marked this module as done:\n%1$s";

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


    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        // Retrieve the module we want to mark a grade done with
        Module moduleToEdit = model.getModule(moduleCode)
            .orElseThrow(() -> new CommandException(String.format(MESSAGE_MODULE_NON_EXISTENT, moduleCode)));

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
                // Create a new Requirement with all the updated information (details).
                Requirement editedRequirement = createEditedRequirement(requirementToEdit, moduleToEdit, editedModule);

                // Update the current Requirement in the model (coursebook) with this latest version.
                model.setRequirement(requirementToEdit, editedRequirement);
            });

        /*
         * Now that we've deleted a module in the system, we need to update CourseInfo, specifically its cap,
         * and the Credits (creditsFulfilled) property.
         *
         * However, in the method below, we just recompute everything (field in course info).
         */
        CourseInfo courseInfoToEdit = model.getCourseInfo();

        /*
         * A call to the retrieveLatestCourseInfo(..) helps to recompute latest course info,
         * based on information provided through Model (coursebook).
         */
        CourseInfo editedCourseInfo = CommandUtil.createEditedCourseInfo(courseInfoToEdit, model);

        // Updating the model with the latest course info
        model.setCourseInfo(editedCourseInfo);

        return new CommandResult(String.format(MESSAGE_MODULE_DONE_SUCCESS, editedModule));
    }

    /**
     * Creates and returns a new {@code Requirement}, replacing a module; {@code moduleToEdit} (which is under that
     * the original requirement; {@code requirementToEdit}), by the module; {@code editedModule})
     */
    private static Requirement createEditedRequirement(Requirement requirementToEdit,
            Module moduleToEdit, Module editedModule) {
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
        return new Requirement(requirementCode, title, credits, modules);
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

        return new Module(title, moduleCode, credits, updatedSemester, updatedGrade);
    }

    @Override
    public boolean equals(Object other) {
        return other == this
            || (other instanceof ModuleDoneCommand
            && ((ModuleDoneCommand) other).editModuleGradeDescriptor.equals(editModuleGradeDescriptor)
            && ((ModuleDoneCommand) other).moduleCode.equals(moduleCode));
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
            EditModuleDescriptor e = (EditModuleDescriptor) other;

            return getGrade().equals(e.getGrade())
                && getSemester().equals(e.getSemester());
        }
    }

}
