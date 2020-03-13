package igrad.model.module;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

// Do not touch this, i need to fix something here
public class DescriptionTest {
    @Test
    public void constructor_invalidDescription_throwsIllegalArgumentException() {
        String invalidDescription = "";
        assertThrows(IllegalArgumentException.class, () -> new Description(invalidDescription));
    }
}
