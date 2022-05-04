package navigation.entities;

import com.fasterxml.jackson.core.JsonProcessingException;
import enums.Endpoint;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import lombok.extern.log4j.Log4j2;
import util.HttpUtil;
import util.JsonMapper;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;


@Log4j2
public class NavigationDataRepository {

    private static final boolean HORSE_RACING_OFF = true;

    private final Root root = new Root();

    EntityManagerFactory factory;

    public NavigationDataRepository(EntityManagerFactory factory) {
        this.factory = factory;
    }

    public void saveAll() throws JsonProcessingException {
        RawObjectDto rootJson = new JsonMapper().readValue(getNavigationDataFromFile(), RawObjectDto.class);

        EntityManager manager = factory.createEntityManager();
        manager.getTransaction().begin();

        manager.persist(rootJson);

        manager.getTransaction().commit();
        manager.close();
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

    public Path updateNavigationData() throws JsonProcessingException {
        String dataJson = downLoadAndSaveNavigationData();

        saveTree(dataJson);
        return NAVIGATION_DATA_JSON;
    }

    public static final Path NAVIGATION_DATA_JSON = Path.of("c:\\temp\\NavigationData.json");
//    Files.createTempDirectory("betfair_aping_temp").resolve("NavigationData.json");

    private String downLoadAndSaveNavigationData() {
        try {
            String dataJson = new HttpUtil().sendPostRequest(null, null, Endpoint.NAVIGATION);
            Files.writeString(NAVIGATION_DATA_JSON, dataJson);
            return dataJson;
        } catch (IOException e) {
            log.error("Nem sikerült kiírni a file-ba a NAVIGATION DATA-t", e);
            return null;
        }
    }


    public void saveTree(String dataJson) throws JsonProcessingException {

        RawObjectDto rootJson = new JsonMapper().readValue(dataJson, RawObjectDto.class);

        EntityManager manager = factory.createEntityManager();
        manager.createQuery("select e.events from EventType e");
//        manager.createNativeQuery(SET FOREIGN_KEY_CHECKS = 0 ;);
        manager.getTransaction().begin();

        bejaras(rootJson, root, 0, manager);

        manager.getTransaction().commit();
//        manager.createNativeQuery("SET FOREIGN_KEY_CHECKS = 1 ;");
        manager.close();

    }


    private void bejaras(RawObjectDto root, Node o, int depth, EntityManager manager) {

        Node nd = null;
        switch (root.type()) {
            case "EVENT_TYPE" -> {
                if (root.id().equals("7") && root.name().equals("Horse Racing") && HORSE_RACING_OFF)
                    break;
                nd = new EventType(depth, root.id(), root.name());
                ((Root) o).getEventTypes().add((EventType) nd);
//                EVENT_TYPES.add((EventType) nd);
            }
            case "GROUP" -> {
                if (root.name().equals("ROOT")) {
                    nd = o;
                    break;
                }
                nd = new Group(depth, root.id(), root.name());
                add(o, (Group) nd);
//                GROUPS.add((Group) nd);
            }
            case "EVENT" -> {
                nd = new Event(depth, root.id(), root.name(), root.countryCode());
                add(o, (Event) nd);
//                EVENTS.add((Event) nd);
            }
            case "RACE" -> {
                if (HORSE_RACING_OFF)
                    break;
                nd = new Race(depth, root.id(), root.name(), root.venue(), root.startTime(), root.raceNumber(), root.countryCode());
                ((EventType) o).getRaces().add((Race) nd);
//                RACES.add((Race) nd);
            }
            case "MARKET" -> {
                nd = new Market(depth, root.id(), root.name(), root.marketStartTime(), root.marketType(), root.numberOfWinners());
                add(o, (Market) nd);
//                MARKETS.add((Market) nd);
            }
            default -> throw new IllegalStateException("A root(child) nem lehet más");
        }

        if (nd != null && !o.id.equals("root")) {
//            nd.parent = o;
            manager.persist(o);
            manager.persist(nd);
        }
        if ((o = nd) == null) return;


        if (root.children() != null)
            for (RawObjectDto c : root.children())
                bejaras(c, o, depth + 1, manager);

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

}
