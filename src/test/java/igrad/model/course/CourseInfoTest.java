package igrad.model.course;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import igrad.testutil.CourseInfoBuilder;
import igrad.testutil.TypicalCourseInfos;

public class CourseInfoTest {
    @Test
    public void equals() {
        // same values -> returns true
        CourseInfo bSciMathCopy = new CourseInfoBuilder(TypicalCourseInfos.B_SCI_MATH).build();
        assertTrue(TypicalCourseInfos.B_SCI_MATH.equals(bSciMathCopy));

        // same object -> returns true
        assertTrue(TypicalCourseInfos.B_SCI_MATH.equals(TypicalCourseInfos.B_SCI_MATH));

        // null -> returns false
        assertFalse(TypicalCourseInfos.B_SCI_MATH.equals(null));

        // TODO: (Teri) please help update the test method accordingly, you can refer to the 3 above examples

        // different type -> returns false
        //assertFalse(TypicalPersons.ALICE.equals(5));

        // different module -> returns false
        //assertFalse(TypicalPersons.ALICE.equals(TypicalPersons.BOB));

        // different name -> returns false
        //Module editedAlice = new ModuleBuilder(TypicalPersons.ALICE).withName(VALID_NAME_BOB).build();
        //assertFalse(TypicalPersons.ALICE.equals(editedAlice));
    }
}

