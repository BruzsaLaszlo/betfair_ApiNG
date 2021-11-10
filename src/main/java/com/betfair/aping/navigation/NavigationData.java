package com.betfair.aping.navigation;

public interface NavigationData {

    String type = "Child";

    void printToConsole(int i);

    String getId();

    String getName();

    Class getType();

    boolean isChildren();

}
