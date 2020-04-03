package igrad.logic.commands.module;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.Optional;

import igrad.logic.commands.CommandResult;
import igrad.logic.commands.exceptions.CommandException;
import igrad.model.Model;
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
         * creditsFulfilled of all requirements (which consists of that module, and that module has
         * been marked done).
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
                int creditsFulfilled = requirementToEdit.getCredits().getCreditsFulfilled();

                // Here we need to be extremely cautious, if the module is not marked done, don't need to do anything
                if (moduleToDelete.isDone()) {
                    creditsFulfilled -= moduleToDelete.getCredits().toInteger();
                }

                // Construct a new Credits object to reflect this new Credits changes
                igrad.model.requirement.Credits updatedCredits =
                    new igrad.model.requirement.Credits(creditsRequired, creditsFulfilled);

                // Deletes from the existing requirement; requirementToEdit, the moduleToDelete
                requirementToEdit.removeModule(moduleToDelete);

                // Get the most update module list (now with the new module replaced)
                List<Module> modules = requirementToEdit.getModuleList();

                // Finally, create a new Requirement with all the updated information (details).
                Requirement editedRequirement = new Requirement(requirementCode, title, updatedCredits, modules);

                // Update the current Requirement in the model (coursebook) with this latest version.
                model.setRequirement(requirementToEdit, editedRequirement);
            });

        return new CommandResult(String.format(MESSAGE_MODULE_DELETE_SUCCESS, moduleToDelete));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof ModuleDeleteCommand // instanceof handles nulls
            && moduleCode.equals(((ModuleDeleteCommand) other).moduleCode)); // state check
    }
}
