package com.betfair.aping.navigation;

import com.betfair.aping.entities.Event;

import java.util.ArrayList;
import java.util.List;

public class Group {

    private String id;

    private String name;

    private String type;

    private List<Event> events = new ArrayList<>();

    private List<Group> groups = new ArrayList<>();

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public List<Event> getEvents() {
        return events;
    }

    public List<Group> getGroups() {
        return groups;
    }
}
