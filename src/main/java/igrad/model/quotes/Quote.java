package igrad.model.quotes;

import com.fasterxml.jackson.annotation.JsonProperty;

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
