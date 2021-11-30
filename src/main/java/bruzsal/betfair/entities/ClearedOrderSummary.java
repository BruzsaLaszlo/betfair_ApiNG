package bruzsal.betfair.entities;

import bruzsal.betfair.enums.OrderType;
import bruzsal.betfair.enums.PersistenceType;
import bruzsal.betfair.enums.Side;

import java.util.Date;

public record ClearedOrderSummary(


        /**
         * The id of the event type bet on. Available at EVENT_TYPE groupBy level or lower.
         */
        String eventTypeId,

        /**
         * The id of the event bet on. Available at EVENT groupBy level or lower.
         */
        String eventId,

        /**
         * The id of the market bet on. Available at MARKET groupBy level or lower.
         */
        String marketId,

        /**
         * The id of the selection bet on. Available at RUNNER groupBy level or lower.
         */
        Long selectionId,

        /**
         * The handicap.  Enter the specific handicap value (returned by RUNNER in listMaketBook)
         * if the market is an Asian handicap market. Available at MARKET groupBy level or lower.
         */
        Double handicap,

        /**
         * The id of the bet. Available at BET groupBy level.
         */
        String betId,

        /**
         * The date the bet order was placed by the customer. Only available at BET groupBy level.
         */
        Date placedDate,

        /**
         * The turn in play persistence state of the order at bet placement time.
         * This field will be empty or omitted on true SP bets. Only available at BET groupBy level.
         */
        PersistenceType persistenceType,

        /**
         * The type of bet (e.g standard limited-liability Exchange bet (LIMIT), a standard BSP bet (MARKET_ON_CLOSE),
         * or a minimum-accepted-price BSP bet (LIMIT_ON_CLOSE)). If the bet has a OrderType of MARKET_ON_CLOSE and
         * a persistenceType of MARKET_ON_CLOSE then it is a bet which has transitioned from LIMIT to MARKET_ON_CLOSE.
         * Only available at BET groupBy level.
         */
        OrderType orderType,

        /**
         * Whether the bet was a back or lay bet. Available at SIDE groupBy level or lower.
         */
        Side side,

        /**
         * A container for all the ancillary data and localised text valid for this Item
         */
        ItemDescription itemDescription,

        /**
         * The settlement outcome of the bet. Tri-state (WIN/LOSE/PLACE) to account for Each Way bets where
         * the place portion of the bet won but the win portion lost. The profit/loss amount in this case could be
         * positive or negative depending on the price matched at. Only available at BET groupBy level.
         */
        String betOutcome,

        /**
         * The average requested price across all settled bet orders under this Item.
         * Available at SIDE groupBy level or lower. For LINE markets this is the line position requested.
         * For LINE markets this is the line position requested.
         */
        Double priceRequested,

        /**
         * The date and time the bet order was settled by Betfair. Available at SIDE groupBy level or lower.
         */
        Date settledDate,

        /**
         * The date and time the last bet order was matched by Betfair. Available on Settled orders only.
         */
        Date lastMatchedDate,

        /**
         * The number of actual bets within this grouping (will be 1 for BET groupBy)
         */
        Integer betCount,

        /**
         * The cumulative amount of commission paid by the customer across all bets under this Item, in the account currency.
         * Available at EXCHANGE, EVENT_TYPE, EVENT and MARKET level groupings only.
         */
        Double commission,

        /**
         * The average matched price across all settled bets or bet fragments under this Item.
         * Available at SIDE groupBy level or lower. For LINE markets this is the line position matched at.
         */
        Double priceMatched,

        /**
         * If true, then the matched price was affected by a reduction factor due to of a runner removal from this Horse Racing market.
         */
        Boolean priceReduced,

        /**
         * The cumulative bet size that was settled as matched or voided under this Item, in the account currency.
         * Available at SIDE groupBy level or lower.
         */
        Double sizeSettled,

        /**
         * The profit or loss (negative profit) gained on this line, in the account currency
         */
        Double profit,

        /**
         * The amount of the bet that was available to be matched, before cancellation or lapsing, in the account currency
         */
        Double sizeCancelled,

        /**
         * The order reference defined by the customer for the bet order
         */
        String customerOrderRef,

        /**
         * The strategy reference defined by the customer for the bet order
         */
        String customerStrategyRef

) {

    @Override
    public String toString() {
        return "ClearedOrderSummary" + '\n' +
                "    eventTypeId = " + eventTypeId + '\n' +
                "    eventId = " + eventId + '\n' +
                "    marketId = " + marketId + '\n' +
                "    selectionId = " + selectionId + '\n' +
                "    handicap = " + handicap + '\n' +
                "    betId = " + betId + '\n' +
                "    placedDate = " + placedDate + '\n' +
                "    persistenceType = " + persistenceType + '\n' +
                "    orderType = " + orderType + '\n' +
                "    side = " + side + '\n' +
                "    itemDescription = " + itemDescription + '\n' +
                "    betOutcome = " + betOutcome + '\n' +
                "    priceRequested = " + priceRequested + '\n' +
                "    settledDate = " + settledDate + '\n' +
                "    lastMatchedDate = " + lastMatchedDate + '\n' +
                "    betCount = " + betCount + '\n' +
                "    commission = " + commission + '\n' +
                "    priceMatched = " + priceMatched + '\n' +
                "    priceReduced = " + priceReduced + '\n' +
                "    sizeSettled = " + sizeSettled + '\n' +
                "    profit = " + profit + '\n' +
                "    sizeCancelled = " + sizeCancelled + '\n' +
                "    customerOrderRef = " + customerOrderRef + '\n' +
                "    customerStrategyRef = " + customerStrategyRef + '\n';
    }

}

    
