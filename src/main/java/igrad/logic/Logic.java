package igrad.logic;

import java.io.IOException;
import java.nio.file.Path;

import igrad.commons.core.GuiSettings;
import igrad.logic.commands.CommandResult;
import igrad.logic.commands.exceptions.CommandException;
import igrad.logic.parser.exceptions.ParseException;
import igrad.model.Model;
import igrad.model.ReadOnlyCourseBook;
import igrad.model.module.Module;
import igrad.services.exceptions.ServiceException;
import javafx.collections.ObservableList;

/**
 * API of the Logic component.
 */
public interface Logic {
    /**
     * Executes the command and returns the result.
     *
     * @param commandText The command as entered by the user.
     * @return the result of the command execution.
     * @throws CommandException If an error occurs during command execution.
     * @throws ParseException   If an error occurs during parsing.
     */
    CommandResult execute(String commandText) throws CommandException, ParseException, IOException, ServiceException;

    /**
     * Returns the CourseBook.
     *
     * @see Model#getCourseBook()
     */
    ReadOnlyCourseBook getCourseBook();

    ObservableList<Module> getFilteredModuleList();

    /**
     * Returns the user prefs' course book file path.
     */
    Path getCourseBookFilePath();

    /**
     * Returns the user prefs' GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Set the user prefs' GUI settings.
     */
    void setGuiSettings(GuiSettings guiSettings);
}
