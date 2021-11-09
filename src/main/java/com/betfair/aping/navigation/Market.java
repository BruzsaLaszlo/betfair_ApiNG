package com.betfair.aping.navigation;

import java.util.Date;

public class Market {

    private final String exchangeId;

    private final String id;

    private final Date martketStartTime;

    private final String marketType;

    private final String numberOfWinners;

    private final String name;

    public Market(String exchangeId, String id, Date martketStartTime, String marketType, String numberOfWinners, String name) {
        this.exchangeId = exchangeId;
        this.id = id;
        this.martketStartTime = martketStartTime;
        this.marketType = marketType;
        this.numberOfWinners = numberOfWinners;
        this.name = name;
    }

    public String getExchangeId() {
        return exchangeId;
    }

    public String getId() {
        return id;
    }

    public Date getMartketStartTime() {
        return martketStartTime;
    }

    public String getMarketType() {
        return marketType;
    }

    public String getNumberOfWinners() {
        return numberOfWinners;
    }

    public String getName() {
        return name;
    }
}
