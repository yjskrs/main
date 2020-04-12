package igrad.logic.commands.requirement;

//@@author yjskrs

import static igrad.logic.commands.CommandTestUtil.assertCommandSuccess;
import static igrad.logic.commands.requirement.RequirementCommandTestUtil.ModelStubWithRequirement;
import static igrad.logic.commands.requirement.RequirementCommandTestUtil.VALID_REQ_CODE_CSF;
import static igrad.logic.commands.requirement.RequirementCommandTestUtil.VALID_REQ_CREDITS_GE;
import static igrad.logic.commands.requirement.RequirementCommandTestUtil.VALID_REQ_TITLE_GE;
import static igrad.logic.commands.requirement.RequirementEditCommand.EditRequirementDescriptor;
import static igrad.logic.commands.requirement.RequirementEditCommand.MESSAGE_REQUIREMENT_EDIT_SUCCESS;
import static igrad.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import igrad.logic.commands.Command;
import igrad.model.requirement.Requirement;
import igrad.model.requirement.RequirementCode;
import igrad.testutil.EditRequirementDescriptorBuilder;
import igrad.testutil.RequirementBuilder;

public class RequirementEditCommandTest {
    private RequirementCode code = new RequirementCode(VALID_REQ_CODE_CSF);
    private Requirement requirement = new RequirementBuilder().build();
    private EditRequirementDescriptor descriptor = new EditRequirementDescriptorBuilder().build();

    @Test
    public void constructor_nullRequirement_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new RequirementEditCommand(null, descriptor));
        assertThrows(NullPointerException.class, () -> new RequirementEditCommand(code, null));
    }

    @Test
    public void execute_editRequirement_success() {
        Requirement modifiedTitle = new RequirementBuilder().withTitle(VALID_REQ_TITLE_GE).build();
        Requirement modifiedCredits = new RequirementBuilder().withCreditsOneParameter(VALID_REQ_CREDITS_GE).build();

        EditRequirementDescriptor descriptorWithTitle = new EditRequirementDescriptorBuilder()
                                                            .withTitle(VALID_REQ_TITLE_GE)
                                                            .build();
        EditRequirementDescriptor descriptorWithCredits = new EditRequirementDescriptorBuilder()
                                                              .withCredits(VALID_REQ_CREDITS_GE)
                                                              .build();

        ModelStubWithRequirement modelStub = new ModelStubWithRequirement(requirement);
        ModelStubWithRequirement modelStubExpected = new ModelStubWithRequirement(modifiedTitle);
        Command command = new RequirementEditCommand(code, descriptorWithTitle);
        String message = String.format(MESSAGE_REQUIREMENT_EDIT_SUCCESS, modifiedTitle);
        assertCommandSuccess(command, modelStub, message, modelStubExpected);

        modelStub = new ModelStubWithRequirement(requirement);
        modelStubExpected = new ModelStubWithRequirement(modifiedCredits);
        command = new RequirementEditCommand(code, descriptorWithCredits);
        message = String.format(MESSAGE_REQUIREMENT_EDIT_SUCCESS, modifiedCredits);
        assertCommandSuccess(command, modelStub, message, modelStubExpected);
    }

    // @Test
    // public void execute_allFieldsSpecifiedUnfilteredList_success() {
    //     Person editedPerson = new PersonBuilder().build();
    //     EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder(editedPerson).build();
    //     EditCommand editCommand = new EditCommand(INDEX_FIRST_PERSON, descriptor);
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
    // public void execute_someFieldsSpecifiedUnfilteredList_success() {
    //     Index indexLastPerson = Index.fromOneBased(model.getFilteredPersonList().size());
    //     Person lastPerson = model.getFilteredPersonList().get(indexLastPerson.getZeroBased());
    //
    //     PersonBuilder personInList = new PersonBuilder(lastPerson);
    //     Person editedPerson = personInList.withName(VALID_NAME_BOB).withPhone(VALID_PHONE_BOB)
    //                               .withTags(VALID_TAG_HUSBAND).build();
    //
    //     EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder().withName(VALID_NAME_BOB)
    //                                           .withPhone(VALID_PHONE_BOB).withTags(VALID_TAG_HUSBAND).build();
    //     EditCommand editCommand = new EditCommand(indexLastPerson, descriptor);
    //
    //     String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_PERSON_SUCCESS, editedPerson);
    //
    //     Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
    //     expectedModel.setPerson(lastPerson, editedPerson);
    //
    //     assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    // }
    //
    // @Test
    // public void execute_noFieldSpecifiedUnfilteredList_success() {
    //     EditCommand editCommand = new EditCommand(INDEX_FIRST_PERSON, new EditPersonDescriptor());
    //     Person editedPerson = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
    //
    //     String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_PERSON_SUCCESS, editedPerson);
    //
    //     Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
    //
    //     assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    // }
    //
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
