package com.betfair.aping.entities;

public class EventResult {
    public com.betfair.aping.entities.Event getEvent() {
        return event;
    }

    public void setEvent(com.betfair.aping.entities.Event event) { this.event = event; }

    public int getMarketCount() {
        return marketCount;
    }

    public void setMarketCount(int marketCount) {
        this.marketCount = marketCount;
    }

    private Event event;
    private int marketCount;

}
