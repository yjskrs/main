package igrad.logic.commands.course;

//@@author teriaiw

import static igrad.logic.parser.CliSyntax.PREFIX_NAME;
import static igrad.logic.parser.CliSyntax.PREFIX_SEMESTER;
import static java.util.Objects.requireNonNull;

import java.util.Optional;

import igrad.logic.commands.CommandResult;
import igrad.logic.commands.CommandUtil;
import igrad.logic.commands.exceptions.CommandException;
import igrad.model.Model;
import igrad.model.course.Cap;
import igrad.model.course.CourseInfo;
import igrad.model.course.Credits;
import igrad.model.course.Name;
import igrad.model.course.Semesters;


/**
 * Edits the details of an existing module in the course book.
 */
public class CourseEditCommand extends CourseCommand {

    public static final String COURSE_EDIT_COMMAND_WORD = COURSE_COMMAND_WORD + SPACE + "edit";

    public static final String MESSAGE_COURSE_EDIT_DETAILS = COURSE_EDIT_COMMAND_WORD
        + ": Edits the name of the course.\n";

    public static final String MESSAGE_COURSE_EDIT_USAGE = "Parameter(s): "
        + "[" + PREFIX_NAME + "COURSE_NAME] "
        + "[" + PREFIX_SEMESTER + "TOTAL_SEMESTERS]\n"
        + "e.g. " + COURSE_EDIT_COMMAND_WORD + " "
        + PREFIX_NAME + "Information Systems "
        + PREFIX_SEMESTER + "7";

    public static final String MESSAGE_COURSE_EDIT_HELP = MESSAGE_COURSE_EDIT_DETAILS + MESSAGE_COURSE_EDIT_USAGE;

    public static final String MESSAGE_COURSE_EDIT_SUCCESS = "Course: %1$s edited successfully!";

    public static final String MESSAGE_COURSE_NOT_EDITED = "At least one field to edit must be provided.\n"
        + "[" + PREFIX_NAME + "COURSE_NAME] "
        + "[" + PREFIX_SEMESTER + "TOTAL_SEMESTERS]";

    private EditCourseDescriptor editCourseDescriptor;

    /**
     * @param editCourseDescriptor details (course name) to edit the course with
     *                             (Note: course is special unlike module and requirement as there is only
     *                             one course in the course book, hence we don't need a 'Name'/'ModuleCode', or any
     *                             kind of identifier to identify the course we want to edit)
     */
    public CourseEditCommand(EditCourseDescriptor editCourseDescriptor) {
        requireNonNull(editCourseDescriptor);

        this.editCourseDescriptor = new EditCourseDescriptor(editCourseDescriptor);
    }

    /**
     * Creates and returns a {@code CourseInfo} with the details of {@code courseInfoToEdit}
     * edited with {@code editCourseDescriptor}.
     */
    private static CourseInfo createEditedCourseInfo(CourseInfo courseInfoToEdit,
                                                     CourseEditCommand.EditCourseDescriptor editCourseDescriptor) {
        /*
         * Just copy everything from the original {@code courseInfoToEdit} to our new {@code CourseInfo}.
         * But for course name and semesters, we retrieve the updated value from the editCourseDescriptor here.
         */
        Optional<Cap> cap = courseInfoToEdit.getCap();
        Optional<Credits> credits = courseInfoToEdit.getCredits();

        Optional<Name> updatedName = editCourseDescriptor.getName().orElse(courseInfoToEdit.getName());
        Optional<Semesters> updatedSemesters = editCourseDescriptor.getSemesters()
            .orElse(courseInfoToEdit.getSemesters());

        return new CourseInfo(updatedName, cap, credits, updatedSemesters);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        CourseInfo courseInfoToEdit = model.getCourseInfo();

        // The course name has to first be set, else we can't proceed to even edit it.
        courseInfoToEdit.getName().orElseThrow(() -> new CommandException(MESSAGE_COURSE_NON_EXISTENT));

        CourseInfo editedCourseInfo = createEditedCourseInfo(courseInfoToEdit, editCourseDescriptor);

        // If none of the parameters have been modified
        if (editedCourseInfo.equals(courseInfoToEdit)) {
            throw new CommandException(MESSAGE_COURSE_NOT_EDITED);
        }

        /*
         * A call to the retrieveLatestCourseInfo(..) helps to recompute latest course info,
         * based on information provided through Model (coursebook).
         */
        CourseInfo finalEditedCourseInfo = CommandUtil.createEditedCourseInfo(editedCourseInfo, model);

        model.setCourseInfo(finalEditedCourseInfo);

        return new CommandResult(String.format(MESSAGE_COURSE_EDIT_SUCCESS, finalEditedCourseInfo));
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof CourseEditCommand)) {
            return false;
        }

        // state check
        CourseEditCommand e = (CourseEditCommand) other;

        return editCourseDescriptor.equals(e.editCourseDescriptor);
    }

    /**
     * Stores the details to edit the module with. Each non-empty field value will replace the
     * corresponding field value of the module.
     */
    public static class EditCourseDescriptor {
        private Optional<Name> name;
        private Optional<Semesters> semesters;

        public EditCourseDescriptor() {
        }

        /**
         * Copy constructor.
         */
        public EditCourseDescriptor(EditCourseDescriptor toCopy) {
            setName(toCopy.name);
            setSemesters(toCopy.semesters);
        }

        public Optional<Optional<Name>> getName() {
            return Optional.ofNullable(name);
        }

        public void setName(Optional<Name> name) {
            this.name = name;
        }

        public Optional<Optional<Semesters>> getSemesters() {
            return Optional.ofNullable(semesters);
        }

        public void setSemesters(Optional<Semesters> semesters) {
            this.semesters = semesters;
        }

        @Override
        public boolean equals(Object other) {
            // short circuit if same object
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditCourseDescriptor)) {
                return false;
            }

            // state check
            EditCourseDescriptor e = (EditCourseDescriptor) other;

            return getName().equals(e.getName())
                && getSemesters().equals(e.getSemesters());
        }
    }
}
