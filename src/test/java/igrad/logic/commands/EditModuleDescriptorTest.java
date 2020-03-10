package igrad.logic.commands;

import static igrad.logic.commands.CommandTestUtil.*;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import igrad.testutil.EditModuleDescriptorBuilder;

public class EditModuleDescriptorTest {

    @Test
    public void equals() {
        // same values -> returns true
        EditCommand.EditModuleDescriptor descriptorWithSameValues = new EditCommand
                .EditModuleDescriptor(DESC_PROGRAMMING_METHODOLOGY);
        assertTrue(DESC_PROGRAMMING_METHODOLOGY.equals(descriptorWithSameValues));

        // same object -> returns true
        assertTrue(DESC_PROGRAMMING_METHODOLOGY.equals(DESC_PROGRAMMING_METHODOLOGY));

        // null -> returns false
        assertFalse(DESC_PROGRAMMING_METHODOLOGY.equals(null));

        // different types -> returns false
        assertFalse(DESC_PROGRAMMING_METHODOLOGY.equals(5));

        // different values -> returns false
        assertFalse(DESC_PROGRAMMING_METHODOLOGY.equals(DESC_COMPUTER_ORGANISATION));

        // different title -> returns false
        EditCommand.EditModuleDescriptor editedProgrammingMethodology = new EditModuleDescriptorBuilder(
                DESC_PROGRAMMING_METHODOLOGY)
                .withTitle(VALID_TITLE_COMPUTER_ORGANISATION).build();
        assertFalse(DESC_PROGRAMMING_METHODOLOGY.equals(editedProgrammingMethodology));

        // different module code -> returns false
        editedProgrammingMethodology = new EditModuleDescriptorBuilder(DESC_PROGRAMMING_METHODOLOGY)
                .withModuleCode(VALID_MODULE_CODE_COMPUTER_ORGANISATION)
                .build();
        assertFalse(DESC_PROGRAMMING_METHODOLOGY.equals(editedProgrammingMethodology));

        // different tags -> returns false
        editedProgrammingMethodology = new EditModuleDescriptorBuilder(DESC_PROGRAMMING_METHODOLOGY)
                .withTags(VALID_TAG_HARD)
                .build();
        assertFalse(DESC_PROGRAMMING_METHODOLOGY.equals(editedProgrammingMethodology));
    }
}
