package com.betfair.aping.navigation;

import java.util.ArrayList;
import java.util.List;

public class Group implements NavigationData {

    private final String id;

    private final String name;

    public Group(String id, String name) {
        this.id = id;
        this.name = name;
    }

    private final List<Event> events = new ArrayList<>();

    private final List<Group> groups = new ArrayList<>();

    @Override
    public void printToConsole(int i) {
        System.out.println(spaces[i] + this);
        events.forEach(et -> et.printToConsole(i + 1));
        groups.forEach(et -> et.printToConsole(i + 1));
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


    public List<Event> getEvents() {
        return events;
    }

    public List<Group> getGroups() {
        return groups;
    }
}
