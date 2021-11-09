package com.betfair.aping.navigation;

import java.util.ArrayList;
import java.util.List;

public class Group {

    private final String id;

    private final String name;

    public Group(String id, String name) {
        this.id = id;
        this.name = name;
    }

    private final List<Event> events = new ArrayList<>();

    private final List<Group> groups = new ArrayList<>();

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }


    public List<Event> getEvents() {
        return events;
    }

    public List<Group> getGroups() {
        return groups;
    }
}
