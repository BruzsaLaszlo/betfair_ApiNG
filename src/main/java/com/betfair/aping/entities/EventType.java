package com.betfair.aping.entities;

import com.betfair.aping.navigation.Group;

import java.util.ArrayList;
import java.util.List;

public class EventType {

    private String id;

    private String name;

    private List<Group> groups = new ArrayList<>();

    private List<Event> events = new ArrayList<>();

    private List<Race> races = new ArrayList<>();

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
