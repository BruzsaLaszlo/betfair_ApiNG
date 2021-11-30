package bruzsal.betfair.entities;

import bruzsal.betfair.enums.MatchProjection;
import bruzsal.betfair.enums.OrderProjection;

import java.util.Date;
import java.util.List;
import java.util.Set;

public class MarketBookParameterBuilder {


    private List<String> marketIds;
    private PriceProjection priceProjection;
    private OrderProjection orderProjection;
    private MatchProjection matchProjection;
    private Boolean includeOverallPosition;
    private Boolean partitionMatchedByStrategyRef;
    private Set<String> customerStrategyRefs;
    private String currencyCode;
    private Date matchedSince;
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
