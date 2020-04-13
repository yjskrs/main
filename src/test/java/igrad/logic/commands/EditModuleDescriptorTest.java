package igrad.logic.commands;

import static igrad.logic.commands.CommandTestUtil.DESC_COMPUTER_ORGANISATION;
import static igrad.logic.commands.CommandTestUtil.DESC_PROGRAMMING_METHODOLOGY;
import static igrad.logic.commands.CommandTestUtil.VALID_MODULE_CODE_CS_2100;
import static igrad.logic.commands.CommandTestUtil.VALID_TITLE_CS_2100;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import igrad.logic.commands.module.ModuleEditCommand;
import igrad.testutil.EditModuleDescriptorBuilder;

public class EditModuleDescriptorTest {

    @Test
    public void equals() {
        // same values -> returns true
        ModuleEditCommand.EditModuleDescriptor descriptorWithSameValues = new ModuleEditCommand
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
        ModuleEditCommand.EditModuleDescriptor editedProgrammingMethodology = new EditModuleDescriptorBuilder(
            DESC_PROGRAMMING_METHODOLOGY)
            .withTitle(VALID_TITLE_CS_2100).build();
        assertFalse(DESC_PROGRAMMING_METHODOLOGY.equals(editedProgrammingMethodology));

        // different module code -> returns false
        editedProgrammingMethodology = new EditModuleDescriptorBuilder(DESC_PROGRAMMING_METHODOLOGY)
            .withModuleCode(VALID_MODULE_CODE_CS_2100)
            .build();
        assertFalse(DESC_PROGRAMMING_METHODOLOGY.equals(editedProgrammingMethodology));
    }
}
