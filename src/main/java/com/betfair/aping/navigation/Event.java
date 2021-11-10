package com.betfair.aping.navigation;

import java.util.ArrayList;
import java.util.List;

public class Event implements NavigationData {

    private final String id;
    private final String name;
    private final String countryCode;

    private final List<Market> markets = new ArrayList<>();

    private final List<Event> events = new ArrayList<>();

    private final List<Group> groups = new ArrayList<>();

    public Event(String id, String name, String countryCode) {
        this.id = id;
        this.name = name;
        this.countryCode = countryCode;
    }


    @Override
    public void printToConsole(int i) {
        System.out.println(spaces[i] + this);
        groups.forEach(et -> et.printToConsole(i + 1));
        events.forEach(et -> et.printToConsole(i + 1));
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

    public String getCountryCode() {
        return countryCode;
    }

    public List<Market> getMarkets() {
        return markets;
    }

    public List<Event> getEvents() {
        return events;
    }

    public List<Group> getGroups() {
        return groups;
    }
}
