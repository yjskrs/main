package igrad.csv_writer;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import igrad.model.module.Module;
import igrad.model.module.sorters.SortBySemester;
import javafx.collections.ObservableList;

/**
 * Writes the data stored as a human-readable CSV file.
 */
public class CsvWriter {

    private static final String fileName = "study_plan.csv";
    private FileWriter csvWriter;
    private ArrayList<Module> sortableList;

    public CsvWriter(ObservableList<Module> filteredModuleList) throws IOException {
        csvWriter = new FileWriter(fileName);
        sortableList = new ArrayList<>(filteredModuleList);

        sortableList.sort(new SortBySemester());
    }

    /**
     * Writes to CSV
     */
    public void write() throws IOException {

        writeHeaders();
        writeBody();
        csvWriter.flush();
        csvWriter.close();

    }

    private void appendNewLine() throws IOException {
        csvWriter.append("\n");
    }

    private void append(String text) throws IOException {
        csvWriter.append(text);
        csvWriter.append(",");
    }

    /**
     * Writes each module as a line. Separates modules taken in different semesters
     * by a new line.
     */
    private void writeBody() throws IOException {

        for (int i = 0; i < sortableList.size(); i++) {
            Module module = sortableList.get(i);

            append(module.getSemester().toString());
            append(module.getModuleCode().toString());
            append(module.getTitle().toString());
            append(module.getCredits().toString());
            appendNewLine();

            if (i < sortableList.size() - 1) {
                Module nextModule = sortableList.get(i + 1);
                if (!nextModule.getSemester().equals(module.getSemester())) {
                    appendNewLine();
                }
            }

        }
    }

    /**
     * Writes the headers of the CSV file.
     */
    private void writeHeaders() throws IOException {

        String[] headers = {
            "Semester",
            "Module Code",
            "Module Title",
            "MCs"
        };

        for (String header : headers) {
            append(header);
        }

        appendNewLine();

    }

}
