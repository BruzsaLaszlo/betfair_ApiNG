package com.betfair.aping.navigation;

import java.util.List;

public class Root implements NavigationData{

    public String name;

    public String id;

    public String type;

    public List<EventType> children;

    @Override
    public void printToConsole(int i) {

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
    public Class<Root> getType() {
        return Root.class;
    }
}
