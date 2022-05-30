package aping.entities;

public record CountryCodeResult(

        String countryCode,
        Integer marketCount

) {

    @Override
    public String toString() {
        return String.format("%s - %s", countryCode, marketCount);
    }

}
