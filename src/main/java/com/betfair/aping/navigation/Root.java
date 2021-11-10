package com.betfair.aping.navigation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Root extends NavigationData {

    public String type;

    public List<EventType> events = new ArrayList<>();

    public Root(String id, String name) {
        super(id, name);
    }

    @Override
    public void printToConsole(int i) {
        System.out.println(spaces[i] + this);
        events.forEach(et -> et.printToConsole(i + 1));
    }

    @Override
    List<List<? extends NavigationData>> getLists() {
        return Arrays.asList(events);
    }


    @Override
    public String toString() {
        return "Root{" +
                "name='" + name + '\'' +
                ", id='" + id + '\'' +
                ", type='" + type + '\'' +
                ", children=" + events +
                '}';
    }

}
