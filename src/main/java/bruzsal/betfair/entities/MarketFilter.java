package bruzsal.betfair.entities;

import bruzsal.betfair.enums.MarketBettingType;
import bruzsal.betfair.enums.OrderStatus;

import java.util.Set;


public class MarketFilter {

    /**
     * Restrict markets by any text associated with the Event name. You can include a wildcard (*) character as long as it is not the first character. Please note - the textQuery field doesn't evaluate market or selection names.
     */
    private String textQuery;

    /**
     * DEPRECATED
     */
    @Deprecated
    private Set<String> exchangeIds;

    /**
     * Restrict markets by event type associated with the market. (i.e., Football, Hockey, etc)
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
     * Restrict to bsp markets only, if True or non-bsp markets if False. If not specified then returns both BSP and non-BSP markets
     */
    private Boolean bspOnly;

    /**
     * Restrict to bsp markets only, if True or non-bsp markets if False. If not specified then returns both BSP and non-BSP markets
     */
    private Boolean inPlayOnly;

    /**
     * Restrict to markets that will turn in play if True or will not turn in play if false. If not specified, returns both.
     */
    private Boolean turnInPlayEnabled;

    /**
     * Restrict to markets that match the betting type of the market (i.e. Odds, Asian Handicap Singles, Asian Handicap Doubles or Line)
     */
    private Set<MarketBettingType> marketBettingTypes;

    /**
     * Restrict to markets that are in the specified country or countries
     */
    private Set<String> marketCountries;

    /**
     * Restrict to markets that match the type of the market (i.e., MATCH_ODDS, HALF_TIME_SCORE).
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
     * Restrict by race type (i.e. Hurdle, Flat, Bumper, Harness, Chase)
     */
    private Set<String> raceTypes;

//    ##############  END OF FIELDS ##############


    public String getTextQuery() {
        return textQuery;
    }

    public void setTextQuery(String textQuery) {
        this.textQuery = textQuery;
    }

    public Set<String> getExchangeIds() {
        return exchangeIds;
    }

    public Set<String> getEventTypeIds() {
        return eventTypeIds;
    }

    public void setEventTypeIds(Set<String> eventTypeIds) {
        this.eventTypeIds = eventTypeIds;
    }

    public Set<String> getMarketIds() {
        return marketIds;
    }

    public void setMarketIds(Set<String> marketIds) {
        this.marketIds = marketIds;
    }

    public Boolean getInPlayOnly() {
        return inPlayOnly;
    }

    public void setInPlayOnly(Boolean inPlayOnly) {
        this.inPlayOnly = inPlayOnly;
    }

    public Set<String> getEventIds() {
        return eventIds;
    }

    public void setEventIds(Set<String> eventIds) {
        this.eventIds = eventIds;
    }

    public Set<String> getCompetitionIds() {
        return competitionIds;
    }

    public void setCompetitionIds(Set<String> competitionIds) {
        this.competitionIds = competitionIds;
    }

    public Set<String> getVenues() {
        return venues;
    }

    public void setVenues(Set<String> venues) {
        this.venues = venues;
    }

    public Boolean getBspOnly() {
        return bspOnly;
    }

    public void setBspOnly(Boolean bspOnly) {
        this.bspOnly = bspOnly;
    }

    public Boolean getTurnInPlayEnabled() {
        return turnInPlayEnabled;
    }

    public void setTurnInPlayEnabled(Boolean turnInPlayEnabled) {
        this.turnInPlayEnabled = turnInPlayEnabled;
    }

    public Set<MarketBettingType> getMarketBettingTypes() {
        return marketBettingTypes;
    }

    public void setMarketBettingTypes(Set<MarketBettingType> marketBettingTypes) {
        this.marketBettingTypes = marketBettingTypes;
    }

    public Set<String> getMarketCountries() {
        return marketCountries;
    }

    public void setMarketCountries(Set<String> marketCountries) {
        this.marketCountries = marketCountries;
    }

    public Set<String> getMarketTypeCodes() {
        return marketTypeCodes;
    }

    public void setMarketTypeCodes(Set<String> marketTypeCodes) {
        this.marketTypeCodes = marketTypeCodes;
    }

    public TimeRange getMarketStartTime() {
        return marketStartTime;
    }

    public void setMarketStartTime(TimeRange marketStartTime) {
        this.marketStartTime = marketStartTime;
    }

    public Set<OrderStatus> getWithOrders() {
        return withOrders;
    }

    public void setWithOrders(Set<OrderStatus> withOrders) {
        this.withOrders = withOrders;
    }

    public String toString() {
        return "{" + "" + "textQuery=" + getTextQuery() + "," + "exchangeIds="
                + getExchangeIds() + "," + "eventTypeIds=" + getEventTypeIds()
                + "," + "eventIds=" + getEventIds() + "," + "competitionIds="
                + getCompetitionIds() + "," + "marketIds=" + getMarketIds()
                + "," + "venues=" + getVenues() + "," + "bspOnly="
                + getBspOnly() + "," + "turnInPlayEnabled="
                + getTurnInPlayEnabled() + "," + "inPlayOnly="
                + getInPlayOnly() + "," + "marketBettingTypes="
                + getMarketBettingTypes() + "," + "marketCountries="
                + getMarketCountries() + "," + "marketTypeCodes="
                + getMarketTypeCodes() + "," + "marketStartTime="
                + getMarketStartTime() + "," + "withOrders=" + getWithOrders()
                + "," + "}";
    }

}
