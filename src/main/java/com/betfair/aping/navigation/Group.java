package com.betfair.aping.navigation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Group extends NavigationData {

    private final List<Event> events = new ArrayList<>();

    private final List<Group> groups = new ArrayList<>();

    public Group(String id, String name) {
        super(id, name);
    }

    @Override
    public void printToConsole(int i) {
        System.out.println(spaces[i] + this);
        events.forEach(et -> et.printToConsole(i + 1));
        groups.forEach(et -> et.printToConsole(i + 1));
    }

    @Override
    List<List<? extends NavigationData>> getLists() {
        return Arrays.asList(events,groups);
    }

    @Override
    public String toString() {
        return "Group{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", events=" + events +
                ", groups=" + groups +
                '}';
    }

    public List<Event> getEvents() {
        return events;
    }

    public List<Group> getGroups() {
        return groups;
    }
}
