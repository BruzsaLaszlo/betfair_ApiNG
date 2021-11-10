package com.betfair.aping.navigation;

import java.util.ArrayList;
import java.util.List;

public abstract class NavigationData {

    protected final String id;

    protected final String name;

    protected int melyseg;

    protected static final List<Market> allMarket = new ArrayList<>();
    protected static final List<Event> allEvent = new ArrayList<>();
    protected static final List<EventType> allEvenType = new ArrayList<>();
    protected static final List<Group> allGroup = new ArrayList<>();
    protected static final List<Race> allRace = new ArrayList<>();


    public NavigationData(String id, String name) {
        this.id = id;
        this.name = name;
    }

    String[] spaces = new String[10];

    {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 10; i++) {
            spaces[i] = sb.toString();
            sb.append("   > ");
        }
    }

    public void printToConsole(int deep) {
        System.out.printf("%s%s%n", spaces[this.melyseg], this);
        if (deep >= this.melyseg && getLists() != null)
            getLists().
                    forEach(list -> list.
                            forEach(navigationData ->
                                    navigationData.printToConsole(deep)));
    }

//    public NavigationData filterByName(String name) {
//        getLists().
//    }

    public NavigationData statics() {
        NavigationData nd;
        getLists().forEach(list -> {
            list.stream().
                    filter(navigationData -> navigationData.name.equals("Soccer")).
                    findFirst().get();
        });
        return null;
    }

    abstract List<List<? extends NavigationData>> getLists();

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public abstract String toString();

    public void getAllFormatToFile(StringBuilder inputOutputStringBuilder, int deep) {
        inputOutputStringBuilder.append(spaces[this.melyseg]).append(this).append("\n");
        if (deep >= this.melyseg && getLists() != null)
            getLists().
                    forEach(list -> list.
                            forEach(navigationData ->
                                    navigationData.getAllFormatToFile(inputOutputStringBuilder, deep)));
    }

    public Integer getMelyseg() {
        return melyseg;
    }

    public void setMelyseg(int melyseg) {
        this.melyseg = melyseg;
    }
}
