package bruzsal.betfair.entities;

public class AccountFundsResponse {

    private double availableToBetBalance;
    private double exposure;
    private double retainedCommission;
    private double exposureLimit;
    private double discountRate;
    private double pointsBalance;
    private String wallet;

    public double getAvailableToBetBalance() {
        return availableToBetBalance;
    }

    public double getExposure() {
        return exposure;
    }

    public double getRetainedCommission() {
        return retainedCommission;
    }

    public double getExposureLimit() {
        return exposureLimit;
    }

    public double getDiscountRate() {
        return discountRate;
    }

    public double getPointsBalance() {
        return pointsBalance;
    }


    @Override
    public String toString() {
        return "AccountFundsResponse" + '\n' +
                "    availableToBetBalance = " + availableToBetBalance + '\n' +
                "    exposure = " + exposure + '\n' +
                "    retainedCommission = " + retainedCommission + '\n' +
                "    exposureLimit = " + exposureLimit + '\n' +
                "    discountRate = " + discountRate + '\n' +
                "    pointsBalance = " + pointsBalance + '\n' +
                "    wallet = " + wallet;
    }
}
