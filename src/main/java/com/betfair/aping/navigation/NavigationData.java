package com.betfair.aping.navigation;

import java.util.List;

public abstract class NavigationData {

    protected String id;

    protected String name;

    protected int melyseg;

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
        System.out.printf("%s%s\n", spaces[this.melyseg], this);
        if (deep >= this.melyseg && getLists() != null)
            getLists().
                    forEach(list -> list.
                            forEach(navigationData ->
                                    navigationData.printToConsole(deep + 1)));
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
                                    navigationData.getAllFormatToFile(inputOutputStringBuilder, deep + 1)));
    }

    public int getMelyseg() {
        return melyseg;
    }

    public void setMelyseg(int melyseg) {
        this.melyseg = melyseg;
    }
}
