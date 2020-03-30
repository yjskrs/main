package igrad.logic.commands.course;

import static java.util.Objects.requireNonNull;

import java.util.Optional;

import igrad.logic.commands.CommandResult;
import igrad.logic.commands.exceptions.CommandException;
import igrad.model.Model;
import igrad.model.course.CourseInfo;
import igrad.model.course.Name;

/**
 * Edits the details of an existing module in the course book.
 */
public class CourseEditCommand extends CourseCommand {

    public static final String COMMAND_WORD = COURSE_COMMAND_WORD + "edit";
    public static final String MESSAGE_SUCCESS = "Edited Course: %1$s";
    public static final String MESSAGE_EDIT_COURSE_SAME_PARAMETERS = "Please change the name of the course";
    public static final String MESSAGE_NOT_EDITED = "Course name must be provided.";

    private EditCourseDescriptor editCourseDescriptor;

    /**
     * @param editCourseDescriptor details (course name) to edit the course with
     * (Note: course is special unlike module and requirement as there is only
     * one course in the course book, hence we don't need a 'Name'/'ModuleCode', or any
     * kind of identifier to identify the course we want to edit)
     */
    public CourseEditCommand(EditCourseDescriptor editCourseDescriptor) {
    }

    /**
     * Creates and returns a {@code CourseInfo} with the details of {@code courseInfoToEdit}
     * edited with {@code editCourseDescriptor}.
     */
    private static CourseInfo createEditedCourseInfo(CourseInfo courseInfoToEdit,
                                             CourseEditCommand.EditCourseDescriptor editCourseDescriptor) {
        /*
         * TODO (Teri): I leave the details to you, you may refer to how ModuleDoneCommand is done.
         * Essentially, the idea here is to create a new CourseInfo with the
         * edited course Name inside. However, note that alike the ModuleDone command, we want to
         * leave the Optional<Cap> unedited
         * Optional<Name> name = ...
         */
        return null;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        CourseInfo courseToEdit = model.getCourseInfo();

        // The course name has to first be set, else we can't proceed to even edit it.
        courseToEdit.getName().orElseThrow(() -> new CommandException(MESSAGE_COURSE_INFO_NON_EXISTENT));

        /*
         * TODO (Teri): I'll leave the rest to you.
         * But you can reference the ModuleDonneCommand.java for this.
         */

        return null;
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
     * Stores the details to edit the module with. Each non-empty field value will replace the
     * corresponding field value of the module.
     */
    public static class EditCourseDescriptor {
        private Optional<Name> name;

        public EditCourseDescriptor() {
        }

        /**
         * Copy constructor.
         */
        public EditCourseDescriptor(EditCourseDescriptor toCopy) {
            setName(toCopy.name);
        }

        public Optional<Name> getName() {
            return name;
        }

        public void setName(Optional<Name> name) {
            this.name = name;
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

            return getName().equals(e.getName());
        }
    }
}
