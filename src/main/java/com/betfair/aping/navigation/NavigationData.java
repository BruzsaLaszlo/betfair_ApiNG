package com.betfair.aping.navigation;

import com.betfair.aping.util.HttpUtil;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public abstract class NavigationData {

    protected final String id;

    protected final String name;

    protected int melyseg;

    protected static final List<Market> allMarket = new ArrayList<>(15_000);
    protected static final List<Event> allEvent = new ArrayList<>(1_500);
    protected static final List<EventType> allEvenType = new ArrayList<>(50);
    protected static final List<Group> allGroup = new ArrayList<>(600);
    protected static final List<Race> allRace = new ArrayList<>(600);


    protected NavigationData(String id, String name) {
        this.id = id;
        this.name = name;
    }

    protected static final String[] spaces = new String[10];

    static {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 10; i++) {
            spaces[i] = sb.toString();
            sb.append("   > ");
        }
    }

    abstract List<List<? extends NavigationData>> getLists();

    public void printToConsole(int deep) {
        System.out.printf("%s%s%n", spaces[this.melyseg], this);
        if (deep >= this.melyseg && getLists() != null)
            getLists().
                    forEach(list -> list.
                            forEach(navigationData ->
                                    navigationData.printToConsole(deep)));
    }

    public NavigationData findEventType(String name) {
        return allEvenType.stream()
                .filter(navigationData -> navigationData.name.equals(name))
                .findFirst()
                .orElse(null);
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public abstract String toString();

    public String getAllData(int deep) {
        StringBuilder sb = new StringBuilder(5_000_000);
        getAllData(sb, deep);
        return sb.toString();
    }

    protected void getAllData(StringBuilder inputOutputStringBuilder, int deep) {
        inputOutputStringBuilder.append(spaces[this.melyseg]).append(this).append("\n");
        if (deep >= this.melyseg && getLists() != null)
            getLists().
                    forEach(list -> list.
                            forEach(navigationData ->
                                    navigationData.getAllData(inputOutputStringBuilder, deep)));
    }

    public Integer getMelyseg() {
        return melyseg;
    }

    public void setMelyseg(int melyseg) {
        this.melyseg = melyseg;
    }

    public static String getSizeOfLists() {
        StringBuilder sb = new StringBuilder();
        sb.append("allEvent: ").append(allEvent.size()).append("\n");
        sb.append("allEventType: ").append(allEvenType.size()).append("\n");
        sb.append("allGroups: ").append(allGroup.size()).append("\n");
        sb.append("allRace: ").append(allRace.size()).append("\n");
        sb.append("allMarket: ").append(allMarket.size()).append("\n");
        return sb.toString();
    }

    public Path updateNavigationData() throws IOException {
        downLoadNavigationData();
        return NAVIGATION_DATA_JSON;
    }

    public static final Path NAVIGATION_DATA_JSON = Path.of("c:\\temp\\NavigationData.json");

    private static String downLoadNavigationData() throws IOException {
//        Files.createTempDirectory("betfair_aping_temp").resolve("NavigationData.json");
        return HttpUtil.getNavigationData(NAVIGATION_DATA_JSON);
    }

}
