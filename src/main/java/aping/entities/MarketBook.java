package aping.entities;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @param marketId              The unique identifier for the market. MarketId's are prefixed with '1.'
 * @param isMarketDataDelayed   True if the data returned by listMarketBook will be delayed.
 *                              The data may be delayed because you are not logged in with a funded account or you are using an
 *                              Application Key that does not allow up to date data.
 * @param status                The status of the market    for example OPEN    SUSPENDED    CLOSED (settled)    etc.
 * @param betDelay              The number of seconds an order is held until it is submitted into the market.
 *                              Orders are usually delayed when the market is in-play
 * @param bspReconciled         True if the market starting price has been reconciled
 * @param complete              If false    runners may be added to the market
 * @param inplay                True if the market is currently in play
 * @param numberOfWinners       The number of selections that could be settled as winners
 * @param numberOfRunners       The number of runners in the market
 * @param numberOfActiveRunners The number of runners that are currently active. An active runner is a selection available for betting
 * @param lastMatchTime         The most recent time an order was executed
 * @param totalMatched          The total amount matched
 * @param totalAvailable        The total amount of orders that remain unmatched
 * @param crossMatching         True if cross matching is enabled for this market.
 * @param runnersVoidable       True if runners in the market can be voided. Please note - this doesn't include horse racing markets under
 *                              which bets are voided on non-runners with any applicable reduction factor applied
 * @param version               The version of the market. The version increments whenever the market status changes    for example    turning in-play    or suspended when a goal is scored.
 * @param runners               Information about the runners (selections) in the market.
 * @param keyLineDescription    Description of a markets key line for valid market types
 */
public record MarketBook(

        String marketId,
        Boolean isMarketDataDelayed,
        String status,
        Integer betDelay,
        Boolean bspReconciled,
        Boolean complete,
        Boolean inplay,
        Integer numberOfWinners,
        Integer numberOfRunners,
        Integer numberOfActiveRunners,
        LocalDateTime lastMatchTime,
        Double totalMatched,
        Double totalAvailable,
        Boolean crossMatching,
        Boolean runnersVoidable,
        Long version,
        List<Runner> runners,
        KeyLineDescription keyLineDescription

) {

    @Override
    public String toString() {
        return "MarketBook{" +
               "    marketId = " + marketId + '\n' +
               "    isMarketDataDelayed = " + isMarketDataDelayed + '\n' +
               "    status = " + status + '\n' +
               "    betDelay = " + betDelay + '\n' +
               "    bspReconciled = " + bspReconciled + '\n' +
               "    complete = " + complete + '\n' +
               "    inplay = " + inplay + '\n' +
               "    numberOfWinners = " + numberOfWinners + '\n' +
               "    numberOfRunners = " + numberOfRunners + '\n' +
               "    numberOfActiveRunners = " + numberOfActiveRunners + '\n' +
               "    lastMatchTime = " + lastMatchTime + '\n' +
               "    totalMatched = " + totalMatched + '\n' +
               "    totalAvailable = " + totalAvailable + '\n' +
               "    crossMatching = " + crossMatching + '\n' +
               "    runnersVoidable = " + runnersVoidable + '\n' +
               "    version = " + version + '\n' +
               "    runners = " + runners + '\n' +
               "    keyLineDescription = " + keyLineDescription + '\n' +
               '}';
    }
}
