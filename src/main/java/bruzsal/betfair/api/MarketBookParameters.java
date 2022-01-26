package bruzsal.betfair.api;

import bruzsal.betfair.entities.PriceProjection;
import bruzsal.betfair.enums.MatchProjection;
import bruzsal.betfair.enums.OrderProjection;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;

import java.util.Date;
import java.util.List;
import java.util.Set;

@Builder
@Getter
public class MarketBookParameters {

    /**
     * One or more market ids.
     * The number of markets returned depends on the amount of data you request via the price projection.
     */
    @NonNull
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


}
