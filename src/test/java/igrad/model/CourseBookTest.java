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

import igrad.model.course.CourseInfo;
import igrad.model.module.Module;
import igrad.model.module.exceptions.DuplicateModuleException;
import igrad.model.requirement.Requirement;
import igrad.testutil.ModuleBuilder;
import igrad.testutil.TypicalModules;
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
        CourseBook newData = TypicalModules.getTypicalCourseBook();
        courseBook.resetData(newData);
        assertEquals(newData, courseBook);
    }

    @Test
    public void resetData_withDuplicateModules_throwsDuplicatePersonException() {
        // Two modules with the same identity fields
        Module editedProgrammingMethodology = new ModuleBuilder(TypicalModules.CS1101S)
            // .withTags(CommandTestUtil.VALID_TAG_HARD)
            .build();
        List<Module> newModules = Arrays.asList(TypicalModules.CS1101S, editedProgrammingMethodology);
        CourseBookStub newData = new CourseBookStub(newModules);

        assertThrows(DuplicateModuleException.class, () -> courseBook.resetData(newData));
    }

    @Test
    public void hasModule_nullModule_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> courseBook.hasModule(null));
    }

    @Test
    public void hasModule_moduleNotInCourseBook_returnsFalse() {
        assertFalse(courseBook.hasModule(TypicalModules.CS1101S));
    }

    @Test
    public void hasModule_moduleInCourseBook_returnsTrue() {
        courseBook.addModule(TypicalModules.CS1101S);
        assertTrue(courseBook.hasModule(TypicalModules.CS1101S));
    }

    @Test
    public void hasModule_moduleWithSameIdentityFieldsInCourseBook_returnsTrue() {
        courseBook.addModule(TypicalModules.CS1101S);
        Module editedAlice = new ModuleBuilder(TypicalModules.CS1101S)
            // .withTags(CommandTestUtil.VALID_TAG_HARD)
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

        // We're not testing requirement as its similar to modules
        @Override
        public ObservableList<Requirement> getRequirementList() {
            return null;
        }

        @Override
        public String requirementsFulfilled() {
            return "";
        }

        @Override
        public boolean isComplete() {
            return true;
        }

        @Override
        public CourseInfo getCourseInfo() {
            return null;
        }
    }

}
