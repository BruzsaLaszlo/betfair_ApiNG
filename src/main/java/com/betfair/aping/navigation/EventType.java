package com.betfair.aping.navigation;

import java.util.ArrayList;
import java.util.List;

public class EventType {

    private final String id;

    private final String name;

    public EventType(String id, String name) {
        this.id = id;
        this.name = name;
    }

    private final List<Group> groups = new ArrayList<>();

    private final List<Event> events = new ArrayList<>();

    private final List<Race> races = new ArrayList<>();

    public List<Group> getGroups() {
        return groups;
    }

    public List<Event> getEvents() {
        return events;
    }

    public List<Race> getRaces() {
        return races;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String toString() {
        return "{" + "" + "id=" + getId() + "," + "name=" + getName() + "}";
    }

}
