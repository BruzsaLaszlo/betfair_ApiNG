package com.betfair.aping.navigation;

import java.util.Date;

public class Market implements NavigationData {

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


    @Override
    public void printToConsole(int i) {
        System.out.println(spaces[i] + this);
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public boolean isChildren() {
        return false;
    }

    public String getExchangeId() {
        return exchangeId;
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

}
