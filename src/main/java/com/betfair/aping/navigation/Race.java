package com.betfair.aping.navigation;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Race extends NavigationData {

    private final String venue;
    private final Date startTime;
    private final String raceNumber;
    private final String countryCode;

    private final List<Market> markets = new ArrayList<>();

    public Race(String id, String name, String venue, Date startTime, String raceNumber, String countryCode) {
        super(id,name);
        this.venue = venue;
        this.startTime = startTime;
        this.raceNumber = raceNumber;
        this.countryCode = countryCode;
    }


    @Override
    List<List<? extends NavigationData>> getLists() {
        return List.of(markets);
    }

    @Override
    public String toString() {
        return "Race{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", venue='" + venue + '\'' +
                ", startTime=" + startTime +
                ", raceNumber='" + raceNumber + '\'' +
                ", countryCode='" + countryCode + '\'' +
                '}';
    }

    public String getVenue() {
        return venue;
    }

    public Date getStartTime() {
        return startTime;
    }

    public String getRaceNumber() {
        return raceNumber;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public List<Market> getMarkets() {
        return markets;
    }
}
