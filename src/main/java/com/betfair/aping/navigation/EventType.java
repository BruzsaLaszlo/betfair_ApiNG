package com.betfair.aping.navigation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class EventType extends NavigationData {

    public EventType(String id, String name) {
        super(id, name);
        allEvenType.add(this);
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

    @Override
    List<List<? extends NavigationData>> getLists() {
        return Arrays.asList(events, groups);
    }

    public String toString() {
        return "{" + "" + "id=" + getId() + "," + "name=" + getName() + "}";
    }

}
