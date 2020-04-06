package igrad.logic.commands.module;

import static igrad.logic.parser.CliSyntax.PREFIX_CREDITS;
import static igrad.logic.parser.CliSyntax.PREFIX_GRADE;
import static igrad.logic.parser.CliSyntax.PREFIX_SEMESTER;
import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.Optional;

import igrad.logic.commands.CommandResult;
import igrad.logic.commands.exceptions.CommandException;
import igrad.model.Model;
import igrad.model.module.Credits;
import igrad.model.module.Grade;
import igrad.model.module.Semester;

/**
 * Filters the module list.
 */
public class ModuleFilterCommand extends ModuleCommand {

    public static final String MODULE_FILTER_COMMAND_WORD = MODULE_COMMAND_WORD + SPACE + "filter";

    public static final String MESSAGE_MODULE_FILTER_USAGE = "Parameter(s): "
        + "[" + PREFIX_SEMESTER + "SEMESTER] "
        + "[" + PREFIX_CREDITS + "CREDITS]"
        + "[" + PREFIX_GRADE + "GRADE] "
        + "[-o]\n"
        + "Example: " + MODULE_FILTER_COMMAND_WORD + " "
        + PREFIX_SEMESTER + "Y1S1 "
        + PREFIX_CREDITS + "4 "
        + PREFIX_GRADE + "A+ ";

    public static final String MESSAGE_DISPLAYING_ALL = "No parameters detected. Displaying all modules.\n";
    public static final String MESSAGE_MODULE_FILTER_SUCCESS = "Filtered modules based on the following: %s\n";
    public static final String MESSAGE_NOTHING_FOUND = "No modules found!\n" + MESSAGE_MODULE_FILTER_USAGE;

    public static final String AND = "AND";
    public static final String OR = "OR";
    private Optional<Semester> semester;
    private Optional<Credits> credits;
    private Optional<Grade> grade;
    //default operator
    private String operator = AND;

    private boolean isRefresh = false;

    /**
     * Creates an ModuleAddCommand to add the specified {@code Module}
     */
    public ModuleFilterCommand(
        Optional<Semester> semester,
        Optional<Credits> credits,
        Optional<Grade> grade,
        String operator
    ) {

        this.semester = semester;
        this.credits = credits;
        this.grade = grade;
        this.operator = operator;
    }

    public ModuleFilterCommand() {
        this.isRefresh = true;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (isRefresh) {
            model.updateFilteredModuleList(m -> true);

            return new CommandResult(MESSAGE_DISPLAYING_ALL);
        } else {
            model.updateFilteredModuleList(m -> {

                boolean match;

                //must match all parameters
                if (operator.equals(AND)) {

                    match = true;

                    if (semester.isPresent()) {
                        //if both are present, then we can do the comparison
                        if (m.getSemester().isPresent()) {
                            if (!semester.get().equals(m.getSemester().get())) {
                                match = false;
                            }
                        } else {
                            //if module doesn't have a semester, don't return
                            match = false;
                        }
                    }

                    //credits for module is always present
                    if (credits.isPresent()) {
                        if (!credits.get().equals(m.getCredits())) {
                            match = false;
                        }
                    }

                    if (grade.isPresent()) {
                        //if both are present, then we can do the comparison
                        if (m.getGrade().isPresent()) {
                            if (!grade.get().equals(m.getGrade().get())) {
                                match = false;
                            }
                        } else {
                            match = false;
                        }
                    }

                } else {
                    //only match one

                    match = false;

                    if (semester.isPresent()) {
                        //if both are present, then we can do the comparison
                        if (m.getSemester().isPresent()) {
                            if (semester.get().equals(m.getSemester().get())) {
                                match = true;
                            }
                        }
                    }

                    //credits for module is always present
                    if (credits.isPresent()) {
                        if (credits.get().equals(m.getCredits())) {
                            match = true;
                        }
                    }

                    if (grade.isPresent()) {
                        //if both are present, then we can do the comparison
                        if (m.getGrade().isPresent()) {
                            if (!grade.get().equals(m.getGrade().get())) {
                                match = true;
                            }
                        }
                    }

                }

                return match;
            });

        }

        //if nothing found
        if (model.getFilteredModuleList().size() == 0) {
            return new CommandResult(MESSAGE_NOTHING_FOUND);
        } else {

            StringBuilder filterString = new StringBuilder();

            ArrayList<String> filters = new ArrayList<>();

            semester.ifPresent(s -> filters.add(s.value));
            credits.ifPresent(c -> filters.add(c.value));
            grade.ifPresent(g -> filters.add(g.value));

            for (String filter : filters) {

                filterString.append(filter);

                int index = filters.indexOf(filter);
                if (index < filters.size() - 1) {
                    filterString.append(" ").append(operator).append(" ");
                }

            }

            return new CommandResult(String.format(MESSAGE_MODULE_FILTER_SUCCESS, filterString));
        }

    }

}
