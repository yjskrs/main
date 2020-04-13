package igrad.model.quotes;

//@@author waynewee

/**
 * Randomly selects a {@code Quote} each time the user starts the application.
 */
public class QuoteGenerator {

    private String[] quotes = {
        "Believe you can and you’re halfway there.",
        "You have to expect things of yourself before you can do them.",
        "It always seems impossible until it’s done.",
        "Don’t let what you cannot do interfere with what you can do.",
        "Start where you are. Use what you have. Do what you can.",
        "The secret of success is to do the common things uncommonly well.",
        "Strive for progress, not perfection.",
        "I find that the harder I work, the more luck I seem to have.",
        "Success is the sum of small efforts, repeated day in and day out.",
        "Don’t wish it were easier; wish you were better.",
        "The secret to getting ahead is getting started.",
        "You don’t have to be great to start, but you have to start to be great.",
        "The expert in everything was once a beginner.",
        "There are no shortcuts to any place worth going.",
        "Push yourself, because no one else is going to do it for you.",
        "There is no substitute for hard work.",
        "The difference between ordinary and extraordinary is that little “extra.”",
        "You don’t always get what you wish for; you get what you work for.",
        "The only place where success comes before work is in the dictionary.",
        "There are no traffic jams on the extra mile.",
        "If you’re going through hell, keep going.",
        "Don’t let your victories go to your head, or your failures go to your heart.",
        "Failure is the opportunity to begin again more intelligently.",
        "You don’t drown by falling in the water; you drown by staying there.",
        "It’s not going to be easy, but it’s going to be worth it.",
        "I’ve failed over and over and over again in my life. And that is why I succeed."
    };

    public String getRandomQuote() {

        int max = quotes.length - 1;
        int min = 0;

        int randomIndex = (int) (Math.random() * ((max - min) + 1)) + min;

        return quotes[randomIndex];

    }

}
