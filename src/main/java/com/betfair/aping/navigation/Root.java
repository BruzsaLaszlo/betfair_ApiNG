package com.betfair.aping.navigation;

import com.betfair.aping.entities.EventType;

import java.util.List;

public class Root {

    private String name;

    private String id;

    private String type;

    private List<EventType> children;

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public List<EventType> getChildren() {
        return children;
    }
}
