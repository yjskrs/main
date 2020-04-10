package igrad.logic.commands.course;

import static igrad.logic.parser.CliSyntax.PREFIX_NAME;
import static igrad.logic.parser.CliSyntax.PREFIX_SEMESTER;
import static java.util.Objects.requireNonNull;

import java.util.Optional;

import igrad.logic.commands.CommandResult;
import igrad.logic.commands.exceptions.CommandException;
import igrad.model.Model;
import igrad.model.course.Cap;
import igrad.model.course.CourseInfo;
import igrad.model.course.Credits;
import igrad.model.course.Name;
import igrad.model.course.Semesters;
//@@author nathanaelseen

/**
 * Edits the details of an existing module in the course book.
 */
public class CourseEditCommand extends CourseCommand {

    public static final String COURSE_EDIT_COMMAND_WORD = COURSE_COMMAND_WORD + SPACE + "edit";
    public static final String MESSAGE_COURSE_EDIT_SUCCESS = "Edited Course: %1$s";
    public static final String MESSAGE_EDIT_COURSE_SAME_PARAMETERS = "Please change the name of the course.";
    public static final String MESSAGE_COURSE_NOT_EDITED = "Course name must be provided.";
    public static final String MESSAGE_COURSE_EDIT_DETAILS = COURSE_EDIT_COMMAND_WORD
                                                                 + ": Edits the name of the course.\n";
    public static final String MESSAGE_COURSE_EDIT_USAGE = "Parameter(s): "
                                                               + "[" + PREFIX_NAME + "COURSE_NAME] "
                                                               + "[" + PREFIX_SEMESTER + "TOTAL_SEMESTERS]\n";
    public static final String MESSAGE_COURSE_EDIT_HELP = MESSAGE_COURSE_EDIT_DETAILS + MESSAGE_COURSE_EDIT_USAGE;

    private EditCourseDescriptor editCourseDescriptor;

    /**
     * @param editCourseDescriptor details (course name) to edit the course with
     * (Note: course is special unlike module and requirement as there is only
     * one course in the course book, hence we don't need a 'Name'/'ModuleCode', or any
     * kind of identifier to identify the course we want to edit)
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
        Optional<Semesters> semesters = editCourseDescriptor.getSemesters().orElse(courseInfoToEdit.getSemesters());

        return new CourseInfo(updatedName, cap, credits, semesters);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        CourseInfo courseToEdit = model.getCourseInfo();

        // The course name has to first be set, else we can't proceed to even edit it.
        courseToEdit.getName().orElseThrow(() -> new CommandException(MESSAGE_COURSE_NON_EXISTENT));

        if (editCourseDescriptor.getName().isEmpty()) {
            throw new CommandException(MESSAGE_COURSE_NOT_EDITED);
        }

        if (courseToEdit.getName().equals(editCourseDescriptor.getName())) {
            throw new CommandException(MESSAGE_EDIT_COURSE_SAME_PARAMETERS);
        }

        CourseInfo editedCourse = createEditedCourseInfo(courseToEdit, editCourseDescriptor);

        model.setCourseInfo(editedCourse);
        return new CommandResult(String.format(MESSAGE_COURSE_EDIT_SUCCESS, editedCourse));
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

        public void setName(Optional<Name> name) {
            this.name = name;
        }

        public Optional<Optional<Name>> getName() {
            return Optional.ofNullable(name);
        }

        public void setSemesters(Optional<Semesters> semesters) {
            this.semesters = semesters;
        }

        public Optional<Optional<Semesters>> getSemesters() {
            return Optional.ofNullable(semesters);
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
