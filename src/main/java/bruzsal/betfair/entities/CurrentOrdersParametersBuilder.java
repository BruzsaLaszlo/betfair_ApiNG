package bruzsal.betfair.entities;

import bruzsal.betfair.enums.OrderBy;
import bruzsal.betfair.enums.OrderProjection;
import bruzsal.betfair.enums.SortDir;

import java.util.HashSet;
import java.util.Set;

public class CurrentOrdersParametersBuilder {

    /**
     * Optionally restricts the results to the specified bet IDs.
     * A maximum of 250 betId's, or a combination of 250 betId's & marketId's are permitted.
     */
    private Set<String> betIds = new HashSet<>();

    /**
     * Optionally restricts the results to the specified market IDs.
     * A maximum of 250 marketId's, or a combination of 250 marketId's & betId's are permitted.
     */
    private Set<String> marketIds = new HashSet<>();

    /**
     * Optionally restricts the results to the specified order status.
     */
    private OrderProjection orderProjection = OrderProjection.ALL;

    /**
     * Optionally restricts the results to the specified customer order references.
     */
    private Set<String> customerOrderRefs;

    /**
     * Optionally restricts the results to the specified customer strategy references.
     */
    private Set<String> customerStrategyRefs;

    /**
     * Optionally restricts the results to be from/to the specified date,
     * these dates are contextual to the orders being returned and
     * therefore the dates used to filter on will change to placed, matched, voided or settled dates
     * depending on the orderBy. This date is inclusive, i.e. if an order was placed on exactly
     * this date (to the millisecond) then it will be included in the results.
     * If the from is later than the to, no results will be returned.
     */
    private TimeRange placedDateRange;

    /**
     * Specifies how the results will be ordered. If no value is passed in, it defaults to BY_BET.
     * Also acts as a filter such that only orders with a valid value in the field being ordered by
     * will be returned (i.e. BY_VOID_TIME returns only voided orders,
     * BY_SETTLED_TIME (applies to partially settled markets) returns only settled orders and
     * BY_MATCH_TIME returns only orders with a matched date (voided, settled, matched orders)).
     * Note that specifying an orderBy parameter defines the context of the date filter applied by
     * the dateRange parameter (placed, matched, voided or settled date) - see the dateRange parameter
     * description (above) for more information. See also the OrderBy type definition.
     */
    private OrderBy orderBy = OrderBy.BY_MARKET;

    /**
     * Specifies the direction the results will be sorted in.
     * If no value is passed in, it defaults to EARLIEST_TO_LATEST.
     */
    private SortDir sortDir = SortDir.LATEST_TO_EARLIEST;

    /**
     * Specifies the first record that will be returned. Records start at index zero, not at index one.
     */
    private int fromRecord = 0;

    /**
     * Specifies how many records will be returned from the index position 'fromRecord'.
     * Note that there is a page size limit of 1000. A value of zero indicates that you would like
     * all records (including and from 'fromRecord') up to the limit.
     */
    private int recordCount = 0;

    /**
     * If true then extra description parameters are included in the CurrentOrderSummaryReport.
     * (Pending Release - w/c 9th August)
     */
    private boolean includeItemDescription = true;

    private CurrentOrdersParametersBuilder copb = new CurrentOrdersParametersBuilder();


    public CurrentOrdersParametersBuilder setBetIds(Set<String> betIds) {
        this.betIds = betIds;
        return this;
    }

    public CurrentOrdersParametersBuilder setMarketIds(Set<String> marketIds) {
        this.marketIds = marketIds;
        return this;
    }

    public CurrentOrdersParametersBuilder setOrderProjection(OrderProjection orderProjection) {
        this.orderProjection = orderProjection;
        return this;
    }

    public CurrentOrdersParametersBuilder setCustomerOrderRefs(Set<String> customerOrderRefs) {
        this.customerOrderRefs = customerOrderRefs;
        return this;
    }

    public CurrentOrdersParametersBuilder setCustomerStrategyRefs(Set<String> customerStrategyRefs) {
        this.customerStrategyRefs = customerStrategyRefs;
        return this;
    }

    public CurrentOrdersParametersBuilder setPlacedDateRange(TimeRange placedDateRange) {
        this.placedDateRange = placedDateRange;
        return this;
    }

    public CurrentOrdersParametersBuilder setOrderBy(OrderBy orderBy) {
        this.orderBy = orderBy;
        return this;
    }

    public CurrentOrdersParametersBuilder setSortDir(SortDir sortDir) {
        this.sortDir = sortDir;
        return this;
    }

    public CurrentOrdersParametersBuilder setFromRecord(int fromRecord) {
        this.fromRecord = fromRecord;
        return this;
    }

    public CurrentOrdersParametersBuilder setRecordCount(int recordCount) {
        this.recordCount = recordCount;
        return this;
    }

    public CurrentOrdersParametersBuilder setIncludeItemDescription(boolean includeItemDescription) {
        this.includeItemDescription = includeItemDescription;
        return this;
    }

    public CurrentOrdersParametersBuilder setCopb(CurrentOrdersParametersBuilder copb) {
        this.copb = copb;
        return this;
    }

    public Set<String> getBetIds() {
        return betIds;
    }

    public Set<String> getMarketIds() {
        return marketIds;
    }

    public OrderProjection getOrderProjection() {
        return orderProjection;
    }

    public Set<String> getCustomerOrderRefs() {
        return customerOrderRefs;
    }

    public Set<String> getCustomerStrategyRefs() {
        return customerStrategyRefs;
    }

    public TimeRange getPlacedDateRange() {
        return placedDateRange;
    }

    public OrderBy getOrderBy() {
        return orderBy;
    }

    public SortDir getSortDir() {
        return sortDir;
    }

    public int getFromRecord() {
        return fromRecord;
    }

    public int getRecordCount() {
        return recordCount;
    }

    public boolean isIncludeItemDescription() {
        return includeItemDescription;
    }

    public CurrentOrdersParametersBuilder getCopb() {
        return copb;
    }
}
