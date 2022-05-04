package entities;

import enums.OrderType;
import enums.PersistenceType;
import enums.Side;

import java.time.LocalDateTime;

/**
 * @param eventTypeId         The id of the event type bet on. Available at EVENT_TYPE groupBy level or lower.
 * @param eventId             The id of the event bet on. Available at EVENT groupBy level or lower.
 * @param marketId            The id of the market bet on. Available at MARKET groupBy level or lower.
 * @param selectionId         The id of the selection bet on. Available at RUNNER groupBy level or lower.
 * @param handicap            The handicap.  Enter the specific handicap value (returned by RUNNER in listMaketBook)
 *                            if the market is an Asian handicap market. Available at MARKET groupBy level or lower.
 * @param betId               The id of the bet. Available at BET groupBy level.
 * @param placedDate          The date the bet order was placed by the customer. Only available at BET groupBy level.
 * @param persistenceType     The turn in play persistence state of the order at bet placement time.
 *                            This field will be empty or omitted on true SP bets. Only available at BET groupBy level.
 * @param orderType           The type of bet (e.g standard limited-liability Exchange bet (LIMIT), a standard BSP bet (MARKET_ON_CLOSE),
 *                            or a minimum-accepted-price BSP bet (LIMIT_ON_CLOSE)). If the bet has a OrderType of MARKET_ON_CLOSE and
 *                            a persistenceType of MARKET_ON_CLOSE then it is a bet which has transitioned from LIMIT to MARKET_ON_CLOSE.
 *                            Only available at BET groupBy level.
 * @param side                Whether the bet was a back or lay bet. Available at SIDE groupBy level or lower.
 * @param itemDescription     A container for all the ancillary data and localised text valid for this Item
 * @param betOutcome          The settlement outcome of the bet. Tri-state (WIN/LOSE/PLACE) to account for Each Way bets where
 *                            * the place portion of the bet won but the win portion lost. The profit/loss amount in this case could be
 *                            * positive or negative depending on the price matched at. Only available at BET groupBy level.
 * @param priceRequested      The average requested price across all settled bet orders under this Item.
 *                            * Available at SIDE groupBy level or lower. For LINE markets this is the line position requested.
 *                            * For LINE markets this is the line position requested.
 * @param settledDate         The date and time the bet order was settled by Betfair. Available at SIDE groupBy level or lower.
 * @param lastMatchedDate     The date and time the last bet order was matched by Betfair. Available on Settled orders only.
 * @param betCount            The number of actual bets within this grouping (will be 1 for BET groupBy)
 * @param commission          The cumulative amount of commission paid by the customer across all bets under this Item, in the account currency.
 *                            Available at EXCHANGE, EVENT_TYPE, EVENT and MARKET level groupings only.
 * @param priceMatched        The average matched price across all settled bets or bet fragments under this Item.
 *                            Available at SIDE groupBy level or lower. For LINE markets this is the line position matched at.
 * @param priceReduced        If true, then the matched price was affected by a reduction factor due to of a runner removal from this Horse Racing market.
 * @param sizeSettled         The cumulative bet size that was settled as matched or voided under this Item, in the account currency.
 *                            Available at SIDE groupBy level or lower.
 * @param profit              The profit or loss (negative profit) gained on this line, in the account currency
 * @param sizeCancelled       The amount of the bet that was available to be matched, before cancellation or lapsing, in the account currency
 * @param customerOrderRef    The order reference defined by the customer for the bet order
 * @param customerStrategyRef The strategy reference defined by the customer for the bet order
 */
public record ClearedOrderSummary(

        String eventTypeId,
        String eventId,
        String marketId,
        Long selectionId,
        Double handicap,
        String betId,
        LocalDateTime placedDate,
        PersistenceType persistenceType,
        OrderType orderType,
        Side side,
        ItemDescription itemDescription,
        String betOutcome,
        Double priceRequested,
        LocalDateTime settledDate,
        LocalDateTime lastMatchedDate,
        Integer betCount,
        Double commission,
        Double priceMatched,
        Boolean priceReduced,
        Double sizeSettled,
        Double profit,
        Double sizeCancelled,
        String customerOrderRef,
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

    
