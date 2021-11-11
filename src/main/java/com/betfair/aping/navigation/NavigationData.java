package com.betfair.aping.navigation;

import com.betfair.aping.api.Operations;
import com.betfair.aping.enums.Endpoint;
import com.betfair.aping.util.HttpUtil;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * A ROOT group node has one or many EVENT_TYPE nodes
 * <p>
 * An EVENT_TYPE node has zero, one or many GROUP nodes
 * <p>
 * An EVENT_TYPE node has zero, one or many EVENT nodes
 * <p>
 * A Horse Racing EVENT_TYPE node has zero, one or many RACE nodes
 * <p>
 * A RACE node has one or many MARKET nodes
 * <p>
 * A GROUP node has zero, one or many EVENT nodes
 * <p>
 * A GROUP node has zero, one or many GROUP nodes
 * <p>
 * An EVENT node has zero, one or many MARKET nodes
 * <p>
 * An EVENT node has zero, one or many GROUP nodes
 * <p>
 * An EVENT node has zero, one or many EVENT nodes
 */
public abstract class NavigationData {

    public static LocalDateTime lastUpdateTime;

    private static final boolean HORSE_RACING_OFF = true;

    protected final String id;

    protected final String name;

    protected int melyseg;

    public static final List<Market> allMarket = new ArrayList<>(15_000);
    protected static final List<Event> allEvent = new ArrayList<>(1_500);
    public static final List<EventType> allEvenType = new ArrayList<>(50);
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
        return new StringBuilder()
                .append("allEvent: ").append(allEvent.size()).append("\n")
                .append("allEventType: ").append(allEvenType.size()).append("\n")
                .append("allGroups: ").append(allGroup.size()).append("\n")
                .append("allRace: ").append(allRace.size()).append("\n")
                .append("allMarket: ").append(allMarket.size()).append("\n")
                .toString();
    }

    public String getNavigationDataFromFile() throws IOException {
        return Files.readString(NavigationData.NAVIGATION_DATA_JSON);
    }

    public Path updateNavigationData() {
        String dataJson = downLoadAndSaveNavigationData();
        createTree(dataJson);
        return NAVIGATION_DATA_JSON;
    }

    public static final Path NAVIGATION_DATA_JSON = Path.of("c:\\temp\\NavigationData.json");
//    Files.createTempDirectory("betfair_aping_temp").resolve("NavigationData.json");

    private static String downLoadAndSaveNavigationData() {
        try {
            String dataJson = HttpUtil.sendPostRequest(null, null, Endpoint.NAVIGATION);
            Files.writeString(NAVIGATION_DATA_JSON, dataJson);
            lastUpdateTime = LocalDateTime.now();
            return dataJson;
        } catch (IOException e) {
            System.err.println("Nem sikerült kiírni a file-ba a NAVIGATION DATA-t");
            e.printStackTrace();
            return null;
        }
    }

    class Child {

        public String type;
        public String name;
        public String id;
        public String exchangeId;
        public String marketType;
        public Date marketStartTime;
        public String numberOfWinners;
        public String countryCode;
        public List<Child> children;
        public String venue;
        public Date startTime;
        public String raceNumber;

    }

    public void createTree(String dataJson) {

        Child rootJson = Operations.GSON.fromJson(dataJson, Child.class);

        Root root = Root.getInstance();

        bejaras(rootJson, root, 0);

        allEvent.forEach(event -> event.getMarkets()
                .forEach(market -> market.setEvent(event)));

    }

    private void add(NavigationData o, Group g) {
        if (o instanceof EventType)
            ((EventType) o).getGroups().add(g);
        else if (o instanceof Group)
            ((Group) o).getGroups().add(g);
        else if (o instanceof Event)
            ((Event) o).getGroups().add(g);
    }

    private void add(NavigationData o, Event e) {
        if (o instanceof EventType)
            ((EventType) o).getEvents().add(e);
        else if (o instanceof Group)
            ((Group) o).getEvents().add(e);
        else if (o instanceof Event)
            ((Event) o).getEvents().add(e);
    }

    private void add(NavigationData o, Market m) {
        if (o instanceof Event)
            ((Event) o).getMarkets().add(m);
        else if (o instanceof Race)
            ((Race) o).getMarkets().add(m);
    }

    private void bejaras(Child root, NavigationData o, int deep) {

        NavigationData nd = null;
        switch (root.type) {
            case "EVENT_TYPE" -> {
                if (root.id.equals("7") && root.name.equals("Horse Racing") && HORSE_RACING_OFF)
                    break;
                nd = new EventType(root.id, root.name);
            }
            case "GROUP" -> {
                if (root.name.equals("ROOT")) {
                    nd = o;
                    break;
                }
                nd = new Group(root.id, root.name);
                add(o, (Group) nd);
            }
            case "EVENT" -> {
                nd = new Event(root.id, root.name, root.countryCode);
                add(o, (Event) nd);
            }
            case "RACE" -> {
                nd = new Race(root.id, root.name, root.venue, root.startTime, root.raceNumber, root.countryCode);
                ((EventType) o).getRaces().add((Race) nd);
            }
            case "MARKET" -> {
                nd = new Market(root.id, root.marketStartTime, root.marketType, root.numberOfWinners, root.name);
                add(o, (Market) nd);
            }
        }

        if ((o = nd) == null) return;
        nd.setMelyseg(deep);


        if (root.children != null)
            for (Child c : root.children)
                bejaras(c, o, deep + 1);

    }

}
