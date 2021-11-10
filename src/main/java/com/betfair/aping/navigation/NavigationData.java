package com.betfair.aping.navigation;

import java.util.List;

public abstract class NavigationData {

    protected String id;

    protected String name;

    protected int deep;

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

    }

    abstract List<List<? extends NavigationData>> getLists();

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public abstract String toString();

    public void getAll(StringBuilder sb, int deep) {
        sb.append(spaces[this.deep]).append(this).append("\n");
        if (deep >= this.deep && getLists() != null) {
            for (List<? extends NavigationData> list : getLists())
                for (NavigationData nd : list)
                    nd.getAll(sb, deep + 1);
        }
    }

    public int getDeep() {
        return deep;
    }

    public void setDeep(int deep) {
        this.deep = deep;
    }
}
