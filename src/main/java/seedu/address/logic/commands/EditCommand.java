package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.*;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.module.*;
import seedu.address.model.module.Module;
import seedu.address.model.module.ModuleCode;
import seedu.address.model.tags.Tags;

/**
 * Edits the details of an existing person in the address book.
 */
public class EditCommand extends Command {

    public static final String COMMAND_WORD = "edit";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the module identified "
            + "by the index number used in the displayed module list. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_MODULE_CODE + "MODULE CODE] "
            + "[" + PREFIX_TITLE + "TITLE] "
            + "[" + PREFIX_CREDITS + "CREDITS] "
            + "[" + PREFIX_MEMO + "MEMO] "
            + "[" + PREFIX_SEMESTER + "SEMESTER]"
            + "[" + PREFIX_TAGS + "TAGS]...\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_MODULE_CODE + "CS2103T "
            + PREFIX_CREDITS + "4";

    public static final String MESSAGE_EDIT_MODULE_SUCCESS = "Edited Module: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_PERSON = "This module already exists in the system.";

    private final Index index;
    private final EditPersonDescriptor editPersonDescriptor;

    /**
     * @param index of the person in the filtered person list to edit
     * @param editPersonDescriptor details to edit the person with
     */
    public EditCommand(Index index, EditPersonDescriptor editPersonDescriptor) {
        requireNonNull(index);
        requireNonNull(editPersonDescriptor);

        this.index = index;
        this.editPersonDescriptor = new EditPersonDescriptor(editPersonDescriptor);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Module> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_MODULE_DISPLAYED_INDEX);
        }

        Module moduleToEdit = lastShownList.get(index.getZeroBased());
        Module editedModule = createEditedPerson( moduleToEdit, editPersonDescriptor);

        if (!moduleToEdit.isSamePerson( editedModule ) && model.hasPerson( editedModule )) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        }

        model.setPerson( moduleToEdit, editedModule );
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        return new CommandResult(String.format( MESSAGE_EDIT_MODULE_SUCCESS, editedModule ));
    }

    /**
     * Creates and returns a {@code Person} with the details of {@code personToEdit}
     * edited with {@code editPersonDescriptor}.
     */
    private static Module createEditedPerson( Module moduleToEdit, EditPersonDescriptor editPersonDescriptor) {
        assert moduleToEdit != null;

        Title updatedTitle = editPersonDescriptor.getTitle().orElse( moduleToEdit.getTitle());
        ModuleCode updatedModuleCode = editPersonDescriptor.getModuleCode().orElse( moduleToEdit.getModuleCode());
        Credits updatedCredits = editPersonDescriptor.getCredits().orElse( moduleToEdit.getCredits());
        Memo updatedMemo = editPersonDescriptor.getMemo().orElse( moduleToEdit.getMemo());
        Semester updatedSemester = editPersonDescriptor.getSemester().orElse( moduleToEdit.getSemester() );
        Set<Tags> updatedTags = editPersonDescriptor.getTags().orElse( moduleToEdit.getTags());

        return new Module( updatedTitle, updatedModuleCode, updatedCredits, updatedMemo, updatedSemester, updatedTags );
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditCommand)) {
            return false;
        }

        // state check
        EditCommand e = (EditCommand) other;
        return index.equals(e.index)
                && editPersonDescriptor.equals(e.editPersonDescriptor);
    }

    /**
     * Stores the details to edit the person with. Each non-empty field value will replace the
     * corresponding field value of the person.
     */
    public static class EditPersonDescriptor {
        private Title title;
        private ModuleCode moduleCode;
        private Credits credits;
        private Memo memo;
        private Semester semester;
        private Set<Tags> tags;

        public EditPersonDescriptor() {}

        /**
         * Copy constructor.
         * A defensive copy of {@code tags} is used internally.
         */
        public EditPersonDescriptor(EditPersonDescriptor toCopy) {
            setTitle(toCopy.title );
            setModuleCode(toCopy.moduleCode );
            setCredits(toCopy.credits );
            setMemo(toCopy.memo );
            setTags(toCopy.tags );
            setSemester(toCopy.semester);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull( title, moduleCode, credits, memo, semester, tags );
        }

        public void setTitle( Title title ) {
            this.title = title;
        }

        public Optional<Title> getTitle() {
            return Optional.ofNullable( title );
        }

        public void setModuleCode( ModuleCode moduleCode ) {
            this.moduleCode = moduleCode;
        }

        public Optional<ModuleCode> getModuleCode() {
            return Optional.ofNullable( moduleCode );
        }

        public void setCredits( Credits credits ) {
            this.credits = credits;
        }

        public Optional<Credits> getCredits() {
            return Optional.ofNullable( credits );
        }

        public void setMemo( Memo memo ) {
            this.memo = memo;
        }

        public Optional<Memo> getMemo() {
            return Optional.ofNullable( memo );
        }

        public void setSemester( Semester semester ) {
            this.semester = semester;
        }

        public Optional<Semester> getSemester() {
            return Optional.ofNullable( semester );
        }

        /**
         * Sets {@code tags} to this object's {@code tags}.
         * A defensive copy of {@code tags} is used internally.
         */
        public void setTags( Set<Tags> tags ) {
            this.tags = ( tags != null) ? new HashSet<>( tags ) : null;
        }

        /**
         * Returns an unmodifiable tag set, which throws {@code UnsupportedOperationException}
         * if modification is attempted.
         * Returns {@code Optional#empty()} if {@code tags} is null.
         */
        public Optional<Set<Tags>> getTags() {
            return ( tags != null) ? Optional.of(Collections.unmodifiableSet( tags )) : Optional.empty();
        }

        @Override
        public boolean equals(Object other) {
            // short circuit if same object
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditPersonDescriptor)) {
                return false;
            }

            // state check
            EditPersonDescriptor e = (EditPersonDescriptor) other;

            return getTitle().equals(e.getTitle())
                    && getModuleCode().equals(e.getModuleCode())
                    && getCredits().equals(e.getCredits())
                    && getMemo().equals(e.getMemo())
                    && getSemester().equals( e.getSemester() )
                    && getTags().equals(e.getTags());
        }
    }
}
