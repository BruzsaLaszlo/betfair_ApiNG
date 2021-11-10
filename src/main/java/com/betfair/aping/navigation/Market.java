package com.betfair.aping.navigation;

import java.util.Date;
import java.util.List;

public class Market extends NavigationData {

    private final String exchangeId;

    private final Date martketStartTime;

    private final String marketType;

    private final String numberOfWinners;

    public Market(String exchangeId, String id, Date martketStartTime, String marketType, String numberOfWinners, String name) {
        super(id,name);
        this.exchangeId = exchangeId;
        this.martketStartTime = martketStartTime;
        this.marketType = marketType;
        this.numberOfWinners = numberOfWinners;
        count++;
    }

    public static int count;

    @Override
    List<List<? extends NavigationData>> getLists() {
        return null;
    }

    @Override
    public String toString() {
        return "Market{" +
                "exchangeId='" + exchangeId + '\'' +
                ", id='" + id + '\'' +
                ", martketStartTime=" + martketStartTime +
                ", marketType='" + marketType + '\'' +
                ", numberOfWinners='" + numberOfWinners + '\'' +
                ", name='" + name + '\'' +
                '}';
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
