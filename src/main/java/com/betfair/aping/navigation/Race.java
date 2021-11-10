package com.betfair.aping.navigation;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Race implements NavigationData {

    private final String id;
    private final String name;
    private final String venue;
    private final Date startTime;
    private final String raceNumber;
    private final String countryCode;

    private final List<Market> markets = new ArrayList<>();

    public Race(String id, String name, String venue, Date startTime, String raceNumber, String countryCode) {
        this.id = id;
        this.name = name;
        this.venue = venue;
        this.startTime = startTime;
        this.raceNumber = raceNumber;
        this.countryCode = countryCode;
    }


    @Override
    public void printToConsole(int i) {
        System.out.println(this);
        markets.forEach(et -> et.printToConsole(i + 1));
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
        return true;
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
