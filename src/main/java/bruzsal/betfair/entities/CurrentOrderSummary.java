package bruzsal.betfair.entities;

import bruzsal.betfair.enums.OrderStatus;
import bruzsal.betfair.enums.OrderType;
import bruzsal.betfair.enums.PersistenceType;
import bruzsal.betfair.enums.Side;

import java.util.Date;

public record CurrentOrderSummary(

        String betId,
        String marketId,
        Long selectionId,
        Double handicap,
        PriceSize priceSize,
        Double bspLiability,
        Side side,
        OrderStatus status,
        PersistenceType persistenceType,
        OrderType orderType,
        Date placedDate,
        Date matchedDate,
        Double averagePriceMatched,
        Double sizeMatched,
        Double sizeRemaining,
        Double sizeLapsed,
        Double sizeCancelled,
        Double sizeVoided,
        String regulatorAuthCode,
        String regulatorCode,
        String customerOrderRef,
        String customerStrategyRef,
        CurrentItemDescription currentItemDescription

) {

    @Override
    public String toString() {
        return "CurrentOrderSummary" + '\n' +
                "    betId = " + betId + '\n' +
                "    marketId = " + marketId + '\n' +
                "    selectionId = " + selectionId + '\n' +
                "    handicap = " + handicap + '\n' +
                "    priceSize = " + priceSize + '\n' +
                "    bspLiability = " + bspLiability + '\n' +
                "    side = " + side + '\n' +
                "    status = " + status + '\n' +
                "    persistenceType = " + persistenceType + '\n' +
                "    orderType = " + orderType + '\n' +
                "    placedDate = " + placedDate + '\n' +
                "    matchedDate = " + matchedDate + '\n' +
                "    averagePriceMatched = " + averagePriceMatched + '\n' +
                "    sizeMatched = " + sizeMatched + '\n' +
                "    sizeRemaining = " + sizeRemaining + '\n' +
                "    sizeLapsed = " + sizeLapsed + '\n' +
                "    sizeCancelled = " + sizeCancelled + '\n' +
                "    sizeVoided = " + sizeVoided + '\n' +
                "    regulatorAuthCode = " + regulatorAuthCode + '\n' +
                "    regulatorCode = " + regulatorCode + '\n' +
                "    customerOrderRef = " + customerOrderRef + '\n' +
                "    customerStrategyRef = " + customerStrategyRef + '\n' +
                "    currentItemDescription = " + currentItemDescription.marketVersion().version();
    }

}
