package aping.entities;


import aping.enums.MarketBettingType;

import java.time.LocalDateTime;

public record MarketDescription(

        Boolean persistenceEnabled,
        Boolean bspMarket,
        LocalDateTime marketTime,
        LocalDateTime suspendTime,
        LocalDateTime settleTime,
        MarketBettingType bettingType,
        Boolean turnInPlayEnabled,
        String marketType,
        String regulator,
        Double marketBaseRate,
        Boolean discountAllowed,
        String wallet,
        String rules,
        Boolean rulesHasDate,
        Double eachWayDivisor,
        String clarifications,
        MarketLineRangeInfo lineRangeInfo,
        String raceType,
        PriceLadderDescription priceLadderDescription

) {

    @Override
    public String toString() {
        return " ### MarketDescription ###{" + '\n' +
                "persistenceEnabled=" + persistenceEnabled + '\n' +
                "bspMarket=" + bspMarket + '\n' +
                "marketTime=" + marketTime + '\n' +
                "suspendTime=" + suspendTime + '\n' +
                "settleTime=" + settleTime + '\n' +
                "bettingType='" + bettingType + '\'' + '\n' +
                "turnInPlayEnabled=" + turnInPlayEnabled + '\n' +
                "marketType='" + marketType + '\'' + '\n' +
                "marketBaseRate=" + marketBaseRate + '\n' +
                "discountAllowed=" + discountAllowed + '\n' +
                "wallet='" + wallet + '\'' + '\n' +
                "rulesHasDate=" + rulesHasDate + '\n' +
                "clarifications='" + clarifications + '\'' + '\n' +
                "} ### END ###";
    }
}
