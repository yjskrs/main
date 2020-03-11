package igrad.model.course;

import org.junit.jupiter.api.Test;

import igrad.testutil.ModuleBuilder;

public class CourseInfoTest {

    @Test
    public void equals() {
        // same values -> returns true
        Module aliceCopy = new ModuleBuilder(TypicalPersons.ALICE).build();
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

