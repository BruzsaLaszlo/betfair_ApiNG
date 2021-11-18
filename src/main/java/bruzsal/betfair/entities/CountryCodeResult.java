package bruzsal.betfair.entities;

public class CountryCodeResult {

    private String countryCode;

    private int marketCount;


    public String getCountryCode() {
        return countryCode;
    }

    public int getMarketCount() {
        return marketCount;
    }


    @Override
    public String toString() {
        return String.format("%s - %s", countryCode,marketCount);
    }
}
