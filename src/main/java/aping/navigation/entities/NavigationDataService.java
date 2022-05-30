package aping.navigation.entities;

import aping.enums.Endpoint;
import aping.util.HttpUtil;
import aping.util.JsonMapper;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.hibernate.annotations.SQLInsert;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;


@Log4j2
@Component
@RequiredArgsConstructor
public class NavigationDataService {

    @NonNull
    HttpUtil httpUtil;

    @NonNull
    NavigationDataRawObjectRepository rawObjectRepository;

    @NonNull
    NavigationDataNodeRepository rootRepository;

    @NonNull
    JsonMapper jsonMapper;

    @Value("${aping.HORSE_RACING_OFF}")
    private static final boolean HORSE_RACING_OFF = true;

    private final Root root = new Root();


    @Transactional
    public void saveAll() {
        RawObjectDto rootJson = new JsonMapper().readValue(getNavigationDataFromFile(), RawObjectDto.class);
        rawObjectRepository.save(new ModelMapper().map(rootJson, RawObject.class));
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

    @Transactional
    public void updateNavigationData() {
        saveTree(downLoadAndSaveNavigationData());
    }

    public static final Path NAVIGATION_DATA_JSON = Path.of("c:\\temp\\NavigationData.json");

    private String downLoadAndSaveNavigationData() {
        try {
            String dataJson = httpUtil.sendPostRequest(null, null, Endpoint.NAVIGATION);
            Files.writeString(NAVIGATION_DATA_JSON, dataJson);
            return dataJson;
        } catch (IOException e) {
            log.error("Nem sikerült kiírni a file-ba a NAVIGATION DATA-t", e);
            return null;
        }
    }

    @Transactional
    @SQLInsert(sql = "INSERT INTO aping.event_types(id, name) VALUES (233232,'semmi')")
    public void saveTree(String dataJson) {
        rootRepository.deleteAllFromAllTable();
        RawObjectDto rootJson = jsonMapper.readValue(dataJson, RawObjectDto.class);
        bejaras(rootJson, root, 0);
    }


    private void bejaras(RawObjectDto root, Node o, int depth) {

        Node nd = null;
        switch (root.type()) {
            case "EVENT_TYPE" -> {
                if (root.id().equals("7") && root.name().equals("Horse Racing") && HORSE_RACING_OFF)
                    break;
                nd = new EventType(depth, root.id(), root.name());
                ((Root) o).getEventTypes().add((EventType) nd);
            }
            case "GROUP" -> {
                if (root.name().equals("ROOT")) {
                    nd = o;
                    break;
                }
                nd = new Group(depth, root.id(), root.name());
                add(o, (Group) nd);
            }
            case "EVENT" -> {
                nd = new Event(depth, root.id(), root.name(), root.countryCode());
                add(o, (Event) nd);
            }
            case "RACE" -> {
                if (HORSE_RACING_OFF)
                    break;
                nd = new Race(depth, root.id(), root.name(), root.venue(), root.startTime(), root.raceNumber(), root.countryCode());
                ((EventType) o).getRaces().add((Race) nd);
            }
            case "MARKET" -> {
                nd = new Market(depth, root.id(), root.name(), root.marketStartTime(), root.marketType(), root.numberOfWinners());
                add(o, (Market) nd);
            }
            default -> throw new IllegalStateException("A root(child) nem lehet más");
        }

        if ((o = nd) == null) return;

        if (!o.id.equals("root")) {
            rootRepository.save(o);
            rootRepository.save(nd);
        }

        if (root.children() != null)
            for (RawObjectDto c : root.children())
                bejaras(c, o, depth + 1);

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
