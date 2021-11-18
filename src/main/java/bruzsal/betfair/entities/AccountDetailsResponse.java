package bruzsal.betfair.entities;

public class AccountDetailsResponse {

    private String currencyCode;
    private String firstName;
    private String lastName;
    private String localeCode;
    private String region;
    private String timezone;
    private double discountRate;
    private int pointsBalance;
    private String countryCode;


    public String getCurrencyCode() {
        return currencyCode;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getLocaleCode() {
        return localeCode;
    }

    public String getRegion() {
        return region;
    }

    public String getTimeZone() {
        return timezone;
    }

    public double getDiscountRate() {
        return discountRate;
    }

    public int getPointsBalance() {
        return pointsBalance;
    }

    public String getCountryCode() {
        return countryCode;
    }

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
