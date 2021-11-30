package bruzsal.betfair.api;

import bruzsal.betfair.entities.PriceProjection;
import bruzsal.betfair.enums.MatchProjection;
import bruzsal.betfair.enums.OrderProjection;

import java.util.Date;
import java.util.List;
import java.util.Set;

public class MarketBookParameterBuilder {

    /**
     * One or more market ids.
     * The number of markets returned depends on the amount of data you request via the price projection.
     */
    private List<String> marketIds;

    /**
     * The projection of price data you want to receive in the response.
     */
    private PriceProjection priceProjection;

    /**
     * The orders you want to receive in the response.
     */
    private OrderProjection orderProjection;

    /**
     * If you ask for orders, specifies the representation of matches.
     */
    private MatchProjection matchProjection;

    /**
     * If you ask for orders, returns matches for each selection. Defaults to true if unspecified.
     */
    private Boolean includeOverallPosition;

    /**
     * If you ask for orders, returns the breakdown of matches by strategy for each selection.
     * Defaults to false if unspecified.
     */
    private Boolean partitionMatchedByStrategyRef;

    /**
     * If you ask for orders, restricts the results to orders matching any of the specified set of customer defined strategies.
     * Also filters which matches by strategy for selections are returned, if partitionMatchedByStrategyRef is true.
     * An empty set will be treated as if the parameter has been omitted (or null passed).
     */
    private Set<String> customerStrategyRefs;

    /**
     * A Betfair standard currency code. If not specified, the default currency code is used.
     * The language used for the response. If not specified, the default is returned.
     */
    private String currencyCode;

    /**
     * If you ask for orders, restricts the results to orders that have at least one fragment matched since
     * the specified date (all matched fragments of such an order will be returned even if some were matched before the specified date).
     * All EXECUTABLE orders will be returned regardless of matched date.
     */
    private Date matchedSince;

    /**
     * If you ask for orders, restricts the results to orders with the specified bet IDs. Omitting this
     * parameter means that all bets will be included in the response. Please note: A maximum of 250 betId's can be provided at a time.
     */
    private Set<String> betIds;


    public MarketBookParameterBuilder validate() {
        if (marketIds == null || marketIds.isEmpty())
            throw new IllegalArgumentException("marketIds List kell hogy legyen");
        return this;
    }

    public MarketBookParameterBuilder setMarketIds(List<String> marketIds) {
        this.marketIds = marketIds;
        return this;
    }

    public MarketBookParameterBuilder setMarketIds(String... marketIds) {
        this.marketIds = List.of(marketIds);
        return this;
    }

    public MarketBookParameterBuilder setPriceProjection(PriceProjection priceProjection) {
        this.priceProjection = priceProjection;
        return this;
    }

    public MarketBookParameterBuilder setOrderProjection(OrderProjection orderProjection) {
        this.orderProjection = orderProjection;
        return this;
    }

    public MarketBookParameterBuilder setMatchProjection(MatchProjection matchProjection) {
        this.matchProjection = matchProjection;
        return this;
    }

    public MarketBookParameterBuilder setIncludeOverallPosition(Boolean includeOverallPosition) {
        this.includeOverallPosition = includeOverallPosition;
        return this;
    }

    public MarketBookParameterBuilder setPartitionMatchedByStrategyRef(Boolean partitionMatchedByStrategyRef) {
        this.partitionMatchedByStrategyRef = partitionMatchedByStrategyRef;
        return this;
    }

    public MarketBookParameterBuilder setCustomerStrategyRefs(Set<String> customerStrategyRefs) {
        this.customerStrategyRefs = customerStrategyRefs;
        return this;
    }

    public MarketBookParameterBuilder setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
        return this;
    }

    public MarketBookParameterBuilder setMatchedSince(Date matchedSince) {
        this.matchedSince = matchedSince;
        return this;
    }

    public MarketBookParameterBuilder setBetIds(Set<String> betIds) {
        this.betIds = betIds;
        return this;
    }

    public MarketBookParameterBuilder setBetIds(String... betIds) {
        this.betIds = Set.of(betIds);
        return this;
    }

    public List<String> getMarketIds() {
        return marketIds;
    }

    public PriceProjection getPriceProjection() {
        return priceProjection;
    }

    public OrderProjection getOrderProjection() {
        return orderProjection;
    }

    public MatchProjection getMatchProjection() {
        return matchProjection;
    }

    public Boolean getIncludeOverallPosition() {
        return includeOverallPosition;
    }

    public Boolean getPartitionMatchedByStrategyRef() {
        return partitionMatchedByStrategyRef;
    }

    public Set<String> getCustomerStrategyRefs() {
        return customerStrategyRefs;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public Date getMatchedSince() {
        return matchedSince;
    }

    public Set<String> getBetIds() {
        return betIds;
    }
}
