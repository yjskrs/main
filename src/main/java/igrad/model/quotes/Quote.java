package igrad.model.quotes;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Represents an inspiration quote, randomly selected each time the user starts the application.
 */
public class Quote {

    private String value;

    public Quote(@JsonProperty("value") String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }
}
