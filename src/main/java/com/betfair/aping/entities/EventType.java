package com.betfair.aping.entities;

public class EventType {

    private final String id;

    private final String name;

    public EventType(String id, String name) {
        this.id = id;
        this.name = name;
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
