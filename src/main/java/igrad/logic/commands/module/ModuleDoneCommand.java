package igrad.logic.commands.module;

import static igrad.commons.util.CollectionUtil.requireAllNonNull;
import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.Optional;

import igrad.logic.commands.CommandResult;
import igrad.logic.commands.exceptions.CommandException;
import igrad.model.Model;
import igrad.model.module.Grade;
import igrad.model.module.ModuleCode;
import igrad.model.requirement.Credits;
import igrad.model.requirement.Name;

/**
 * Marks the module as done, with a specified grade.
 */
public class ModuleDoneCommand extends ModuleEditCommand {
    public static final String COMMAND_WORD = MODULE_COMMAND_WORD + "done";

    private ModuleCode moduleCode;
    private Grade newGrade;

    /**
     * @param moduleCode           of the module in the filtered module list to edit
     * @param editModuleDescriptor details to edit the module with
     */
    public ModuleDoneCommand(ModuleCode moduleCode, EditModuleDescriptor editModuleDescriptor) {
        super(moduleCode, editModuleDescriptor);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        return null;
    }
}
