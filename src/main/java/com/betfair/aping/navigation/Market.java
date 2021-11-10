package com.betfair.aping.navigation;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class Market extends NavigationData {

    private final LocalDateTime martketStartTime;

    private final String marketType;

    private final String numberOfWinners;

    private Event event;

    public Market(String id, Date martketStartTime, String marketType, String numberOfWinners, String name) {
        super(id, name);
        this.martketStartTime = new Timestamp(martketStartTime.getTime()).toLocalDateTime();
        this.marketType = marketType;
        this.numberOfWinners = numberOfWinners;
        allMarket.add(this);
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    @Override
    List<List<? extends NavigationData>> getLists() {
        return Collections.emptyList();
    }

    @Override
    public String toString() {
        return new StringBuilder()
                .append("{ Market }  ")
                .append(name).append(" ")
                .append(martketStartTime).append("  ")
                .append("\"").append(marketType).append("\"  ")
                .append("numOfWin=").append(numberOfWinners).append("  ")
                .append("id=").append(id).append("  ")
                .toString();
    }


    public LocalDateTime getMartketStartTime() {
        return martketStartTime;
    }

    public String getMarketType() {
        return marketType;
    }

    public String getNumberOfWinners() {
        return numberOfWinners;
    }

}
