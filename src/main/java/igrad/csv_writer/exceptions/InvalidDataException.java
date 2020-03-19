package igrad.csv_writer.exceptions;

import java.io.IOException;

/**
 * Signals that one or more of the required fields are not available
 */
public class InvalidDataException extends IOException {

    public InvalidDataException(String msg) {
        super(msg);
    }

}
