package igrad.logic.commands.requirement;

import static igrad.logic.parser.CliSyntax.PREFIX_CREDITS;
import static igrad.logic.parser.CliSyntax.PREFIX_NAME;
import static igrad.logic.parser.CliSyntax.PREFIX_TITLE;
import static java.util.Objects.requireNonNull;

import java.util.ArrayList;

import igrad.logic.commands.CommandResult;
import igrad.logic.commands.exceptions.CommandException;
import igrad.model.Model;
import igrad.model.requirement.Requirement;
import igrad.model.requirement.RequirementCode;
import javafx.collections.ObservableList;

/**
 * Adds a requirement to the course.
 */
public class RequirementAddCommand extends RequirementCommand {
    public static final String COMMAND_WORD = REQUIREMENT_COMMAND_WORD + "add";

    public static final String MESSAGE_DETAILS = COMMAND_WORD + ": Adds a requirement.\n";

    public static final String MESSAGE_USAGE = "Parameter: "
        + PREFIX_NAME + "NAME "
        + PREFIX_CREDITS + "CREDITS_TO_FULFIL\n"
        + "Example: " + COMMAND_WORD + " "
        + PREFIX_NAME + "Unrestricted Electives "
        + PREFIX_CREDITS + "24\n";

    public static final String MESSAGE_HELP = MESSAGE_DETAILS + MESSAGE_USAGE;

    public static final String MESSAGE_REQUIREMENT_ADD_SUCCESS = "New requirement added: %1$s";
    public static final String MESSAGE_REQUIREMENT_NOT_ADDED = "Added requirement must be provided with arguments "
        + PREFIX_TITLE + "TITLE " + PREFIX_CREDITS + "CREDITS ";
    public static final String MESSAGE_REQUIREMENT_DUPLICATE = "This requirement already exists in the course book.";

    private final Requirement requirementToAdd;

    public RequirementAddCommand(Requirement requirementToAdd) {
        requireNonNull(requirementToAdd);

        this.requirementToAdd = requirementToAdd;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        ObservableList<Requirement> requirementList = model.getRequirementList();

        String alphaCode = stripDigits(requirementToAdd.getRequirementCode().toString());

        ArrayList<Integer> usedDigits = new ArrayList<>();

        for (Requirement requirement : requirementList) {
            String requirementCodeStr = requirement.getRequirementCode().toString();
            String alphaCodeCmp = stripDigits(requirementCodeStr);

            if (alphaCode.equals(alphaCodeCmp)) {
                String numCodeCmp = stripAlpha(requirementCodeStr);
                usedDigits.add(Integer.parseInt(numCodeCmp));
            }
        }

        int max = -1;

        for (Integer digit : usedDigits) {
            if (digit > max) {
                max = digit;
            }
        }

        int index = max + 1;

        requirementToAdd.setRequirementCode(new RequirementCode(alphaCode + index));

        // if the name of the requirement has already been used
        if (model.hasRequirement(requirementToAdd)) {
            throw new CommandException(MESSAGE_REQUIREMENT_DUPLICATE);
        }

        model.addRequirement(requirementToAdd);
        return new CommandResult(String.format(MESSAGE_REQUIREMENT_ADD_SUCCESS, requirementToAdd));
    }

    private String stripDigits(String str) {
        return str.replaceAll("[0123456789]", "");
    }

    private String stripAlpha(String str) {
        return str.replaceAll("\\D+", "");
    }
}
