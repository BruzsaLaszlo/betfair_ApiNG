package com.betfair.aping.navigation;

import java.util.List;

public class Root implements NavigationData {

    public String name;

    public String id;

    public String type;

    public List<EventType> children;

    @Override
    public void printToConsole(int i) {
        System.out.println(spaces[i] + this);
        children.forEach(et -> et.printToConsole(i + 1));
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

    @Override
    public String toString() {
        return "Root{" +
                "name='" + name + '\'' +
                ", id='" + id + '\'' +
                ", type='" + type + '\'' +
                ", children=" + children +
                '}';
    }
}
