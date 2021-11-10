package com.betfair.aping.navigation;

public interface NavigationData {

    String[] spaces = {
            "",
            "    ",
            "         ",
            "              ",
            "                   ",
            "                        ",
            "                             ",
    };

    void printToConsole(int i);

    String getId();

    String getName();

    boolean isChildren();

}
