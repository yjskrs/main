package igrad.ui;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class StatusBarTest {

    private StatusBar statusBar = new StatusBar();

    @Test
    public void constructor() {
        statusBar = new StatusBar();
        assertEquals("Course: ", statusBar.getLabelText());
    }

    @Test
    public void isStatusBarUpdating() {
        statusBar = new StatusBar();
        statusBar.setCourseName("Computer Science");
        assertEquals("Course: Computer Science", statusBar.getLabelText());
    }
}
