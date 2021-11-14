package com.betfair.aping.entities;

public class EventTypeResult {

    /**
     * The ID identifying the Event Type
     */
    private EventType eventType;

    /**
     * Count of markets associated with this eventType
     */
    private int marketCount;



    public EventType getEventType() {
        return eventType;
    }

    public int getMarketCount() {
        return marketCount;
    }

}
