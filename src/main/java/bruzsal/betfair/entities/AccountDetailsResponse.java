package bruzsal.betfair.entities;

public record AccountDetailsResponse(

        String currencyCode,
        String firstName,
        String lastName,
        String localeCode,
        String region,
        String timezone,
        Double discountRate,
        Integer pointsBalance,
        String countryCode

) {

    @Override
    public String toString() {
        return "AccountDetailsResponse" + '\n' +
                "    currencyCode = " + currencyCode + '\n' +
                "    firstName = " + firstName + '\n' +
                "    lastName = " + lastName + '\n' +
                "    localeCode = " + localeCode + '\n' +
                "    region = " + region + '\n' +
                "    timeZone = " + timezone + '\n' +
                "    discountRate = " + discountRate + '\n' +
                "    pointsBalance = " + pointsBalance + '\n' +
                "    countryCode = " + countryCode;
    }
}
