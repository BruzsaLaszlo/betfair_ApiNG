package bruzsal.betfair.navigation;

import bruzsal.betfair.enums.Endpoint;
import bruzsal.betfair.util.HttpUtil;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import static bruzsal.betfair.api.Operations.om;
import static bruzsal.betfair.navigation.NavigationDataBase.*;

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
public class NavigationData {

    private static final boolean HORSE_RACING_OFF = true;

    public static LocalDateTime lastUpdateTime;

    public static final String[] SPACES = new String[10];

    private final Root root = new Root();

    static {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 10; i++) {
            SPACES[i] = sb.toString();
            sb.append("   > ");
        }
    }

    public Root getRoot() {
        return root;
    }


    public String getAllData(int deep) {
        StringBuilder sb = new StringBuilder(5_000_000);
        root.getAllData(sb, deep);
        return sb.toString();
    }


    public String getNavigationDataFromFile() {
        try {
            return Files.readString(NAVIGATION_DATA_JSON);
        } catch (IOException exception) {
            throw new IllegalStateException("nincs meg a file: " + NAVIGATION_DATA_JSON);
        }
    }

    public Path updateNavigationData() throws JsonProcessingException {
        String dataJson = downLoadAndSaveNavigationData();
        createTree(dataJson);
        return NAVIGATION_DATA_JSON;
    }

    public static final Path NAVIGATION_DATA_JSON = Path.of("c:\\temp\\NavigationData.json");
//    Files.createTempDirectory("betfair_aping_temp").resolve("NavigationData.json");

    private String downLoadAndSaveNavigationData() {
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

    public static class rawChild {

        public String type;
        public String name;
        public String id;
        @Deprecated
        public String exchangeId;
        public String marketType;
        public Date marketStartTime;
        public String numberOfWinners;
        public String countryCode;
        public List<rawChild> children;
        public String venue;
        public Date startTime;
        public String raceNumber;

    }

    public void createTree(String dataJson) throws JsonProcessingException {

        rawChild rootJson = om.readValue(dataJson, rawChild.class);

        NavigationDataBase.clearLists();

        bejaras(rootJson, root, 0);

//        allEvent.forEach(event -> event.getMarkets()
//                .forEach(market -> market.setEvent(event)));
        for (var event : EVENTS)
            for (var market : event.getMarkets())
                market.setEvent(event);

    }

    private void add(Child o, Group g) {
        switch (o) {
            case EventType eventType -> eventType.getGroups().add(g);
            case Group group -> group.getGroups().add(g);
            case Event event -> event.getGroups().add(g);
            case null, default -> throw new IllegalStateException("Az EventType nem lehet mas");
        }
    }

    private void add(Child o, Event e) {
        switch (o) {
            case EventType eventType -> eventType.getEvents().add(e);
            case Group group -> group.getEvents().add(e);
            case Event event -> event.getEvents().add(e);
            case null, default -> throw new IllegalStateException("Az Event nem lehet mas");
        }
    }

    private void add(Child o, Market m) {
        switch (o) {
            case Event event -> event.getMarkets().add(m);
            case Race race -> race.getMarkets().add(m);
            case null, default -> throw new IllegalStateException("Az Market nem lehet mas");
        }
    }

    private void bejaras(rawChild root, Child o, int depth) {

        Child nd = null;
        switch (root.type) {
            case "EVENT_TYPE" -> {
                if (root.id.equals("7") && root.name.equals("Horse Racing") && HORSE_RACING_OFF)
                    break;
                nd = new EventType(depth,root.id, root.name);
                ((Root) o).getEventTypes().add((EventType) nd);
                EVENT_TYPES.add((EventType) nd);
            }
            case "GROUP" -> {
                if (root.name.equals("ROOT")) {
                    nd = o;
                    break;
                }
                nd = new Group(depth,root.id, root.name);
                add(o, (Group) nd);
                GROUPS.add((Group) nd);
            }
            case "EVENT" -> {
                nd = new Event(depth,root.id, root.name, root.countryCode);
                add(o, (Event) nd);
                EVENTS.add((Event) nd);
            }
            case "RACE" -> {
                if (HORSE_RACING_OFF)
                    break;
                nd = new Race(depth,root.id, root.name, root.venue, root.startTime, root.raceNumber, root.countryCode);
                ((EventType) o).getRaces().add((Race) nd);
                RACES.add((Race) nd);
            }
            case "MARKET" -> {
                nd = new Market(depth,root.id, root.name, root.marketStartTime, root.marketType, root.numberOfWinners);
                add(o, (Market) nd);
                MARKETS.add((Market) nd);
            }
            default -> throw new IllegalStateException("A root(child) nem lehet mas");
        }

        if (nd != null)
            nd.parent = o;
        if ((o = nd) == null) return;


        if (root.children != null)
            for (rawChild c : root.children)
                bejaras(c, o, depth + 1);

    }

}
