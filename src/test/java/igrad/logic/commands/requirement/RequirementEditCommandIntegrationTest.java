package igrad.logic.commands.requirement;

//@@author yjskrs

import static igrad.logic.commands.requirement.RequirementCommandTestUtil.VALID_REQ_CODE_CSF;
import static igrad.logic.commands.requirement.RequirementCommandTestUtil.VALID_REQ_CREDITS_MS;
import static igrad.logic.commands.requirement.RequirementCommandTestUtil.VALID_REQ_TITLE_MS;
import static igrad.logic.commands.requirement.RequirementCommandTestUtil.assertCommandSuccess;
import static igrad.logic.commands.requirement.RequirementCommandTestUtil.assertCommandThrows;
import static igrad.logic.commands.requirement.RequirementEditCommand.EditRequirementDescriptor;
import static igrad.logic.commands.requirement.RequirementEditCommand.MESSAGE_REQUIREMENT_EDIT_SUCCESS;
import static igrad.logic.commands.requirement.RequirementEditCommand.MESSAGE_REQUIREMENT_NOT_EDITED;
import static igrad.testutil.TypicalRequirements.CS_FOUNDATION;
import static igrad.testutil.TypicalRequirements.getTypicalCourseBook;

import org.junit.jupiter.api.Test;

import igrad.model.CourseBook;
import igrad.model.Model;
import igrad.model.ModelManager;
import igrad.model.UserPrefs;
import igrad.model.requirement.Requirement;
import igrad.model.requirement.RequirementCode;
import igrad.testutil.EditRequirementDescriptorBuilder;
import igrad.testutil.RequirementBuilder;

public class RequirementEditCommandIntegrationTest {
    private Model model = new ModelManager(getTypicalCourseBook(), new UserPrefs());
    private Requirement requirement = CS_FOUNDATION;
    private RequirementCode code = new RequirementCode(VALID_REQ_CODE_CSF);

