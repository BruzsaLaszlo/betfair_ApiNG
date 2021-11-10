package com.betfair.aping.navigation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class EventType extends NavigationData {

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

    @Override
    public void printToConsole(int i) {
        System.out.println(spaces[i] + this);
        groups.forEach(et -> et.printToConsole(i + 1));
        events.forEach(et -> et.printToConsole(i + 1));
        races.forEach(et -> et.printToConsole(i + 1));
    }

    @Override
    List<List<? extends NavigationData>> getLists() {
        return Arrays.asList(events,groups);
    }

    public String toString() {
        return "{" + "" + "id=" + getId() + "," + "name=" + getName() + "}";
    }

}
