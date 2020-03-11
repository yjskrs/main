package igrad.model.module;

import static igrad.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import igrad.testutil.ModuleBuilder;

public class ModuleTest {

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        Module module = new ModuleBuilder().build();
        assertThrows(UnsupportedOperationException.class, () -> module.getTags().remove(0));
    }

    @Test
    public void isSameModule() {
        // same object -> returns true
        //assertTrue(TypicalPersons.ALICE.isSameModule(TypicalPersons.ALICE));

        // null -> returns false
        //assertFalse(TypicalPersons.ALICE.isSameModule(null));

        // different phone and email -> returns false
        //Module editedAlice = new ModuleBuilder(TypicalPersons.ALICE).withPhone(VALID_PHONE_BOB)
        //        .withEmail(VALID_EMAIL_BOB).build();
        //assertFalse(TypicalPersons.ALICE.isSameModule(editedAlice));

        // different name -> returns false
        //editedAlice = new ModuleBuilder(TypicalPersons.ALICE).withName(VALID_NAME_BOB).build();
        //assertFalse(TypicalPersons.ALICE.isSameModule(editedAlice));

        // same name, same phone, different attributes -> returns true
        //editedAlice = new ModuleBuilder(TypicalPersons.ALICE).withEmail(VALID_EMAIL_BOB)
        //        .withTags(VALID_TAG_HUSBAND).build();
        //assertTrue(TypicalPersons.ALICE.isSameModule(editedAlice));

        // same name, same email, different attributes -> returns true
        //editedAlice = new ModuleBuilder(TypicalPersons.ALICE).withPhone(VALID_PHONE_BOB)
        //        .withTags(VALID_TAG_HUSBAND).build();
        //assertTrue(TypicalPersons.ALICE.isSameModule(editedAlice));

        // same name, same phone, same email, different attributes -> returns true
        //editedAlice = new ModuleBuilder(TypicalPersons.ALICE).withTags(VALID_TAG_HUSBAND).build();
        //assertTrue(TypicalPersons.ALICE.isSameModule(editedAlice));
    }

    @Test
    public void equals() {
        // same values -> returns true
        //Module aliceCopy = new ModuleBuilder(TypicalPersons.ALICE).build();
        //assertTrue(TypicalPersons.ALICE.equals(aliceCopy));

        // same object -> returns true
        //assertTrue(TypicalPersons.ALICE.equals(TypicalPersons.ALICE));

        // null -> returns false
        //assertFalse(TypicalPersons.ALICE.equals(null));

        // different type -> returns false
        //assertFalse(TypicalPersons.ALICE.equals(5));

        // different module -> returns false
        //assertFalse(TypicalPersons.ALICE.equals(TypicalPersons.BOB));

        // different name -> returns false
        //Module editedAlice = new ModuleBuilder(TypicalPersons.ALICE).withName(VALID_NAME_BOB).build();
        //assertFalse(TypicalPersons.ALICE.equals(editedAlice));

        // different phone -> returns false
        //editedAlice = new ModuleBuilder(TypicalPersons.ALICE).withPhone(VALID_PHONE_BOB).build();
        //assertFalse(TypicalPersons.ALICE.equals(editedAlice));

        // different email -> returns false
        //editedAlice = new ModuleBuilder(TypicalPersons.ALICE).withEmail(VALID_EMAIL_BOB).build();
        //assertFalse(TypicalPersons.ALICE.equals(editedAlice));

        // different tags -> returns false
        //editedAlice = new ModuleBuilder(TypicalPersons.ALICE).withTags(VALID_TAG_HUSBAND).build();
        //assertFalse(TypicalPersons.ALICE.equals(editedAlice));
    }
}