    @Test
    public void execute_allFieldsSpecified_success() {
        Model expectedModel = new ModelManager(new CourseBook(model.getCourseBook()), new UserPrefs());
        Requirement editedRequirement = new RequirementBuilder(requirement).withTitle(VALID_REQ_TITLE_MS)
                                            .withCreditsOneParameter(VALID_REQ_CREDITS_MS).build();
        EditRequirementDescriptor descriptor = new EditRequirementDescriptorBuilder().withTitle(VALID_REQ_TITLE_MS)
                                                   .withCredits(VALID_REQ_CREDITS_MS).build();

        expectedModel.setRequirement(model.getRequirement(code).get(), editedRequirement);
        RequirementEditCommand command = new RequirementEditCommand(code, descriptor);
        String expectedMessage = String.format(MESSAGE_REQUIREMENT_EDIT_SUCCESS, editedRequirement);

        assertCommandSuccess(command, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_someFieldsSpecified_success() {
        Requirement editedTitle = new RequirementBuilder(requirement).withTitle(VALID_REQ_TITLE_MS).build();
        Requirement editedCredits = new RequirementBuilder(requirement)
                                        .withCreditsOneParameter(VALID_REQ_CREDITS_MS).build();
        EditRequirementDescriptor descriptorTitle = new EditRequirementDescriptorBuilder()
                                                        .withTitle(VALID_REQ_TITLE_MS).build();
        EditRequirementDescriptor descriptorCredits = new EditRequirementDescriptorBuilder()
                                                        .withCredits(VALID_REQ_CREDITS_MS).build();

        Model expectedModel = new ModelManager(new CourseBook(model.getCourseBook()), new UserPrefs());
        expectedModel.setRequirement(model.getRequirement(code).get(), editedTitle);
        RequirementEditCommand command = new RequirementEditCommand(code, descriptorTitle);
        String expectedMessage = String.format(MESSAGE_REQUIREMENT_EDIT_SUCCESS, editedTitle);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);

        model = new ModelManager(getTypicalCourseBook(), new UserPrefs());
        expectedModel = new ModelManager(new CourseBook(model.getCourseBook()), new UserPrefs());
        expectedModel.setRequirement(model.getRequirement(code).get(), editedCredits);
        command = new RequirementEditCommand(code, descriptorCredits);
        expectedMessage = String.format(MESSAGE_REQUIREMENT_EDIT_SUCCESS, editedCredits);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_noFieldSpecified_modelNotUpdated() {
        EditRequirementDescriptor descriptor = new EditRequirementDescriptor();
        Model expectedModel = new ModelManager(new CourseBook(model.getCourseBook()), new UserPrefs());
        RequirementEditCommand command = new RequirementEditCommand(code, descriptor);
        String expectedMessage = MESSAGE_REQUIREMENT_NOT_EDITED;

        assertCommandThrows(command, model, expectedMessage, expectedModel);
    }

    // @Test
    // public void execute_filteredList_success() {
    //     showPersonAtIndex(model, INDEX_FIRST_PERSON);
    //
    //     Person personInFilteredList = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
    //     Person editedPerson = new PersonBuilder(personInFilteredList).withName(VALID_NAME_BOB).build();
    //     EditCommand editCommand = new EditCommand(INDEX_FIRST_PERSON,
    //         new EditPersonDescriptorBuilder().withName(VALID_NAME_BOB).build());
    //
    //     String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_PERSON_SUCCESS, editedPerson);
    //
    //     Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
    //     expectedModel.setPerson(model.getFilteredPersonList().get(0), editedPerson);
    //
    //     assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    // }
    //
    // @Test
    // public void execute_duplicatePersonUnfilteredList_failure() {
    //     Person firstPerson = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
    //     EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder(firstPerson).build();
    //     EditCommand editCommand = new EditCommand(INDEX_SECOND_PERSON, descriptor);
    //
    //     assertCommandFailure(editCommand, model, EditCommand.MESSAGE_DUPLICATE_PERSON);
    // }
    //
    // @Test
    // public void execute_duplicatePersonFilteredList_failure() {
    //     showPersonAtIndex(model, INDEX_FIRST_PERSON);
    //
    //     // edit person in filtered list into a duplicate in address book
    //     Person personInList = model.getAddressBook().getPersonList().get(INDEX_SECOND_PERSON.getZeroBased());
    //     EditCommand editCommand = new EditCommand(INDEX_FIRST_PERSON,
    //         new EditPersonDescriptorBuilder(personInList).build());
    //
    //     assertCommandFailure(editCommand, model, EditCommand.MESSAGE_DUPLICATE_PERSON);
    // }
    //
    // @Test
    // public void execute_invalidPersonIndexUnfilteredList_failure() {
    //     Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
    //     EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder().withName(VALID_NAME_BOB).build();
    //     EditCommand editCommand = new EditCommand(outOfBoundIndex, descriptor);
    //
    //     assertCommandFailure(editCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    // }
    //
    // /**
    //  * Edit filtered list where index is larger than size of filtered list,
    //  * but smaller than size of address book
    //  */
    // @Test
    // public void execute_invalidPersonIndexFilteredList_failure() {
    //     showPersonAtIndex(model, INDEX_FIRST_PERSON);
    //     Index outOfBoundIndex = INDEX_SECOND_PERSON;
    //     // ensures that outOfBoundIndex is still in bounds of address book list
    //     assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getPersonList().size());
    //
    //     EditCommand editCommand = new EditCommand(outOfBoundIndex,
    //         new EditPersonDescriptorBuilder().withName(VALID_NAME_BOB).build());
    //
    //     assertCommandFailure(editCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    // }
    //
    // @Test
    // public void equals() {
    //     final EditCommand standardCommand = new EditCommand(INDEX_FIRST_PERSON, DESC_AMY);
    //
    //     // same values -> returns true
    //     EditPersonDescriptor copyDescriptor = new EditPersonDescriptor(DESC_AMY);
    //     EditCommand commandWithSameValues = new EditCommand(INDEX_FIRST_PERSON, copyDescriptor);
    //     assertTrue(standardCommand.equals(commandWithSameValues));
    //
    //     // same object -> returns true
    //     assertTrue(standardCommand.equals(standardCommand));
    //
    //     // null -> returns false
    //     assertFalse(standardCommand.equals(null));
    //
    //     // different types -> returns false
    //     assertFalse(standardCommand.equals(new ClearCommand()));
    //
    //     // different index -> returns false
    //     assertFalse(standardCommand.equals(new EditCommand(INDEX_SECOND_PERSON, DESC_AMY)));
    //
    //     // different descriptor -> returns false
    //     assertFalse(standardCommand.equals(new EditCommand(INDEX_FIRST_PERSON, DESC_BOB)));
    // }
}
