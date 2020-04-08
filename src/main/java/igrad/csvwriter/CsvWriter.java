package igrad.csvwriter;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import igrad.model.module.Module;

/**
 * Writes the data stored as a human-readable CSV file.
 */
public class CsvWriter {

    private static final String fileName = "study_plan.csv";
    private FileWriter csvWriter;
    private List<Module> sortedList;

    public CsvWriter(List<Module> sortedList) throws IOException {
        csvWriter = new FileWriter(fileName);

        this.sortedList = sortedList;
    }

    /**
     * Writes to CSV
     */
    public void write() throws IOException {
        writeHeaders();
        writeBody();
        closeWriter();
    }

    private void closeWriter() throws IOException {
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

        for (int i = 0; i < sortedList.size(); i++) {
            Module module = sortedList.get(i);

            if (module.getSemester().isPresent()) {
                append(module.getSemester().get().toString());
            }
            append(module.getModuleCode().toString());
            append(module.getTitle().toString());
            append(module.getCredits().toString());
            appendNewLine();

            if (i < sortedList.size() - 1) {
                Module nextModule = sortedList.get(i + 1);
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
            "Module Credits"
        };

        for (String header : headers) {
            append(header);
        }

        appendNewLine();

    }

}
