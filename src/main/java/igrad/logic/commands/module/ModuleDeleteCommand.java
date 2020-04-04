package igrad.logic.commands.module;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.Optional;

import igrad.logic.commands.CommandResult;
import igrad.logic.commands.CommandUtil;
import igrad.logic.commands.exceptions.CommandException;
import igrad.model.Model;
import igrad.model.course.CourseInfo;
import igrad.model.module.Module;
import igrad.model.module.ModuleCode;
import igrad.model.requirement.Requirement;

/**
 * Deletes a {@code Module} identified using it's displayed index from the course book.
 */
public class ModuleDeleteCommand extends ModuleCommand {

    public static final String MODULE_DELETE_COMMAND_WORD = MODULE_COMMAND_WORD + SPACE + "delete";

    public static final String MESSAGE_MODULE_DELETE_SUCCESS = "Deleted Module:\n%1$s";

    private final ModuleCode moduleCode;

    public ModuleDeleteCommand(ModuleCode moduleCode) {
        this.moduleCode = moduleCode;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Module> lastShownList = model.getFilteredModuleList();

        Optional<Module> moduleToDeleteOpt = Optional.empty();

        for (Module module : lastShownList) {
            if (module.getModuleCode().equals(moduleCode)) {
                moduleToDeleteOpt = Optional.of(module);
            }
        }

        if (moduleToDeleteOpt.isEmpty()) {
            throw new CommandException(MESSAGE_MODULE_NON_EXISTENT);
        }

        Module moduleToDelete = moduleToDeleteOpt.get();

        model.deleteModule(moduleToDelete);


        List<Requirement> requirementsToUpdate = model.getRequirementsWithModule(moduleToDelete);

        /*
         * Given that this module has been deleted in the modules list, there are two things we need
         * to do, first is to delete the copies of this modules existing in the modules list of all
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
                 * Now given that we've delete a module from a requirement, we've to update (recompute)
                 * creditsFulfilled in the relevant Requirements, but since Requirement constructor already does
                 * it for us, based on the module list passed in, we don't have to do anything here, just
                 * propagate the old credits value.
                 */
                igrad.model.requirement.Credits credits = requirementToEdit.getCredits();

                // Deletes from the existing requirement; requirementToEdit, the moduleToDelete
                requirementToEdit.removeModule(moduleToDelete);

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

        return new CommandResult(String.format(MESSAGE_MODULE_DELETE_SUCCESS, moduleToDelete));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof ModuleDeleteCommand // instanceof handles nulls
            && moduleCode.equals(((ModuleDeleteCommand) other).moduleCode)); // state check
    }
}
