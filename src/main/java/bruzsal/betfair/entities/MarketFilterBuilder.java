package bruzsal.betfair.entities;

import bruzsal.betfair.enums.MarketBettingType;
import bruzsal.betfair.enums.OrderStatus;

import java.util.Set;

public class MarketFilterBuilder {
    
    private MarketFilter marketFilter;

    public void setTextQuery(String textQuery) {
        marketFilter.setTextQuery (textQuery);
    }

    public void setEventTypeIds(Set<String> eventTypeIds) {
        marketFilter.setEventTypeIds (eventTypeIds);
    }

    public void setEventIds(Set<String> eventIds) {
        marketFilter.setEventIds (eventIds);
    }

    public void setCompetitionIds(Set<String> competitionIds) {
        marketFilter.setCompetitionIds (competitionIds);
    }

    public void setMarketIds(Set<String> marketIds) {
        marketFilter.setMarketIds (marketIds);
    }

    public void setVenues(Set<String> venues) {
        marketFilter.setVenues (venues);
    }

    public void setBspOnly(Boolean bspOnly) {
        marketFilter.setBspOnly (bspOnly);
    }

    public void setInPlayOnly(Boolean inPlayOnly) {
        marketFilter.setInPlayOnly (inPlayOnly);
    }

    public void setTurnInPlayEnabled(Boolean turnInPlayEnabled) {
        marketFilter.setTurnInPlayEnabled (turnInPlayEnabled);
    }

    public void setMarketBettingTypes(Set<MarketBettingType> marketBettingTypes) {
        marketFilter.setMarketBettingTypes (marketBettingTypes);
    }

    public void setMarketCountries(Set<String> marketCountries) {
        marketFilter.setMarketCountries (marketCountries);
    }

    public void setMarketTypeCodes(Set<String> marketTypeCodes) {
        marketFilter.setMarketTypeCodes (marketTypeCodes);
    }

    public void setMarketStartTime(TimeRange marketStartTime) {
        marketFilter.setMarketStartTime (marketStartTime);
    }

    public void setWithOrders(Set<OrderStatus> withOrders) {
        marketFilter.setWithOrders (withOrders);
    }

    public void setRaceTypes(Set<String> raceTypes) {
        marketFilter.setRaceTypes (raceTypes);
    }
    
}
