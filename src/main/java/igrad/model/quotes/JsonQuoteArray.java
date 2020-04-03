package igrad.model.quotes;

import java.util.ArrayList;
import com.fasterxml.jackson.annotation.JsonProperty;

public class JsonQuoteArray {

    private ArrayList<Quote> quotes = new ArrayList<>();

    public JsonQuoteArray(@JsonProperty("quotes") ArrayList<Quote> quotes) {
        this.quotes = quotes;
    }

    public ArrayList<Quote> getQuotes() {
        return quotes;
    }
}
