package igrad.logic.commands;

import igrad.logic.commands.exceptions.CommandException;
import igrad.model.Model;

/**
 * Deletes an existing requirement from the course book.
 */
public class RequirementDeleteCommand extends RequirementCommand {

    @Override
    public CommandResult execute(Model model) throws CommandException {
        return null;
    }
}
