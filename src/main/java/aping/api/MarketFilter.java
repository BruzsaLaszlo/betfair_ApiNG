package aping.api;

import aping.entities.TimeRange;
import aping.enums.CountryCodes;
import aping.enums.EventTypeIds;
import aping.enums.MarketBettingType;
import aping.enums.OrderStatus;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
public class MarketFilter {

    /**
     * Restrict markets by any text associated with the Event name. You can include a wildcard (*) character as long as it is not the first character. Please note - the textQuery field doesn't evaluate market or selection names.
     */
    private String textQuery;


    /**
     * Restrict markets by event type associated with the market. (i.e.\n    Football\n    Hockey\n    etc)
     */
    private Set<String> eventTypeIds;

    /**
     * Restrict markets by the event id associated with the market.
     */
    private Set<String> eventIds;

    /**
     * Restrict markets by the competitions associated with the market.
     */
    private Set<String> competitionIds;


    /**
     * Restrict markets by the market id associated with the market.
     */
    private Set<String> marketIds;

    /**
     * Restrict markets by the venue associated with the market. Currently only Horse Racing markets have venues.
     */
    private Set<String> venues;

    /**
     * Restrict to bsp markets only\n    if True or non-bsp markets if False. If not specified then returns both BSP and non-BSP markets
     */
    private Boolean bspOnly;

    /**
     * Restrict to bsp markets only\n    if True or non-bsp markets if False. If not specified then returns both BSP and non-BSP markets
     */
    private Boolean inPlayOnly;

    /**
     * Restrict to markets that will turn in play if True or will not turn in play if false. If not specified\n    returns both.
     */
    private Boolean turnInPlayEnabled;

    /**
     * Restrict to markets that match the betting type of the market (i.e. Odds\n    Asian Handicap Singles\n    Asian Handicap Doubles or Line)
     */
    private Set<MarketBettingType> marketBettingTypes;

    /**
     * Restrict to markets that are in the specified country or countries
     */
    private Set<String> marketCountries;

    /**
     * Restrict to markets that match the type of the market (i.e.\n    MATCH_ODDS\n    HALF_TIME_SCORE).
     * You should use this instead of relying on the market name as the market type codes are the same in all locales.
     * Please note: All marketTypes are available via the listMarketTypes operations.
     */
    private Set<String> marketTypeCodes;

    /**
     * Restrict to markets with a market start time before or after the specified date
     */
    private TimeRange marketStartTime;

    /**
     * Restrict to markets that I have one or more orders in these status.
     */
    private Set<OrderStatus> withOrders;


    /**
     * Restrict by race type (i.e. Hurdle\n    Flat\n    Bumper\n    Harness\n    Chase)
     */
    private Set<String> raceTypes;

//    ##############  END OF FIELDS ##############


    public static MarketFilter empty() {
        return new MarketFilter();
    }


    public MarketFilter setEventTypeIds(Set<String> eventTypeIds) {
        this.eventTypeIds = eventTypeIds;
        return this;
    }

    public MarketFilter setEventTypeId(String eventTypeId) {
        return setEventTypeIds(Set.of(eventTypeId));
    }

    public MarketFilter setEventTypeId(EventTypeIds eventTypeIds) {
        return setEventTypeId(eventTypeIds.id);
    }


    public MarketFilter setEventIds(Set<String> eventIds) {
        this.eventIds = eventIds;
        return this;
    }

    public MarketFilter setEventIds(String... eventIds) {
        return setEventIds(Set.of(eventIds));
    }


    public MarketFilter setCompetitionIds(Set<String> competitionIds) {
        this.competitionIds = competitionIds;
        return this;
    }

    public MarketFilter setCompetitionIds(String... competitionIds) {
        return setCompetitionIds(Set.of(competitionIds));
    }


    public MarketFilter setMarketIds(Set<String> marketIds) {
        this.marketIds = marketIds;
        return this;
    }

    public MarketFilter setMarketIds(String... marketIds) {
        return setMarketIds(Set.of(marketIds));
    }


    public MarketFilter setMarketBettingTypes(Set<MarketBettingType> marketBettingTypes) {
        this.marketBettingTypes = marketBettingTypes;
        return this;
    }

    public MarketFilter setMarketBettingTypes(MarketBettingType... marketBettingTypes) {
        return setMarketBettingTypes(Set.of(marketBettingTypes));
    }


    public MarketFilter setMarketCountries(Set<String> marketCountries) {
        this.marketCountries = marketCountries;
        return this;
    }

    public MarketFilter setMarketCountries(CountryCodes... countryCodes) {
        return setMarketCountries(Arrays.stream(countryCodes).map(cc -> cc.code).collect(Collectors.toSet()));
    }


    public MarketFilter setMarketTypeCodes(Set<String> marketTypeCodes) {
        this.marketTypeCodes = marketTypeCodes;
        return this;
    }

    public MarketFilter setMarketTypeCodes(String... marketTypeCodes) {
        return setMarketTypeCodes(Set.of(marketTypeCodes));
    }


    public MarketFilter setMarketStartTime(TimeRange marketStartTime) {
        this.marketStartTime = marketStartTime;
        return this;
    }


    public MarketFilter setMarketStartTime(LocalDateTime from, LocalDateTime to) {
        return setMarketStartTime(new TimeRange(from, to));
    }

    public MarketFilter setMarketStartTime(LocalDateTime from) {
        return setMarketStartTime(from, null);
    }


    public MarketFilter setWithOrders(Set<OrderStatus> withOrders) {
        this.withOrders = withOrders;
        return this;
    }

    public MarketFilter setWithOrders(OrderStatus... orderStatuses) {
        return setWithOrders(Set.of(orderStatuses));
    }


    @Override
    public String toString() {
        return "MarketFilter :" +
                "\n    textQuery = " + textQuery +
                "\n    eventTypeIds = " + eventTypeIds +
                "\n    eventIds = " + eventIds +
                "\n    competitionIds = " + competitionIds +
                "\n    marketIds = " + marketIds +
                "\n    venues = " + venues +
                "\n    bspOnly = " + bspOnly +
                "\n    inPlayOnly = " + inPlayOnly +
                "\n    turnInPlayEnabled = " + turnInPlayEnabled +
                "\n    marketBettingTypes = " + marketBettingTypes +
                "\n    marketCountries = " + marketCountries +
                "\n    marketTypeCodes = " + marketTypeCodes +
                "\n    marketStartTime = " + marketStartTime +
                "\n    withOrders = " + withOrders +
                "\n    raceTypes = " + raceTypes;
    }

}
