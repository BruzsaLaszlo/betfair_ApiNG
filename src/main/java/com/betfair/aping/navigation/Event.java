package com.betfair.aping.navigation;

import java.util.ArrayList;
import java.util.List;

public class Event {

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

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
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
