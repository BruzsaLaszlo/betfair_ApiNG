package bruzsal.betfair.entities;

public record AccountFundsResponse(

        double availableToBetBalance,
        double exposure,
        double retainedCommission,
        double exposureLimit,
        double discountRate,
        double pointsBalance,
        String wallet

) {

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
