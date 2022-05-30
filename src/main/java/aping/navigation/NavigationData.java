package aping.navigation;

import aping.enums.Endpoint;
import aping.util.HttpUtil;
import aping.util.JsonMapper;
import lombok.Data;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;


@Log4j2
@Component
@RequiredArgsConstructor
public class NavigationData {

    @NonNull
    HttpUtil httpUtil;

    private static final boolean HORSE_RACING_OFF = true;

    @Getter
    private LocalDateTime lastUpdateTime;

    private final Root root = new Root();

    private static final List<Market> MARKETS = new ArrayList<>(25_000);
    private static final List<Event> EVENTS = new ArrayList<>(2_000);
    private static final List<EventType> EVENT_TYPES = new ArrayList<>(50);
    private static final List<Group> GROUPS = new ArrayList<>(600);
    private static final List<Race> RACES = new ArrayList<>(600);


    static void clearLists() {
        MARKETS.clear();
        EVENTS.clear();
        EVENT_TYPES.clear();
        GROUPS.clear();
        RACES.clear();
    }

    public static String getSizeOfLists() {
        return "allEvent: " + EVENTS.size() + "\n" +
                "allEventType: " + EVENT_TYPES.size() + "\n" +
                "allGroups: " + GROUPS.size() + "\n" +
                "allRace: " + RACES.size() + "\n" +
                "allMarket: " + MARKETS.size() + "\n";
    }

    public String getAllData() {
        return getDataUntil(Integer.MAX_VALUE);
    }

    public String getDataUntil(int deep) {
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

    public Path updateNavigationData() {
        String dataJson = downLoadAndSaveNavigationData();

        createTree(dataJson);
        return NAVIGATION_DATA_JSON;
    }

    public static final Path NAVIGATION_DATA_JSON = Path.of("c:\\temp\\NavigationData.json");
//    Files.createTempDirectory("betfair_aping_temp").resolve("NavigationData.json");

    private String downLoadAndSaveNavigationData() {
        try {
            String dataJson = httpUtil.sendPostRequest(null, null, Endpoint.NAVIGATION);
            Files.writeString(NAVIGATION_DATA_JSON, dataJson);
            lastUpdateTime = LocalDateTime.now();
            return dataJson;
        } catch (IOException e) {
            log.error("Nem sikerült kiírni a file-ba a NAVIGATION DATA-t", e);
            return null;
        }
    }

    @Data
    public static class RawObject {

        private String type;
        private String name;
        private String id;
        @Deprecated(since = "régen")
        private String exchangeId;
        private String marketType;
        private LocalDateTime marketStartTime;
        private String numberOfWinners;
        private String countryCode;
        private List<RawObject> children;
        private String venue;
        private LocalDateTime startTime;
        private String raceNumber;

    }

    public void createTree(String dataJson) {

        RawObject rootJson = new JsonMapper().readValue(dataJson, RawObject.class);

        clearLists();

        bejaras(rootJson, root, 0);

        EVENTS.forEach(event -> event.getMarkets()
                .forEach(market -> market.setEvent(event)));

    }

    private void add(Node o, Group g) {
        switch (o) {
            case EventType eventType -> eventType.getGroups().add(g);
            case Group group -> group.getGroups().add(g);
            case Event event -> event.getGroups().add(g);
            default -> throw new IllegalStateException("Az EventType nem lehet más");
        }
    }

    private void add(Node o, Event e) {
        switch (o) {
            case EventType eventType -> eventType.getEvents().add(e);
            case Group group -> group.getEvents().add(e);
            case Event event -> event.getEvents().add(e);
            default -> throw new IllegalStateException("Az Event nem lehet más");
        }
    }

    private void add(Node o, Market m) {
        switch (o) {
            case Event event -> event.getMarkets().add(m);
            case Race race -> race.getMarkets().add(m);
            default -> throw new IllegalStateException("Az Market nem lehet más");
        }
    }

    private void bejaras(RawObject root, Node o, int depth) {

        Node nd = null;
        switch (root.type) {
            case "EVENT_TYPE" -> {
                if (root.id.equals("7") && root.name.equals("Horse Racing") && HORSE_RACING_OFF)
                    break;
                nd = new EventType(depth, root.id, root.name);
                ((Root) o).getEventTypes().add((EventType) nd);
                EVENT_TYPES.add((EventType) nd);
            }
            case "GROUP" -> {
                if (root.name.equals("ROOT")) {
                    nd = o;
                    break;
                }
                nd = new Group(depth, root.id, root.name);
                add(o, (Group) nd);
                GROUPS.add((Group) nd);
            }
            case "EVENT" -> {
                nd = new Event(depth, root.id, root.name, root.countryCode);
                add(o, (Event) nd);
                EVENTS.add((Event) nd);
            }
            case "RACE" -> {
                if (HORSE_RACING_OFF)
                    break;
                nd = new Race(depth, root.id, root.name, root.venue, root.startTime, root.raceNumber, root.countryCode);
                ((EventType) o).getRaces().add((Race) nd);
                RACES.add((Race) nd);
            }
            case "MARKET" -> {
                nd = new Market(depth, root.id, root.name, root.marketStartTime, root.marketType, root.numberOfWinners);
                add(o, (Market) nd);
                MARKETS.add((Market) nd);
            }
            default -> throw new IllegalStateException("A root(child) nem lehet más");
        }

        if (nd != null)
            nd.parent = o;
        if ((o = nd) == null) return;


        if (root.children != null)
            for (RawObject c : root.children)
                bejaras(c, o, depth + 1);

    }


    public static Stream<Market> markets() {
        return MARKETS.stream();
    }

    public static Stream<Event> events() {
        return EVENTS.stream();
    }

    public static Stream<EventType> eventTypes() {
        return EVENT_TYPES.stream();
    }

    public static Stream<Race> races() {
        return RACES.stream();
    }

    public static Stream<Group> groups() {
        return GROUPS.stream();
    }

}
