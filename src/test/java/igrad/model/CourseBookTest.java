package igrad.model;

import static igrad.testutil.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import igrad.logic.commands.CommandTestUtil;
import igrad.model.module.exceptions.DuplicateModuleException;
import igrad.model.module.Module;
import igrad.testutil.ModuleBuilder;
import igrad.testutil.TypicalPersons;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class CourseBookTest {

    private final CourseBook courseBook = new CourseBook();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), courseBook.getModuleList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> courseBook.resetData(null));
    }

    @Test
    public void resetData_withValidReadOnlyCourseBook_replacesData() {
        CourseBook newData = TypicalPersons.getTypicalCourseBook();
        courseBook.resetData(newData);
        assertEquals(newData, courseBook);
    }

    @Test
    public void resetData_withDuplicatePersons_throwsDuplicatePersonException() {
        // Two modules with the same identity fields
        Module editedAlice = new ModuleBuilder(TypicalPersons.ALICE).withTags(CommandTestUtil.VALID_TAG_HUSBAND)
                .build();
        List<Module> newModules = Arrays.asList(TypicalPersons.ALICE, editedAlice);
        CourseBookStub newData = new CourseBookStub(newModules);

        assertThrows(DuplicateModuleException.class, () -> courseBook.resetData(newData));
    }

    @Test
    public void hasPerson_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> courseBook.hasModule(null));
    }

    @Test
    public void hasPerson_personNotInCourseBook_returnsFalse() {
        assertFalse(courseBook.hasModule(TypicalPersons.ALICE));
    }

    @Test
    public void hasPerson_personInCourseBook_returnsTrue() {
        courseBook.addModule(TypicalPersons.ALICE);
        assertTrue(courseBook.hasModule(TypicalPersons.ALICE));
    }

    @Test
    public void hasPerson_personWithSameIdentityFieldsInCourseBook_returnsTrue() {
        courseBook.addModule(TypicalPersons.ALICE);
        Module editedAlice = new ModuleBuilder(TypicalPersons.ALICE).withTags(CommandTestUtil.VALID_TAG_HUSBAND)
                .build();
        assertTrue(courseBook.hasModule(editedAlice));
    }

    @Test
    public void getPersonList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> courseBook.getModuleList().remove(0));
    }

    /**
     * A stub ReadOnlyCourseBook whose modules list can violate interface constraints.
     */
    private static class CourseBookStub implements ReadOnlyCourseBook {
        private final ObservableList<Module> modules = FXCollections.observableArrayList();

        CourseBookStub(Collection<Module> modules) {
            this.modules.setAll(modules);
        }

        @Override
        public ObservableList<Module> getModuleList() {
            return modules;
        }
    }

}
