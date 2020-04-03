package igrad.model.quotes;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Optional;
import java.util.logging.Logger;
import igrad.commons.core.LogsCenter;
import igrad.commons.exceptions.DataConversionException;
import igrad.commons.util.JsonUtil;

public class QuoteGenerator {

    private static final Logger logger = LogsCenter.getLogger(QuoteGenerator.class);
    private ArrayList<Quote> quotes = new ArrayList<>();

    public QuoteGenerator() {

        try {
            Path quotesPath = Paths.get("quotes.json");
            Optional<JsonQuoteArray> jsonQuoteArray = JsonUtil.readJsonFile(quotesPath, JsonQuoteArray.class);

            jsonQuoteArray.ifPresent(quoteArray -> quotes = quoteArray.getQuotes());

        } catch (DataConversionException e) {
            logger.info("Error reading quotes file" + e.getMessage());
        }
    }

    public Quote getRandomQuote() {

        int max = quotes.size() - 1;
        int min = 0;

        int randomIndex = (int) (Math.random() * ((max - min) + 1)) + min;

        return quotes.get(randomIndex);

    }

}
