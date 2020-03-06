package igrad.logic.commands;

import static igrad.logic.commands.CommandTestUtil.assertCommandSuccess;

import igrad.testutil.TypicalPersons;
import org.junit.jupiter.api.Test;

import igrad.model.CourseBook;
import igrad.model.Model;
import igrad.model.ModelManager;
import igrad.model.UserPrefs;

public class ClearCommandTest {

    @Test
    public void execute_emptyAddressBook_success() {
        Model model = new ModelManager();
        Model expectedModel = new ModelManager();

        assertCommandSuccess(new ClearCommand(), model, ClearCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_nonEmptyAddressBook_success() {
        Model model = new ModelManager(TypicalPersons.getTypicalAddressBook(), new UserPrefs());
        Model expectedModel = new ModelManager(TypicalPersons.getTypicalAddressBook(), new UserPrefs());
        expectedModel.setCourseBook(new CourseBook());

        assertCommandSuccess(new ClearCommand(), model, ClearCommand.MESSAGE_SUCCESS, expectedModel);
    }

}
