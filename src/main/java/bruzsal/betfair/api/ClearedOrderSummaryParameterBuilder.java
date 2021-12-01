package bruzsal.betfair.api;

import bruzsal.betfair.entities.TimeRange;
import bruzsal.betfair.enums.BetStatus;
import bruzsal.betfair.enums.GroupBy;
import bruzsal.betfair.enums.Side;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class ClearedOrderSummaryParameterBuilder {

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
    private boolean includeItemDescription = true;

    /**
     * The language used for the itemDescription. If not specified, the customer account default is returned.
     */
    private String locale;

    /**
     * Specifies the first record that will be returned. Records start at index zero.
     */
    private int fromRecord = 0;

    /**
     * Specifies how many records will be returned, from the index position 'fromRecord'.
     * Note that there is a page size limit of 1000.
     * A value of zero indicates that you would like all records (including and from 'fromRecord') up to the limit.
     */
    private int recordCount = 0;


    public Map<String, Object> build() {
        var params = new HashMap<String, Object>();
        params.put("marketIds", marketIds);
        params.put("eventTypeIds", eventTypeIds);
        params.put("eventIds", eventIds);
        params.put("runnerIds", runnerIds);
        params.put("side", side);
        params.put("betIds", betIds);
        params.put("settledDateRange", settledDateRange);
        params.put("betStatus", betStatus);
        params.put("groupBy", groupBy);
        params.put("fromRecord", fromRecord);
        params.put("recordCount", recordCount);
        params.put("includeItemDescription", includeItemDescription);
        return params;
    }

    public ClearedOrderSummaryParameterBuilder setBetStatus(BetStatus betStatus) {
        this.betStatus = betStatus;
        return this;
    }

    public ClearedOrderSummaryParameterBuilder setEventTypeIds(Set<String> eventTypeIds) {
        this.eventTypeIds = eventTypeIds;
        return this;
    }


    public ClearedOrderSummaryParameterBuilder setEventIds(Set<String> eventIds) {
        this.eventIds = eventIds;
        return this;
    }


    public ClearedOrderSummaryParameterBuilder setMarketIds(Set<String> marketIds) {
        this.marketIds = marketIds;
        return this;
    }


    public ClearedOrderSummaryParameterBuilder setRunnerIds(Set<String> runnerIds) {
        this.runnerIds = runnerIds;
        return this;
    }


    public ClearedOrderSummaryParameterBuilder setBetIds(Set<String> betIds) {
        this.betIds = betIds;
        return this;
    }


    public ClearedOrderSummaryParameterBuilder setCustomerOrderRefs(Set<String> customerOrderRefs) {
        this.customerOrderRefs = customerOrderRefs;
        return this;
    }


    public ClearedOrderSummaryParameterBuilder setCustomerStrategyRefs(Set<String> customerStrategyRefs) {
        this.customerStrategyRefs = customerStrategyRefs;
        return this;
    }


    public ClearedOrderSummaryParameterBuilder setSide(Side side) {
        this.side = side;
        return this;
    }


    public ClearedOrderSummaryParameterBuilder setSettledDateRange(TimeRange settledDateRange) {
        this.settledDateRange = settledDateRange;
        return this;
    }


    public ClearedOrderSummaryParameterBuilder setGroupBy(GroupBy groupBy) {
        this.groupBy = groupBy;
        return this;
    }


    public ClearedOrderSummaryParameterBuilder setIncludeItemDescription(boolean includeItemDescription) {
        this.includeItemDescription = includeItemDescription;
        return this;
    }


    public ClearedOrderSummaryParameterBuilder setLocale(String locale) {
        this.locale = locale;
        return this;
    }


    public ClearedOrderSummaryParameterBuilder setFromRecord(int fromRecord) {
        this.fromRecord = fromRecord;
        return this;
    }


    public ClearedOrderSummaryParameterBuilder setRecordCount(int recordCount) {
        this.recordCount = recordCount;
        return this;
    }
}
