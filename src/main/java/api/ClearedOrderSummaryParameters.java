package api;

import entities.TimeRange;
import enums.BetStatus;
import enums.GroupBy;
import enums.Side;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.Getter;

import java.util.Set;

@Getter
@Builder
public class ClearedOrderSummaryParameters {

    /**
     * Restricts the results to the specified status.
     */
    private BetStatus betStatus;

    /**
     * Optionally restricts the results to the specified Event Type IDs.
     */
    private Set<String> eventTypeIds;

    /**
     * Optionally restricts the results to the specified Event IDs.
     */
    private Set<String> eventIds;

    /**
     * Optionally restricts the results to the specified market IDs.
     */
    private Set<String> marketIds;

    /**
     * Optionally restricts the results to the specified Runners.
     */
    private Set<String> runnerIds;

    /**
     * Optionally restricts the results to the specified bet IDs.
     * A maximum of 1000 betId's are allowed in a single request.
     */
    private Set<String> betIds;

    /**
     * Optionally restricts the results to the specified customer order references.
     */
    private Set<String> customerOrderRefs;

    /**
     * Optionally restricts the results to the specified customer strategy references.
     */
    private Set<String> customerStrategyRefs;

    /**
     * Optionally restricts the results to the specified side.
     */
    private Side side;

    /**
     * Optionally restricts the results to be from/to the specified settled date.
     * This date is inclusive, i.e. if an order was cleared on exactly this date (to the millisecond)
     * then it will be included in the results. If the from is later than the to, no results will be returned.
     * <p>
     * Please Note: if you have a longer running market that is settled at multiple different times then
     * there is no way to get the returned market rollup to only include bets settled in a certain date range,
     * it will always return the overall position from the market including all settlements.
     */
    private TimeRange settledDateRange;

    /**
     * How to aggregate the lines, if not supplied then the lowest level is returned,
     * i.e. bet by bet This is only applicable to SETTLED BetStatus.
     */
    private GroupBy groupBy;

    /**
     * If true then an ItemDescription object is included in the response.
     */
    @Default
    private boolean includeItemDescription = true;

    /**
     * The language used for the itemDescription. If not specified, the customer account default is returned.
     */
    private String locale;

    /**
     * Specifies the first record that will be returned. Records start at index zero.
     */
    @Default
    private int fromRecord = 0;

    /**
     * Specifies how many records will be returned, from the index position 'fromRecord'.
     * Note that there is a page size limit of 1000.
     * A value of zero indicates that you would like all records (including and from 'fromRecord') up to the limit.
     */
    @Default
    private int recordCount = 0;


}
