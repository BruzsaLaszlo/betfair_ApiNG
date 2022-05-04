package entities;

import java.time.LocalDateTime;
import java.util.List;

public record MarketBook(

        /**
         * The unique identifier for the market. MarketId's are prefixed with '1.'
         */
        String marketId,

        /**
         * True if the data returned by listMarketBook will be delayed. 
         * The data may be delayed because you are not logged in with a funded account or you are using an 
         * Application Key that does not allow up to date data.
         */
        Boolean isMarketDataDelayed,

        /**
         * The status of the market    for example OPEN    SUSPENDED    CLOSED (settled)    etc.
         */
        String status,

        /**
         * The number of seconds an order is held until it is submitted into the market.
         * Orders are usually delayed when the market is in-play
         */
        Integer betDelay,

        /**
         * True if the market starting price has been reconciled
         */
        Boolean bspReconciled,

        /**
         * If false    runners may be added to the market
         */
        Boolean complete,

        /**
         * True if the market is currently in play
         */
        Boolean inplay,

        /**
         * The number of selections that could be settled as winners
         */
        Integer numberOfWinners,

        /**
         * The number of runners in the market
         */
        Integer numberOfRunners,

        /**
         * The number of runners that are currently active. An active runner is a selection available for betting
         */
        Integer numberOfActiveRunners,

        /**
         * The most recent time an order was executed
         */
        LocalDateTime lastMatchTime,

        /**
         * The total amount matched
         */
        Double totalMatched,

        /**
         * The total amount of orders that remain unmatched
         */
        Double totalAvailable,

        /**
         * True if cross matching is enabled for this market.
         */
        Boolean crossMatching,

        /**
         *True if runners in the market can be voided. Please note - this doesn't include horse racing markets under
         * which bets are voided on non-runners with any applicable reduction factor applied
         */
        Boolean runnersVoidable,

        /**
         *The version of the market. The version increments whenever the market status changes    for example    turning in-play    or suspended when a goal is scored.
         */
        Long version,

        /**
         * Information about the runners (selections) in the market.
         */
        List<Runner> runners,

        /**
         * Description of a markets key line for valid market types
         */
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
