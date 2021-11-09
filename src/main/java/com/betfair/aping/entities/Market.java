package com.betfair.aping.entities;

import java.util.Date;

public class Market {

    private String exchangeId;

    private String id;

    private Date martketStartTime;

    private String marketType;

    private String numberOfWinners;

    private String name;

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
