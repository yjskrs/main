package igrad.logic.parser;

import static igrad.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static igrad.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static igrad.testutil.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import igrad.logic.commands.ExitCommand;
import igrad.logic.commands.HelpCommand;
import igrad.logic.parser.exceptions.ParseException;

public class CourseBookParserTest {

    private final CourseBookParser parser = new CourseBookParser();

    /*
    @Test
    public void parseCommand_add() throws Exception {
        Module module = new ModuleBuilder().build();
        ModuleAddCommand command = (ModuleAddCommand) parser.parseCommand(ModuleUtil.getAddCommand(module));
        assertEquals(new ModuleAddCommand(module), command);
    }*/

    /*
    @Test
    public void parseCommand_delete() throws Exception {
        ModuleDeleteCommand command = (ModuleDeleteCommand) parser.parseCommand(
            ModuleDeleteCommand.COMMAND_WORD + " " + INDEX_FIRST_MODULE.getOneBased());
        assertEquals(new ModuleDeleteCommand(INDEX_FIRST_MODULE), command);
    }*/

    /*
    @Test
    public void parseCommand_edit() throws Exception {
        Module module = new ModuleBuilder().build();
        EditModuleDescriptor descriptor = new EditModuleDescriptorBuilder(module).build();
        ModuleEditCommand command = (ModuleEditCommand) parser.parseCommand(ModuleEditCommand.COMMAND_WORD + " "
            + INDEX_FIRST_MODULE.getOneBased() + " "
            + ModuleUtil.getEditModuleDescriptorDetails(descriptor));
        assertEquals(new ModuleEditCommand(INDEX_FIRST_MODULE, descriptor), command);
    }*/

    @Test
    public void parseCommand_exit() throws Exception {
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD) instanceof ExitCommand);
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD + " 3") instanceof ExitCommand);
    }

    @Test
    public void parseCommand_help() throws Exception {
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD) instanceof HelpCommand);
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD + " 3") instanceof HelpCommand);
    }

    @Test
    public void parseCommand_unrecognisedInput_throwsParseException() {
        assertThrows(ParseException.class, String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE), ()
            -> parser.parseCommand(""));
    }

    @Test
    public void parseCommand_unknownCommand_throwsParseException() {
        assertThrows(ParseException.class, MESSAGE_UNKNOWN_COMMAND, ()
            -> parser.parseCommand("unknownCommand"));
    }
}
