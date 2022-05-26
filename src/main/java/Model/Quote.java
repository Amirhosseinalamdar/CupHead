package Model;

public enum Quote {
    PARENT("always be kind to your parents"),
    SIBLINGS("always be kind to your siblings"),
    SANDWICH("always be kind to your sandwiches"),
    TWO("two is always better than one"),
    EAT("eat and drink but don't extravagance"),
    LAST("I promise you it's not hard at least for you"),
    KILL("did habil killed qabil or qabil killed habil?");

    private String quote;

    Quote(String quote) {
        this.quote = quote;
    }

    public String getQuote() {
        return quote;
    }
}
