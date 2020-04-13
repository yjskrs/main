package igrad.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.io.IOException;

import igrad.logic.commands.Command;
import igrad.logic.parser.exceptions.ParseException;
import igrad.services.exceptions.ServiceException;

/**
 * Contains helper methods for testing command parsers.
 */
public class CommandParserTestUtil {

    /**
     * Asserts that the parsing of {@code userInput} by {@code parser} is successful
     * and the command created is equals to {@code expectedCommand}.
     *
     * @param parser          Parser object.
     * @param userInput       User input string.
     * @param expectedCommand Expected command.
     */
    public static void assertParseSuccess(Parser parser, String userInput, Command expectedCommand) {
        try {
            Command command = parser.parse(userInput);
            assertEquals(expectedCommand, command);
        } catch (ParseException pe) {
            throw new IllegalArgumentException("Invalid userInput.", pe);
        } catch (ServiceException | IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Asserts that the parsing of {@code userInput} by {@code parser} is unsuccessful
     * and the error message thrown is equals to {@code expectedMessage}.
     *
     * @param parser          Parser object.
     * @param userInput       User input string.
     * @param expectedMessage Expected command.
     */
    public static void assertParseFailure(Parser parser, String userInput, String expectedMessage) {
        try {
            parser.parse(userInput);
            throw new AssertionError("The expected ParseException was not thrown.");
        } catch (ParseException pe) {
            assertEquals(expectedMessage, pe.getMessage());
        } catch (ServiceException | IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Asserts that the parsing of {@code inputA} by {@code parser} produces the same
     * output as the parsing of {@code inputB} by {@code parser}.
     *
     * @param parser Parser object.
     * @param inputA User input string A.
     * @param inputB User input string B.
     */
    public static void assertParseEquals(Parser parser, String inputA, String inputB) {
        try {
            Command commandA = parser.parse(inputA);
            Command commandB = parser.parse(inputB);
            assertEquals(commandA, commandB);
        } catch (ParseException pe) {
            throw new IllegalArgumentException("Invalid input.", pe);
        } catch (ServiceException | IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Asserts that the parsing of {@code inputA} by {@code parser} produces different
     * output from the parsing of {@code inputB} by {@code parser}.
     *
     * @param parser Parser object.
     * @param inputA User input string A.
     * @param inputB User input string B.
     */
    public static void assertParseNotEquals(Parser parser, String inputA, String inputB) {
        try {
            Command commandA = parser.parse(inputA);
            Command commandB = parser.parse(inputB);
            assertNotEquals(commandA, commandB);
        } catch (ParseException pe) {
            throw new IllegalArgumentException("Invalid input.", pe);
        } catch (ServiceException | IOException e) {
            e.printStackTrace();
        }
    }
}
