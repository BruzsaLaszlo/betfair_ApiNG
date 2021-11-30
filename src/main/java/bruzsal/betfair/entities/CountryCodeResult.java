package bruzsal.betfair.entities;

public record CountryCodeResult(

        String countryCode,
        int marketCount

) {

    @Override
    public String toString() {
        return String.format("%s - %s", countryCode, marketCount);
    }

}
