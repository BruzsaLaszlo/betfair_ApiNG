package bruzsal.betfair.entities;

import bruzsal.betfair.enums.CountryCodes;
import bruzsal.betfair.enums.EventTypeIds;
import bruzsal.betfair.enums.MarketBettingType;
import bruzsal.betfair.enums.OrderStatus;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

public class MarketFilterBuilder {

    private final MarketFilter marketFilter;

    public MarketFilterBuilder() {
        marketFilter = new MarketFilter();
    }

    public MarketFilter build() {
        return marketFilter;
    }

    public MarketFilterBuilder setTextQuery(String textQuery) {
        marketFilter.setTextQuery(textQuery);
        return this;
    }

    public MarketFilterBuilder setEventTypeIds(Set<String> eventTypeIds) {
        marketFilter.setEventTypeIds(eventTypeIds);
        return this;
    }

    public MarketFilterBuilder setEventTypeId(EventTypeIds eventTypeIds) {
        switch (eventTypeIds) {
            case SOCCER -> {
                return setEventTypeId(1);
            }
            default -> throw new IllegalStateException(eventTypeIds.name());
        }
    }

    public MarketFilterBuilder setEventTypeId(String eventTypeId) {
        marketFilter.setEventTypeIds(Set.of(eventTypeId));
        return this;
    }

    public MarketFilterBuilder setEventTypeId(long eventTypeId) {
        marketFilter.setEventTypeIds(Set.of(String.valueOf(eventTypeId)));
        return this;
    }

    public MarketFilterBuilder setEventIds(Set<String> eventIds) {
        marketFilter.setEventIds(eventIds);
        return this;
    }

    public MarketFilterBuilder setCompetitionIds(Set<String> competitionIds) {
        marketFilter.setCompetitionIds(competitionIds);
        return this;
    }

    public MarketFilterBuilder setMarketIds(Set<String> marketIds) {
        marketFilter.setMarketIds(marketIds);
        return this;
    }

    public MarketFilterBuilder setVenues(Set<String> venues) {
        marketFilter.setVenues(venues);
        return this;
    }

    public MarketFilterBuilder setBspOnly(Boolean bspOnly) {
        marketFilter.setBspOnly(bspOnly);
        return this;
    }

    public MarketFilterBuilder setInPlayOnly(Boolean inPlayOnly) {
        marketFilter.setInPlayOnly(inPlayOnly);
        return this;
    }

    public MarketFilterBuilder setTurnInPlayEnabled(Boolean turnInPlayEnabled) {
        marketFilter.setTurnInPlayEnabled(turnInPlayEnabled);
        return this;
    }

    public MarketFilterBuilder setMarketBettingTypes(Set<MarketBettingType> marketBettingTypes) {
        marketFilter.setMarketBettingTypes(marketBettingTypes);
        return this;
    }

    public MarketFilterBuilder setMarketCountries(Set<String> marketCountries) {
        marketFilter.setMarketCountries(marketCountries);
        return this;
    }

    public MarketFilterBuilder setMarketCountries(CountryCodes... countryCodes) {
        marketFilter.setMarketCountries(Arrays.stream(countryCodes).map(cc -> cc.CODE).collect(Collectors.toSet()));
        return this;
    }

    public MarketFilterBuilder setMarketTypeCodes(Set<String> marketTypeCodes) {
        marketFilter.setMarketTypeCodes(marketTypeCodes);
        return this;
    }

    public MarketFilterBuilder setMarketStartTime(LocalDateTime from, LocalDateTime to) {
        TimeRange timeRange = new TimeRange();
        timeRange.setFrom(Timestamp.valueOf(from));
        if (to != null)
            timeRange.setTo(Timestamp.valueOf(to));
        marketFilter.setMarketStartTime(timeRange);
        return this;
    }

    public MarketFilterBuilder setMarketStartTime(LocalDateTime from) {
        return setMarketStartTime(from, null);
    }

    public MarketFilterBuilder setWithOrders(Set<OrderStatus> withOrders) {
        marketFilter.setWithOrders(withOrders);
        return this;
    }

    public static MarketFilter empty() {
        return new MarketFilter();
    }

//    public MarketFilterBuilder setRaceTypes(Set<String> raceTypes) {
//        marketFilter.setRaceTypes(raceTypes);
//        return this;
//    }

}
