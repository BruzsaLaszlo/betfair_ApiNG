package entities;

public record AccountFundsResponse(

        Double availableToBetBalance,
        Double exposure,
        Double retainedCommission,
        Double exposureLimit,
        Double discountRate,
        Double pointsBalance,
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
